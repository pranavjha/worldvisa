(function( $, undefined ) {
$.widget("ui.validator",
{
    options :
    {
        customValidate:function(value){return true;}
    },
    _create : function()
    {
        var _fieldLabel = this.element.parent().closest('table').find('label[for='+this.element.attr('id').replace(/\[/g, '\\[')+']');
        if(!this.element.attr('label'))
        {
            if(_fieldLabel.length > 0){
                this.element.attr('label',_fieldLabel.html().replace(/:/g,""));
            }
        }
        var _format = this.element.attr('format');
        if(_format != null){
            _format = _format.split(/[\(\,\)]/);
        }
        if(_format != null && _format.length > 2 && $.trim(_format[2]).length != 0){
            var _format1 = $.trim(_format[1]);
            var _format2 = $.trim(_format[2]);
            switch($.trim(_format[0])){
                case 'positiveNumeric':
                    if(_format1.length != 0){
                        this.element.attr('maxlength',''+(parseInt(_format1)+parseInt(_format2)+1));
                    }
                    break;
                case 'numeric':
                    if(_format1.length != 0){
                        this.element.attr('maxlength',''+(parseInt(_format1)+parseInt(_format2)+2));
                    }
                    break;
                case 'englishOnly':
                case 'email':
                case 'phoneNum':
                case 'zipCode':
                case 'any':
                    this.element.attr('maxlength',_format2);
                    break;
            }
        }
        _fieldLabel.siblings('.mandatory-marker').remove();
        if(this.element.attr('mandatory') && this.element.attr('mandatory') == 'true'){
            _fieldLabel.after('<span class="mandatory-marker">*</span>');
        }
        this.element.bind('change.validator', function(){
            $(this).data('validator').validate();
        });
    },
    validate:function(){
        var _value = null;
        var _element = this.element;
        var _noError = true;
        var _message = null;
    	_value = this.element.attribute('unFormattedValue');
        if (_value == null || (_value instanceof Array && _value.length == 0)){
        	if(_element.attr('mandatory') && _element.attr('mandatory') == 'true'){
        		_noError = false;
        		_message = framework.errorMessages['E00020'].replace('#1#', _element.attr('label'));
        	}
        } else {
            // check based on format
            var _format = this.element.attr('format');
            if(_format != null){
                _format = _format.split(/[\(\,\)]/);
            } else {
                _format = [''];
            }
            switch(_format[0]){
                case 'date':
                    try {
                        if($.datepicker.parseDate('d-M-yy', _value) == null){
                        	throw "invalid";
                        }
                    } catch (e) {
                        _noError = false;
                        _message = framework.errorMessages['E00002'].replace('#1#', _element.attr('label'));
                    }
                    break;
                case 'daterange':
                    try {
                        var inputDate = _value.split('to');
                        if($.datepicker.parseDate('d-M-yy', $.trim(inputDate[0])) ==  null || 
                        		$.datepicker.parseDate('d-M-yy', $.trim(inputDate[inputDate.length - 1])) == null){
                        	throw "invalid";
                        }
                    } catch (e) {
                        _noError = false;
                        _message = framework.errorMessages['E00002'].replace('#1#', _element.attr('label'));
                    }                        
                    break;
                case 'timestamp':
                	try {
                        if($.datepicker.parseDate('d-M-yy', _value) == null){
                        	throw "invalid";
                        }
                        var _valueSplit = _value.split(" ");
                        if(!(/^(([0]{0,1}[1-9]{1})|([1][0-2]{1}))[:][0-5]{0,1}[0-9]{1}$/.test(_valueSplit[1]))){
                        	throw "invalid";
                        }
                    	if(_valueSplit[2] != 'am' && _valueSplit[2] != 'pm'){
                    		throw "invalid";
                        }
                    } catch (e) {
                        _noError = false;
                        _message = framework.errorMessages['E00003'].replace('#1#', _element.attr('label'));
                    }
                    break;
                case 'positiveNumeric':
                    if(!(/^[0-9]*([\.][0-9]+)*$/.test(_value))){
                        _noError = false;
                        _message = framework.errorMessages['E00005'].replace('#1#', _element.attr('label'));
                    } else if(/[\-]/.test(_value)){
                        _noError = false;
                        _message = framework.errorMessages['E00008'].replace('#1#', _element.attr('label'));
                    } else if (_format.length > 1 && $.trim(_format[1]).length != 0) {
                        if(!new RegExp("^[0-9]{0,"+_format[1]+"}([\.][0-9]+)*$").test(_value)){
                            _noError = false;
                            _message = framework.errorMessages['E00009'].replace('#1#', _element.attr('label')).replace('#2#', _format[1]);
                        }
                    }
                    break;
                case 'numeric':
                    if(!(/^[\-]{0,1}[0-9]*([\.][0-9]+)*$/.test(_value))){
                        _noError = false;
                        _message = framework.errorMessages['E00005'].replace('#1#', _element.attr('label'));
                    } else if (_format.length > 1 && $.trim(_format[1]).length != 0) {
                        if(!new RegExp("^[\-]{0,1}[0-9]{0,"+_format[1]+"}([\.][0-9]+)*$").test(_value)){
                            _noError = false;
                            _message = framework.errorMessages['E00009'].replace('#1#', _element.attr('label'))
                                        .replace('#2#', _format[1]);
                        }
                    }
                    break;
                case 'currency':
                    if(!(/^[a-zA-Z. ]*[0-9]*([\.][0-9]+)*$/.test(_value))){
                        _noError = false;
                        _message = framework.errorMessages['E00011'].replace('#1#', _element.attr('label'));
                    }
                    break;
                case 'englishOnly':
                    var patt=/^[a-zA-Z ]*$/;
                    if(!patt.test(_value)){
                        _noError = false;
                        _message = framework.errorMessages['E00006'].replace('#1#', _element.attr('label'));
                    } else if (_format.length > 1 && $.trim(_format[1]).length != 0) {
                        if(!new RegExp("^.{"+_format[1]+",}$").test(_value)){
                            _noError = false;
                            _message = framework.errorMessages['E00015'].replace('#1#', _element.attr('label'))
                                    .replace('#2#', _format[1]);
                        }
                    }
                    if (_noError && _format.length > 2 && $.trim(_format[2]).length != 0) {
                        if(!new RegExp("^.{0,"+_format[2]+"}$").test(_value)){
                            _noError = false;
                            _message = framework.errorMessages['E00016'].replace('#1#', _element.attr('label'))
                                    .replace('#2#', _format[2]);
                        }
                    }
                    break;
                case 'email':
                    var patt=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                    if(!patt.test(_value)){
                        _noError = false;
                        _message = framework.errorMessages['E00007'].replace('#1#', _element.attr('label'));
                    } else if (_format.length > 1 && $.trim(_format[1]).length != 0) {
                        if(!new RegExp("^.{"+_format[1]+",}$").test(_value)){
                            _noError = false;
                            _message = framework.errorMessages['E00015'].replace('#1#', _element.attr('label'))
                                    .replace('#2#', _format[1]);
                        }
                    }
                    if (_noError && _format.length > 2 && $.trim(_format[2]).length != 0) {
                        if(!new RegExp("^.{0,"+_format[2]+"}$").test(_value)){
                            _noError = false;
                            _message = framework.errorMessages['E00016'].replace('#1#', _element.attr('label'))
                                    .replace('#2#', _format[2]);
                        }
                    }
                    break;
                case 'phoneNum':
                    var patt=/^[0-9]+[0-9\-]*[0-9]+$/;
                    if(!patt.test(_value)){
                        _noError = false;
                        _message = framework.errorMessages['E00010'].replace('#1#', _element.attr('label'));
                    } else if (_format.length > 1 && $.trim(_format[1]).length != 0) {
                        if(!new RegExp("^.{"+_format[1]+",}$").test(_value)){
                            _noError = false;
                            _message = framework.errorMessages['E00015'].replace('#1#', _element.attr('label'))
                                    .replace('#2#', _format[1]);
                        }
                    } 
                    if (_noError && _format.length > 2 && $.trim(_format[2]).length != 0) {
                        if(!new RegExp("^.{0,"+_format[2]+"}$").test(_value)){
                            _noError = false;
                            _message = framework.errorMessages['E00016'].replace('#1#', _element.attr('label'))
                                    .replace('#2#', _format[2]);
                        }
                    }  
                    break;
                case 'zipCode':
                    var patt = /^[A-Za-z0-9]+[ ]*[\-]?[ ]*[A-Za-z0-9]+$/;

                    if(!patt.test(_value)){
                        _noError = false;
                        _message = framework.errorMessages['E00019'].replace('#1#', _element.attr('label'));
                    } else if (_format.length > 1 && $.trim(_format[1]).length != 0) {
                        if(!new RegExp("^.{"+_format[1]+",}$").test(_value)){
                            _noError = false;
                            _message = framework.errorMessages['E00015'].replace('#1#', _element.attr('label'))
                                    .replace('#2#', _format[1]);
                        }
                    }
                    if (_noError && _format.length > 2 && $.trim(_format[2]).length != 0) {
                        if(!new RegExp("^.{0,"+_format[2]+"}$").test(_value)){
                            _noError = false;
                            _message = framework.errorMessages['E00016'].replace('#1#', _element.attr('label'))
                                    .replace('#2#', _format[2]);
                        }
                    }
                    break;
                default:
                    if (_format.length > 1 && $.trim(_format[1]).length != 0) {
                        if(!new RegExp("^.{"+_format[1]+",}$").test(_value)){
                            _noError = false;
                            _message = framework.errorMessages['E00015'].replace('#1#', _element.attr('label'))
                                    .replace('#2#', _format[1]);
                        }
                    }
                    if (_noError && _format.length > 2 && $.trim(_format[2]).length != 0) {
                        if(!new RegExp("^.{0,"+_format[2]+"}$").test(_value)){
                            _noError = false;
                            _message = framework.errorMessages['E00016'].replace('#1#', _element.attr('label'))
                                    .replace('#2#', _format[2]);
                        }
                    }
            }
        }
        if(_noError == false){
            _element.attr('title', _message).addClass('validation-error');
        } else {
        	var message = this.options.customValidate.call(_element, _element.attribute('value'));
            if(message != null && message != true){
            	_noError = false;
            	if(message == false) {
            		_message = framework.errorMessages['E00019'].replace('#1#', _element.attr('label'));
            		_element.attr('title', _message).addClass('validation-error');
            	} else {
            		_message = message;
            		_element.attr('title', _message).addClass('validation-error');
            	}
            } else {
                _element.removeAttr('title').removeClass('validation-error');
            }
        }
        return _message;
    },
    destroy : function()
    {
        this.element.unbind('change.validator');
        $.Widget.prototype.destroy.apply(this, arguments); 
    }
});
})( jQuery );
