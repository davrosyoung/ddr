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

package au.com.polly.plotter;

import au.com.polly.plotter.csvgrapher.GraphConfiguration;
import au.com.polly.util.ColourParser;

import java.awt.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The metadata which causes an axis to be displayed in the appropriate fashion.
 * This can either be setup manually, or read from a configuration file. The axis
 * can either have it's range (min and max) set explicitly, or it can be set to
 * auto scaling instead.
 *
 * This was written to assist with graphing data extracted from the watson project. It
 * attempts to decouple the rendering "hints" for the axis, from the data model of the
 * axis itself.
 *
 */
public class AxisConfiguration
{
String label = null;
String units = null;
Color colour = null;
Color gridColour = null;
int length;
boolean autoScale = true;
Number min = null;
Number max = null;
final private static Pattern integerPattern = Pattern.compile("^\\-?\\d+$");
final private static Pattern doublePattern = Pattern.compile("^\\-?\\d+\\.?\\d?$");
protected GraphConfiguration graphConfiguration = null;

public AxisConfiguration()
{
    // default constructor.
}

public AxisConfiguration(
        String label,
        String units,
        Color colour,
        int length
)
{
    this( label, units, colour, colour, length );
}

public AxisConfiguration(
        String label,
        String units,
        Color colour,
		Color gridColour,
        int length
)
{
    setLabel( label );
    setUnits( units );
    setColour( colour );
	setGridColour( gridColour );
    setPlotLength( length );
}

/**
 *
  * @param props
 *
 * populate from a set of properties, which are only intended for this axis (ie: there is not configuration
 * for other components which we might get confused by).
 */
public void populate( Properties props )
{
    populate( props, null );
}

/**
 *
 * @param props set of properties to configure this axis configuration from.
 * @param qualifier a string which each property must start with, in order for it
 * to be applicable to this axis.
 *
 * Enables the axis configuration to be extracted from a set of properties. Only the subset of those properties
 * that begin with the qualifier string will be used for populating this axis configuration.
 */
public void populate( Properties props, String qualifier )
{
    String minText = "auto";
    String maxText = "auto";
    Number min;
    Number max;
	boolean gridColourSpecified = false;

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

        if ( key.endsWith( "label" ) )
        {
            setLabel( props.getProperty( key ) );
            continue;
        }

		if ( key.contains( "grid" ) && ( key.endsWith( "colour" ) || key.endsWith( "color" ) ) )
		{
			setGridColour( props.getProperty( key ) );
			gridColourSpecified = true;
			continue;
		}

        if ( key.endsWith( "colour" ) || key.endsWith(  "color" ) )
        {
            setColour( props.getProperty( key ) );
            continue;
        }

        if ( key.endsWith( "units" ) )
        {
            setUnits( props.getProperty( key ) );
        }

        if ( key.endsWith( "min" ) )
        {
            minText = props.getProperty( key );
        }

        if ( key.endsWith( "max" ) )
        {
            maxText = props.getProperty( key );
        }
    }

	if ( ! gridColourSpecified )
	{
		setGridColour( getColour() );
	}

    // break out of this do-while loop, as soon as we find the appropriate format
    // for specifying the range for this axis.
    // ------------------------------------------------------------------------------
    do {
        // do the min and max values look like numbers??

        // ... ok check for integer format first..
        // -------------------------------------------------
        Matcher minIntMatcher = integerPattern.matcher( minText );
        Matcher maxIntMatcher = integerPattern.matcher( maxText );
        if ( minIntMatcher.find() && maxIntMatcher.find() )
        {
            setRange( Long.parseLong(minText), Long.parseLong(maxText) );
            break;
        }

        // ok ... now check for doubles..
        // ------------------------------------
        Matcher minDoubleMatcher = doublePattern.matcher( minText );
        Matcher maxDoubleMatcher = doublePattern.matcher( maxText );
        if ( minDoubleMatcher.find() && maxDoubleMatcher.find() )
        {
            setRange( Double.parseDouble(minText), Double.parseDouble(maxText) );
            break;
        }

        // if we haven't matched appopriate numeric formats for the range, then
        // set the axis config to auto scaling.
        // -----------------------------------------------------------------------
        setAutoScale( true );

    } while( false );

