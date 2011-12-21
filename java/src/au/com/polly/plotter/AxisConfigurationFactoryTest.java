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

package au.com.polly.plotter;

import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * Battery of tests of the axis configuration class. we need to ensure that we can appropriately populate
 * the axis from a set of poperty values, even if those property values are embedded within other property
 * values with similar names.
 *
 */
public class AxisConfigurationFactoryTest {
    Properties relativeProperties;
    Properties qualifiedProperties;

    @Before
    public void buildProperties()
    {

        relativeProperties = new Properties();
        relativeProperties.put( "axis[1].label", "dave's test" );
        relativeProperties.put( "axis[1].colour", "yellow" );
        relativeProperties.put( "axis[1].min", "auto" );
        relativeProperties.put( "axis[1].max", "auto" );
        relativeProperties.put( "axis[2].label", "second axis" );
        relativeProperties.put( "axis[2].colour", "red" );
        relativeProperties.put( "axis[2].min", "42" );
        relativeProperties.put( "axis[2].max", "97" );
        relativeProperties.put( "axis[2].units", "kg" );
        relativeProperties.put( "axis[3].label", "third axis" );
        relativeProperties.put( "axis[3].colour", "green" );
        relativeProperties.put( "axis[3].min", "15" );
        relativeProperties.put( "axis[3].max", "20" );

        qualifiedProperties = new Properties();
        qualifiedProperties.put( "graph[4].axis[1].label", "james's test" );
        qualifiedProperties.put( "graph[4].axis[1].colour", "gelb" );
        qualifiedProperties.put( "graph[4].axis[1].min", "auto" );
        qualifiedProperties.put( "graph[4].axis[1].max", "auto" );
        qualifiedProperties.put( "graph[4].axis[2].label", "zweite axis" );
        qualifiedProperties.put( "graph[4].axis[2].colour", "red" );
        qualifiedProperties.put( "graph[4].axis[2].min", "43" );
        qualifiedProperties.put( "graph[4].axis[2].max", "96" );
        qualifiedProperties.put( "graph[4].axis[3].label", "dritte axis" );
        qualifiedProperties.put( "graph[4].axis[3].colour", "gruen" );
        qualifiedProperties.put( "graph[4].axis[3].min", "16" );
        qualifiedProperties.put( "graph[4].axis[3].max", "21" );

        qualifiedProperties.put( "graph[5].axis[1].label", "dave's test" );
        qualifiedProperties.put( "graph[5].axis[1].colour", "orange" );
        qualifiedProperties.put( "graph[5].axis[1].min", "auto" );
        qualifiedProperties.put( "graph[5].axis[1].max", "auto" );
        qualifiedProperties.put( "graph[5].axis[2].label", "second axis" );
        qualifiedProperties.put( "graph[5].axis[2].colour", "blue" );
        qualifiedProperties.put( "graph[5].axis[2].min", "41" );
        qualifiedProperties.put( "graph[5].axis[2].max", "98" );
        qualifiedProperties.put( "graph[5].axis[2].units", "polly" );
        qualifiedProperties.put( "graph[5].axis[3].label", "third axis" );
        qualifiedProperties.put( "graph[5].axis[3].colour", "green" );
        qualifiedProperties.put( "graph[5].axis[3].min", "15" );
        qualifiedProperties.put( "graph[5].axis[3].max", "20" );

        qualifiedProperties.put( "graph[6].axis[1].label", "james's test" );
        qualifiedProperties.put( "graph[6].axis[1].colour", "gelb" );
        qualifiedProperties.put( "graph[6].axis[1].min", "auto" );
        qualifiedProperties.put( "graph[6].axis[1].max", "auto" );
        qualifiedProperties.put( "graph[6].axis[2].label", "zweite axis" );
        qualifiedProperties.put( "graph[6].axis[2].colour", "red" );
        qualifiedProperties.put( "graph[6].axis[2].min", "43" );
        qualifiedProperties.put( "graph[6].axis[2].max", "96" );
        qualifiedProperties.put( "graph[6].axis[3].label", "dritte axis" );
        qualifiedProperties.put( "graph[6].axis[3].colour", "gruen" );
        qualifiedProperties.put( "graph[6].axis[3].min", "16" );
        qualifiedProperties.put( "graph[6].axis[3].max", "21" );
    }


