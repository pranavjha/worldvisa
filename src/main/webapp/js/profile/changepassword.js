framework.addScreen({
	load: function(hashTokens, callback){
		$('#confirmNewPassword').validator('option', 'customValidate', function(value){
			if(value != $('#newPassword').attribute('value')){
				return 'The confirmed password value does not match with the actual password value.';
			}
		});
		
		callback.call(this);
	},
	changePassword:function(){
		Form.submit({
			repopulate : false,
            beforeSubmit : function(data)
            {
            	return $('#newPassword').attribute('value');
            },
            dwrMethod : ChangePasswordView.changePassword,
            sectionId : 'change-password-form-main-form'
		});
	}
});
