(function($){
    $.fn.mask = function(options) {
        var defaults = { 
            label:"loading...",
            icon:"css/images/ajax-loader.gif",
            secret:false
        };
        var opts = $.extend(defaults, options);
       
        var $element = this;
        if($element.data('isMasked') == true){
        	return;
        }
        $element.data('isMasked', true);
        var $dim = $.extend({}, {width:$element.outerWidth(), height:$element.outerHeight()}, $element.offset());
        var $loadingMask = $('<div id="loading-mask"/>');
        $element.after($loadingMask);
        $loadingMask.width($dim.width).height($dim.height).offset($dim);

        if(opts.secret == false){
            var $loading = $('<div id="loading-indicator">'+opts.label+'</div>').css('background-image', 'url('+opts.icon+')');
            $loadingMask.append($loading);
            $loading.css({left : ($dim.width - $loading.outerWidth())/2,top : ($dim.height - $loading.outerHeight())/2});
        }
    };
    $.fn.unmask = function() {
        var $element = $(this);
        if($element.data('isMasked') == true){
	        $element.data('isMasked', null);
	        $element.next('#loading-mask').remove();
        }
    };
})(jQuery);     