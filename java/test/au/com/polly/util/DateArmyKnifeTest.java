/*
 * Copyright (c) 2011-2012 Polly Enterprises Pty Ltd and/or its affiliates.
 *  All rights reserved. This code is not to be distributed in binary
 * or source form without express consent of Polly Enterprises Pty Ltd.
 *
 *  
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *  IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package au.com.polly.util;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Exercise the date parser
 */
@RunWith(JUnit4.class)
public class DateArmyKnifeTest
{
DateParser parser = null;

@Before
public void setUp() throws Exception
{
    parser = new AussieDateParser();
}

@Test
public void testFormattingDateWithMilliseconds()
{
    Date stamp = parser.parse( "13/JUN/1968 04:13:59.235" ).getTime();
    String text = DateArmyKnife.format(stamp);
    Assert.assertEquals( "13/JUN/1968 04:13:59.235", text  );
}

@Test
public void testFormattingDateWithMillisecondsTwoDigitYear()
{
    Date stamp = parser.parse( "13/JUN/1968 04:13:59.235" ).getTime();
    String text = DateArmyKnife.format( stamp, false );
    Assert.assertEquals( "13/JUN/68 04:13:59.235", text  );
}

@Test
public void testFormattingDateWithSeconds()
{
    Date stamp = parser.parse( "13/JUN/1968 04:13:59.235" ).getTime();
    String text = DateArmyKnife.formatWithSeconds( stamp );
    Assert.assertEquals( "13/JUN/1968 04:13:59", text  );
}

@Test
public void testFormattingDateWithSecondsTwoDigitYear()
{
    Date stamp = parser.parse( "13/JUN/1968 04:13:59.235" ).getTime();
    String text = DateArmyKnife.formatWithSeconds(stamp, false);
    Assert.assertEquals( "13/JUN/68 04:13:59", text  );
}

@Test
public void testFormattingDateWithMinutes()
{
    Date stamp = parser.parse( "13/JUN/1968 04:13:59.235" ).getTime();
    String text = DateArmyKnife.formatWithMinutes( stamp );
    Assert.assertEquals( "13/JUN/1968 04:13", text  );
}

@Test
public void testFormattingDateWithMinutesTwoDigitYear()
{
    Date stamp = parser.parse( "13/JUN/1968 04:13:59.235" ).getTime();
    String text = DateArmyKnife.formatWithMinutes(stamp, false);
    Assert.assertEquals( "13/JUN/68 04:13", text  );
}

@Test
public void testFormattingCaledarWithMinutesTwoDigitYearAndDashMonthSeparator()
{
    Date stamp = parser.parse( "13/JUN/1968 04:13:59.235" ).getTime();
	Calendar cal = Calendar.getInstance();
	cal.setTime( stamp );
    String text = DateArmyKnife.formatWithMinutes( cal, false, '-' );
    Assert.assertEquals( "13-JUN-68 04:13", text  );
}

@Test
public void testFormattingJustDate()
{
    Date stamp = parser.parse( "13/JUN/1968 04:13:59.235" ).getTime();
    String text = DateArmyKnife.formatJustDate(stamp);
    Assert.assertEquals( "13/JUN/1968", text  );
}

@Test
public void testFormattingJustDateWithTwoDigitYear()
{
    Date stamp = parser.parse( "13/JUN/1968 04:13:59.235" ).getTime();
    String text = DateArmyKnife.formatJustDate(stamp, false);
    Assert.assertEquals( "13/JUN/68", text  );
}

public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( DateArmyKnifeTest.class );
}

}
