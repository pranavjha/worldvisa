framework.addScreen({
    _criteria:{},
    _selectedGroup:null,
	load : function(hashTokens, callback) {
		framework.getScreen()._initialize();
		callback.call(this);
	},
	_initialize : function() {
		$('#userGroupListTable').dataTable ({
			columns:[
			{
			    displayName:'Group Name',
			    colwidth:'15%',
			    columnData:function(row){
			    	return $('<div>').html('<a href="#market/ListContacts/'+row.userGroupId+'">'+row.userGroupName+'</a>');
			    },
			    sortVal:'USER_GROUP_NAME'
			},{
			    displayName:'Branch Office',
			    colwidth:'15%',
			    columnData:function(row){ 
	    			return $('<div>').html(framework.lookupMap[row.branchOffice].lookupDescription);
		    	},
			    sortVal:'BRANCH_OFFICE'
			},{
			    displayName:'Service Type',
			    colwidth:'15%',
			    columnData:function(row){ 
	    			return $('<div>').html(framework.lookupMap[row.serviceType].lookupDescription);
		    	},
			    sortVal:'SERVICE_TYPE'
			},{
			    displayName:'Added On',
			    colwidth:'15%',
			    columnData:'addedOn',
			    sortVal:'ADDED_ON',
			    format:'timestamp'
			},{
			    displayName:'Added By',
			    colwidth:'15%',
			    columnData:'addedBy',
			    sortVal:'ADDED_BY'
			},{
			   displayName:'--',
		        columnData:function(row){
		        	var _div = $('<div/>');
		        	_div.append(
        				$('<button>Communicate</button>').button({
			                icons: {
			                    primary: "ui-icon-mail-closed"
			                },
			                text: false
        				}).bind('click', function(){
        					framework.getScreen()._selectedGroup = row.userGroupId;
        					_dialog = $('<div id="inline-communicationsListSelection"></div>');
        			    	_dialog.appendTo($('body')).dialog({
        						resizable : false,
        						autoOpen : true,
        						modal : true,
        						width : 800,
        						beforeClose: function(event, ui) {
        							if(framework.isActiveScreenModal()){
        								framework.removeScreen();
        							}
        						}
        					});
        			    	framework.loadView(_dialog, 'market/ListTemplates');
        				})
    				).append(
        				$('<button>Add Status</button>').button({
			                icons: {
			                    primary: "ui-icon-plus"
			                },
			                text: false
        				}).on('click', function(){
        					Form.clear($('#add-group-status-dialog-form'));
        					$('#add-group-status-dialog').data('userGroupId', row.userGroupId).dialog('open');
        				})
    				).append(
	    				$('<button>View Followup History</button>').button({
			                icons: {
			                    primary: "ui-icon-clock"
			                },
			                text: false
	    				}).bind('click', function(){
        					$('#view-group-followup-history-dialog').dialog('open');
        					$('#group-followup-history-table').dataTable('destroy');
        					$('#group-followup-history-table').dataTable({
        						pagenate:false,
        						fetcherFunction:ListGroupsView.showFollowupHistory,
        						beforeSend: function(data){
        							return row;
        						},
    							columns:[
    						         {
    						 	        displayName:'Set By',
    							        columnData:'addedBy',
    							        colwidth:'15%'
    						         },
    						         {
     						 	        displayName:'Set On',
     							        columnData:'addedOn',
     							        colwidth:'15%',
     							        format:'timestamp'
     						         },
    						         {
     						 	        displayName:'Communication',
    							        columnData:function(row){
    							        	if(row.communication) {
	    							        	var _subject = $.trim(row.communication.mailSubject);
	    							        	var _element = $('<div style="width:100%">'+(_subject.length == 0?'(no - subject)':_subject)+'</div>');
	    							        	_element.tooltip({
	    							        		bodyHandler: function() { 
	    										        return row.communication.mailBody; 
	    										    },
	    										    track: true, 
	    										    delay: 0, 
	    										    showURL: false, 
	    										    fade: 250
	    							        	});
	    							        	return _element;
    							        	} else {
    							        		return $('<span>-</span>');
    							        	}
    							        },
     							        colwidth:'15%'
     						         },
    						         {
    						 	        displayName:'Description',
    							        columnData:'description',
    							        colwidth:'40%'
    						         },
    						         {
    						 	        displayName:'Sub-status',
    							        columnData:function(row){
    							        	return $('<span>'+framework.lookupMap[row.substatus].lookupDescription+'</span>');
    							        },
    							        colwidth:'15%'
    						         }
						         ]
        					});
        				})    				
    				).append(
						$('<button>Delete Group</button>').button({
			                icons: {
			                    primary: "ui-icon-trash"
			                },
			                text: false
	    				}).on('click', function(){
	    					framework.getScreen().deleteGroup(row.userGroupId);
	    				})
    				);
					return _div;
    		    },
			   colwidth:'17%'
			}],
			rowCountFunction:ListGroupsView.listUserGroupsCount,
			fetcherFunction:ListGroupsView.listUserGroups,
			beforeSend:function(data) {
				return $.extend({}, data, framework.getScreen()._criteria);
			},
			defaultSort:0
		});
	},
	deleteGroup:function(groupId){
		Form.submit({
			repopulate : false,
            beforeSubmit : function(data){
                return {userGroupId:groupId};
            },
            success : function(data){
        		$('#userGroupListTable').data('dataTable').reload('true');
            },
            dwrMethod : ListGroupsView.deleteGroup,
            sectionId : '-undefined-form'
		});
	},
	addStatus:function(){
		Form.submit({
			repopulate : false,
            beforeSubmit : function(data){
                return $.extend({userGroupId:$('#add-group-status-dialog').data('userGroupId')}, data);
            },
            success : function(data){
            	$('#add-group-status-dialog').dialog('close');
            },
            dwrMethod : ListGroupsView.addStatus,
            sectionId : 'add-group-status-dialog-form'
		});
	},
	search:function(){
		framework.getScreen()._criteria = Form.pull($('#search-form'));
		$('#userGroupListTable').data('dataTable').reload('true');
	},
	communicate:function(isAll){
		$("div.jGrowl").jGrowl("close");
		if(isAll == true){
			framework.getScreen()._selectedUsers = null;
		} else {
			framework.getScreen()._selectedUsers = $('#contactsListTable').attribute('value');
			if(framework.getScreen()._selectedUsers == null || framework.getScreen()._selectedUsers.length == 0){
				$.jGrowl('ERROR', 'Please select at lease one user.');
				return;
			}
		}
		if($('#inline-communicationsListSelection').size() > 0) {
			$('#inline-communicationsListSelection').dialog('close').dialog('destroy').remove();
		}
		_dialog = $('<div id="inline-communicationsListSelection"></div>');
    	_dialog.appendTo($('body')).dialog({
			resizable : false,
			autoOpen : true,
			modal : true,
			width : 800,
			beforeClose: function(event, ui) {
				if(framework.isActiveScreenModal()){
					framework.removeScreen();
				}
			}
		});
    	framework.loadView(_dialog, 'market/ListTemplates');
	},
	sendSelectedCommunications: function(selectedCommunicationIds){
		ListGroupsView.sendCommunications(framework.getScreen()._selectedGroup, selectedCommunicationIds, 
			function(data){
				if(data.messages){
	            	$.each(data.messages, function(index, message){
	            		$.jGrowl(message.severity, message.message);
	            	});
	            }
			}
		);
		if(framework.isActiveScreenModal()){
			framework.removeScreen();
		}
	}
});