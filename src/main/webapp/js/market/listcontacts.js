framework.addScreen({
    _criteria:{},
	load : function(hashTokens, callback) {
		ListContactsView.getAllUserGroups(function(data){
			var _select = $('#userGroupIdOptions').parent('select');
			$('#userGroupIdOptions').remove();
			$.each(data, function(index, val){
				_select.append('<option value="'+val.userGroupId+'">'+val.userGroupName +
						' ( '+framework.lookupMap[val.serviceType].lookupDescription+' - '+framework.lookupMap[val.branchOffice].lookupDescription+' ) </option>');
			});
			if(_select.data('loadValue')){
				_select.attribute('value',_select.data('loadValue'));
			}

		});
		
		framework.getScreen()._initialize(hashTokens[2]);
		callback.call(this);
	},
	exportToExcel:function($button){
		ListContactsView.exportToExcel(framework.getScreen()._criteria, function(data){
		    dwr.engine.openInDownload(data);
		});
	},

	_initialize : function(userGroupId) {
		if(framework.getUser().userRole != 11002) {
			$('#export-to-excel').hide();
		}
		if(userGroupId){
			$('#userGroupId').attribute('value', userGroupId);
			framework.getScreen()._criteria['userGroupId'] = userGroupId;
		}
		$('#contactsListTable').dataTable ({
			columns:[
			{
			    displayName:'Service',
			    colwidth:'15%',
			    columnData:function(row){
			    	if(row.requestNumber == null){
			    		if(row.serviceType != null){
			    			return $('<div>').html(framework.lookupMap[row.userGroup.serviceType].lookupDescription);
			    		} else {
			    			return $('<div>--</div>');
			    		}
			    	} else {
				    	return $('<div>').html(
				    		'<a href="#request/ReportDetails/'+row.requestNumber+'">'
				    		+ framework.lookupMap[row.userGroup.serviceType].lookupDescription+'</a>'
		    			);
			    	}
			    },
			    sortVal:'serviceType'
			},{
			    displayName:'Name',
			    colwidth:'35%',
			    columnData:function(row){
			    	return $('<div style="width:100%">'+row.name+'</div>').tooltip({
			    		bodyHandler: function() { 
			    			return '<table class="form">'+
			    			((row.email != null)?('<tr><th nowrap="nowrap">e-mail</th><td nowrap="nowrap">'+row.email+'</td></tr>'):'')+
			    			((row.mobile != null)?('<tr><th nowrap="nowrap">Mobile</th><td nowrap="nowrap">'+ row.mobile+'</td></tr>'):'')+
	    					((row.telephoneNo != null)?('<tr><th nowrap="nowrap">Landline</th><td nowrap="nowrap">'+ row.telephoneNo+'</td></tr>'):'')+
							((row.gender != null)?('<tr><th nowrap="nowrap">Gender</th><td nowrap="nowrap">'+ row.gender + '</td></tr>'):'')+
							((row.age != null)?('<tr><th nowrap="nowrap">Age</th><td nowrap="nowrap">'+ row.age+'</td></tr>'):'')+
							((row.dateOfBirth != null)?('<tr><th nowrap="nowrap">Date of Birth</th><td nowrap="nowrap">'+ row.dateOfBirth+'</td></tr>'):'')+
							((row.qualCompleted != null)?('<tr><th nowrap="nowrap">Qualification Completed</th><td nowrap="nowrap">'+ row.qualCompleted + '</td></tr>'):'')+
							((row.desCountries != null)?('<tr><th nowrap="nowrap">Desired Countries</th><td nowrap="nowrap">'+ row.desCountries+'</td></tr>'):'')+
							((row.desCourse != null)?('<tr><th nowrap="nowrap">Desired Course</th><td nowrap="nowrap">'+ row.desCourse+'</td></tr>'):'')+
							((row.specialization != null)?('<tr><th nowrap="nowrap">Specialization</th><td nowrap="nowrap">'+ row.specialization+'</td></tr>'):'')+
							((row.desCollege != null)?('<tr><th nowrap="nowrap">Desired College</th><td nowrap="nowrap">'+ row.desCollege+'</td></tr>'):'')+
			    			'</table>'; 
					    },
					    track: true, 
					    delay: 0, 
					    showURL: false, 
					    fade: 250
			    	});
			    },
			    sortVal:'name'
			},{
			    displayName:'User Group',
			    colwidth:'15%',
			    columnData:'userGroup.userGroupName',
			    sortVal:'userGroupName'
			},{
			    displayName:'Added On',
			    colwidth:'15%',
			    columnData:'addedOn',
			    sortVal:'addedOn',
			    format:'timestamp'
			},{
			   displayName:'--',
		        columnData:function(row){
		        	var _div = $('<div/>');
		        	if(row.userId != null){
		        		_div.append(
            				$('<button>Add Notification and Status</button>').button({
    			                icons: {
    			                    primary: "ui-icon-star"
    			                },
    			                text: false
            				}).bind('click', function(){
	        					Form.clear($('#add-notification-dialog-form'));
	        					$('#add-notification-dialog').data('userId', row.userId+":"+row.name+'('+row.userGroup.userGroupName+')').dialog('open');
            				})
        				).append(
            				$('<button>Edit User</button>').button({
    			                icons: {
    			                    primary: "ui-icon-pencil"
    			                },
    			                text: false
            				}).bind('click', function(){
	        					Form.clear($('#edit-user-dialog-form'));
	        					Form.populate($('#edit-user-dialog-form'), row);
	        					$('#edit-user-dialog').data('_user', row).dialog('open');
            				})
        				);
		        	}
		        	_div.append(
        				$('<button>View Followup History</button>').button({
			                icons: {
			                    primary: "ui-icon-clock"
			                },
			                text: false
        				}).bind('click', function(){
        					$('#view-followup-history-dialog').dialog('open');
        					$('#followup-history-table').dataTable('destroy');
        					$('#followup-history-table').dataTable({
        						pagenate:false,
        						fetcherFunction:ListContactsView.showFollowupHistory,
        						beforeSend: function(data){
        							return row;
        						},
    							columns:[
    						         {
    						 	        displayName:'Set By',
    							        columnData:'addedBy',
    							        colwidth:'20%'
    						         },
    						         {
    						 	        displayName:'Set On',
    							        columnData:'addedOn',
    							        colwidth:'20%',
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
      							        colwidth:'20%'
      						         },{
    						 	        displayName:'Description',
    							        columnData:'description',
    							        colwidth:'20%'
    						         },
    						         {
    						 	        displayName:'Sub-status',
    							        columnData:function(row){
    							        	return $('<span>'+framework.lookupMap[row.substatus].lookupDescription+'</span>');
    							        },
    							        colwidth:'20%'
    						         }
						         ]
        					});
        				})
    				);
					return _div;
    		    },
			   colwidth:'17%'
			}],
			rowCountFunction:ListContactsView.getContactsLength,
			fetcherFunction:ListContactsView.getContacts,
			beforeSend:function(data) {
				return $.extend({}, data, framework.getScreen()._criteria);
			},
			defaultSort:1,

			rowSelectable:true,
			rowSelectId:"userId",
			enabledWhen:function(row) {
				return (row.requestNumber == null);
			}
		});
	},
	addNotification: function(){
		Form.submit({
			repopulate : false,
            beforeSubmit : function(data){
                return $.extend({byUserId:$('#add-notification-dialog').data('userId')}, data);
            },
            success : function(data){
            	$('#add-notification-dialog').dialog('close');
            },
            dwrMethod : ListContactsView.addNotification,
            sectionId : 'add-notification-dialog-form'
		});
	},
	saveUser:function(){
		Form.submit({
			repopulate : false,
            beforeSubmit : function(data){
                return $.extend($('#edit-user-dialog').data('_user'), data);
            },
            success : function(data){
            	$('#edit-user-dialog').dialog('close');
            	$('#contactsListTable').data('dataTable').reload('true');
            },
            dwrMethod : ListContactsView.updateUserDetails,
            sectionId : 'edit-user-dialog-form'
		});
	},
	search:function(){
		framework.getScreen()._criteria = Form.pull($('#search-form'));
		$('#contactsListTable').data('dataTable').reload('true');
	},
	deleteSelectedUser:function(){
		$("div.jGrowl").jGrowl("close");
		var _userIds = $('#contactsListTable').attribute('value');
		if(_userIds == null || _userIds.length == 0){
			$.jGrowl('ERROR', 'Please select at lease one user to delete');
		} else {
			ListContactsView.deleteUsers(_userIds, function(data){
				if(data.messages){
	            	$.each(data.messages, function(index, message){
	            		$.jGrowl(message.severity, message.message);
	            	});
	            }
				$('#contactsListTable').data('dataTable').reload('true');
			});
		}
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
		if(framework.getScreen()._selectedUsers == null){
			ListContactsView.sendCommunicationsToAll(framework.getScreen()._criteria, selectedCommunicationIds, 
				function(data){
					if(data.messages){
		            	$.each(data.messages, function(index, message){
		            		$.jGrowl(message.severity, message.message);
		            	});
		            }
				}
			);
		} else {
			ListContactsView.sendCommunications(framework.getScreen()._selectedUsers, selectedCommunicationIds, 
				function(data){
					if(data.messages){
		            	$.each(data.messages, function(index, message){
		            		$.jGrowl(message.severity, message.message);
		            	});
		            }
				}
			);
		}
		if(framework.isActiveScreenModal()){
			framework.removeScreen();
		}
	}
});