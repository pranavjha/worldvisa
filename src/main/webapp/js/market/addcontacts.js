framework.addScreen({
	_criteria:{},
	load : function(hashTokens, callback) {
		callback.call(this);
	},
	loadContacts:function(){
		if(Form.validate($('#load-contacts-form'))) {
			$('#load-contacts-form-section').accordion( "activate", false);
			$('#load-contacts-data-section').accordion( "activate", 0);
			$('#load-contacts-data-section').mask();
			framework.getScreen()._criteria = Form.pull($('#load-contacts-form'));
			$('#contacts-fetched-table').dataTable('destroy');
			AddContactsView.setupContacts(framework.getScreen()._criteria, function(data){
				framework.getScreen()._criteria = data;
				AddContactsView.readContactsColumns(framework.getScreen()._criteria, function(data){
					var _columns = [];
					_columns.push({
			 	        displayName:'-',
					    columnData:function(row){
					    	return $('<div>').append(
			        				$('<button>Delete</button>').button({
						                icons: {
						                    primary: "ui-icon-trash"
						                },
						                text: false
			        				}).bind('click', function(){
			        					AddContactsView.deleteContact(framework.getScreen()._criteria, row[row.length - 1], function(data){
			        		            	$('#contacts-fetched-table').data('dataTable').reload('true');
			        					});
			        				})
		        				);
					    },
					    colwidth:'3%'
					});
					var i;
					for(i = 0; i < data; i++){
						_columns.push({
				 	        displayName:'-',
					        columnData:''+i,
					        colwidth:'10%'
				         });
					};
					$('#contacts-fetched-table').dataTable ({
						'columns':_columns,
				        'fetcherFunction':AddContactsView.readContacts,
				        'rowCountFunction':AddContactsView.readContactsSize,
						'beforeSend':function(data) {
							return $.extend({}, framework.getScreen()._criteria, data);
						},
						'width':((10*i+3)+'%')
					});
					AddContactsView.getTableColumnList(function(data){
						var _select = $('<select style="width:50px"></select>');
						$.each(data, function(index, value){
							_select.append('<option value="'+value[0]+'">'+value[1]+'</option>');
						});
						$('#contacts-fetched-table').find('.list-table thead th').filter(':not(:first)').each(function(index){
							$(this).empty().append(_select.clone().attr('id', 'option-'+index));
						});
					});
					$('#load-contacts-data-section').unmask();
				});
			});
		}
	},
	saveContacts:function(){
		if(Form.validate($('#load-contacts-form'))) {
			var _metadata = $.extend(framework.getScreen()._criteria, Form.pull($('#load-contacts-form')));
			var _columnOrder = new Array();
			$('#contacts-fetched-table').find('.list-table thead th select').each(function(index){
				var _val = $(this).attribute('value');
				var _id = $(this).attr('id').split('-');
				var index = parseInt(_id[_id.length-1]);
				_columnOrder[index] = _val;
			});
			$('#mainContainer').mask({
				label:'saving contacts...'
			});
			AddContactsView.uploadContacts(_metadata, _columnOrder, function(data){
				$('#mainContainer').unmask();
            	$('#contacts-fetched-table').data('dataTable').reload('true');
            	if(data.messages){
                	$.each(data.messages, function(index, message){
                		$.jGrowl(message.severity, message.message);
                	});
                }
			});
		}
	}
});