    public void testCreatingAxisConfigFactory()
    {
        AxisConfigurationFactory factory = AxisConfigurationFactory.getInstance();
        AxisConfigurationFactory factory2 = AxisConfigurationFactory.getInstance();
        assertNotNull( factory );
        assertNotNull( factory2 );
        assertTrue( factory == factory2 );
    }

    /**
     * Demonstrate that if we define both min and max values for the axis to be autoscaled, that
     * the configuration gets appropriately populated.
     */
    @Test
    public void testExtactingAxesFromRelativeProperties()
    {
        AxisConfiguration config = new AxisConfiguration();
        AxisConfigurationFactory factory = AxisConfigurationFactory.getInstance();
        List<AxisConfiguration> configList = null;
        int count=0;

        configList = factory.extract( relativeProperties, null );
        assertNotNull( configList );
        assertEquals( 3, configList.size() );

        config = configList.get( 0 );
        assertNotNull( config );
        assertEquals( "dave's test", config.getLabel() );
        assertEquals( Color.YELLOW, config.getColour() );
        assertEquals( true, config.isAutoScale() );
        assertNull( config.getUnits() );
        assertNull( config.getMax() );
        assertNull( config.getMin() );

        config = configList.get( 1 );
        assertNotNull( config );
        assertEquals( "second axis", config.getLabel() );
        assertEquals( Color.RED, config.getColour() );
        assertFalse( config.isAutoScale() );
        assertNotNull( config.getUnits() );
        assertEquals( "kg", config.getUnits() );
        assertEquals( 97, config.getMax() );
        assertEquals( 42, config.getMin() );

        config = configList.get( 2 );
        assertNotNull( config );
        assertEquals( "third axis", config.getLabel() );
        assertEquals( Color.GREEN, config.getColour() );
        assertFalse( config.isAutoScale() );
        assertNull( config.getUnits() );
        assertEquals( 20, config.getMax() );
        assertEquals( 15, config.getMin() );
    }

    /**
     * Demonstrate that if we define both min and max values for the axis to be autoscaled, that
     * the configuration gets appropriately populated.
     */
    @Test
    public void testExtractingAxesQualifiedProperties()
    {
        AxisConfiguration config = new AxisConfiguration();
        AxisConfigurationFactory factory = AxisConfigurationFactory.getInstance();
        List<AxisConfiguration> configList = null;
        int count=0;

        configList = factory.extract( qualifiedProperties, "graph[5]" );
        assertNotNull( configList );

        assertEquals( 3, configList.size() );

        assertEquals( "dave's test", configList.get( 0 ).getLabel() );
        assertEquals( Color.ORANGE, configList.get( 0 ).getColour() );
        assertTrue( configList.get( 0 ).isAutoScale() );
        assertNull( configList.get( 0 ).getMin() );
        assertNull( configList.get( 0 ).getMin() );

        assertEquals( "second axis", configList.get( 1 ).getLabel() );
        assertEquals( Color.BLUE, configList.get( 1 ).getColour() );
        assertFalse( configList.get( 1 ).isAutoScale() );
        assertEquals( 41, configList.get( 1 ).getMin() );
        assertEquals( 98, configList.get( 1 ).getMax() );
        assertEquals( "polly", configList.get( 1 ).getUnits() );

        assertEquals( "third axis", configList.get( 2 ).getLabel() );
        assertEquals( Color.GREEN, configList.get( 2 ).getColour() );
        assertFalse( configList.get( 2 ).isAutoScale() );
        assertEquals( 15, configList.get( 2 ).getMin() );
        assertEquals( 20, configList.get( 2 ).getMax() );
        assertNull( configList.get( 2 ).getUnits() );
    }


    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( AxisConfigurationFactoryTest.class );
    }

}
