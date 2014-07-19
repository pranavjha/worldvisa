framework.addScreen({
	lastSeqNum:0,
	load : function(hashTokens, callback) {
		framework.getScreen()._initialize();
		callback.call(this);
	},
	exportToExcel:function($button){
		DailyReportingView.exportToExcel(Form.pull($('#dailyReportingFilter')), function(data){
		    dwr.engine.openInDownload(data);
		});
	},
	showAddActivityDialog:function(){
		$('#addActivityDialog').dialog('open');
		Form.clear($('#addActivityDialogForm'));
	},
	addActivity:function(){
		Form.submit({
			repopulate : false,
            beforeSubmit : function(data)
            {
            	framework.getScreen().lastSeqNum = framework.getScreen().lastSeqNum+1;
            	return $.extend(data, {seqNum:framework.getScreen().lastSeqNum},
            			Form.pull($('#dailyReportingFilter')));
            },
            success : function(data)
            {
            	$('#addActivityDialog').dialog('close');
            	$('#dartList').data('dataTable').reload('true');
            },
            dwrMethod : DailyReportingView.addDart,
            sectionId : 'addActivityDialogForm'
		});
	},
	_initialize : function() {
		if(framework.getUser().userRole != 11002) {
			$('#export-to-excel').hide();
		}
		$('#reportDate').attribute('value', new Date());
		$('#dartList').dataTable ({
			columns:[
		         {
		 	        displayName:'Report ID',
			        columnData:function(row){
			        	if(framework.getScreen().lastSeqNum < row.seqNum){
			        		framework.getScreen().lastSeqNum = row.seqNum;
			        	}
			        	if(row['reportId'] != null) {
			        		return $('<a href="#request/ReportDetails/'+row['reportId']+'">'+row['reportId']+'</a>');
			        	} else {
			        		return $('<span>--</span>');		        		
			        	}
			        },
			        colwidth:'5%'
		         },
		         {
		 	        displayName:'Time',
			        columnData:'reportDate',
			        format:'timestamp',
			        colwidth:'15%'
	        	 },
		         {
		 	        displayName:'Description',
			        columnData:'description',
			        colwidth:'50%'
	        	 },
		         {
		 	        displayName:'Activity Type',
		 	        columnData:function(row){
		 	    	   return $('<span>'+framework.lookupMap[row.dartType].lookupDescription+'</span>');
		 	        },
			       colwidth:'20%'
		         },
		         {
		 	        displayName:'Action',
			        colwidth:'10%',
			        columnData:function(row){
			        	if(framework.getUser().emailId == $('#employeeEmail').attribute('value')
			        	&& $.datepicker.formatDate('d-M-yy', $('#reportDate').attribute('value')) == $.datepicker.formatDate('d-M-yy', new Date())) {
			        		return $('<div/>').append(
		        				$('<button>delete</button>').button({
					                icons: {
					                    primary: "ui-icon-circle-close"
					                },
					                text: false
		        				}).on('click', function(){
		        					Form.submit({
		        						repopulate : false,
		        			            beforeSubmit : function(data)
		        			            {
		        			            	return row;
		        			            },
		        			            success : function(data)
		        			            {
		        			            	$('#dartList').data('dataTable').reload('true');
		        			            },
		        			            dwrMethod : DailyReportingView.deleteDart,
		        			            sectionId : 'dailyReportingFilter'
		        					});
		        				})
	        				);
			        	} else {
			        		return $('<span>-</span>');
			        	}
			        }
		         }
	         ],
	         pagenate:false,
			 fetcherFunction:DailyReportingView.fetchDarts,
			 beforeSend: function(data){
			 	return Form.pull($('#dailyReportingFilter'));
			 }
	    });
		$('#employeeEmail, #reportDate').on('change.main', function(){	
			$('#dartList').data('dataTable').reload('true');
			$('#add-activity-button').button("option","disabled", (framework.getUser().emailId != $('#employeeEmail').attribute('value')
		        	|| $.datepicker.formatDate('d-M-yy', $('#reportDate').attribute('value')) != $.datepicker.formatDate('d-M-yy', new Date())));
		});
	}
});