    return;
}

/**
 *
 * @param label label to be applied to this axis.
 */
public void setLabel( String label )
{
    this.label = label;
}

public String getLabel()
{
    return this.label;
}

/**
 *
 * @param units units of measure (if any) to be applied to this axis.
 */
public void setUnits( String units )
{
    this.units = units;
}

/**
 *
 * @return units of measure (if any) to be applied to this axis.
 */
public String getUnits()
{
    return this.units;
}

/**
 *
 * @param colourName name of the colour desired for this axis
 *
 * convenience method, allowing colour to be set by name, rather than using
 * a java.awt.Color object.
 */
public void setColour( String colourName )
{
    Color colour = ColourParser.parse( colourName );

    if ( colour != null )
    {
        setColour( colour );
    }
}

/**
 *
 * @param colour what colour to paint this axis in.
 */
public void setColour( Color colour )
{
    this.colour = colour;
}

/**
 *
 * @return if a colour has been specified for this axis, it will return that colour,
 * otherwise black will be returned.
 */
public Color getColour()
{
    Color result = Color.BLACK;

    if ( this.colour != null )
    {
        result = colour;
    }

    return result;
}

/**
 *
 * @param colourName name of the colour desired for this axis
 *
 * convenience method, allowing colour to be set by name, rather than using
 * a java.awt.Color object.
 */
public void setGridColour( String colourName )
{
    Color colour = ColourParser.parse( colourName );

    if ( colour != null )
    {
        setGridColour(colour);
    }
}

/**
 *
 * @param colour what colour to paint this axis in.
 */
public void setGridColour( Color colour )
{
    this.gridColour = colour;
}

/**
 *
 * @return if a colour has been specified for this axis, it will return that colour,
 * otherwise black will be returned.
 */
public Color getGridColour()
{
    Color result = Color.BLACK;

    if ( this.gridColour != null )
    {
        result = this.gridColour;
    }

    return result;
}


/**
 * @param length how many pixels in length, the values along this axis are plotted against.
 */
public void setPlotLength( int length )
{
    this.length = length;
}

/**
 * @return how many pixels in length, the values along this axis are plotted against.
 */
public int getPlotLength()
{
    return this.length;
}

/**
 *
 * @return whether or not the range for this axis should be determined automatically
 * dependant upon the data which is going to be plotted along it.
 */
public boolean isAutoScale()
{
    return autoScale;
}


/**
 *
 * @param autoScale whether or not the range for this axis should be determined automatically
 * dependant upon the data which is going to be plotted along it.
 */
public void setAutoScale(boolean autoScale)
{
    this.autoScale = autoScale;
}


/**
 *
 * @param min the minimum value to plot against this axis. if specified, then
 * autoscaling will be turned off, and a maximum value will need to be specified.
 */
public void setRange( Number min, Number max )
{
    this.min = min;
    this.max = max;
    this.autoScale = false;
}


/**
 * @return the minimum value to plot against this axis. not applicable if this axis
 * has been set for autoscaling.
 */
public Number getMin()
{
    return min;
}


/**
 * @return the minimum value to plot against this axis. not applicable if this axis
 * has been set for autoscaling.
 */
public Number getMax()
{
    return max;
}

/**
 *
 * @return the graph configuration that this axis configuration belongs to.
 */
public GraphConfiguration getGraphConfiguration()
{
    return graphConfiguration;
}

/**
 *
 * @param graphConfiguration the graph configuration to set for this axis.
 */
public void setGraphConfiguration(GraphConfiguration graphConfiguration)
{
    this.graphConfiguration = graphConfiguration;
}


}
