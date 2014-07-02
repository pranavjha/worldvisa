framework.addScreen({
	report:{},
	load : function(hashTokens, callback) {
		var _clientView = (framework.getUser().userRole == null || framework.getUser().userRole == 11004);
		if(hashTokens.length == 3 && hashTokens[2] != 'independent') {
			// report id passed. get the report details
			ReportDetailsView.load(hashTokens[2], {callback:function(data){
				framework.getScreen().report = $.extend({}, data, {
					 countryPreferencesList:[], eduExperienceList:[],
					 workExperienceList:[], relativesAbroadList:[],
					 familyDetailsList:[], langProfeciencieList:[],
					 coursePreferenceList:[], testScoreList:[],
					 reportAttributesMap:{},conversation:[]
				});
				framework.getScreen().setupReportForm({'readonly':(data.editableInd != 1 && _clientView), 'clientView':_clientView}, framework.getScreen()._afterRecievingReport(data));
				callback.call(this);
			}, 
			arg : "#mainContainer"
			});
			return;
		}
		if(hashTokens.length == 3 && hashTokens[2] == 'independent'){
			$('#menuContainer').hide();
			$('#mainContainer').css('top', '0');
		}
		framework.getScreen().setupReportForm({'readonly':false, 'clientView':_clientView});
		// load as create new request form
		callback.call(this);
	},
	setupReportForm: function(screenParams, reportDetails){
		framework.getScreen()._setupGlobalForm(screenParams, reportDetails);
		framework.getScreen()._setupBasicReportForm(screenParams, reportDetails);
		framework.getScreen()._setupPaymentRecordForm(screenParams, reportDetails);
		framework.getScreen()._setupConversationsForm(screenParams, reportDetails);
		framework.getScreen()._setupNotificationsForm(screenParams, reportDetails);
		framework.getScreen()._setupCaseStatusForm(screenParams, reportDetails);
	},
	_setupGlobalForm:function(screenParams, reportDetails){
		$('.buttons-section').show();
		// tabs and button configuration
		if(screenParams['clientView']){
			$('#report-details-jump-menu option[value=notifications]').remove();
		}
		$('#report-details-jump-menu').on('change.main', function(){
			$(this).find('option').each(function(){
				if($(this).is(':selected')){
					$('#'+$(this).attr('value')).show();
					$('#'+$(this).attr('value')+'-button').show();
				} else {
					$('#'+$(this).attr('value')).hide();
					$('#'+$(this).attr('value')+'-button').hide();
				}
			});
		}).trigger('change.main');
		if(reportDetails == null){
			// new request
			$('#report-details-jump-menu-wrap').hide();
			$('.buttons-section #new-request').show();
			$('.buttons-section #view-request').hide();
			$('.buttons-section #edit-request').hide();
			
			$('#report-details-back').on('click', function(){
				var _selected = $('#reportDetailsForm').tabs('option','selected');
				$('#reportDetailsForm').tabs('select', _selected -1);
			});
			$('#report-details-next').on('click', function(){
				if(Form.validate($('#reportDetailsForm .ui-tabs-panel:visible'))){
					$('#reportDetailsForm').tabs('select', $('#reportDetailsForm').tabs('option','selected')+1);				
				}
			});
			$('#report-details-submit').on('click', function(){
				Form.submit({
					sectionId:"reportDetailsForm",
					dwrMethod:ReportDetailsView.createRequest,
					beforeSubmit: function(data){
						return framework.getScreen()._beforeSendingReport(data);
					},
					repopulate:false,
					success:function(data){
						$('#mainContainer').fadeOut();
					}
				});
			});
			$('#report-details-back').button( "option", "disabled", true);
			$('#report-details-next').button( "option", "disabled", false);
			$('#report-details-submit').button( "option", "disabled", true);
		} else {
			// view or edit request
			$('#report-details-jump-menu-wrap').show();
			$('.buttons-section #new-request').hide();
			$('.buttons-section #view-request').show();
			$('#report-details-reload').on('click', function(){
				$(window).trigger('hashchange');
			});
			if(screenParams['readonly']) {
				$('.buttons-section #edit-request').hide();
			} else {
				$('.buttons-section #edit-request').show();
				$('#report-details-update').on('click', function(){
					Form.submit({
						sectionId:"reportDetailsForm",
						dwrMethod:ReportDetailsView.updateRequest,
						beforeSubmit: function(data){
							data = $.extend(true,{}, framework.getScreen().report, data);
							return framework.getScreen()._beforeSendingReport(data);
						},
						repopulate:false,
						success:function(data){
							framework.getScreen().setupReportForm({'readonly':false, 'clientView':(framework.getUser().userRole == 11004)}, framework.getScreen()._afterRecievingReport(data));
						}
					});
					
				});
			}
		}
	},
	_setupBasicReportForm:function(screenParams, reportDetails){
		// enable / disable input fields
		if(screenParams['readonly']){
			$('#reportDetailsForm').find('input[type=text], textarea, select, [class^="jquery-"]').each(
				function(){
					$(this).attribute('disabled', true);
				}
			);
		} else {
			$('#reportDetailsForm').find('input[type=text], textarea, select, [class^="jquery-"]').each(
				function(){
					$(this).attribute('disabled', false);
				}
			);
		}
		
		// add / remove validator to email address
		$('#personalDetails-emailId').validator('destroy');
		$('#personalDetails-emailId').validator({customValidate:function(val){
			if(val == framework.getScreen().report.raisedFor){
				return;
			}
			ReportDetailsView.checkEmail(val, function(data){
				if(data != null){
			    	$.jGrowl(data.severity, data.message);
			    	if(data.severity == 'ERROR'){
			    		$('#personalDetails-emailId').attr('title', data.message).addClass('validation-error');
			    	}
				}
			});
		}});
		
		// editable Ind and request mode configuration
		if(screenParams['clientView']){
			$('#editableInd').attribute('visible', false).attribute('submittable', false);
			if(reportDetails == null){
				$('#requestMode').attribute('value', 10201);
			}
			$('#requestMode').attribute('visible', false).attribute('submittable', true);
		}
		// tabs and button configuration
		if(reportDetails == null){
			$('#reportDetailsForm .ui-tabs-nav').hide();
			$('#reportDetailsForm').bind('tabsselect', function(event, ui){
				$('#report-details-back').button( "option", "disabled", (ui.index == 0));
				$('#report-details-next').button( "option", "disabled", (ui.index == 4));
				$('#report-details-submit').button( "option", "disabled", (ui.index < 2));
			});
		} else {
			$('#reportDetailsForm .ui-tabs-nav').show();
		}
		// attach change handler for request type and marital status
		$('#requestType').off('change.main').on('change.main', function(){
			framework.getScreen()._requestTypeChange($(this).attribute('value'));
		});
		$('#personalDetails-maritalStatus').off('change.main').on('change.main', function(){
			framework.getScreen()._maritalStatusChange($(this).attribute('value'));
		});
		if(reportDetails != null) {
			Form.populate($('#reportDetailsForm'), reportDetails);
			framework.getScreen()._requestTypeChange(reportDetails.requestType, function(){
				Form.populate($('#other-details-form'), reportDetails);
				if(screenParams['readonly']){
					$('#other-details-form').find('input[type=text], textarea, select').each(
						function(){
							$(this).attribute('disabled', true);
						}
					);
				}
			});
			framework.getScreen()._maritalStatusChange(reportDetails['personalDetails']['maritalStatus']);
		} else {
			framework.getScreen()._requestTypeChange(null);
			framework.getScreen()._maritalStatusChange(null);
		}
	},

	_setupPaymentRecordForm:function(screenParams, reportDetails){
		if(screenParams['clientView']){
			$('#add-new-payment-details').hide();
		}
		var paymentForm = $('#payment-record-main-form');
		if(reportDetails != null) {
			var _isEducation = (reportDetails.requestType == 10109 || reportDetails.requestType == 10110);
			Form.populate(paymentForm, reportDetails);
			if(_isEducation){
				paymentForm.find('#numPass').attribute('disabled', true);
				paymentForm.find('#numUniv').attribute('visible', true).attribute('submittable', true);
				paymentForm.find('#numColl').attribute('visible', true).attribute('submittable', true);
			} else {
				paymentForm.find('#numPass').attribute('disabled', (reportDetails.personalDetails.registrationStatus == 10302));
				paymentForm.find('#numUniv').attribute('visible', false).attribute('submittable', false);
				paymentForm.find('#numColl').attribute('visible', false).attribute('submittable', false);
			}
			// if payment options are frozen
			if(reportDetails.personalDetails.registrationStatus == 10302) {
				paymentForm.find('input[type=text], select').attribute('disabled', true);
				paymentForm.find('#freeze-payment').button('option', 'disabled', true);
				paymentForm.find('#freeze-payment .ui-button-text').html('payment options are frozen.');
			} else {
				paymentForm.find('#numPass, #otherServices, #numUniv, #numColl, #paymentOption').off('change.main').on('change.main', function(){
					framework.getScreen()._calcPayableAmount();
				});
				framework.getScreen()._calcPayableAmount();
				paymentForm.find('#payableAmount, #discountAmount').off('change.main').on('change.main', function(){
					$('#netPayableAmount').attribute('value', 
							$('#payableAmount').attribute('value') - $('#discountAmount').attribute('value'));
				});
				paymentForm.find('#freeze-payment').off('click').on('click', function(){
					Form.submit({
							sectionId:"payment-record-main-form",
							dwrMethod:ReportDetailsView.freezePayment,
							beforeSubmit: function(data){
								data = $.extend(true,{}, framework.getScreen().report, data);
								return framework.getScreen()._beforeSendingReport(data);
							},
							repopulate:false,
							success:function(data){
								$(window).trigger('hashchange');
							}
						});
				});
			}
			$('#payment-list-table').dataTable ({
				columns:[{
		 	        displayName:'ID',
			        columnData:'paymentId',
			        colwidth:'10%'
		         },
		         {
		 	        displayName:'Transaction Date',
			        columnData:'transactionDt',
			        colwidth:'10%',
			        format:'date'
		         },
		         {
		 	        displayName:'Mode',
			        columnData:function(row){
			        	return $('<div>').html(framework.lookupMap[row.paymentMode].lookupDescription);
			        },
			        colwidth:'10%'
		         },
		         {
		 	        displayName:'Cheque/DD No',
			        columnData:'chequeDdNo',
			        colwidth:'15%'
		         },
		         {
		 	        displayName:'Amount',
			        columnData:'amount',
			        format: 'currency',
			        colwidth:'10%'
		         },
		         {
		 	        displayName:'Remarks',
			        columnData:'transactionRemarks',
			        colwidth:'35%'
		         },
		         {
		 	        displayName:'-',
			        columnData:function(row){
			        	var _div = $('<div/>');
			        	if(screenParams['clientView'] == false){
			        		_div.append(
			    				$('<button>'+((row.invoiceSentInd == 0)?'Send Receipt':'Resend Receipt')+'</button>').button({
					                icons: {
					                    primary: ((row.invoiceSentInd == 0)?'ui-icon-mail-closed':'ui-icon-mail-open')
					                },
					                text: false
			    				}).bind('click', function(){
			    					ReportDetailsView.sendReceipt(row.paymentId, function(data){
			                            if(data.messages){
			                            	$.each(data.messages, function(index, message){
			                            		$.jGrowl(message.severity, message.message);
			                            	});
			                            }
			                            $('#payment-list-table').data('dataTable').reload('true');
			    					});
			    				})
							).append(
			    				$('<button>Delete Payment Record</button>').button({
					                icons: {
					                    primary: "ui-icon-trash"
					                },
					                text: false
			    				}).bind('click', function(){
			    					ReportDetailsView.deletePayment(row, function(data){
			                            if(data.messages){
			                            	$.each(data.messages, function(index, message){
			                            		$.jGrowl(message.severity, message.message);
			                            	});
			                            }
			                            $('#payment-list-table').data('dataTable').reload('true');
			    					});
			    				})
							);
			        	}
			        	_div.append(
	        				$('<button>Download Receipt</button>').button({
				                icons: {
				                    primary: "ui-icon-clipboard"
				                },
				                text: false
	        				}).bind('click', function(){
	        					ReportDetailsView.downloadReceipt(row.paymentId, function(data){
	        					    dwr.engine.openInDownload(data);
	        					});
	        				})
			        	);
			        	return _div;
        		    },
			        colwidth:'10%'
		         }],
		        fetcherFunction:ReportDetailsView.getPaymentList,
		        pagenate:false,
				beforeSend:function(data) {
					return {reportId:framework.getScreen().report.reportId};
				}
			});
			$('#add-new-payment-details').off('click.main').on('click.main', function(){
				Form.clear($('#add-new-payment-details-dialog-form'));
				$('#add-new-payment-details-dialog').dialog('open');
			});
		}
	},
	addNewPayment:function(){
		Form.submit({
			sectionId:"add-new-payment-details-dialog-form",
			dwrMethod:ReportDetailsView.addPayment,
			beforeSubmit: function(data){
				data = $.extend(data, {reportId:framework.getScreen().report.reportId});
				return data;
			},
			repopulate:false,
			success:function(data){
				$('#add-new-payment-details-dialog').dialog('close');
				$('#payment-list-table').data('dataTable').reload('true');
			}
		});
	},
	_setupConversationsForm:function(screenParams, reportDetails){
		if(reportDetails != null){
			$('#documents-list-table').dataTable ({
				columns:[{
		 	        displayName:'Date',
			        columnData:'uploadedDt',
			        colwidth:'20%',
			        format:'timestamp'
				},
				{
		 	        displayName:'Description',
			        columnData:function(row){
			        	$('#add-new-conversation-dialog #attatchments').append(
			        			'<option value="'+row.fileId+'">'+row.fileDesc+' ('+row.file.fileName+')</option>');
			        	return $('<div>').append(
    						$('<div style="cursor:pointer;color:#2E6E9E">'+row.fileDesc+' ('+row.file.fileName+')'+'</div>').on('click', function(){
    							CommonView.getFile(row.file, function(data){
    								dwr.engine.openInDownload(data);
    							});
		        			})); 
			        },
			        colwidth:'77%'
		        },{
		 	        displayName:'-',
			        columnData:function(row){
						var _div = $('<div/>');
			        	if(row.uploadedBy == framework.getUser().emailId){
				        	_div.append(
		        				$('<button>Delete File</button>').button({
					                icons: {
					                    primary: "ui-icon-trash"
					                },
					                text: false
		        				}).bind('click', function(){
		        					ReportDetailsView.deleteFile(row, function(data){
		                                if(data.messages){
		                                	$.each(data.messages, function(index, message){
		                                		$.jGrowl(message.severity, message.message);
		                                	});
		                                }
		                                $('#add-new-conversation-dialog #attatchments').empty();
	                    				$('#documents-list-table').data('dataTable').reload('true');
		        					});
		        				})
	        				);
			        	} else {
			        		_div.append(
		        				$('<button>Delete File</button>').button({
					                icons: {
					                    primary: "ui-icon-trash"
					                },
					                text: false,
					                disabled: true
		        				})
	        				);
			        	}
			        	return _div;
			        },
			        colwidth:'3%'
		        }],
		        fetcherFunction:ReportDetailsView.getFileList,
		        pagenate:false,
				beforeSend:function(data) {
					return {reportId:framework.getScreen().report.reportId};
				}
			});

			$('#conversations-list-table').dataTable ({
				columns:[{
		 	        displayName:'Date',
			        columnData:'timestamp',
			        colwidth:'20%',
			        format:'timestamp'
		         },
		         {
		 	        displayName:'From',
			        columnData:'sentBy',
			        colwidth:'20%'
		         },
		         {
		 	        displayName:'Files',
			        columnData:function(row){
			        	var _element = $('<div>');
			        	$.each(row.attatchmentFiles, function(index, columnValue){
	        				_element.append(
        						$('<div style="cursor:pointer;color:#2E6E9E;display:inline">'+(index == 0?'':', ')+columnValue.fileName+'</div>').on('click', function(){
        							CommonView.getFile(columnValue, function(data){
        							    dwr.engine.openInDownload(data);
        							});
        						})
    						);
			        	});
			        	return _element;
			        },
			        colwidth:'20%'
		         },
		         {
		 	        displayName:'Mail Content',
			        columnData:function(row){
			        	var _subject = $.trim(row.mailSubject);
			        	var _element = $('<div style="width:100%">'+(_subject.length == 0?'(no - subject)':_subject)+'</div>');
			        	_element.tooltip({
			        		bodyHandler: function() { 
						        return row.remarks; 
						    },
						    track: true, 
						    delay: 0, 
						    showURL: false, 
						    fade: 250
			        	});
			        	return _element;
			        },
			        colwidth:'30%'
		         },
		         {
		 	        displayName:'-',
			        columnData:function(row){
			        	var _div = $('<div/>');
			        	if(row.draftInd == 1 && row.sentBy == framework.getUser().emailId){
				        	_div.append(
		        				$('<button>Edit Conversation</button>').button({
					                icons: {
					                    primary: "ui-icon-pencil"
					                },
					                text: false
		        				}).bind('click', function(){
		        					$('#add-new-conversation-dialog-form').removeData('_conversationId').data('_conversationId', row.conversationId);
		        					Form.populate($('#add-new-conversation-dialog-form'), row);
		        					$('#add-new-conversation-dialog').dialog('open');
		        				})
	        				).append(
	    						$('<button>Send Conversation</button>').button({
					                icons: {
					                    primary:'ui-icon-mail-closed'
					                },
					                text: false
		        				}).bind('click', function(){
		        					ReportDetailsView.addUpdateConversation($.extend(row, {draftInd:0}), function(data){
		                                if(data.messages){
		                                	$.each(data.messages, function(index, message){
		                                		$.jGrowl(message.severity, message.message);
		                                	});
		                                }
	                    				$('#conversations-list-table').data('dataTable').reload('true');
		        					});
		        				})
	        				).append(
		        				$('<button>Delete Conversation</button>').button({
					                icons: {
					                    primary: "ui-icon-trash"
					                },
					                text: false
		        				}).bind('click', function(){
		        					ReportDetailsView.deleteConversation(row, function(data){
		                                if(data.messages){
		                                	$.each(data.messages, function(index, message){
		                                		$.jGrowl(message.severity, message.message);
		                                	});
		                                }
	                    				$('#conversations-list-table').data('dataTable').reload('true');
		        					});
		        				})
	        				);
			        	} else if( row.sentBy == framework.getUser().emailId ){
			        		_div.append(
		        				$('<button>Create Copy</button>').button({
					                icons: {
					                    primary: "ui-icon-copy"
					                },
					                text: false
		        				}).bind('click', function(){
		        					$('#add-new-conversation-dialog-form').removeData('_conversationId');
		        					Form.populate($('#add-new-conversation-dialog-form'), row);
		        					$('#add-new-conversation-dialog').dialog('open');
		        				})
	        				).append(
	    						$('<button>Re-send Conversation</button>').button({
					                icons: {
					                    primary:'ui-icon-mail-open'
					                },
					                text: false
		        				}).bind('click', function(){
		        					ReportDetailsView.addUpdateConversation($.extend(row, {conversationId:null, draftInd:0}), function(data){
		                                if(data.messages){
		                                	$.each(data.messages, function(index, message){
		                                		$.jGrowl(message.severity, message.message);
		                                	});
		                                }
	                    				$('#conversations-list-table').data('dataTable').reload('true');
		        					});
		        				})
	        				);
			        	}
			        	return _div;
        		    },
			        colwidth:'10%'
		         }],
		        fetcherFunction:ReportDetailsView.getConversationList,
		        pagenate:false,
				beforeSend:function(data) {
					return {reportId:framework.getScreen().report.reportId};
				}
			});
			$('#add-new-conversation').off('click.main').on('click.main', function(){
				$('#add-new-conversation-dialog-form').removeData('_conversationId');
				Form.clear($('#add-new-conversation-dialog-form'));
				$('#add-new-conversation-dialog').dialog('open');
			});
			$('#add-new-file').off('click.main').on('click.main', function(){
				$('#add-new-document-dialog-form').removeData('_conversationId');
				Form.clear($('#add-new-document-dialog-form'));
				$('#add-new-document-dialog').dialog('open');
			});
		}
	},
	addNewFile:function(){
		Form.submit({
			sectionId:"add-new-document-dialog-form",
			dwrMethod:ReportDetailsView.addFile,
			beforeSubmit: function(data){
				return $.extend(data, {reportId:framework.getScreen().report.reportId});
			},
			repopulate:false,
			mask: false,
			success:function(data){
				$('#add-new-document-dialog').dialog('close');
                $('#add-new-conversation-dialog #attatchments').empty();
				$('#documents-list-table').data('dataTable').reload('true');
			}
		});
	},
	addNewConversation:function(draftIndVal){
		Form.submit({
			sectionId:"add-new-conversation-dialog-form",
			dwrMethod:ReportDetailsView.addUpdateConversation,
			beforeSubmit: function(data){
				return $.extend(data, {
					conversationId:$('#add-new-conversation-dialog-form').data('_conversationId'), 
					reportId:framework.getScreen().report.reportId, 
					draftInd:draftIndVal
				});
			},
			repopulate:false,
			mask: false,
			success:function(data){
				$('#add-new-conversation-dialog').dialog('close');
				$('#conversations-list-table').data('dataTable').reload('true');
			}
		});
	},
	_setupNotificationsForm:function(screenParams, reportDetails){
		if(screenParams['clientView']){
			$('#add-new-notification').hide();
		}
		if(reportDetails != null){
			$('#notifications-list-table').dataTable ({
				columns:[
				         {
				 	        displayName:'When',
					        columnData:'notificationTime',
					        colwidth:'10%',
					        format:'timestamp'
				         },
				         {
				 	        displayName:'Timestamp',
					        columnData:'creationTime',
					        colwidth:'10%',
					        format:'timestamp'
				         },
				         {
				 	        displayName:'Set By',
					        columnData:'byUserId',
					        colwidth:'15%'
				         },
				         {
				 	        displayName:'Status',
					        columnData:function(row){
					        	return $('<span>'+framework.lookupMap[row.notificationStatus].lookupDescription+'</span>');
					        },
					        colwidth:'15%'
				         },
				         {
				 	        displayName:'Subject',
					        columnData:'notificationComment',
					        colwidth:'36%'
				         },
				         {
				 	        displayName:'Action',
					        columnData:function(row){
					        	var _element = $('<div/>');
					        	if(row.notificationStatus != 10003 && row.notificationStatus != 10004){
					        		_element.append(
				        				$('<button>Acknowledge</button>').button({
							                icons: {
							                    primary: "ui-icon-check"
							                },
							                text: false
				        				}).bind('click', function(){
				        					$('#notificationUpdateDialog').data('_notificationDetails', 
				        							$.extend({}, row, {notificationStatus:10003}));
				        					Form.clear($('#notificationUpdateDialog'));
				        					Form.populate($('#notificationUpdateDialog'), row);
				        					$('#notificationUpdateDialog').dialog('open');
				        				})
			        				).append(
				        				$('<button>Re-schedule</button>').button({
							                icons: {
							                    primary: "ui-icon-calendar"
							                },
							                text: false
				        				}).bind('click', function(){
				        					$('#notificationUpdateDialog').data('_notificationDetails', 
				        							$.extend({}, row, {notificationStatus:10002}));
				        					Form.clear($('#notificationUpdateDialog'));
				        					Form.populate($('#notificationUpdateDialog'), row);
				        					$('#notificationUpdateDialog').dialog('open');
				        				})
			        				).append(
				        				$('<button>Delete</button>').button({
							                icons: {
							                    primary: "ui-icon-trash"
							                },
							                text: false
				        				}).bind('click', function(){
				        					$('#notificationUpdateDialog').data('_notificationDetails', 
				        							$.extend({}, row, {notificationStatus:10004}));
				        					Form.clear($('#notificationUpdateDialog'));
				        					Form.populate($('#notificationUpdateDialog'), row);
				        					$('#notificationUpdateDialog').dialog('open');
				        				})
			        				);	
					        	}
					        	_element.append(
			        				$('<button>Show History</button>').button({
						                icons: {
						                    primary: "ui-icon-clock"
						                },
						                text: false
			        				}).bind('click', function(){
			        					$('#notificationHistoryDialog').dialog('open');
			        					$('#notificationListHistoryTable').dataTable('destroy');
			        					$('#notificationListHistoryTable').dataTable({
			        						pagenate:false,
			        						fetcherFunction:ReportDetailsView.showNotificationHistory,
			        						beforeSend: function(data){
			        							return row.notificationId;
			        						},
		        							columns:[
		        						         {
		        						 	        displayName:'When',
		        							        columnData:'notificationTime',
		        							        colwidth:'17%',
		        							        format:'timestamp'
		        						         },
		        						         {
		        						 	        displayName:'Timestamp',
		        							        columnData:'creationTime',
		        							        colwidth:'17%',
		        							        format:'timestamp'
		        						         },
		        						         {
		        						 	        displayName:'By',
		        							        columnData:'byUserId',
		        							        colwidth:'10%'
		        						         },
		        						         {
		        						 	        displayName:'Subject',
		        							        columnData:'notificationComment',
		        							        colwidth:'40%'
		        						         },
		        						         {
		        						 	        displayName:'Status',
		        							        columnData:function(row){
		        							        	return $('<span>'+framework.lookupMap[row.notificationStatus].lookupDescription+'</span>');
		        							        },
		        							        colwidth:'16%'
		        						         }
	        						         ]
			        					});
		        					})
		        				);
					        	return _element;
		        		    },
					        colwidth:'14%'
				         }
			         ],
		        fetcherFunction:ReportDetailsView.getNotificationsList,
		        pagenate:false,
				beforeSend:function(data) {
					return {reportId:framework.getScreen().report.reportId};
				}
			});
			
			$('#add-new-notification').off('click').on('click', function(){
				$('#notificationUpdateDialog').dialog('open');
				Form.clear($('#notificationUpdateDialog'));
				$('#notificationUpdateDialog').data('_notificationDetails', {
					'forUserId':reportDetails.assignedTo, 
					'reportId':reportDetails.reportId, 
					'notificationStatus':10001
				});
			});
		}
	},
	addNotification: function(){
		Form.submit({
			repopulate : false,
            success : function(data)
            {
            	$('#addNotificationDialog').dialog('close');
            },
            dwrMethod : ReportDetailsView.addNotification,
            sectionId : 'notificationAddForm'
		});
	},
	updateNotificationStatus: function(){
		var notificationData = $('#notificationUpdateDialog').data('_notificationDetails');
		Form.submit({
			repopulate : false,
            beforeSubmit : function(data)
            {
                return $.extend(notificationData, data);
            },
            success : function(data)
            {
            	$('#notificationUpdateDialog').dialog('close');
            	$('#notifications-list-table').data('dataTable').reload('true');
            },
            dwrMethod : ReportDetailsView.changeNotificationStatus,
            sectionId : 'notificationUpdateForm'
		});
	},
	_setupCaseStatusForm:function(screenParams, reportDetails){
		if(screenParams['clientView']){
			$('#add-new-case-status').hide();
		}

		if(reportDetails != null){
			$('#case-status-list-table').dataTable ({
				columns:[{
		 	        displayName:'Sl#',
			        columnData:'caseStatusId',
			        colwidth:'2%'
		         },
		         {
		 	        displayName:'Set By',
			        columnData:'setBy',
			        colwidth:'18%'
		         },
		         {
		 	        displayName:'Sub-status',
		 	        columnData:function(row){
		 	        	return $('<span>'+framework.lookupMap[row.substatus].lookupDescription+'</span>');
			        },
			        colwidth:'20%'
		         },
		         {
		 	        displayName:'Description',
			        columnData:'description',
			        colwidth:'40%'
		         },
		         {
		 	        displayName:'Added On',
			        columnData:'addedDate',
			        colwidth:'20%',
			        format:'timestamp'
		         }],
		        fetcherFunction:ReportDetailsView.getCaseStatusList,
		        pagenate:false,
				beforeSend:function(data) {
					return {reportId:framework.getScreen().report.reportId};
				}
			});
			$('#add-new-case-status').off('click.main').on('click.main', function(){
				Form.clear($('#add-new-case-status-dialog-form'));
				$('#add-new-case-status-dialog').dialog('open');
			});
		}
	},
	addNewCaseStatus:function(){
		Form.submit({
			sectionId:"add-new-case-status-dialog-form",
			dwrMethod:ReportDetailsView.addCaseStatus,
			beforeSubmit: function(data){
				return $.extend(data, {reportId:framework.getScreen().report.reportId});
			},
			repopulate:false,
			success:function(data){
				$('#add-new-case-status-dialog').dialog('close');
				$('#case-status-list-table').data('dataTable').reload('true');
			}
		});
	},
	_calcPayableAmount:function(){
		var paymentForm = $('#payment-record-main-form');
		var values = Form.pull(paymentForm);
		var _isValid = true;
		$.each(values, function(key, value){
			if(key == 'paymentOption' || key == 'numPass' || key == 'numUniv' || key == 'numColl'){
				if(value == null){
					_isValid = false;
				}
			}
		});
		if(_isValid == true){
			Form.submit({
				sectionId:"payment-record-main-form",
				dwrMethod:ReportDetailsView.calcPayableAmount,
				repopulate:false,
				validate:false,
				beforeSubmit: function(data){
					data = $.extend(true,{}, framework.getScreen().report, data);
					return framework.getScreen()._beforeSendingReport(data);
				},
				success:function(data){
					paymentForm.find('#payableAmount').attribute('disabled', false);
					paymentForm.find('#discountAmount').attribute('disabled', false);
					paymentForm.find('#freeze-payment').button('option', 'disabled', false);
					$('#payableAmount').attribute('value', data.payableAmount);
				}
			});
		} else {
			paymentForm.find('#payableAmount').attribute('disabled', true);
			paymentForm.find('#discountAmount').attribute('disabled', true);
			paymentForm.find('#freeze-payment').button('option', 'disabled', true);
		}
	},
	_maritalStatusChange: function(value){
		if(null == value || parseInt(value) == 10701){
			$('#familyDetailsList').attribute('visible', false).attribute('submittable', false);
		} else {
			$('#familyDetailsList').attribute('visible', true).attribute('submittable', true);
		}
	},
	_requestTypeChange: function(newRequestType, callback){
		if(newRequestType == null) {
			$('#coursePreferenceList').attribute('visible', false).attribute('submittable', false);
			return;
		}
		newRequestType = parseInt(newRequestType);
		if(newRequestType == 10109 || newRequestType == 10110){
			$('#coursePreferenceList').attribute('visible', true).attribute('submittable', true);
		} else {
			$('#coursePreferenceList').attribute('visible', false).attribute('submittable', false);
		}
		ReportDetailsView.getAttributeList(newRequestType, function(data){
			var _otherDetailsForm = $('#other-details-form');
			var _tr = null, _field;
			var _table = $('<table class="form"></table>');
			$.each(data, function(index, value){
				_tr = $('<tr/>').append('<th width="50%"><label for="reportAttributesMap-'+value.attributeId+'-atributeValue">'+value.attributeDesc+'</label></th>');
				switch(value.inputType){
				case 'ITA':
					_field = $('<textarea id="reportAttributesMap-'+value.attributeId+'-atributeValue"  maxlength="200" ></textarea>');
					break;
				case 'SOM':
					_field = $('<select id="reportAttributesMap-'+value.attributeId+'-atributeValue"></select>').append('<option value="">select...</option><option lookupFetch="'+value.lookupId+'" disabled="true">loading...</option>');
					break;
				default:
					_field = $('<input type="text" id="reportAttributesMap-'+value.attributeId+'-atributeValue" maxlength="200" />');
					break;
				}
				_tr.append($('<td width="50%"></td>').append(_field));
				_table.append(_tr);
			});
			_otherDetailsForm.empty().append(_table);
			framework._initializeControls(_table);
			if(callback){
				callback.call(this);
			}
		});
	},
	_afterRecievingReport:function(reportDetails){
		var countries = new Array();
		$.each(reportDetails.countryPreferencesList, function(index, value){
			countries.push(value.country);
		});
		reportDetails.countryPreferencesList = countries;
		return reportDetails;
	},
	_beforeSendingReport:function(reportDetails){
		var countries = new Array();
		$.each(reportDetails.countryPreferencesList, function(index, value){
			countries.push({'country':value, 'seqNum':(index+1)});
		});
		var newAttributeMap = {};
		$.each(reportDetails.reportAttributesMap, function(key, value){
			newAttributeMap[key] = $.extend(value, {'attributeId':key});
		});
		reportDetails.reportAttributesMap = newAttributeMap;
		reportDetails.countryPreferencesList = countries;
		return reportDetails;
	}

});