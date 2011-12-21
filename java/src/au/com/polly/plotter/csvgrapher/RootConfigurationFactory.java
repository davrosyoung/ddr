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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Factory object which can instantiate axis configuration objects from a set of properties. Wrote this
 * to move the logic for identifying different properties for axes, from the graph configuratin class.
 *
 *
 */
public class RootConfigurationFactory
{
static RootConfigurationFactory factorySingleton = null;
static Object classLock = new java.util.Date();
protected Pattern graphIdPattern = Pattern.compile("^.*graph\\[(\\d+)\\]");
protected GraphConfigurationFactory graphConfigFactory;

/**
 *
 * @return the singleton instance of the factory object used to extract data series configurations
 * from properties.
 */
public static RootConfigurationFactory getInstance()
{
    synchronized(classLock)
    {
        if ( factorySingleton == null )
        {
            factorySingleton = new RootConfigurationFactory();
        }
    }
    return factorySingleton;
}

/**
 * Please don't invoke directly!! This should only ever be invoked by the getInstance() method.
  */
private RootConfigurationFactory()
{

}


/**
 *
 * @return the configuration for the single x-axis.
 */
public RootConfiguration extract( Properties props, String qualifier )
{
    RootConfiguration result = null;
    RootConfiguration config = new RootConfiguration();
    boolean populated = false;
    Map<Integer,String> graphIdLUT = new HashMap<Integer,String>();
    Integer graphId = null;
    String graphIdText = null;
    Matcher graphIdMatcher = null;

    // do-while(false) that we can easily break out of.......
    // ---------------------------------------------------
    do {

        if ( graphConfigFactory == null )
        {
            graphConfigFactory = GraphConfigurationFactory.getInstance();
        }

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

            if ( key.endsWith( "source_file" ) )
            {
                config.setSourceDataPath( props.getProperty( key ) );
                populated = true;
                continue;
            }

            if ( key.endsWith( "tz" ) )
            {
                config.setTimezone( TimeZone.getTimeZone(props.getProperty(key)) );
                populated = true;
                continue;
            }

            // ok, for each graph, let's store the fully qualified part of the property
            // name, up until the graph part, so that we can ensure that we can extract
            // the properties for each individual graph separately.
            // --------------------------------------------------------------------------------------
            graphIdMatcher = graphIdPattern.matcher( key );
            if ( graphIdMatcher.find() )
            {
                graphIdText = graphIdMatcher.group( 1 );
                graphId = Integer.parseInt(graphIdText);
                if ( ! graphIdLUT.containsKey( graphId ) )
                {
                    graphIdText = graphIdMatcher.group( 1 );
                    graphId = Integer.parseInt(graphIdText);
                    graphIdLUT.put( graphId, graphIdMatcher.group( 0 ) );
                    populated = true;
                    System.out.println( "graphId=\"" + graphId + "\", graphIdMatcher.group(0)=\"" + graphIdMatcher.group( 0 ) + "\"" );
                    System.out.flush();
                }
                continue;
            }
        }

        // ok, now to instantiate and populate any graphs.....
        // ----------------------------------------------------------------------------------------------------
        for( Integer gId : graphIdLUT.keySet() )
        {
            String graphQualifier = graphIdLUT.get( gId );
            GraphConfiguration graphConfig = graphConfigFactory.extract( props, graphQualifier, config );
            config.add( graphConfig );
        }

    } while( false );

    if ( populated )
    {
        result = config;
    }

    return result;
}



}
