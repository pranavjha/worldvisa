var framework = {
	lookupMap:null,
	lookupSelect:{},
	_defferedInitialization:new Array(),
	// screen handling
	_mainScreens:[],
	_currScreen:undefined,
	addScreen:function(data){
		if(framework._currScreen != undefined) {
			framework._mainScreens.push(framework._currScreen);	
		}
		framework._currScreen = data;
	},
	removeScreen:function(){
		framework._currScreen = framework._mainScreens.pop();	
	},
	getScreen:function(){
		return framework._currScreen;
	},
	_clearScreen:function(){
		framework._mainScreens = [];	
		framework._currScreen = {};	
	},
	isActiveScreenModal: function(){
		return framework._mainScreens.length > 1;
	},
	// logged in user
	_user:{},
	getUser:function(){
		return framework._user;
	},
	searchReports:function(){
		framework.loadView($("#mainContainer"), "request/MyAssignments", function(){
			var _searchData = Form.pull($('#search-form-dialog'));
			Form.populate($('#search-form'), _searchData);
			framework.getScreen()._criteria = _searchData;
		});
	},
	// framework initialization
	init : function() {
		var hash = window.location.hash;
		// bind hashchange event
		$(window).on('hashchange', function(){
			// remove the current screen from framework
			framework._clearScreen();
			// remove all dialogues
			$('.jquery-dialog').each(function(){
				$(this).remove();
			});
			$("div.jGrowl").jGrowl("close");
			// load the navigation menu if no menu
			if($('#menuContainer').data('navigationMenu') == undefined){
				CommonView.getMenu(function(data){
					if(data != null){
						$('#menuContainer').navigationMenu(data);
						framework._user = data.user;
					}
				});
			}
			if(framework.lookupMap == null){
				CommonView.getAllLookupMap(function(data){
					framework.lookupMap = data;
					$.each(data, function(key, value){
						if(value.parentLookupId != null){
							if(framework.lookupSelect[value.parentLookupId] == null){
								framework.lookupSelect[value.parentLookupId] = new Array();
							}
							framework.lookupSelect[value.parentLookupId].push(value);
						}
					});
					$.each(framework._defferedInitialization, function(index, value){
						framework.initializeElement(value);
					});
					framework._defferedInitialization = new Array();
				});
			}
			var newHash = location.hash;
			if (newHash.indexOf("#") != -1) {
				newHash = newHash.substring(1);
			}
			framework.loadView($("#mainContainer"), newHash);
		});
		// ajax error handler
		$(window).ajaxError(function(e, jqxhr, settings, exception) {
			if (wondow.console) {
				console.log('ajax error...');
				console.log(e);
				console.log(jqxhr);
				console.log(settings);
				console.log(exception);
			} else {
				alert('ajax error!!');
			}
		});

		// on no hash, redirect to login
		if (!hash) {
			hash = "profile/Login";
			window.location.hash = hash;
		} else {
			$(window).trigger('hashchange');
		}
	},
	loadView:function($container, viewId, callbackBeforeLoad){
		$container.mask({label:"loading... please wait"});
		var __callCount = 0;
		var _initScreen = function() {
			__callCount = __callCount+1;		
			if(__callCount == 3) {
				dwr.engine.beginBatch();
				framework._initializeControls($container, false);
				if(callbackBeforeLoad){
					callbackBeforeLoad.call(this);
				}
				framework.getScreen().load(viewId.split("/"), function(){
					$container.unmask();
				});
				dwr.engine.endBatch();
			}
		};
		var _tokens = viewId.split("/");
		if(_tokens.length < 2){
			throw "invalid hash";
		}
		// load screen
		$.ajaxSetup({cache: true});
		$.getScript('js/' + _tokens[0]+'/'+_tokens[1].toLowerCase() + '.js', _initScreen);
		$.getScript('dwr/interface/'+_tokens[1] + 'View.js', _initScreen);
		$container.load('html/'+ _tokens[0]+'/'+_tokens[1]+'.html', _initScreen);
	},

	_initializeControls : function(domElement) {
		// setup element for initialization
		var _backupId = domElement.attr('id');
		var _newId = ''+new Date().getTime();
		domElement.attr('id', _newId);
		//initialize uniform
		domElement.find('input:checkbox, input:radio').uniform();
		// initialize elements
		domElement.find('[class^="jquery-"], button, option[lookupFetch], option[dwrFetch]')
			.not('#'+_newId+' .jquery-editable-list *, #'+_newId+' .jquery-file-upload *').each(function(){
			framework.initializeElement($(this));
		});
		// setting sendtoserver attribute to true for fields
		domElement.find('input[id], select[id], textarea[id], .jquery-editable-list, .jquery-file-upload')
			.not('[type=button], [type=submit], [immediate]')
			.not('#'+_newId+' .jquery-editable-list *, #'+_newId+' .jquery-file-upload *')
			.attr("sendtoserver", "true").validator();
		// disable fields
		domElement.find('[disabled]')
		.not('#'+_newId+' .jquery-editable-list *, #'+_newId+' .jquery-file-upload *').each(function(){
				$(this).attribute('disabled', true);
		});
		// initialize dialog boxes
		domElement.find('.jquery-dialog').not('#'+_newId+' .jquery-editable-list *, #'+_newId+' .jquery-file-upload *')
			.each(function(){
			var $element = $(this);
 			$element.dialog({
 				resizable : false,
 				autoOpen : false,
 				modal : true,
 				width : ($element.attr('width') ? $element.attr('width') : 800)
 			});
 		});
		// revert element
		domElement.attr('id', _backupId);
		return domElement;
	},
	initializeElement:function($element){
		switch ($element.get(0).nodeName){
        case 'DIV':
        	if($element.hasClass('jquery-tabs')){
            	$element.tabs({
        			selected : 0,
        			fx: { opacity: 'toggle' }
        		});
        	} else if($element.hasClass('jquery-accordion')){
        		$element.accordion({
    				collapsible : true,
    				autoHeight : false,
    				active : parseInt($element.attr('defaultLoaded'))
    			});     	
        	} else if($element.hasClass('jquery-accordion-collapsed')){
        		$element.accordion({
        			active : false,
        			collapsible : true,
        			autoHeight : false
        		});
        	} else if($element.hasClass('jquery-editable-list')){
    			$element.editableList({
    				minrows:parseInt($element.attr('minrows')),
					maxrows:parseInt($element.attr('maxrows'))
    			});
        	} else if($element.hasClass('jquery-file-upload')){
    			$element.fileUpload({allowedTypes:$element.attr('allowedTypes')});
        	}
        	break;
        case 'INPUT':
    		if($element.hasClass('jquery-autosuggest')) {
    			$element.autoSuggest({
    				binding : $element.attr("binding"),
    				multiple : $element.attr("multiselect")
    			});
    		} else if($element.hasClass('jquery-datepicker')) {
        		$element.datepicker({
    				showOn : "button",
    				buttonImage : "css/images/date.png",
    				buttonImageOnly : true,
    				dateFormat : 'd-M-yy',
    				showOn : 'both',
    				changeMonth : true,
    				changeYear : true,
    				minDate: $element.attr('mindate')==null?null:parseInt($element.attr('mindate')), 
    				maxDate: $element.attr('maxdate')==null?null:parseInt($element.attr('maxdate'))
    			}).removeClass('datePicker').css('width', $element.width() - 20)
    			.attr('format', 'date');
    		}  else if($element.hasClass('jquery-datetimepicker')) {
        		$element.datetimepicker({
    				showOn : "button",
    				buttonImage : "css/images/datetime.png",
    				buttonImageOnly : true,
    				dateFormat : 'd-M-yy',
    				showOn : 'both',
    				changeMonth : true,
    				changeYear : true,
    				minDate: $element.attr('mindate')==null?null:parseInt($element.attr('mindate')), 
    				maxDate: $element.attr('maxdate')==null?null:parseInt($element.attr('maxdate')),
    				ampm: true
    			}).removeClass('datePicker').css('width', $element.width() - 20)
    			.attr('format', 'timestamp');
    		} else if($element.hasClass('jquery-daterangepicker')) {
				$element.attr('format', 'daterange').attr("sendtoserver","true").daterangepicker({
					dateFormat : 'd-M-yy'
				});
			}
    		break;
        case 'OPTION':
			var _this = $element;
    		if($element.is('option[lookupFetch]')) {
    			var _select = _this.parent('select');
    			if(framework.lookupSelect[$element.attr("lookupFetch")] == null){
    				framework._defferedInitialization.push(_this);
    			} else {
        			_this.remove();
	    			$.each(framework.lookupSelect[$element.attr("lookupFetch")], function(index, value){
						_select.append($('<option value="'+value.lookupId+'">'+value.lookupDescription+'</option>'));
					});
    				if(_select.data('loadValue')){
    					_select.attribute('value',_select.data('loadValue'));
    				}
    			}
    		} else if($element.is('option[dwrFetch]')){
    			eval($element.attr("dwrFetch")).call(this, function(data){
    				var _select = _this.parent('select');
    				_this.remove();
    				var _label = _this.attr('label');
    				var _value = _this.attr('value');
    				$.each(data, function(index, value){
    					_select.append($('<option value="'+value[_value]+'">'+value[_label]+'</option>'));
    				});
    				if(_select.data('loadValue')){
    					_select.attribute('value',_select.data('loadValue'));
    				}
    			});
    		}
    		break;
        case 'BUTTON':
			var _icon = $element.attr('icon');
			$element.button({
				icons: {
	                primary: _icon
	            }
			}).removeAttr('icon');
			break;
        case 'TEXTAREA':
        	if($element.hasClass('jquery-richeditor')){
        		$element.tinymce({
        			width:'100%',
        			script_url : 'js/framework/tiny_mce/tiny_mce.js',
        			mode : "specific_textareas",editor_selector : "richEditor",theme : "advanced",
        			plugins : "autolink,lists,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,wordcount,advlist,autosave",
        			theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,advhr,|,print,|,ltr,rtl,|,fullscreen",theme_advanced_toolbar_location : "top",theme_advanced_toolbar_align : "left",theme_advanced_statusbar_location : "bottom",
        			theme_advanced_resizing : false, /*content_css : "css/content.css",*/
        			template_external_list_url : "lists/template_list.js",external_link_list_url : "lists/link_list.js",external_image_list_url : "lists/image_list.js",media_external_list_url : "lists/media_list.js",
        			// Style formats
        			style_formats : [ {title : 'Bold text',inline : 'b'}, {title : 'Red text',inline : 'span',styles : {color : '#ff0000'}}, {title : 'Red header',block : 'h1',styles : {color : '#ff0000'}}, {title : 'Example 1',inline : 'span',classes : 'example1'}, {title : 'Example 2',inline : 'span',classes : 'example2'}, {title : 'Table styles'}, {title : 'Table row 1',selector : 'tr',classes : 'tablerow1'} ]
        		});
    		}
        	break;
		}
	},
    errorMessages:{
    	"E00002":"Invalid date value provided for #1#.",
    	"E00003":"Invalid timestamp value provided for #1#.",
    	"E00005":"Invalid numeric value provided for #1#.",
    	"E00006":"Non-English characters are not allowed for #1#.",
    	"E00007":"Invalid email value provided for #1#.",
    	"E00008":"Negative value is not allowed for #1#.",
    	"E00009":"Number of integer digits for #1# are more than allowed #2#.",
    	"E00010":"Invalid phone number provided for #1#.",
    	"E00011":"Invalid currency value provided for #1#.",
    	"E00015":"Number of characters for #1# are less than allowed #2#.",
    	"E00016":"Number of characters for #1# are more than allowed #2#.",
    	"E00017":"Number of bytes for #1# are more than allowed #2#.",
    	"E00018":"Number of decimal digits for #1# are more than allowed #2#.",
    	"E00019":"Invalid value provided for #1#.",
    	"E00020":"Please provide the value for #1#."
	}
};

$(function() {
	framework.init();
	DWRSupport.init();
});