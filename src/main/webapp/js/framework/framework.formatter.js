var Formatter =
{
    getAsObject : function(value, format)
    {
        if (null == format || value == null || value instanceof Array)
        {
            return value;
        }
        value = $.trim(value);
        if (value.length == 0)
        {
            return null;
        }
        format = format.split(/[(,)]/);
        switch ($.trim(format[0]))
        {
            case 'date':
                return $.datepicker.parseDate('d-M-yy', value);
            case 'daterange':
                var inputDate = value.split('to');
                var retVal = new Array();
                retVal[0] = $.datepicker.parseDate('d-M-yy', $.trim(inputDate[0]));
                retVal[1] = $.datepicker.parseDate('d-M-yy', $
                        .trim(inputDate[inputDate.length - 1]));
                return retVal;
            case 'timestamp':
            	retVal = $.datepicker.parseDate('d-M-yy', value);
                var _valueSplit = value.split(" ");
                retVal.setHours(parseInt(_valueSplit[1].split(':')[0]%12));
                retVal.setMinutes(parseInt(_valueSplit[1].split(':')[1]));
                if(_valueSplit[2] == 'pm'){
                	retVal.setHours(retVal.getHours()+12);
                }
                return retVal;
            case 'positiveNumeric':
            case 'numeric':
                if (format.length > 2 && $.trim(format[2]).length > 0)
                {
                    return parseFloat(value).toFixed(parseInt(format[2]));
                }
                else
                {
                    return parseFloat(value);
                }
            case 'currency':
            	return parseFloat('0'+value.replace(/[a-zA-Z. ]*/,"")).toFixed(2);
            default:
                return value;
        }
    },
    getAsString : function(value, format)
    {
        if(null == value){
            return "";
        }
        if (null == format)
        {
            return value;
        }
        format = format.split(/[(,)]/);
        switch ($.trim(format[0]))
        {
            case 'date':
                return $.datepicker.formatDate('d-M-yy', value);
            case 'daterange':
                return ($.datepicker.formatDate('d-M-yy', value[0]) + " to " + $.datepicker.formatDate('d-M-yy',
                        value[value.length - 1]));
            case 'timestamp':
                return $.datepicker.formatDate('d-M-yy', value) + ' ' + 
                	(((value.getHours()+11)%12+1)<10?'0':'') + ((value.getHours()+11)%12+1) + ':' +
                	(value.getMinutes()<10?'0':'') + value.getMinutes()+' '+((value.getHours() < 12)?'am':'pm');
            case 'positiveNumeric':
            case 'numeric':
                if (format.length > 2 && $.trim(format[2]).length > 0)
                {
                    return ''+parseFloat(value).toFixed(parseInt(format[2]));
                }
                else
                {
                    return ''+parseFloat(value);
                }
            case 'currency':
            	return 'Rs. '+parseFloat(value).toFixed(2);
            default:
                return value;
        }

    },
    format : function(value, format)
    {
        if (null == value)
        {
            return "";
        }
        if(value instanceof Array){
            return value;
        }
        value = $.trim(value);
        if (null == format || value.length == 0)
        {
            return value;
        }
        format = format.split(/[(,)]/);
        switch (format[0])
        {
            case 'positiveNumeric':
            case 'numeric':
                if (format.length > 2 && $.trim(format[2]).length > 0)
                {
                    return ''+parseFloat(value).toFixed(parseInt(format[2]));
                }
                else
                {
                    return ''+parseFloat(value);
                }
            case 'currency':
            	return 'Rs. '+parseFloat(value).toFixed(2);
            default:
                return value;
        }
    }
};