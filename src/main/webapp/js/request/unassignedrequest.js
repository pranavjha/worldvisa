framework.addScreen({
	load : function(hashTokens, callback) {
		framework.getScreen()._initialize();
		callback.call(this);
	},
	_initialize : function() {
		if(framework.getUser().userRole != 11002) {
			$('#export-to-excel').hide();
		}
		$('#unassignedRequestListTable').dataTable ({
			columns:[
			{
			    displayName:'SLA/Reg',
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
			   sortVal:'REQUEST_TYPE'
			},{
			   displayName:'Raised By',
			   columnData:"raisedBy",
			   colwidth:'15%',
			   sortVal:'RAISED_BY'
			},{
			   displayName:'Raised Time',
			   columnData:'raisedDt',
			   colwidth:'15%',
			   sortVal:'RAISED_DT',
			   format: 'timestamp'
			}],
			rowCountFunction:UnassignedRequestView.getUnassignedRequestListCount,
			fetcherFunction:UnassignedRequestView.getUnassignedRequestList,
			defaultSort:6,
			sortOrder:'DESC',
			rowSelectable:true,
			rowSelectId:"reportId"
		});
	},
	lockRequests:function(){
		$("div.jGrowl").jGrowl("close");
		var _assignTo = $('#assign-to').val();
		var _requestList = $('#unassignedRequestListTable').attribute('value');
		if(_requestList == null || _requestList.length == 0){
			$.jGrowl('error', 'Please select at lease one request to assign');
		} else {
			UnassignedRequestView.assign(_assignTo, _requestList, function(data){
				if(data.messages){
	            	$.each(data.messages, function(index, message){
	            		$.jGrowl(message.severity, message.message);
	            	});
	            }
				$('#unassignedRequestListTable').data('dataTable').reload('true');
			});
		}
	},
	exportToExcel:function($button){
		UnassignedRequestView.exportToExcel(function(data){
			dwr.engine.openInDownload(data);
		});
	}
});