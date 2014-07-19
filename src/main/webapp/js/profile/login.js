framework.addScreen({
	load: function(hashTokens, callback){
		if($.cookie("emailId") != null) {
			LoginView.activeSessionDetails({'emailId':$.cookie("emailId"), 'password':$.cookie("password")}, function(data){
				callback.call(this);
				if(data != null){
					if(framework.isActiveScreenModal()){
						framework.removeScreen();
						$('#inline-login-form').dialog('close').dialog('destroy').remove();
						$(window).trigger('hashchange');
						$("div.jGrowl").jGrowl("close");
						$.jGrowl("INFO","Login attempt successful. The current screen had to be reloaded. Your unsaved changes might have been lost.");
					} else {
						window.location.hash = data.nextScreen;
					}
				} else {
					$('#menuContainer').navigationMenu('destroy');
					$('#login-form-autologin-form').css('display', 'none');
					$('#login-form-main-form').css('display', 'block');
					$.cookie("emailId", null);
					$.cookie("password", null);
				}
			});
		} else {
			$('#menuContainer').navigationMenu('destroy');
			$('#login-form-autologin-form').css('display', 'none');
			$('#login-form-main-form').css('display', 'block');
			$.cookie("emailId", null);
			$.cookie("password", null);
			callback.call(this);
		}
	},
	login:function(){
		var cookieEnabled = $('#enableCookie').attribute('value');
		if(framework.isActiveScreenModal()){
			Form.submit({
				sectionId:"login-form-main-form",
				dwrMethod:LoginView.inlineLogin,
				beforeSubmit: function(data){
					if(cookieEnabled) {
						$.cookie("password", data.password, { expires: 7 });
					}
					return data;
				},
				success : function(data){
					if(cookieEnabled) {
						$.cookie("emailId", data.emailId, { expires: 7 });
					}
					$('#menuContainer').navigationMenu('destroy');
					framework.removeScreen();
					$('#inline-login-form').dialog('close').dialog('destroy').remove();
					$(window).trigger('hashchange');
				}
			});
		} else {
			Form.submit({
				sectionId:"login-form-main-form",
				dwrMethod:LoginView.login,
				beforeSubmit: function(data){
					if(cookieEnabled) {
						$.cookie("password", data.password, { expires: 7 });
					}
					return data;
				},
				success : function(data){
					if(cookieEnabled) {
						$.cookie("emailId", data.emailId, { expires: 7 });
					}
					$('#menuContainer').navigationMenu('destroy');
				}
			});
		}
	},
	forgotPassword:function(){
		Form.submit({
			sectionId:"loginForm",
			dwrMethod:LoginView.forgotPassword
		});
	}
});
