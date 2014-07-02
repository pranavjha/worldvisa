var Form =
{
    submit : function(settings)
    {
        var _settings = $.extend(
        {},
        {
            mask : true,
            message : "submitting form...",
            validate : true,
            repopulate : true,
            beforeSubmit : function(data)
            {
                return data;
            },
            success : function(data)
            {
            }
        }, settings);
        var _valid = true;
        if (_settings.validate)
        {
            _valid = Form.validate($('#' + _settings.sectionId));
        }
        if (_valid)
        {
            if (_settings.mask) {
                $('#' + _settings.sectionId).mask(
                {
                    label : _settings.message
                });
            }
            _settings.dwrMethod.call(this, _settings.beforeSubmit(Form.pull($('#' + _settings.sectionId))),
            {
                arg : ("#" + _settings.sectionId),
                callback : function(data, arg)
                {
                    if (_settings.repopulate && data.mainObject)
                    {
                        Form.clear($(arg));
                        Form.populate($(arg), data.mainObject);
                    }
                    $(arg).unmask();
                    if (_settings.success)
                    {
                        _settings.success.call(this, data.mainObject);
                    }
                    if(data.messages){
                    	$.each(data.messages, function(index, message){
                    		$.jGrowl(message.severity, message.message);
                    	});
                    }
                    if(data.nextScreen){
                    	window.location.hash = data.nextScreen;
                    }
                }
            });
        }
    },
    populate : function($element, data)
    {
        Form._recursive_populate($element, data, "");
    },
    pull : function($element)
    {
        var $retVal = {};
        $element.find('[sendtoserver=true]').each( function()
        {
            var id = $(this).attr("id").split("-");
            var $valAdd = $retVal;

            if (id.length == 1 && id[0].length == 0)
            {
                return;
            }
            for ( var i = 0; i < id.length - 1; i++)
            {
                if ($valAdd[id[i]] == null)
                    $valAdd[id[i]] =
                    {};
                $valAdd = $valAdd[id[i]];
            }
            $valAdd[id[id.length - 1]] = $(this).attribute('value');
        });
        return $retVal;
    },
    clear : function($element)
    {
        $element.find('[sendtoserver=true]').each( function()
        {
            $(this).attribute('value', null);
        });
    },
    validate : function($element)
    {
        $("div.jGrowl").jGrowl("close");
        var errors = new Array();
        $element.find('[sendtoserver=true]').each( function()
        {
            if ($(this).data('validator') != null)
            {
                var _message = $(this).data('validator').validate();
                if (_message != null)
                    errors.push(
                    {
                        element : $(this),
                        message : _message
                    });
            }
        });
        if (errors.length > 0)
        {
            var $messages = errors.length + ' error(s) encountered during data validation.<div><ul class="jgrowl-grouped-messages">';
            $
                    .each(
                            errors,
                            function(index, value)
                            {
                                $messages += ('<li onclick='
                                        + '"$(\'#'
                                        + value.element.attr('id')
                                        + '\').closest(\'.jquery-accordion\').filter(\':not(:has(.ui-state-active))\').accordion(\'activate\', 0);$(\'#'
                                        + value.element.attr('id') + '\').focus().select();">' + value.message + '</li>');
                            });
            $messages += '</ul></div>';
            $.jGrowl('error', $messages);
            return false;
        }
        return true;
    },
    _recursive_populate : function($element, data, prependId)
    {
        $.each(data, function(key, value)
        {
            if (value instanceof Object && !(value instanceof Array) && !(value instanceof Date) && $element.find('#' + prependId + key).size() == 0)
            {
                Form._recursive_populate($element, value, prependId + key + '-');
            }
            else
            {
                $element.find('#' + prependId + key).each(function(){
                	$(this).attribute('value', value);
                });
            }
        });
    }
};