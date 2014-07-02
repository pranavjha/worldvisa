framework.addScreen({
    _criteria:{},
	load : function(hashTokens, callback) {
		framework.getScreen()._initialize();
		callback.call(this);
	},
	_initialize : function() {
		if(framework.isActiveScreenModal()){
			$('#button-select-template').show();
			$('#button-add-template').hide();
			$('#templateListTable').dataTable ({
				columns:[
				{
				    displayName:'-',
				    colwidth:'5%',
				    columnData:function(row){
				    	return $('<div/>').append(
					    	$('<input type="checkbox" style="float:left;margin:2px 5px;" name="selected-contacts" id="selected-contacts" value="'+row.seqNum+'"/>')
		    			);
				    }
				},{
				    displayName:'Type',
				    colwidth:'10%',
				    columnData:function(row){
				    	return $('<div>').html(framework.lookupMap[row.actionType].lookupDescription);
				    },
				    sortVal:'ACTION_TYPE'
				},{
				    displayName:'Created on',
				    colwidth:'15%',
				    columnData:'sentTime',
				    sortVal:'SENT_TIME',
				    format:"date"
				},{
				   displayName:'Template Content',
				   columnData:function(row){
			        	var _subject = $.trim(row.mailSubject);
			        	var _element = $('<div style="width:100%">'+(_subject.length == 0?'(no - subject)':_subject)+'</div>');
			        	_element.tooltip({
			        		bodyHandler: function() { 
						        return row.mailBody; 
						    },
						    track: true, 
						    delay: 0, 
						    showURL: false, 
						    fade: 250
			        	});
			        	return _element;
				   },
				   colwidth:'70%'
	        	}],
				rowCountFunction:ListTemplatesView.getCommunicationListCount,
				fetcherFunction:ListTemplatesView.getCommunicationList,
				beforeSend:function(data) {
					return $.extend({}, data, framework.getScreen()._criteria);
				},
				defaultSort:2
			});
		} else {
			$('#button-select-template').hide();
			$('#button-add-template').show();
			$('#templateListTable').dataTable ({
				columns:[
				{
				    displayName:'Type',
				    colwidth:'6%',
				    columnData:function(row){
				    	return $('<div>').html(framework.lookupMap[row.actionType].lookupDescription);
				    },
				    sortVal:'ACTION_TYPE'
				},{
				    displayName:'Created on',
				    colwidth:'10%',
				    columnData:'sentTime',
				    sortVal:'SENT_TIME',
				    format:"date"
				},{
				   displayName:'Template Content',
				   columnData:function(row){
			        	var _subject = $.trim(row.mailSubject);
			        	var _element = $('<div style="width:100%">'+(_subject.length == 0?'(no - subject)':_subject)+'</div>');
			        	_element.tooltip({
			        		bodyHandler: function() { 
						        return row.mailBody; 
						    },
						    track: true, 
						    delay: 0, 
						    showURL: false, 
						    fade: 250
			        	});
			        	return _element;
				   },
				   colwidth:'77%'
	        	},{
				   displayName:'--',
			        columnData:function(row){
			        	return $('<div/>').append(
	        				$('<button>Edit Template</button>').button({
				                icons: {
				                    primary: "ui-icon-pencil"
				                },
				                text: false
	        				}).on('click', function(){
	        					window.location.hash='market/AddTemplate/'+row.seqNum;
	        				})
	    				).append(
	        				$('<button>Delete Template</button>').button({
				                icons: {
				                    primary: "ui-icon-trash"
				                },
				                text: false
	        				}).bind('click', function(){
	        					ListTemplatesView.deleteCommunication({seqNum:row.seqNum}, function(){
	        		            	$('#templateListTable').data('dataTable').reload('true');
	        					});
	        				})
	    				);
	    		    },
				   colwidth:'7%'
				}],
				rowCountFunction:ListTemplatesView.getCommunicationListCount,
				fetcherFunction:ListTemplatesView.getCommunicationList,
				beforeSend:function(data) {
					return $.extend({}, data, framework.getScreen()._criteria);
				},
				defaultSort:1
			});
		}
	},
	search:function(){
		framework.getScreen()._criteria = Form.pull($('#search-form'));
		$('#templateListTable').data('dataTable').reload('true');
	},
	selectTemplates:function(){
		var selectedIds = $('#templateListTable').attribute('value');
		if(selectedIds == null || selectedIds.length == 0){
			$.jGrowl('ERROR', 'Please select at lease one template.');
			return;
		}
		if(framework.isActiveScreenModal()){
			framework.removeScreen();
		}
		$('#inline-communicationsListSelection').dialog('close').dialog('destroy').remove();
		framework.getScreen().sendSelectedCommunications(selectedIds);
	}
});