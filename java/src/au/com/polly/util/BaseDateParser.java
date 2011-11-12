package au.com.polly.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 10/11/11
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseDateParser implements DateParser
{
    protected Calendar cal;

    public BaseDateParser()
    {
        cal = Calendar.getInstance();
    }

    abstract public Calendar parse( String text );
}
