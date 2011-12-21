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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Factory object which can instantiate axis configuration objects from a set of properties. Wrote this
 * to move the logic for identifying different properties for axes, from the graph configuratin class.
 *
 *
 */
public class DataSeriesConfigurationFactory
{
static DataSeriesConfigurationFactory factorySingleton = null;
static Object classLock = new java.util.Date();
static Properties defaultProperties = null;
static protected Pattern xSeriesPattern = Pattern.compile("^.*x\\.([^=]+)$");
static protected Pattern ySeriesPattern = Pattern.compile("^.*y\\.([^=]+)$");
static protected Pattern multiYSeriesPattern = Pattern.compile("^.*y\\[(\\d+)\\]\\.([^=]+)$");
protected DataSeriesConfiguration xSeriesConfiguration = null;
protected DataSeriesConfiguration ySeriesConfiguration = null;
protected List<DataSeriesConfiguration> multiYSeriesConfiguration = null;
    

/**
 *
 * @return the singleton instance of the factory object used to extract data series configurations
 * from properties.
 */
public static DataSeriesConfigurationFactory getInstance()
{
    synchronized(classLock)
    {
        if ( factorySingleton == null )
        {
            factorySingleton = new DataSeriesConfigurationFactory();
        }
    }
    return factorySingleton;
}

/**
 * Please don't invoke directly!! This should only ever be invoked by the getInstance() method.
  */
private DataSeriesConfigurationFactory()
{

}

/**
 *
 * @return the configuration for the single x-axis.
 */
public DataSeriesConfiguration extractYSeries(
        Properties props,
        String qualifier
)
{
    DataSeriesConfiguration result = null;
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    boolean populated = false;
    String yQualifier = ( qualifier != null ) ? qualifier + ".y." : "y.";

    // only return a non-null value if we actually populated the x data series config
    // as a result of examining the properties...
    // ---------------------------------------------------------------------------------
    if ( populated = config.populate( props, yQualifier ) )
    {
        result = config;
    }

    return result;
}



/**
 *
 * @return the configuration for the single x-axis.
 */
public DataSeriesConfiguration extractXSeries(
        Properties props,
        String qualifier
)
{
    DataSeriesConfiguration result = null;
    DataSeriesConfiguration config = new DataSeriesConfiguration();
    boolean populated = false;
    String xQualifier = ( qualifier != null ) ? qualifier + ".x." : "x.";

    // only return a non-null value if we actually populated the x data series config
    // as a result of examining the properties...
    // ---------------------------------------------------------------------------------
    if ( populated = config.populate( props, xQualifier ) )
    {
        result = config;
    }

    return result;
}


/**
 *
 * @return the configuration for the multiple y data series, generally used
 * against a single timestamp based x-axis
 */
public List<DataSeriesConfiguration> extractMultiYSeries( Properties props, String qualifier )
{
    List<DataSeriesConfiguration> result = null;
    List<DataSeriesConfiguration> configList = new ArrayList<DataSeriesConfiguration>();
    Map<Integer,String> yIdLUT = new TreeMap<Integer,String>();
    Matcher yIdMatcher = null;
    String idText = null;


    // y[n].type
    // ----------------------------------------------------------------------------
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

        // ok, for each "y" data series, let's store the fully qualified part of the property
        // name, up until the actual attribute part, so that we can ensure that we can extract
        // the properties for each individual y data series separately.
        // --------------------------------------------------------------------------------------
        yIdMatcher = multiYSeriesPattern.matcher( key );
        if ( yIdMatcher.find() )
        {
            idText = yIdMatcher.group( 1 );
            Integer id = Integer.parseInt(idText);
            if ( ! yIdLUT.containsKey( id ) )
            {
                String text = yIdMatcher.group( 0 );
                int cutoff = text.lastIndexOf( "." );
                String subQualifier = text.substring( 0, cutoff );
                yIdLUT.put( id, subQualifier );
            }
        }
    }

    // ok, now to instantiate and populate the y data series .....
    // .... how can we do this, such that the y-axis are populated in the correct order??
    // ----------------------------------------------------------------------------------------------------
    for( Integer id : yIdLUT.keySet() )
    {
        String yQualifier = yIdLUT.get( id );
        DataSeriesConfiguration config = new DataSeriesConfiguration();
        config.populate( props, yQualifier );
        configList.add( config );
    }

    if ( configList.size() > 0 )
    {
        result = configList;
    }

    return result;
}

}
