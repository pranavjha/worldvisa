/******************************************************************************
 * Name : BasicUtils.java Author : Administrator Date : 02-Sep-2010,5:21:28 AM Description : Implementation for BasicUtils
 *****************************************************************************/
package com.worldvisa.business.infra.utils;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import java.util.Calendar;

/**
 * 
 */
public class BasicUtils {
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static <V> Future<V> executeAsThread(Callable<V> task) {
        return BasicUtils.executor.submit(task);
    }

    public static void executeAsThread(Runnable task) {
        BasicUtils.executor.execute(task);
    }

    public static Date getCurrentDate() {
        final GregorianCalendar c = new GregorianCalendar();
        c.setTime(BasicUtils.getCurrentDateTime());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getCurrentDateTime() {
        final long thistime = new Date().getTime();
        return new Date(thistime - TimeZone.getDefault().getOffset(thistime) + TimeZone.getTimeZone("IST").getOffset(thistime));
    }

    public static Date truncDate(final Date date) {
        final GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public String evaluate(final String javascriptString) {
        final Context cx = Context.enter();
        try {
            final Scriptable scope = cx.initStandardObjects();
            final Object result = cx.evaluateString(scope, javascriptString, "<cmd>", 1, null);
            return Context.toString(result);
        } finally {
            Context.exit();
        }
    }
}
