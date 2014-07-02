(function( $, undefined ) {
$.widget("ui.dataTable",
{
    options :
    {
    	columns:[],
        fetcherFunction:null,
        rowCountFunction:null,
        beforeSend:function(data){
        	return data;
        },
        rows:10,
        defaultSort:0,
        sortOrder:'ASC',
        pagenate:true,
        _rowCount:0,
        width:"100%",
        rowSelectable:false,
        rowSelectId:null,
        enabledWhen:function(row){
        	return true;
        }
    },
    ajaxparams:{},
    value : function(data)
    {
    	if (arguments.length == 0)
        {
	    	var retVal = null;
	    	var _checkboxes = this.element.find('tbody input:checkbox:checked');
	    	if(_checkboxes.length > 0){
				retVal = new Array();
				_checkboxes.each(function(){
					retVal.push($(this).attr('value'));
				});
	    	}
	    	return retVal;
        } else {
    		return this.element;
        }
    },
    unFormattedValue : function(){
    	var _value = this.value();
    	return (_value.length == 0?null:_value);
    },
    _create : function(data)
    {
    	this.element.attr('component', 'dataTable');
    	this.element.addClass('jquery-datatable');
    	var headerRow = $('<tr/>');
    	var _this = this;
    	if(this.options.rowSelectable) {
			headerRow.prepend($('<th class="ui-state-default" width="3%"></th>').append(
				$('<input type="checkbox">').on('click', function(){
					var isChecked = $(this).is(':checked');
					_this.element.find('.select-td input').each(function(){
						$(this).attr('checked', isChecked);
						$.uniform.update($(this));
					});
				})
			));
			headerRow.find('input:checkbox').uniform();
		}

    	$.each(this.options.columns, function(index, value){
        	if(value.sortVal == null){
        		headerRow.append($('<th class="ui-state-default" width="'+value.colwidth+'" title="'+value.displayName+'">'+value.displayName+'</th>'));
        	} else {
        		headerRow.append($('<th class="ui-state-default" width="'+value.colwidth+'" title="'+value.displayName+'"/>').append(
    				$('<div/>').addClass('list-table-sortable-th')
    				.append($('<span>'+value.displayName+'</span>'))
            		.append($('<span class="ui-icon ui-icon-carat-2-n-s list-table-sort-icon"/>'))
    				.mouseenter(function(){
    					$(this).parent().addClass('ui-state-highlight');
    				}).mouseleave(function(){
    					$(this).parent().removeClass('ui-state-highlight');
        			}).click(function(){
        				var _span = $(this).find('span.ui-icon');
        				var _currSort = _span.hasClass('ui-icon-carat-2-n-s')?0:(_span.hasClass('ui-icon-carat-1-n')?1:-1);
        				_this.element.find('div.list-table-sortable-th span.ui-icon').removeClass('ui-icon-carat-1-n')
        					.removeClass('ui-icon-carat-1-s').addClass('ui-icon-carat-2-n-s');
        				switch (_currSort){
        				case 0:
        					_this.ajaxparams.sortOrder = "ASC";
        					_span.removeClass('ui-icon-carat-2-n-s').addClass('ui-icon-carat-1-n');
                			break;
        				case 1:
        					_this.ajaxparams.sortOrder = "DESC";
        					_span.removeClass('ui-icon-carat-2-n-s').addClass('ui-icon-carat-1-s');
        					break;
        				case -1:
        					_this.ajaxparams.sortOrder = "ASC";
        					_span.removeClass('ui-icon-carat-2-n-s').addClass('ui-icon-carat-1-n');
        					break;
        				}
        				_this.ajaxparams.sortCol = value.sortVal;
        				_this.ajaxparams.startRow = 0;
        				_this.reload(false);
            		})
				));
        	}
    	});
    	var _table = $('<table width="'+this.options.width+'" cellspacing="0" cellpadding="0" class="list-table"></table>')
		.append(
			$('<thead/>').append(headerRow)
		).append($('<tbody><tr><td colspan="'+this.options.columns.length+'" >&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>&nbsp;<br/></td></tr></tbody>'));
    	this.element.append($('<div style="margin:0;padding:0;width:100%;overflow-x:auto;">').append(_table));
    	if(this.options.pagenate == true){
    		this.element.append(
    			$('<div/>')
    			.addClass('list-table-footer ui-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix')
    			.append($('<div class="list-table-info">&nbsp;</div>'))
    			.append($('<div class="list-table-numrecords">Show <select style="width:auto;">'+
    					'<option value = "10">10</option>'+
    							'<option value = "15">15</option>'+
    							'<option value = "20">20</option>'+
    							'<option value = "25">25</option>'+
							'</select> entries</div>'))
    			.append($('<div class="list-table-pagenate">&nbsp;</div>'))
    		);
	    	var _this = this;
	    	this.element.find('.list-table-numrecords').change(function(){
	    		_this.options.rows = _this.ajaxparams.numRows = parseInt($(this).find('option:selected').val());
				_this.ajaxparams.startRow = 0;
	    		_this.reload(false);
		
	    	});
    	}
    	this.ajaxparams = {
	    	startRow:0,
            numRows:this.options.rows,
            sortCol:this.options.columns[this.options.defaultSort].sortVal,
            sortOrder:this.options.sortOrder
    	};
    	this.reload(true);
    },
    reload:function(reCalculateRowCount){
    	var _id = this.element.attr('id');
    	var _newParams = this.options.beforeSend.call(this, this.ajaxparams);
    	var _options = this.options;
    	var _element = this.element;
    	var _tfooter = this.element.find('.list-table-footer');
    	var _tbody = _element.find('tbody');
    	var _this = this;
    	_element.mask({label : "loading values..." });
    	_element.find('thead input:checkbox').attr('checked', false);
    	$.uniform.update(_element.find('thead input:checkbox'));
    	if(reCalculateRowCount && _options.pagenate == true) {       	
        	_options.rowCountFunction.call(this, _newParams, function(data){
        		_options._rowCount = data;    		
	    	});
    	}
    	_options.fetcherFunction.call(this, _newParams, function(data){
    		// render the table
    		_element.unmask();
    		_tbody.empty();
        	var _tr, _td;
        	var _value, _dataIndex, _data;
        	if(data.length == 0){
        		_tbody.append(
        				$('<tr class="even"/>').append($('<td colspan="'+_options.columns.length+'"/>').html('<div class="ui-widget">'+
        						'<div style="padding: 10px;" class="ui-state-highlight ui-corner-all">' +  
        						'<p><span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>' +
        						'<strong>No records to show.</strong></p></div></div>'))
    			);
        	} else {
	    		$.each(data, function(rowIndex, rowVal){
	    			_tr = $('<tr/>').addClass(rowIndex%2 == 0?'odd':'even');
	    			if(_options.rowSelectable) {
	    				if(_options.enabledWhen.call(this, rowVal)) {
	    					_tr.prepend('<td class="select-td"><input type="checkbox" value="'+rowVal[_options.rowSelectId]+'"></td>');
	    				} else {
	    					_tr.prepend('<td class="select-td">&nbsp;</td>');
	    				}
	    			}
	    			$.each(_options.columns, function(colIndex, colVal){
	    				_td = $('<td/>').addClass(colVal.columnClass);
	    				if(!(colVal.columnData instanceof Function)){
	    					
	    					_dataIndex = colVal.columnData.split('.');
	    					_data = rowVal;
	    					for(var i = 0; i < _dataIndex.length; i++){
	    						if(_data != null && _data != undefined) {
	    							_data = _data[_dataIndex[i]];
	    						}
	    					}
	    					_value = Formatter.getAsString(_data, colVal.format);
	    					_td.html(_value).attr('title', _value);
	    				} else {
	    					_value = colVal.columnData.call(this, rowVal);
	    					_td.append(_value);
	    				}
	    				_tr.append(_td);
	    			});
	    			_tbody.append(_tr);
	    		});
	    		_tbody.find('input:checkbox').uniform();
        	}
    		// render the footer
    		if(_options.pagenate == true){
    			var _toRow = _newParams.startRow+_newParams.numRows;
    			var _pagenation = _tfooter.find('.list-table-pagenate');
	    		_pagenation.empty();
		    	if(_options._rowCount != 0) {
    				 _tfooter.find('.list-table-info').html((_newParams.startRow+1)+' to '+(_toRow<_options._rowCount?_toRow:_options._rowCount)+' of '+_options._rowCount+' entries.');
    		    		var numPages = Math.ceil(_options._rowCount/_options.rows);
    		    		var _currPage = (_newParams.startRow/_options.rows)+1;
    		    		var _start,_end;
    		    		if(_currPage < 4){
    		    			_start = 1;
    		    			_end = (numPages < 7?numPages:7);
    		    		} else if(_currPage > (numPages-4)){
    		    			_start = (numPages<7?1:(numPages-7));
    		    			_end = numPages;
    		    		} else {
    		    			_start = _currPage-3;
    		    			_end = _currPage+3;
    		    		}
    		    		_pagenation.append($(
    							'<input type="radio" id="'+_id+'list-table-pagenate-first" name="'+_id+'list-table-pagenate" />'+
    							'<label for="'+_id+'list-table-pagenate-first"><span class="ui-icon ui-icon-seek-prev">&nbsp;</span></label>'
    					)).append($(
    							'<input type="radio" id="'+_id+'list-table-pagenate-prev" name="'+_id+'list-table-pagenate" />'+
    							'<label for="'+_id+'list-table-pagenate-prev"><span class="ui-icon ui-icon-triangle-1-w">&nbsp;</span></label>'
    					));
    		    		for(var i = _start; i <= _end; i++){
    		    			_pagenation.append($(
    							'<input type="radio" id="'+_id+'list-table-pagenate-'+i+'" name="'+_id+'list-table-pagenate" '+(i==_currPage?'checked':'')+'/>'+
    								'<label for="'+_id+'list-table-pagenate-'+i+'">'+i+'</label>'
    						));
    		    		}
    		    		_pagenation.append($(
    							'<input type="radio" id="'+_id+'list-table-pagenate-next" name="'+_id+'list-table-pagenate" />'+
    							'<label for="'+_id+'list-table-pagenate-next"><span class="ui-icon ui-icon-triangle-1-e">&nbsp;</span></label>'
    					)).append($(
    							'<input type="radio" id="'+_id+'list-table-pagenate-last" name="'+_id+'list-table-pagenate" />'+
    							'<label for="'+_id+'list-table-pagenate-last"><span class="ui-icon ui-icon-seek-next">&nbsp;</span></label>'
    					));
    		    		_pagenation.buttonset();
    		    		if(_currPage == 1){
    		    			_pagenation.find('#'+_id+'list-table-pagenate-first').button( "option", "disabled", true );
    		    			_pagenation.find('#'+_id+'list-table-pagenate-prev').button( "option", "disabled", true );	
    		    		}
    		    		if(_currPage == numPages){
    		    			_pagenation.find('#'+_id+'list-table-pagenate-next').button( "option", "disabled", true );
    		    			_pagenation.find('#'+_id+'list-table-pagenate-last').button( "option", "disabled", true );	
    		    		}
    		    		$('[name='+_id+'list-table-pagenate]').click(function(){
    		    			var _paramArr = $(this).attr('id').split('-');
    		    			var _param = _paramArr[_paramArr.length -1];
    		    			switch (_param){
    		    			case 'first':
    		        			_this.ajaxparams.startRow = 0;
    		    				break;
    		    			case 'prev':
    		        			_this.ajaxparams.startRow-=_this.options.rows;
    		    				break;
    		    			case 'next':
    		        			_this.ajaxparams.startRow+=_this.options.rows;
    		    				break;
    		    			case 'last':
    		        			_this.ajaxparams.startRow = Math.floor((_this.options._rowCount-1)/_this.options.rows)*_this.options.rows;
    		    				break;
    		    			default:
    		        			_this.ajaxparams.startRow = (parseInt(_param)-1)*_this.options.rows;
    		    				break;
    		    			}
    		    			_this.reload(false);
    		    		});
    			 } else {
    				 _tfooter.find('.list-table-info').html('no records found');
    			}
    		 }
    	});
    },
    destroy : function()
    {
    	this.element.empty();
    	this.element.attr('component', null);
        $.Widget.prototype.destroy.apply(this, arguments); 
    }
});
})( jQuery );