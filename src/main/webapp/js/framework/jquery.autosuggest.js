(function( $, undefined ) {
$.widget("ui.autoSuggest",
{
    options :
    {
		multiple : 'false'
    },
    _create : function(val)
    {
    	this.element.attr('component', 'autoSuggest');
		var _options = this.options;
		if(_options.multiple == 'true') {
			this.element.bind( "keydown", function( event ) {
				if ( event.keyCode === $.ui.keyCode.TAB &&
						$(this).data("autocomplete").menu.active ) {
					event.preventDefault();
				}
			}).autocomplete({
				source: function( request, response ) {
					var _this = this;
					_this.element.addClass('autosuggest-loading');
					eval(_options.binding).call(this, request.term.split( /,\s*/ ).pop(), function(){
						response.apply(_this, arguments);
						_this.element.removeClass('autosuggest-loading');
					});
				},
				search: function() {
					// custom minLength
					var term = this.value.split( /,\s*/ ).pop();
					if ( term.length < 1 ) {
						return false;
					}
				},
				focus: function() {
					// prevent value inserted on focus
					return false;
				},
				select: function( event, ui ) {
					var terms = this.value.split( /,\s*/ );
					// remove the current input
					terms.pop();
					// add the selected item
					terms.push( ui.item.value );
					// add placeholder to get the comma-and-space at the end
					terms.push( "" );
					this.value = terms.join( ", " );
					return false;
				}
			});
		} else {
			this.element.autocomplete({
				source: function( request, response ) {
					var _this = this;
					_this.element.addClass('autosuggest-loading');
					eval(_options.binding).call(this, request.term, function(){
						response.apply(_this, arguments);
						_this.element.removeClass('autosuggest-loading');
					});
				}
			});
		}
    },
    value : function()
    {
    	if(arguments.length == 0){
    		if(this.options.multiple == 'true'){
    			var _valArr = this.element.val().split( /,\s*/ );
    			var _retVal = new Array();
    			$.each(_valArr, function(index, value){
    				var _singleValue = $.trim(value);
    				if(_singleValue.length != 0){
    					_retVal.push(_singleValue);
    				}
    			});
    			return _retVal;
    		} else {
    			return this.element.val();
    		}
    	} else {
    		if(arguments[0] != null){
        		if(this.options.multiple == 'true'){
        			this.element.val(arguments[0].join(", ")).trigger('change.convertToText');
        		} else {
        			this.element.val(arguments[0]).trigger('change.convertToText');
        		}
    		} else {
    			this.element.val("").trigger('change.convertToText');
    		}
    		return this.element;
    	}
    },
    unFormattedValue : function(){
    	var _value = $.trim(this.element.val());
    	return (_value.length == 0?null:_value);
    },
    destroy : function()
    {
    	this.element.attr('component', null);
        $.Widget.prototype.destroy.apply(this, arguments); 
    }
});
})( jQuery );