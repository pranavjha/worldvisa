( function($) {
	$.alert = function(title, message, callback) {
		var _alertDiv = $('<div title="'+title+'" style="line-height: 20px;padding: 30px;">'+message+'</div>');
		_alertDiv.appendTo('body').dialog({
			resizable: false,
			width:500,
			modal: true,
			buttons: {
				OK: function() {
					if(callback) {
						callback.call(window);
					}
					$( this ).dialog( "close" ).dialog('destroy').remove();
				}
			},
			close: function(event, ui) {
				if(callback) {
					callback.call(window);
				}
				$(this).dialog('destroy').remove();
				return false;
			}
		});
    };
    
    $.confirm = function(title, message, trueCallback, falseCallback) {
		var _alertDiv = $('<div title="'+title+'" style="line-height: 20px;padding: 30px;">'+message+'</div>');
		_alertDiv.appendTo('body').dialog({
			resizable: false,
			width:500,
			modal: true,
			buttons: {
				Yes: function() {
					if(trueCallback){
						trueCallback.call(window);
					}
					$( this ).dialog( "close" ).dialog('destroy').remove();
				},
				No: function() {
					if(falseCallback){
						falseCallback.call(window);
					}
					$( this ).dialog( "close" ).dialog('destroy').remove();
				}
			},
			close: function(event, ui) {
				if(falseCallback){
					falseCallback.call(window);
				}
				$(this).dialog('destroy').remove();
				return false;
			}
		});
    };
})(jQuery);
