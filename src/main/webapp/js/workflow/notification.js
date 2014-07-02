framework.addScreen({
    load : function(hashTokens, callback) {
		framework.getScreen()._initialize();
		callback.call(this);
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
            	$('#notificationListTable').data('dataTable').reload('true');
            },
            dwrMethod : NotificationView.changeNotificationStatus,
            sectionId : 'notificationUpdateForm'
		});
	},
	exportToExcel:function($button){
		NotificationView.exportToExcel(function(data){
		    dwr.engine.openInDownload(data);
		});
	},
	_initialize : function() {
		$('#notificationListTable').dataTable ({
			columns:[
		         {
		 	        displayName:'When',
			        columnData:'notificationTime',
			        colwidth:'10%',
			        sortVal:'NOTIFICATION_TIME',
			        format:'timestamp'
		         },
		         {
		 	        displayName:'Timestamp',
			        columnData:'creationTime',
			        colwidth:'10%',
			        sortVal:'CREATION_TIME',
			        format:'timestamp'
		         },
		         {
		 	        displayName:'By',
			        columnData:'byUserId',
			        colwidth:'20%',
			        sortVal:'BY_USER_ID'
		         },
		         {
		 	        displayName:'Report ID',
			        columnData:function(row){
			        	if(row.reportId != null){
			        		return $('<a href="#request/ReportDetails/'+row.reportId+'">'+row.reportId+'</a>');
			        	} else {
			        		return $('<span>-</span>');
			        	}
			        },
			        colwidth:'10%',
			        sortVal:'REPORT_ID'
		         },{
		 	        displayName:'Subject',
			        columnData:'notificationComment',
			        colwidth:'36%',
			        sortVal:'NOTIFICATION_COMMENT'
		         },
		         {
		 	        displayName:'Action',
			        columnData:function(row){
			        	return $('<div/>').append(
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
	        				).append(
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
		        						fetcherFunction:NotificationView.showHistory,
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
        		    },
			        colwidth:'14%'
		         }
	         ],
	        'fetcherFunction':NotificationView.getNotificationList,
	        'rowCountFunction':NotificationView.getNotificationListCount
	    });
	}
});
