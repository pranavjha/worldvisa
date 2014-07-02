(function( $, undefined ) {
$.widget("ui.fileUpload",
{
    options :{},
    ajaxparams:{},
    _create : function(){
    	this.element.attr('component', 'fileUpload');
    	this.element.css('position', 'relative');
    	var _id = this.element.attr('id');
    	var _options = this.options;
    	var _this = this;
    	this.element.append(
    		$('<span/>').append(
    			$('<button class="fileupload-file-name">(no - file)</button>').button({
    				disabled:true
	    		}).on('click', function(){
	    			if($(this).data('_fileDetails') != null) {
	    				CommonView.getFile($(this).data('_fileDetails'), function(data){
	    					dwr.engine.openInDownload(data);
	    				});
	    			}
	    		})
    		).append(
				$('<button>Upload</button>').button({
	                icons: {
	                    primary: "ui-icon-arrowthickstop-1-n"
	                },
	                text:false
	    		})
			).buttonset()
    	).append(
    		$('<input type="file" id="_file_'+_id+'" name="_file_'+_id+'" style="display: inline;cursor:pointer;filter:alpha(opacity=0); position: absolute;width: 28px;overflow: hidden;top: 0;left: 180px;height: 28px;opacity: 0;z-index: 1001;" />').on('change', function(){
    			var _thisButton = $(this).prev().find('.fileupload-file-name');
    			_thisButton.find('.ui-button-text').html("uploading file...");
    			$.ajaxFileUpload({
	                url:'upload/',
	                secureuri:false,
	                fileElementId:'_file_'+_id,
	                data:{'allowedTypes':_options.allowedTypes, 'fileName':$('#_file_'+_id).val().split(/[\\\\\/]/).pop().split(' ').join('')},
	                success: function (data, status){
	                	// stripping off tags
	                	var tmp = document.createElement("DIV");
	                	tmp.innerHTML = $(data.body).html();
	                	var _data = $.parseJSON($.trim(tmp.textContent||tmp.innerText));
	                	if(_data['exception']) {
                    		$.jGrowl('ERROR', _data['exception']);
                    		_data = _thisButton.data('_fileDetails');
	                	}
	                	_this.value(_data);
	                },
	                error: function (data, status, e)
	                {
	                    console.log('error: '+e);
	                }
    			});
    		})
		);
    },
    value : function(data){
    	if (arguments.length == 0) {
    		return this.element.find('.fileupload-file-name').data('_fileDetails');
        } else {
        	if(null != data){
        		this.element.find('.fileupload-file-name').data('_fileDetails', data).button('option', 'disabled', false)
    				.find('.ui-button-text').html(data.fileName);
        	} else {
        		this.element.find('.fileupload-file-name').removeData('_fileDetails').button('option', 'disabled', true)
				.find('.ui-button-text').html('(no - file)');
        	}
        }
    },
    unFormattedValue : function(){
    	return this.element.find('.fileupload-file-name').data('_fileDetails');
    },
    disabled: function(){
    	if (arguments.length == 0) {
    		return (this.attr('disabled') != null);
        } else {
        	if(arguments[0] == true){
            	this.element.attr('disabled', 'disabled');
        		this.element.find('.fileupload-file-name').next().hide();
        	} else {
            	this.element.removeAttr('disabled');
        		this.element.find('.fileupload-file-name').next().show();
        	}
        }
    },
    destroy : function(){
    	this.element.removeAttr('component', 'fileUpload');
    	this.element.empty();
    }
});
})( jQuery );