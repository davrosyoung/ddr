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

package au.com.polly.plotter.csvgrapher;

import junit.framework.JUnit4TestAdapter;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 *
 * Battery of tests of the data series class.
 *
 */
public class GraphConfigurationTest
{

/**
 * Demonstrate that we don't get a null pointer exception if we pass a null pointer, rather than an
 * actual set of properties to populate a graph configurtion object.
 */
@Test
public void testCreatingDefaultGraphConfig()
{
    Properties props = null;
    GraphConfiguration config = new GraphConfiguration();
    assertNotNull( config );
    assertNull( config.getTitle() );
    assertNull( config.getDestinationPath() );
    assertNull( config.getAxisConfigurations() );
    assertNull( config.getXSeriesConfiguration() );
    assertNull( config.getYSeriesConfiguration() );
    assertNull( config.getMultiYSeriesConfiguration() );
}

@Test
public void testSettingDestinationPath()
{
    GraphConfiguration config = new GraphConfiguration();
    assertNotNull( config );
    assertNull( config.getTitle() );
    assertNull( config.getDestinationPath() );
    assertNull( config.getAxisConfigurations() );
    assertNull( config.getXSeriesConfiguration() );
    assertNull( config.getYSeriesConfiguration() );
    assertNull( config.getMultiYSeriesConfiguration() );
    assertNull( config.getImageTypeSuffix() );


    config.setDestinationPath( "logs/test.png" );
    assertNotNull( config.getDestinationPath() );
    assertEquals( "logs/test.png", config.getDestinationPath() );
    assertEquals( "png", config.getImageTypeSuffix() );
}

public static junit.framework.Test suite()
{
    return new JUnit4TestAdapter( GraphConfigurationTest.class );
}
}
