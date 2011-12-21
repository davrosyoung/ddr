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
public class AxisConfigurationFactory
{
static AxisConfigurationFactory factorySingleton = null;
static Object classLock = new java.util.Date();
static Properties defaultProperties = null;
static protected Pattern axisPattern = Pattern.compile("^.*axis\\[(\\d+)\\]");

public static AxisConfigurationFactory getInstance()
{
    synchronized( classLock )
    {
        if ( factorySingleton == null )
        {
            factorySingleton = new AxisConfigurationFactory();
        }
    }
    return factorySingleton;
}

protected AxisConfigurationFactory()
{

}

/**
 *
  * @param props the set of properties to populate the set of axis configurations with.
 * @param qualifier if not null, then only examine those properties whose names start with this qualifier.
 * @return the list of axis configurations extracted from the specified set of properties, where the
 * property names are qualified by the specified qualifier.
 */
public List<AxisConfiguration> extract( Properties props, String qualifier )
{
    List<AxisConfiguration> result = new ArrayList<AxisConfiguration>();

    Matcher axisMatcher;
    String axisIdText;
    Map<Integer,String> axisIdLUT = new TreeMap<Integer,String>();

    // axis[n]

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


        axisMatcher = axisPattern.matcher( key );
        if ( axisMatcher.find() )
        {
            axisIdText = axisMatcher.group( 1 );
            Integer axisId = Integer.parseInt(axisIdText);
            if ( ! axisIdLUT.containsKey( axisId ) )
            {
                axisIdLUT.put( axisId, axisMatcher.group( 0 ) );
            }
        }

    }

    // ok, now to instantiate and populate any axes.....
    // ----------------------------------------------------------------------------------------------------
    for( Integer axisId : axisIdLUT.keySet() )
    {
        String axisQualifier = axisIdLUT.get( axisId );
        AxisConfiguration axisConfig = new AxisConfiguration();
        axisConfig.populate( props, axisQualifier );
        result.add( axisConfig );
    }

    return result;
}

}