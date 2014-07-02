(function( $, undefined ) {
$.widget("ui.navigationMenu",
{
    options :
    {
    },
    _create: function(){
    	var _options = this.options.menuList;
    	if(_options == null) return;
    	var _ul = $('<ul class = "jquery-navigation-menu"></ul>');
    	var _li,_cul;
    	$.each(_options, function(index, data){
    		_li = $('<li class="ui-state-default"><a href="#'+data.href+'" title="'+data.description+'">'+data.displayName+'</a></li>');
    		if(data.childMenu != null){
    	    	_li.hover(
	    	        function () {
	    	            $('ul', this).slideDown(100);
	    	            $(this).removeClass('ui-state-default').addClass('ui-state-hover');
	    	        },
	    	        function () {
	    	            $('ul', this).slideUp(100);
	    	            $(this).removeClass('ui-state-hover').addClass('ui-state-default');

	    	        }
	    	    );
    			_cul = $('<ul class="submenu"></ul>');
    			$.each(data.childMenu, function(index, cdata){
    				_cul.append($('<li class="ui-state-default"><a href="#'+cdata.href+'" title="'+cdata.description+'">'+cdata.displayName+'</a></li>')
						.hover(
				    	        function () {
				    	            $(this).removeClass('ui-state-default').addClass('ui-state-hover');
				    	        },
				    	        function () {
				    	            $(this).removeClass('ui-state-hover').addClass('ui-state-default');

				    	        }
				    	    )
    				);
    			});
    			_li.append(_cul);
    		} else {
    			_li.hover(
	    	        function () {
	    	            $(this).removeClass('ui-state-default').addClass('ui-state-hover');
	    	        },
	    	        function () {
	    	            $(this).removeClass('ui-state-hover').addClass('ui-state-default');

	    	        }
	    	    );
    		}
    		_ul.append(_li);
    	});
    	this.element.append(_ul);
    	_ul = $('<ul class="right"/>');
    	if(this.options.user.userRole != 11004) {
	    	_ul.append(
				$('<li></li>').append(
					$('<button>Search Report</button>').button({
		                icons: {
		                    primary: "ui-icon-search"
		                },
		                text: false
					}).bind('hover', function(){
						if(!$('#search-form-dialog').is(":visible")) {
							if('true' != $('#search-form-dialog').attr('initialized')){
								framework._initializeControls($('#search-form-dialog'), false);
								$('#search-form-dialog').attr('initialized', 'true');
							}
							$('#search-form-dialog').slideDown(300);
						}
					})
				)
			);
	    	$('#search-form-dialog').on("mousedown",function(event){
	    			event.stopPropagation();
    			}
			);
	    	$(document).on("mousedown",function(event){
	    		$('#search-form-dialog').slideUp(300);
			}
		);
    	}
    	_ul.append(
			$('<li></li>').append(
				$('<button>Change Password</button>').button({
	                icons: {
	                    primary: "ui-icon-key"
	                },
	                text: false
				}).bind('click', function(){
					window.location.hash="profile/ChangePassword";
				})
			)
		).append(
			$('<li></li>').append(
				$('<button>Logout</button>').button({
	                icons: {
	                    primary: "ui-icon-power"
	                },
	                text: false
				}).bind('click', function(){
					window.location.hash="profile/Logout";
				})
			)
		).append(
			$('<li></li>').append(
				$('<button>Go to Worldvisa Group Website</button>').button({
	                icons: {
	                    primary: "ui-icon-home"
	                },
	                text: false
				}).bind('click', function(){
					window.location.href="http://www.worldvisagroup.com";
				})
			)
		);
    	this.element.append(_ul);
    },
    destroy : function()
    {
    	this.element.empty();
        $.Widget.prototype.destroy.apply(this, arguments); 
    }
});
})( jQuery );