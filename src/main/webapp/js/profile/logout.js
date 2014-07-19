framework.addScreen({
	load: function(hashTokens, callback){
		$("div.jGrowl").jGrowl("close");
		LogoutView.logout(function(data){
			callback.call(this);
			$.cookie("emailId", null);
			$.cookie("password", null);
			window.location.hash="profile/Login";
			if(data.messages){
				$.each(data.messages, function(index, message){
					$.jGrowl(message.severity, message.message);
            	});
            }
		});
	}
});
