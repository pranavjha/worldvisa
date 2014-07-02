jQuery.dwrAjax = function(settings)
{

    var _settings = $.extend(
    {
        success : function(data)
        {
        },
        data : [],
        context:document
    }, settings);
    var args = _settings.data.slice().concat( [ function(data)
    {
        _settings["success"].apply(_settings.context, [ data ]);
    } ]);
    eval(_settings["url"]).apply(_settings.context, args);
};