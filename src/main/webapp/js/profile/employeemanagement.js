framework.addScreen({
	_criteria : {},
	load : function(hashTokens, callback) {
		framework.getScreen()._initialize();
		callback.call(this);
	},
	exportToExcel:function($button){
		EmployeeManagementView.exportToExcel(framework.getScreen()._criteria,function(data){
		    dwr.engine.openInDownload(data);
		});
	},
	search:function(){
		framework.getScreen()._criteria = Form.pull($('#search-form'));
		$('#employeeListTable').data('dataTable').reload('true');
	},
	addNotification:function(){
		Form.submit({
			repopulate : false,
            success : function(data)
            {
            	$('#addNotificationDialog').dialog('close');
            },
            dwrMethod : EmployeeManagementView.addNotification,
            sectionId : 'notificationAddForm'
		});
	},
	_initialize : function() {
		if(framework.getUser().userRole == 11002) {
			$('#add-new-employee-button').show();
		} else {
			$('#export-to-excel').hide();
			$('#add-new-employee-button').hide();
		}
		$('#employeeListTable').dataTable ({
			columns:[{
		 	        displayName:'Name',
		 	        columnData:function(row){
		 	        	if(framework.getUser().userRole == 11002) {
		 	        		return $('<a href="#profile/EmployeeDetails/'+row.employeeId+'">'+row.employeeName+'</a>');
			 	        } else {
		 	        		return $('<span>'+row.employeeName+'</span>');
		 	        	}
				    },
			        colwidth:'10%',
			        sortVal:'EMPLOYEE_NAME'
		         },
		         {
		 	        displayName:'Joining',
			        columnData:'joiningDate',
			        colwidth:'10%',
			        sortVal:'JOINING_DATE',
			        format:'date'
		         },
		         {
		 	        displayName:'E-Mail',
			        columnData:'emailId',
			        colwidth:'10%',
			        sortVal:'ped.EMAIL_ID'
		         },
		         {
		 	        displayName:'Mobile',
			        columnData:'mobileNum',
			        colwidth:'10%',
			        sortVal:'MOBILE_NUM'
		         },
		         {
		 	        displayName:'Base',
			        columnData:function(row){
			        	return $('<div>').html(framework.lookupMap[row.baseLocation].lookupDescription);
			        },
			        colwidth:'10%',
			        sortVal:'BASE_LOCATION'
		         },
		         {
		 	        displayName:'Education',
			        columnData:function(row){
			        	if(row.educationTotal == 0) {
			        		return $('<div>').html("0 (N/A)");	
			        	} else {
			        		return $('<div>').html(row.educationConverted 
			        			+ " ( "+ parseFloat((row.educationConverted/row.educationTotal)*100).toFixed(2)
			        			+ "% )");
			        	}
			        },
			        colwidth:'10%'
		         },
		         {
		 	        displayName:'Immigration',
			        columnData:function(row){
			        	if(row.immigrationTotal == 0) {
			        		return $('<div>').html("0 (N/A)");	
			        	} else {
				        	return $('<div>').html(row.immigrationConverted 
			        			+ " ( "+ parseFloat((row.immigrationConverted/row.immigrationTotal)*100).toFixed(2)
			        			+ "% )");
			        	}
			        },
			        colwidth:'10%'
		         },
		         {
		 	        displayName:'Others',
			        columnData:function(row){
			        	if(row.othersTotal == 0) {
			        		return $('<div>').html("0 (N/A)");	
			        	} else {
			        		return $('<div>').html(row.othersConverted 
			        			+ " ( "+ parseFloat((row.othersConverted/row.othersTotal)*100).toFixed(2)
			        			+ "% )");
			        	}
			        },
			        colwidth:'10%'
		         },
		         {
		 	        displayName:'-',
			        columnData:function(row){
			        	var _retDiv = $('<div/>');
		 	        	if(framework.getUser().userRole == 11002) {
		 	        		_retDiv.append(
		        				$('<button>Delete</button>').button({
					                icons: {
					                    primary: "ui-icon-trash"
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
	    					            	$('#employeeListTable').data('dataTable').reload('true');
	    					            },
	    					            dwrMethod : EmployeeManagementView.deleteEmployee,
	    					            sectionId : 'dummyDiv'
	    							});
		        				})
	        				);
		 	        	}
		 	        	_retDiv.append(
	        				$('<button>Add Notification</button>').button({
				                icons: {
				                    primary: "ui-icon-mail-closed"
				                },
				                text: false
	        				}).on('click', function(){
	        					$('#addNotificationDialog').dialog('open');
	        					Form.clear($('#notificationAddForm'));
	        					$('#notificationAddForm #forUserId').attribute('value', row.emailId);
	        				})
        				);
		 	        	return _retDiv;
        		    },
			        colwidth:'10%'
		         }
	         ],
	        'fetcherFunction':EmployeeManagementView.searchResult,
	        'rowCountFunction':EmployeeManagementView.searchResultCount,
	        defaultSort:0,
			beforeSend:function(data) {
				return $.extend({}, data, framework.getScreen()._criteria);
			}
	    });
	}
});
