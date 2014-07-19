framework.addScreen({
	communicationId:null,
	load : function(hashTokens, callback) {
		if(hashTokens.length == 3) {
			AddTemplateView.readCommunication({seqNum:hashTokens[2]}, function(data){
				framework.getScreen().communicationId = data.seqNum;
				Form.populate($('#add-communications-form'), data);
				framework.getScreen()._initializeControls(callback);
			});
		} else {
			framework.getScreen()._initializeControls(callback);
		}
	},
	_initializeControls:function(callback){
		callback.call(this);
		// setting up file details for including images in conversation
		$('#attachments .button-cell-header').css('width', '62px');
		$('#attachments tbody tr').each(function(){
			$(this).find('.button-cell').append(
				$('<button>insert in body</button>').button({
	                icons: {
	                    primary: "ui-icon-person"
	                },
	                text: false
				}).bind('click', function(){
					$('#mailBody').val($('#mailBody').val()+'<img src="download/'+$(this).closest('tr').find('.jquery-file-upload').attribute('value')['relativePath']+'"></img>');
				})
			);
		});
		
		$('#attachments').editableList('option', 'rowCallback', function(_tr){
			_tr.find('.button-cell').append(
				$('<button>insert in body</button>').button({
	                icons: {
	                    primary: "ui-icon-person"
	                },
	                text: false
				}).bind('click', function(){
					$('#mailBody').val($('#mailBody').val()+'<img src="download/'+$(this).closest('tr').find('.jquery-file-upload').attribute('value')['relativePath']+'"></img>');
				})
			);
		});
		
		$('#actionType').on('change.main', function(){
			var _value = $(this).attribute('value');
			switch(_value){
			case 12101://mailer
				$('#mailSubject').attribute('visible', true).attribute('submittable', true);
				$('#attachments').attribute('visible', true).attribute('submittable', true);
				break;
			case 12102://sms
				$('#mailSubject').attribute('visible', false).attribute('submittable', false);
				$('#attachments').attribute('visible', false).attribute('submittable', false);
				break;
			case 12103://general update
				$('#mailSubject').attribute('visible', true).attribute('submittable', true);
				$('#attachments').attribute('visible', false).attribute('submittable', false);
				break;
			default:
				$('#mailSubject').attribute('visible', false).attribute('submittable', false);
				$('#attachments').attribute('visible', false).attribute('submittable', false);
				break;
			}
		}).trigger('change.main');
	},
	saveCommunication:function(){
		Form.submit({
			beforeSubmit : function(data)
            {
                return $.extend(data, {seqNum : framework.getScreen().communicationId});
            },
            success: function(data){
            	framework.getScreen().communicationId = data.seqNum;
            },
            dwrMethod : AddTemplateView.saveCommunication,
            sectionId : 'add-communications-form'
		});
	}
});