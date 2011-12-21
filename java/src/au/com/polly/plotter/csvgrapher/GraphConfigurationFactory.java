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

import au.com.polly.plotter.AxisConfiguration;
import au.com.polly.plotter.AxisConfigurationFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Factory object which can instantiate axis configuration objects from a set of properties. Wrote this
 * to move the logic for identifying different properties for axes, from the graph configuratin class.
 *
 *
 */
public class GraphConfigurationFactory {
static GraphConfigurationFactory factorySingleton = null;
static Object classLock = new java.util.Date();
protected Pattern integerPattern = Pattern.compile("^\\-?\\d+$");
protected Pattern doublePattern = Pattern.compile("^\\-?\\d+\\.?\\d?$");
protected List<AxisConfiguration> axisConfigurations;
protected DataSeriesConfiguration xSeriesConfiguration;
protected DataSeriesConfiguration ySeriesConfiguration;
protected List<DataSeriesConfiguration> multiYSeriesConfiguration;
protected AxisConfigurationFactory axisConfigFactory;
protected DataSeriesConfigurationFactory dataSeriesConfigFactory;


/**
 *
 * @return the singleton instance of the factory object used to extract data series configurations
 * from properties.
 */
public static GraphConfigurationFactory getInstance()
{
    synchronized(classLock)
    {
        if ( factorySingleton == null )
        {
            factorySingleton = new GraphConfigurationFactory();
        }
    }
    return factorySingleton;
}

/**
 * Please don't invoke directly!! This should only ever be invoked by the getInstance() method.
  */
private GraphConfigurationFactory()
{

}


/**
 *
 * @return the configuration for the single x-axis.
 */
public GraphConfiguration extract( Properties props, String qualifier)
{
    return extract( props, qualifier, null );    
}

/**
 *
 * @return the configuration for the single x-axis.
 */
public GraphConfiguration extract(
        Properties props,
        String qualifier,
        RootConfiguration rootConfiguration
)
{
    GraphConfiguration result = null;
    GraphConfiguration config = new GraphConfiguration();
    boolean populated = false;
    Matcher plotMatcher;
    String plotIdText;
    Map<String,String> plotIdLUT = new HashMap<String,String>();

    // do-while(false) that we can easily break out of.......
    // ---------------------------------------------------
    do {

        if ( axisConfigFactory == null )
        {
            axisConfigFactory = AxisConfigurationFactory.getInstance();
        }

        if ( dataSeriesConfigFactory == null )
        {
            dataSeriesConfigFactory = DataSeriesConfigurationFactory.getInstance();
        }

        if ( rootConfiguration != null )
        {
            config.setRootConfiguration( rootConfiguration );   
        }

/*
        // if no properties were actually supplied, then just return...
        // -----------------------------------------------------------------
        if ( props == null )
        {
            break;
        }
*/
        
        for ( Object keyo : props.keySet() )
        {
            String key = ((String)keyo).toLowerCase().trim();

            // if a qualifier has been specified, and this property entry's name does
            // not start with it, then discard it...
            // -----------------------------------------------------------------
            if ( ( qualifier != null ) && ( ! key.startsWith( qualifier ) ) )
            {
                continue;
            }

            if ( key.endsWith( "title" ) )
            {
                config.setTitle( props.getProperty( key ) );
                populated = true;
                continue;
            }

            if ( key.endsWith( "image_type" ) )
            {
                config.setImageTypeSuffix( props.getProperty( key ) );
                populated = true;
                continue;
            }

            if ( key.endsWith( "file" ) )
            {
                config.setDestinationPath( props.getProperty( key ) );
                populated = true;
                continue;
            }

            if ( key.endsWith( "width" ) )
            {
                config.setWidth( Integer.parseInt(props.getProperty(key)) );
                populated = true;
                continue;
            }

            if ( key.endsWith( "height" ) )
            {
                config.setHeight( Integer.parseInt(props.getProperty(key)) );
                populated = true;
                continue;
            }
        }

        // extract any axis configurations for this graph...
        // --------------------------------------------------------
        if ( ( config.axisConfigurations = axisConfigFactory.extract( props, qualifier ) ) != null )
        {
            for( AxisConfiguration axisConfiguration : config.axisConfigurations )
            {
                axisConfiguration.setGraphConfiguration( config );    
            }
        }


        // now extract any data series configurations for this graph...
        // --------------------------------------------------------
        if ( ( config.xSeriesConfiguration = dataSeriesConfigFactory.extractXSeries( props, qualifier ) ) != null )
        {
            config.xSeriesConfiguration.setGraphConfiguration( config );
            populated = true;
        }

        if ( ( config.ySeriesConfiguration = dataSeriesConfigFactory.extractYSeries( props, qualifier ) ) != null )
        {
            config.ySeriesConfiguration.setGraphConfiguration( config );
            populated = true;
        }


        if ( ( config.multiYSeriesConfiguration = dataSeriesConfigFactory.extractMultiYSeries( props, qualifier ) ) != null )
        {
            for( DataSeriesConfiguration dataConfiguration : config.multiYSeriesConfiguration )
            {
                dataConfiguration.setGraphConfiguration( config );
            }
            populated = true;
        }


    } while( false );

    if ( populated )
    {
        result = config;
    }

    return result;
}



}
