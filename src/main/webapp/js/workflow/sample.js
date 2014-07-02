framework.addScreen({
	_criteria : {},
	load : function(hashTokens, callback) {
		framework.getScreen()._initialize();
		callback.call(this);
	},
	search:function(){
		framework.getScreen()._criteria = Form.pull($('#search-form'));
		$('#orderTable').data('dataTable').reload('true');
	},
	_initialize : function() {
		$('#orderTable').dataTable ({
			columns:[
				{
					displayName:'APS NO',
					columnData:'0',
				    colwidth:'10%'
				},{
					displayName:'PRIORITY',
					columnData:'1',
				    colwidth:'10%'
				},{
					displayName:'APS PO ',
					columnData:'2',
				    colwidth:'10%'
				},{
					displayName:'QTY',
					columnData:'3',
				    colwidth:'10%'
				},{
					displayName:'CUSTOMER PO',
					columnData:'4',
				    colwidth:'10%'
				},{
					displayName:'ORDER DESCRIPTION',
					columnData:'5',
				    colwidth:'20%'
				},{
					displayName:'SHIP TERMS',
					columnData:'6',
				    colwidth:'10%'
				},{
					displayName:'DESTINATION',
					columnData:'7',
				    colwidth:'10%'
				},{
					displayName:'ORDER DATE',
					columnData:'8',
				    colwidth:'10%'
				},{
					displayName:'PROMISE DATE',
					columnData:'9',
				    colwidth:'10%'
				},{
					 displayName:'%',
					 columnData:'10',
					    colwidth:'10%'
				 },{
					displayName:'EDD',
					columnData:'11',
				    colwidth:'10%'
				},{
					displayName:'QA DATE',
					columnData:'12',
				    colwidth:'10%'
				},{
					displayName:'ADD',
					columnData:'13',
				    colwidth:'10%'
				},{
					displayName:'COMMENTS',
					columnData:'14',
				    colwidth:'10%'
				},{
					displayName:'D/S ',
					columnData:'15',
				    colwidth:'10%'
				},{
					displayName:'ETA',
					columnData:'16',
				    colwidth:'10%'
				},{
					displayName:'PO FOR FREIGHT',
					columnData:'17',
				    colwidth:'10%'
				},{
					displayName:'Serial Number',
					columnData:'18',
				    colwidth:'10%'
				},{
					displayName:'STATUS',
					columnData:'19',
				    colwidth:'10%'
				}
	         ],
	        'fetcherFunction':SampleView.readList,
	        'rowCountFunction':SampleView.readListLength,
	        defaultSort:2,
	        width:'210%',
			beforeSend:function(data) {
				return $.extend({}, data, framework.getScreen()._criteria);
			}
	    });
	}
});
