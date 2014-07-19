( function($) {
	var methods = {
    	visible : function(isVisible){
    		if(arguments.length != 0){
		    	var _id = this.attr('id');
		    	if(isVisible == true){
		        	$('label[for='+_id+']').show().next('.mandatory-marker').show();
		        	this.show();
		        	this.prev('.noneditable-content-placeholder').show();
		        	this.removeAttr('hidden');
		    	} else {
			    	$('label[for='+_id+']').hide().next('.mandatory-marker').hide();
		        	this.prev('.noneditable-content-placeholder').hide();
			    	this.hide();
		        	this.attr('hidden', true);
		    	}
		    	return this;
    		} else {
    			return (this.attr('hidden') != true);
    		}
	    },
	    submittable : function(isSubmittable){
    		if(arguments.length != 0){
		    	if(isSubmittable == true){	
		    		this.attr("sendtoserver", "true");    	
		        	if(this.data('validator') == undefined){
		        		this.validator();
		        	}
		    	} else {
		    		this.removeAttr("sendtoserver");
		        	if(this.data('validator')){
		        		this.validator('destroy');
		        	}
		    	}
		    	return this;
    		} else {
    			return (this.attr("sendtoserver") == 'true');
    		}
	    },
	    disabled:function(isDisabled){
	    	if(arguments.length != 0){
    			this.prev('.noneditable-content-placeholder').remove();
        		this.unbind('change.convertToText');
	        	this.removeAttr('disabled');
	        	if(isDisabled == true){
	        		if(this.attribute('visible') == true) {
	        			this.before($('<span class="noneditable-content-placeholder"/>'));
	        		} else {
	        			this.before($('<span class="noneditable-content-placeholder"/>').hide());
	        		}
		        	this.attr('disabled', true).on('change.convertToText', function(){
    					var _val = "";
	        			if ($(this).get(0).nodeName == 'SELECT'){
			        		$(this).find('option:selected').each(function(){
			        			_val = _val + ", "+$(this).html();
			        		});
			        		if(_val.length > 0) {
			        			_val =_val.substring(2);
			        		}
	        			} else {
			        		_val = $(this).val();
					        if($(this).hasClass('hasDatepicker')){
					        	$(this).datepicker('destroy');
					        	$(this).attr('format', 'date').validator();
					        }
			        	}
	        			$(this).prev('.noneditable-content-placeholder').html(_val);
		        	}).trigger('change.convertToText');
	        	}
	        	return this;
			} else {
				return this.attr('disabled');
			}
	    },
		unFormattedValue:function() {
	        var retVal;
            if (this.attr('type') == 'checkbox' || this.attr('type') == 'radio') {
	            retVal = (this.attr('checked')?'Y':"");
	        } else if (this.get(0).nodeName == 'DIV' || this.get(0).nodeName == 'SPAN') {
	            retVal = this.text();
	        } else {
	            retVal = this.val();
	        }
	        if ($.trim(''+retVal).length == 0) {
	            retVal = null;
	        }
	        return retVal;
	    },
	    value: function() {
	        if (arguments.length == 0) {
	            var retVal = null;
	            if (this.attr('type') == 'checkbox' || this.attr('type') == 'radio') {
	                retVal = (this.attr('checked')? true : false );
	            } else if (this.get(0).nodeName == 'SELECT' && this.attr('multiple') != undefined && this.attr('multiple') != null) {
	            	var _valArr = this.val();
	            	var _format = this.attr('format');
	            	retVal = new Array();
	            	if(_valArr != null) {
		            	$.each(_valArr, function(index, value){
		            		if(value != null && $.trim(value).length != 0){
		            			retVal.push(Formatter.getAsObject(value, _format));
		            		}
		            	});
	            	}
	            } else if (this.get(0).nodeName == 'SELECT') {
	            	retVal = Formatter.getAsObject(this.val(), this.attr('format'));
	            } else if (this.get(0).nodeName == 'DIV' || this.get(0).nodeName == 'SPAN') {
	                retVal = this.text();
	            } else {
	                retVal = Formatter.getAsObject(this.val(), this.attr('format'));
	            }
	            if (retVal == null || retVal.length == 0) {
	                return null;
	            }
	            return retVal;
	        } else {
	            if(this.get(0).nodeName == 'INPUT'){
                    var _type = this.attr('type');
                    if (_type == 'checkbox' || _type == 'radio') {
                        this.attr('checked', true);
                    } else if (this.hasClass("hasDatepicker")) {
                        this.datepicker("setDate", arguments[0]);
                    } else {
                        this.val(Formatter.getAsString(arguments[0], this.attr('format')));
                    }
	            } else if(this.get(0).nodeName == 'SELECT'){
	                this.val(arguments[0]).data('loadValue', arguments[0]);
	            } else if(this.get(0).nodeName == 'SPAN' || this.get(0).nodeName == 'DIV'){
	            	this.html(''+Formatter.getAsString(arguments[0], this.attr('format')));
	            } else {
	            	try {
	            		this.val(arguments[0]);
                    } catch (e) {
                    }
	            }
	    		this.trigger('change.convertToText', [arguments[0]]).trigger('change.main', [arguments[0]]);
	        	return this;
	        }
	    }
	};
    
    $.fn.attribute = function(method) {
    	var _method = null;
    	var _this = null;
    	if (this.attr('component') != undefined && this.attr('component') != null){
        	_this = this.data(this.attr('component'));
        	_method = _this[method];
        }
    	if(!_method){
    		_this = this;
        	_method = methods[method];
        }
        if (_method) {
          return _method.apply( _this, Array.prototype.slice.call( arguments, 1 ));
        } else {
          $.error( 'Method ' +  method + ' does not exist on' );
        }    
    };
})(jQuery);
