framework.addScreen({
    _criteria:{},
	load : function(hashTokens, callback) {
		framework.getScreen()._initialize();
		callback.call(this);
	},
	_initialize : function() {
		if(framework.getUser().userRole != 11002) {
			$('#export-to-excel').hide();
		}
		$('#myAssignmentsListTable').dataTable ({
			columns:[
			{
			    displayName:'SLA/R',
			    colwidth:'5%',
			    columnData:function(row){
			    	return $('<div/>').append(
				    	$('<span>').addClass('ui-icon').addClass(
				    			row.slaStatus == 10401?'ui-icon-circle-close':(row.slaStatus == 10402?'ui-icon-alert':'ui-icon-check')
		    			).attr('title', framework.lookupMap[row.slaStatus].lookupDescription)
		    			.attr('style', 'float:left;margin:0px 5px;')
	    			).append(
    					$('<span>').addClass('ui-icon').addClass(
							row.personalDetails.registrationStatus == 10302?'ui-icon-check':'ui-icon-closethick'
						).attr('title', framework.lookupMap[row.personalDetails.registrationStatus].lookupDescription)
						.attr('style', 'float:left;margin:0px 5px;')
	    			);
			    }
			},{
			   displayName:'Report ID',
			   columnData:function(row){
				   return $('<a href="#request/ReportDetails/'+row.reportId+'">'+row.reportId+'</a>');
			   },
			   colwidth:'8%',
			   sortVal:'REPORT_ID'
			},{
			   displayName:'Customer Details',
			   columnData:function(row){
			    	return $('<div style="width:100%">'+row.personalDetails.name+'</div>').tooltip({
			    		bodyHandler: function() { 
			    			return '<table class="form">'+
			    			'<tr><th nowrap="nowrap">Customer Email</th><td nowrap="nowrap">'+row.raisedFor+'</td></tr>'+
			    			'<tr><th nowrap="nowrap">Cellphone</th><td nowrap="nowrap">'+row.personalDetails.cellphone+'</td></tr>'+
			    			'<tr><th nowrap="nowrap">City</th><td nowrap="nowrap">'+row.personalDetails.city+'</td></tr>'+
			    			'</table>'; 
					    },
					    track: true, 
					    delay: 0, 
					    showURL: false, 
					    fade: 250
			    	});
			   },
			   colwidth:'24%',
			   sortVal:'NAME'
			},{
			   displayName:'Request Type',
			   columnData:function(row){
			    	return $('<div>').html(framework.lookupMap[row.requestType].lookupDescription);
			    },
			   colwidth:'20%',
			   sortVal:'REQUEST_TYPE'
			},{
			   displayName:'Request Mode',
			   columnData:function(row){
			    	return $('<div>').html(framework.lookupMap[row.requestMode].lookupDescription);
			   },
			   colwidth:'10%',
			   sortVal:'REQUEST_MODE'
			},{
			   displayName:'Assigned To',
			   columnData:"assignedTo",
			   colwidth:'15%',
			   sortVal:'ASSIGNED_TO'
			},{
			   displayName:'Raised Time',
			   columnData:'raisedDt',
			   colwidth:'15%',
			   sortVal:'RAISED_DT',
			   format: 'timestamp'
			}],
			rowCountFunction:MyAssignmentsView.getMyAssignmentsListCount,
			fetcherFunction:MyAssignmentsView.getMyAssignmentsList,
			defaultSort:6,
			sortOrder:'DESC',
			beforeSend:function(data) {
				return $.extend({}, data, framework.getScreen()._criteria);
			},
			rowSelectable:true,
			rowSelectId:"reportId"
		});
	},
	assign:function(){
		$("div.jGrowl").jGrowl("close");
		var _assignTo = $('#assign-to').val();
		var _requestList = $('#myAssignmentsListTable').attribute('value');
		if(_requestList == null || _requestList.length == 0){
			$.jGrowl('error', 'Please select at lease one request to assign');
		} else {
			MyAssignmentsView.assign(_assignTo, _requestList, function(data){
				if(data.messages){
	            	$.each(data.messages, function(index, message){
	            		$.jGrowl(message.severity, message.message);
	            	});
	            }
				$('#myAssignmentsListTable').data('dataTable').reload('true');
			});
		}
	},
	search:function(){
		framework.getScreen()._criteria = Form.pull($('#search-form'));
		$('#myAssignmentsListTable').data('dataTable').reload('true');
	},
	exportToExcel:function($button){
		MyAssignmentsView.exportToExcel(framework.getScreen()._criteria, function(data){
			dwr.engine.openInDownload(data);
		});
	}
});