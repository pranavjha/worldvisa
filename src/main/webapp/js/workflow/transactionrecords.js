framework.addScreen({
	_criteria : {},
	load : function(hashTokens, callback) {
		framework.getScreen()._initialize();
		callback.call(this);
	},
	exportToExcel:function($button){
		TransactionRecordsView.exportToExcel(framework.getScreen()._criteria,function(data){
		    dwr.engine.openInDownload(data);
		});
	},
	downloadReceipts:function($button){
		TransactionRecordsView.downloadAllReceipts(framework.getScreen()._criteria,function(data){
		    dwr.engine.openInDownload(data);
		});
	},
	search:function(){
		framework.getScreen()._criteria = Form.pull($('#search-form'));
		$('#transactionRecordsTable').data('dataTable').reload('true');
	},
	_initialize : function() {
		if(framework.getUser().userRole != 11002) {
			$('#export-to-excel').hide();
		}
		$('#transactionRecordsTable').dataTable ({
			columns:[{
		 	        displayName:'ID',
			        columnData:'paymentId',
			        colwidth:'10%',
			        sortVal:'PAYMENT_ID'
		         },
		         {
		 	        displayName:'Report Id',
		 	        columnData:function(row){
					    return $('<a href="#request/ReportDetails/'+row.reportId+'">'+row.reportId+'</a>');
				    },
			        colwidth:'10%',
			        sortVal:'WP.REPORT_ID'
		         },
		         {
		 	        displayName:'Transaction Date',
			        columnData:'transactionDt',
			        colwidth:'10%',
			        sortVal:'TRANSACTION_DT',
			        format:'date'
		         },
		         {
		 	        displayName:'Customer',
			        columnData:'details.personalDetails.name',
			        colwidth:'10%',
			        sortVal:'NAME'
		         },
		         {
		 	        displayName:'Employee',
			        columnData:'details.assignmentDetails.employeeName',
			        colwidth:'10%',
			        sortVal:'EMPLOYEE_NAME'
		         },
		         {
		 	        displayName:'Mode',
			        columnData:function(row){
			        	return $('<div>').html(framework.lookupMap[row.paymentMode].lookupDescription);
			        },
			        colwidth:'10%',
			        sortVal:'PAYMENT_MODE'
		         },
		         {
		 	        displayName:'Cheque/DD No',
			        columnData:'chequeDdNo',
			        colwidth:'10%',
			        sortVal:'CHEQUE_DD_NO'
		         },
		         {
		 	        displayName:'Amount',
			        columnData:'amount',
			        colwidth:'10%',
			        sortVal:'AMOUNT',
			        format:"currency"
		         },
		         {
		 	        displayName:'Remarks',
			        columnData:'transactionRemarks',
			        colwidth:'15%',
			        sortVal:'TRANSACTION_REMARKS'
		         },
		         {
		 	        displayName:'-',
			        columnData:function(row){
			        	return $('<div/>').append(
	        				$('<button>Download Receipt</button>').button({
				                icons: {
				                    primary: "ui-icon-clipboard"
				                },
				                text: false
	        				}).bind('click', function(){
	        					TransactionRecordsView.downloadReceipt(row.paymentId, function(data){
	        					    dwr.engine.openInDownload(data);
	        					});
	        				})
        				);
        		    },
			        colwidth:'5%'
		         }
	         ],
	        'fetcherFunction':TransactionRecordsView.readSearchList,
	        'rowCountFunction':TransactionRecordsView.readSearchListCount,
	        defaultSort:2,
			beforeSend:function(data) {
				return $.extend({}, data, framework.getScreen()._criteria);
			}
	    });
	}
});
