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

import au.com.polly.util.ColourParser;
import au.com.polly.util.DataType;
import au.com.polly.util.DataTypeParser;
import au.com.polly.util.StringArmyKnife;

import java.awt.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Specifies how a data series should be represented.
 * Contains
 * <ul>
 * <li>Type (long/double)</li>
 * <li>Column ID</li>
 * <li>Axis ID</li>
 * <li>Colour (y-axis colour overrides x-axis colour)</li>
 * </ul>
 *
 */
public class DataSeriesConfiguration
{
DataType type = DataType.UNKNOWN;
int columnID = -1;
int axisID = -1;
Color colour = null;
int markerSize = 8;
protected GraphConfiguration graphConfiguration;  
static Pattern onlyDigitsPattern = Pattern.compile( "^\\d+$" );
static Pattern onlyLettersPattern = Pattern.compile( "^[a-zA-Z]+$" );


    /**
 * Default constructor.
 */
public DataSeriesConfiguration()
{

}

public DataSeriesConfiguration( Properties properties )
{
    this.populate( properties, null );
}

public DataSeriesConfiguration( Properties properties, String qualifier )
{
    this.populate( properties, qualifier );
}

/**
 *
 * @param props set of properties to configure this plot configuration from.
 * @param qualifier a string which each property must start with, in order for it
 * to be applicable to this plot.
 * @return whether any fields/attributes for this data series configuration were populated
 * as a result of this method invocation.
 *
 */
public boolean populate( Properties props, String qualifier )
{
    boolean result = false;

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

        if ( key.endsWith( "column" ) )
        {
            setColumnID( props.getProperty( key ) );
            result = true;
        }

        if ( key.endsWith( "axis" ) )
        {
            setAxisID( Integer.parseInt(props.getProperty(key)) );
            result = true;
        }

        if ( key.endsWith( "type" ) )
        {
            setType( DataTypeParser.parse( props.getProperty( key ) ) );
            result = true;
        }

        if ( key.endsWith( "colour" ) )
        {
            setColour( ColourParser.parse( props.getProperty( key ) ) );
            result = true;
        }

        if ( key.endsWith( "marker_size" ) )
        {
            setMarkerSize( Integer.parseInt(props.getProperty(key)) );
            result = true;
        }
    }

    return result;
}


public DataType getType()
{
    return type;
}

public void setType( DataType type )
{
    this.type = type;
}

public int getColumnID()
{
    return columnID;
}

public void setColumnID( String key )
{
    String refined;
    do {
        // don't bother with null text or empty strings...
        // ------------------------------------------------
        if ( key == null )
        {
            throw new NullPointerException( "NULL value supplied." );
        }

        refined = key.toLowerCase().trim();

        // if no value was supplied, set the column Id to an invalid value...
        // --------------------------------------------------------------------
        if ( refined.length() == 0 )
        {
            setColumnID( -1 );
            break;
        }

        Matcher digitsMatcher = onlyDigitsPattern.matcher( refined );
        if ( digitsMatcher.matches() )
        {
            setColumnID( Integer.parseInt(refined) );
            break;
        } else {
            Matcher alphaMatcher = onlyLettersPattern.matcher( refined );
            // accept Excel style column identifiers... A-Z, AA-AZ, BA-BZ etc...
            // ------------------------------------------------------------------
            if ( alphaMatcher.matches() )
            {
                if ( refined.length() == 1 )
                {
                    setColumnID( (int)( refined.charAt( 0 ) - 'a' ) + 1);
                    break;
                }

                if ( refined.length() == 2 )
                {
                    setColumnID(
                            ( ((int)( refined.charAt( 0 ) - 'a' ) + 1 ) * 26 )
                            + (int)( refined.charAt( 1 ) - 'a' ) + 1 );
                    break;
                }
            }
        }

        // if we could not determine an appropriate value, then set the column ID to be -1
        // ----------------------------------------------------------------------------------
        setColumnID( -1 );
    } while( false );
}

public void setColumnID(int columnID)
{
    this.columnID = columnID;
}

public int getAxisID()
{
    return axisID;
}

public void setAxisID(int axisID)
{
    this.axisID = axisID;
}

public Color getColour()
{
    Color result = Color.BLACK;

    if ( this.colour != null )
    {
        result = this.colour;
    }

    return result;
}

public void setColour(Color colour)
{
    this.colour = colour;
}

public int getMarkerSize()
{
    return markerSize;
}

public void setMarkerSize(int markerSize)
{
    this.markerSize = markerSize;
}


public GraphConfiguration getGraphConfiguration()
{
    return graphConfiguration;
}

public void setGraphConfiguration(GraphConfiguration graphConfiguration)
{
    this.graphConfiguration = graphConfiguration;
}


}
