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

package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import au.com.polly.util.DateRange;
import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Exercise the methods of the gas well data set utility class.
 */
@RunWith(JUnit4.class)
public class ApplicationConfigurationTest
{
static Logger logger = Logger.getLogger( ApplicationConfigurationTest.class );


public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( ApplicationConfigurationTest.class );
}

@Before
public void setupData()
{


}



@Test
public void testCreatingApplicationConfigurationFromProperties()
{
    Properties properties = new Properties();
    File cwd = new File( "." );
    String cwdPath = null;

    try
    {
        cwdPath = cwd.getCanonicalPath();
    } catch (IOException e) {
        logger.error( "Failed to obtain canonical path!! " + e.getClass().getName() + ":" + e.getMessage() );
        
    }
    logger.debug( "cwd=[" + cwdPath + "]" );

    properties.setProperty( "interval_add_button_label", "AA4" );
    properties.setProperty( "interval_delete_button_label", "AA5" );
    
    ApplicationConfiguration appConf = new ApplicationConfiguration( properties );
    assertNotNull(appConf);

    assertEquals( "AA4", appConf.getIntervalAddButtonLabel() );
    assertEquals( "AA5", appConf.getIntervalDeleteButtonLabel() );
}


}