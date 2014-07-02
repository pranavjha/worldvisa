var DWRSupport =
{
	_callCount:0,
	_authError: false,
    init : function()
    {	
    	dwr.engine.setPreHook(function(){
    		DWRSupport._callCount++;
    		$('#global-loading-indicator').fadeIn();
    	});
    	dwr.engine.setPostHook(function(){
    		DWRSupport._callCount--;
    		if(DWRSupport._callCount == 0){
        		$('#global-loading-indicator').fadeOut();
    		}
    	});
        dwr.engine.setErrorHandler( function(msg, exc)
        {
    		$.each(this.handlers, function(index, handler){
	            if(handler.exceptionArg){
	                $(handler.exceptionArg).unmask();
	            }
    		});
        	if(exc.type=='com.worldvisa.business.infra.exception.AutenticationException'){
        		DWRSupport._loginAgain();
        	} else if(exc.type=='com.worldvisa.business.infra.exception.AuthorizationException'){
        		$("div.jGrowl").jGrowl("close");
        		$.jGrowl("ERROR","You are not authorized to view this page.");
        	} else if(exc.type=='com.worldvisa.business.infra.exception.DWRException'){
        		$("div.jGrowl").jGrowl("close");
        		$.jGrowl(exc.severity,exc.message);
            } else {
            	$.jGrowl('ERROR',"An Unexpected error has occured. Please try again.");
            }
        });
        dwr.engine.setTextHtmlHandler( function()
        {
        	DWRSupport._loginAgain();
        });
    },
    _loginAgain : function(){
    	if($('#inline-login-form').size() != 0 && $('inline-login-form').dialog( "isOpen" )){
    		return;
    	}
    	var _dialog = $('<div id="inline-login-form" title="Login"></div>');
    	_dialog.appendTo($('body')).dialog({
			resizable : false,
			autoOpen : true,
			modal : true,
			width : 800
		});
    	framework.loadView(_dialog, 'profile/Login');
        $("div.jGrowl").jGrowl("close");
    	$.jGrowl("ERROR","You are logged out of the system! Please Login Again");
    }
};