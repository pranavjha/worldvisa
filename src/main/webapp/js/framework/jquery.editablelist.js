(function( $, undefined ) {
$.widget("ui.editableList",
{
    options :
    {
		minrows : 0,
		maxrows : 10,
		rowCallback:function($tr){
		},
		_seqNum:0
    },
    _inputFieldSelectors:'input[type!=file], select, textarea, [class^="jquery-"]',
    _createRow:null,
    _create : function(val)
    {

    	this.element.attr('component', 'editableList');
    	var _this = this;
		if(isNaN(this.options.minrows)){
			_this.options.minrows = 0;
    	}
    	if(isNaN(this.options.maxrows)){
    		_this.options.maxrows = 10;
    	}
    	_this.element.find('table').addClass('list-table');
    	_this.element.find('thead th').addClass('ui-state-default');
    	_this.element.find('thead tr').append('<th class="ui-state-default button-cell-header" style="width:40px;">-</th>');
		var _action = $('<th colspan="'+_this.element.find('thead tr:first th').size()+'" style="text-align:left" class="ui-state-default add-button-cell"></th>');
		_this.element.find('thead tr').before($('<tr></tr>').append(_action));
		_action.append($('<button>add row</button>').button({
            icons: {
                primary: "ui-icon-circle-plus"
            }
		}).bind('click', function(){
			_this._editRow();
		}));
		this._createRow = this.element.find('tbody tr').detach();
    },
    _editRow:function($tr){
		var _dialog = $('<div id="edit-add-row-dialog"></div>');
		var _this = this;
		var _table = $('<table class="form" width="100%">');
		_dialog.append(_table);
		var _thead = this.element.find('thead');
		this._createRow.find(_this._inputFieldSelectors).each(function(){
			_table.append(
				$('<tr/>').append(
					$('<th width="50%">').append(
						_thead.find('label[for='+$(this).attr('id')+']').clone().attr('for', $(this).attr('id'))
					)
				).append(
					$('<td>').append($(this).clone())
				)
			);
		});
    	if($tr != null){
    		_dialog.attr('title', 'Edit Row');
    	} else {
    		_dialog.attr('title', 'Add New Row');
    	}
    	_dialog.append($('<div class="buttons-section"><button icon="ui-icon-check" id="save-edit-add-row">Save</button><button icon="ui-icon-close" id="close-edit-add-row">Cancel</button></div>'));
    	_dialog.appendTo($('body')).dialog({
			resizable : false,
			autoOpen : false,
			modal : true,
			width : 800
		});
    	framework._initializeControls(_dialog);
    	if($tr != null){
    		Form.populate(_table, $tr.data('rowVal'));
    	}
    	_dialog.dialog('open');
    	_dialog.find('#save-edit-add-row').bind('click', function(){
    		if(Form.validate(_dialog)) {
    			_dialog.mask({label : "please wait..."});
    			if($tr == null){
    				_this.options._seqNum = _this.options._seqNum+1;
    				_data = $.extend({'seqNum':_this.options._seqNum}, Form.pull(_dialog));
        			_this._attatchNewRow(_data);
        			_dialog.dialog('close').dialog('destroy').remove();	
    			} else {
    				_data = $.extend({}, $tr.data('rowVal'), Form.pull(_dialog));
        			$tr.find(_this._inputFieldSelectors).each(function(){
    		    		$(this).attr('id', $(this).attr('id').split('[')[0]);
    		    	});
    		    	Form.populate($tr, data);
    		    	$tr.data('rowVal', _data).find(_this._inputFieldSelectors).each(function(){
    		    		$(this).attr('id', $(this).attr('id')+'['+_data.seqNum);
    		    	});
    				_dialog.dialog('close').dialog('destroy').remove();	
    			}
    			_this.element.trigger('change');
    		} else {
    			return false;
    		}
    	});
    	_dialog.find('#close-edit-add-row').bind('click', function(){
    		_dialog.dialog('close').dialog('destroy').remove();
    	});
    },
    _attatchNewRow:function(data){
    	var _newRow = this._createRow.clone();
    	this.element.find('tbody').append(_newRow);
    	_newRow.data('rowVal', data).find(this._inputFieldSelectors).attr("disabled", true).each(function(){
    		$(this).attr('id', $(this).attr('id')+'['+data.seqNum);
    	});
		var _this = this;
		framework._initializeControls(_newRow).append($('<td class="button-cell">').append(
			$('<button>edit row</button>').button({
                icons: {
                    primary: "ui-icon-pencil"
                },
                text: false
			}).bind('click', function(){
				_this._editRow($(this).closest('tr'));
			})
		).append(
			$('<button>delete row</button>').button({
                icons: {
                    primary: "ui-icon-close"
                },
                text: false
			}).bind('click', function(){
				$(this).closest('tr').remove();
			})
		));
    	_newRow.find(_this._inputFieldSelectors).each(function(){
    		$(this).attr('id', $(this).attr('id').split('[')[0]);
    	});
    	Form.populate(_newRow, data);
    	this.options.rowCallback.call(this, _newRow);
    	_newRow.find(_this._inputFieldSelectors).each(function(){
    		$(this).attr('id', $(this).attr('id')+'['+data.seqNum);
    	});
    	return _newRow;
    },
    value : function()
    {
    	var _this = this;
    	if(arguments.length == 0){
    		var _retVal = new Array();
    		this.element.find('tbody tr').each(function(index){
    			_retVal.push($.extend($(this).data('rowVal'),{'seqNum':(index+1)}));
    		});
    		return _retVal;
    	} else {
    		this.element.find('tbody').empty();
    		if(arguments[0] != null){
	    		$.each(arguments[0], function(index, data){
	    			_this._attatchNewRow(data);
	    		});
    		}
    		return this.element;
    	}
    },
    unFormattedValue : function(){
    	var _value = this.value();
    	return (_value.length == 0?null:_value);
    },
    disabled:function(isDisabled){
    	if(arguments.length == 0){
    		return this.element.hasClass('disabled');
    	} else {
    		if(isDisabled == true){
    			this.element.addClass('disabled');
    		} else {
    			this.element.removeClass('disabled');
    		}
    		return this.element;
    	}
    },

    destroy : function()
    {
    	this.element.attr('component', null);
        $.Widget.prototype.destroy.apply(this, arguments); 
    }
});
})( jQuery );