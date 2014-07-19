framework.addScreen({
	_employeeDetails: {},
	load: function(hashTokens, callback){
		if(hashTokens.length < 3){
			framework.getScreen()._initialize(null);
			callback.call(this);
		} else {
			EmployeeDetailsView.getEmployeeDetails({employeeId:hashTokens[2]}, function(data){
				framework.getScreen()._employeeDetails = data.mainObject;
				Form.populate($('#employee-details-main-form'), framework.getScreen()._employeeDetails);
				framework.getScreen()._initialize(hashTokens[2]);
				callback.call(this);
			});
		}
	},
	_initialize:function(employeeId){
		if(framework.getUser().userRole != 11002) {
			$('#export-to-excel').hide();
		}
		if(employeeId != null) {
			$('#emailId').attribute('disabled', true);
			$('#employee-performance-filter-form #dateRange').attribute('value', 
					[Date.parse('today - 30'), Date.parse('today')]);
			$('#employee-performance-filter-form #dateRange, #employee-performance-filter-form #requestType').on('change.main', function(){
				$('#employee-performance-report').data('dataTable').reload('true');
			}); 
			$('#employee-performance-report').dataTable ({
				columns:[
			         {
			 	        displayName:'Date',
				        columnData:'pdate',
				        colwidth:'10%',
				        format:'date'
			         },
			         {
			 	        displayName:'Requests',
				        columnData:function(row){
				        	return $('<div>'+row.convertedRequests+' / '+row.totalRequest+'</div>');
				        },
				        colwidth:'30%'
		        	 },
			         {
			 	        displayName:'Revenue',
			 	        columnData:'totalRevenue',
			 	        colwidth:'20%',
			 	        format:'currency'
			         }
		         ],
		         pagenate:false,
				 fetcherFunction:EmployeeDetailsView.getPerformanceReport,
				 beforeSend: function(data){
					return $.extend({
				 		employeeEmail:framework.getScreen()._employeeDetails.emailId
			 		},Form.pull($('#employee-performance-filter-form')));
				 }
		    });
		} else {
			$('#employee-performance').hide();
			$('#export-to-excel').hide();
		}
	},
	save: function() {
		if(framework.getScreen()._employeeDetails.employeeId == null){
			Form.submit({
	            dwrMethod : EmployeeDetailsView.createEmployee,
	            beforeSubmit: function(data){
	            	return $.extend({}, framework.getScreen()._employeeDetails, data);
	            },
	            success:function(data){
	            	framework.getScreen()._employeeDetails = data;
	            	window.location.hash = 'profile/EmployeeDetails/'+data.employeeId;
	            },
	            sectionId : 'employee-details-main-form'
			});
		} else {
			Form.submit({
	            dwrMethod : EmployeeDetailsView.save,
	            beforeSubmit: function(data){
	            	return $.extend({}, framework.getScreen()._employeeDetails, data);
	            },
	            success:function(data){
	            	framework.getScreen()._employeeDetails = data;
	            },
	            sectionId : 'employee-details-main-form'
			});
		}
	},
	reload : function(){
		Form.clear($('#employee-details-main-form'));
		Form.populate($('#employee-details-main-form'), framework.getScreen()._employeeDetails);
	},
	exportToExcel:function($button){
		EmployeeManagementView.exportToExcel(
			$.extend({
			    employeeEmail:framework.getScreen()._employeeDetails.emailId
	 		},Form.pull($('#employee-performance-filter-form'))), function(data){
		    dwr.engine.openInDownload(data);
		});
	}
});
