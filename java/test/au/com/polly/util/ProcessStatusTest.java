/*
 * Copyright (c) 2011-2011 Polly Enterprises Pty Ltd and/or its affiliates.
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

import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Exercise the date parser
 */
@RunWith(JUnit4.class)
public class ProcessStatusTest
{


@Before
public void setUp() throws Exception
{

}

@Test
public void testConstructor()
{
    List<String> errors;
    List<String> warnings;
    List<String> info;
    
    ProcessStatus status = new ProcessStatus();
    assertNotNull(status);
    assertEquals(0, status.getPercentageComplete());
    assertEquals( 0, status.numberErrors() );
    assertEquals( 0, status.numberWarnings() );
    
    errors = status.getErrorMessages();
    warnings = status.getWarningMessages();
    info = status.getInfoMessages();
    
    assertNotNull( errors );
    assertEquals( 0, errors.size() );
    
    assertNotNull( warnings );
    assertEquals( 0, warnings.size() );
    
    assertNotNull( info );
    assertEquals( 0, info.size() );
    
    assertEquals( "processing", status.getPhase() );
}

@Test
public void testAddingMessages()
{
    List<String> errors;
    List<String> warnings;
    List<String> info;

    ProcessStatus status = new ProcessStatus();
    assertNotNull(status);
    assertEquals(0, status.getPercentageComplete());
    assertEquals( 0, status.numberErrors() );
    assertEquals( 0, status.numberWarnings() );

    errors = status.getErrorMessages();
    warnings = status.getWarningMessages();
    info = status.getInfoMessages();

    assertNotNull( errors );
    assertEquals( 0, errors.size() );

    assertNotNull( warnings );
    assertEquals( 0, warnings.size() );

    assertNotNull( info );
    assertEquals( 0, info.size() );

    assertEquals( "processing", status.getPhase() );  
    
    status.addErrorMessage( "Oh no ... it broke!!" );
    status.addErrorMessage( "The CPU is on fire!!" );
    status.addErrorMessage( "Please plug power cord back in!!" );
    
    status.addWarningMessage( "I wouldn't do that if I were you!!");
    status.addWarningMessage( "No I cannot calculate the square root of a negative number for you!!");
    
    status.addInfoMessage( "You are now broke!" );
    
    status.setPhase( "breaking down", 73 );
    
    assertEquals( 3, errors.size() );
    assertEquals( 2, warnings.size() );
    assertEquals( 1, info.size() );
    
    assertEquals( 3, status.numberErrors() );
    assertEquals( 2, status.numberWarnings() );
    
    assertEquals( "breaking down", status.getPhase() );
    assertEquals( 73, status.getPercentageComplete() );
}

public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( ProcessStatusTest.class );
}

}
