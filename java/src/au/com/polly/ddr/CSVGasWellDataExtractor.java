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

package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import au.com.polly.util.DateRange;
import org.apache.log4j.Logger;
import sun.nio.cs.ext.DBCS_ONLY_IBM_EBCDIC_Decoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Obtains gas well data sets from a java serialzed object input stream.
 * file format;
 * # starts a comment line
 * expect column headings such as;
 * date/time,interval length,oil flow,gas flow,water flow
 */
public class CSVGasWellDataExtractor extends BaseGasWellDataExtractor implements GasWellDataExtractor
{
private final static Logger logger = Logger.getLogger( CSVGasWellDataExtractor.class );
protected MultipleWellDataMap mwdm = null;
enum FieldType { OIL_FLOW, GAS_FLOW, WATER_FLOW, CONDENSATE_FLOW, TIMESTAMP, INTERVAL_LENGTH, WELL_NAME, UNKNOWN };
enum ProcessMode { BEFORE_HEADINGS, AFTER_HEADINGS };
protected ProcessMode mode = ProcessMode.BEFORE_HEADINGS;
protected GasWellDataSet dataSet = null;
private static Map<FieldType,Pattern[]> headingPatterns = new HashMap<FieldType,Pattern[]>();
protected BufferedReader reader = null;
protected static DateParser parser = new AussieDateParser();


static {
  headingPatterns.put( FieldType.TIMESTAMP, new Pattern[] { Pattern.compile( "^time.*$"), Pattern.compile( "^date.*") } );
  headingPatterns.put( FieldType.INTERVAL_LENGTH, new Pattern[] { Pattern.compile( "^interval.*"), Pattern.compile( "^int[\\w\\.]+\\s+len[\\w\\.]+$"), Pattern.compile( "^duration$" ), Pattern.compile( "^len.*$" ) } );
  headingPatterns.put( FieldType.OIL_FLOW, new Pattern[] { Pattern.compile( "^oil.*$") } );
  headingPatterns.put( FieldType.GAS_FLOW, new Pattern[] { Pattern.compile( "^gas flow.*$" ), Pattern.compile( "^gas rate.*$") } );
  headingPatterns.put( FieldType.CONDENSATE_FLOW, new Pattern[] { Pattern.compile( "^(gas\\s+)?cond[^\\w\\.]*(\\s+flow)?(\\s+rate)?.*$" ) } );
  headingPatterns.put( FieldType.WATER_FLOW, new Pattern[] { Pattern.compile( "^water.*" ) } );
  headingPatterns.put( FieldType.WELL_NAME, new Pattern[] { Pattern.compile( "^well" ), Pattern.compile( "^well\\s+name$") } );
};

// if any of the input lines matches this regexp, then it identifies a well to extract data for;
private final static Pattern wellIDPattern = Pattern.compile( "^#\\s*well[:=]\\(.*\\)$" );


protected CSVGasWellDataExtractor( Reader reader )
{
    if ( reader == null )
    {
        throw new NullPointerException( "NULL input source specified to constructor. Can't extract CSV data from a null input source!!" );
    }

    this.reader = ( reader instanceof BufferedReader ) ? (BufferedReader)reader : new BufferedReader( reader );
}

protected void process()
{
    String line;
    int lineCount = 0;
    List<FieldType> columnHeadings = null;
    GasWell lastWell = null;
    GasWellDataSet dataSet = null;

    if ( mwdm == null )
    {
        mwdm = new MultipleWellDataMap();
        try
        {
            while( ( line = reader.readLine() ) != null )
            {
                GasWellDataEntry entry = null;
                lineCount++;
                
                // ignore comment lines and blank lines..
                // -----------------------
                if ( line.startsWith("#") || line.trim().length() == 0 )
                {
                    continue;
                }
                
                switch( mode )
                {
                    case BEFORE_HEADINGS:
                        columnHeadings = processColumnHeadings( line );
                        logger.debug( "Obtained column headings ... " );
                        for( FieldType ft : columnHeadings )
                        {
                            logger.debug( ft.name() );
                        }
                        mode = ProcessMode.AFTER_HEADINGS;
                        break;
                    
                    case AFTER_HEADINGS:
                        entry = processDataLine( line, columnHeadings, lineCount, lastWell );
                        
                        if ( ! entry.getWell().equals( lastWell ) )
                        {
                            if ( dataSet != null )
                            {
                                mwdm.addDataSet( dataSet );
                            }
                            
                            dataSet = new GasWellDataSet( entry.getWell() );
                        }

                        dataSet.addDataEntry( entry );
                        lastWell = entry.getWell();
                        
                        break;
                }
            }
            
            if ( ( dataSet != null ) && ( dataSet.getData().size() > 0 ) )
            {
                mwdm.addDataSet( dataSet );
            }
        } catch (IOException e)
        {
            logger.error( "What the!?!?!" );
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

protected static GasWellDataEntry processDataLine( String line, List<FieldType> columnHeadings, int lineCount, GasWell lastWell )
{
    GasWellDataEntry result = new GasWellDataEntry();
    String[] fields = line.split( "," );
    int n = fields.length < columnHeadings.size() ? fields.length : columnHeadings.size();
    Double intervalLengthHours = 0.0;
    Date intervalStart = null;
    
    logger.debug( "line=[" + line + "]" );
    
    if ( fields.length != columnHeadings.size() )
    {
        logger.error( "Number of fields (" + fields.length + ") differs from number of column headings (" + columnHeadings.size() + ") at line " + lineCount );
    }
    
    for ( int i = 0; i < n; i++ )
    {
        String text = fields[ i ].trim();
        Double numeric;

        // ignore fields which are marked with ------
        // -------------------------------------------
        if ( text.startsWith( "----" ) )
        {
            continue;
        }
        
        switch( columnHeadings.get( i ) )
        {
            case UNKNOWN:
                logger.error( "Field i=" + i + " is of type UNKNOWN. data=\"" + text + "\"");
                break;
            
            case CONDENSATE_FLOW:
                numeric = Double.parseDouble( text );
                result.setMeasurement( WellMeasurementType.CONDENSATE_FLOW, numeric );
                break;
            
            case OIL_FLOW:
                numeric = Double.parseDouble( text );
                result.setMeasurement( WellMeasurementType.OIL_FLOW, numeric );
                break;
            
            case WATER_FLOW:
                numeric = Double.parseDouble( text );
                result.setMeasurement( WellMeasurementType.WATER_FLOW, numeric );
                break;
            
            case GAS_FLOW:
                numeric = Double.parseDouble( text );
                result.setMeasurement( WellMeasurementType.GAS_FLOW, numeric );
                break;
            
            case TIMESTAMP:
                intervalStart = parser.parse( text ).getTime();
                break;
            
            case INTERVAL_LENGTH:
                intervalLengthHours = Double.parseDouble( text );
                break;
            
            case WELL_NAME:
                if ( ( lastWell != null ) && text.equals( lastWell.getName() ) )
                {
                    result.setWell( lastWell );
                } else {
                    result.setWell( new GasWell( text ) );
                }
                break;
        }
    }
    
    
    if ( ( intervalStart != null ) && ( intervalLengthHours > 0.0 ) )
    {
        result.setDateRange( new DateRange( intervalStart, (long)Math.round( intervalLengthHours * 3600.0 ) * 1000, 1000L ) );
    } else {
        logger.error( "Failed to obtain date range for entry at line=" + lineCount );
    }

    return result;
}

public MultipleWellDataMap extract()
{
    if ( mwdm == null )
    {
        process();
    }
    return mwdm;
}

protected static List<FieldType> processColumnHeadings( String line )
{
    List<FieldType> result = null;
    
    if ( line == null )
    {
         throw new NullPointerException( "NULL line passed!! How am I going to process the column headings in that?!?" );
    }
    
    if ( line.trim().length() == 0 )
    {
         throw new IllegalArgumentException( "Blank line passed!! How am I going to process the column headings in that?!?" );
    }
    
    String[] headings = line.split( "," );
    
    if ( ( headings != null ) && ( headings.length > 0 ) )
    {
        result = new ArrayList<FieldType>();
        for( String heading : headings )
        {
            FieldType ft = resolveFieldType( heading );
            if ( ft != FieldType.UNKNOWN )
            {
                result.add( ft );
            } else {
                result.add( ft );
                logger.error( "Unable to resolve column heading \"" + heading + "\"" );
            }
        }
    }

    return result;
}

/**
 *
 * @param columnHeading text containing a candidate column heading
 * @return the type of field that the column heading text represents.
 */
protected static FieldType resolveFieldType( String columnHeading )
{
    FieldType result = FieldType.UNKNOWN;
    
    if ( columnHeading == null )
    {
        throw new NullPointerException( "NULL column heading specified. Can't resolve that!!" );
    }
    String refined = columnHeading.toLowerCase().trim();

    for( FieldType fieldType : headingPatterns.keySet() )
    {
        Pattern[] patterns = headingPatterns.get( fieldType );
        for( int i = 0; ( i < patterns.length ) && ( result == FieldType.UNKNOWN ); i++ )
        {
            Matcher matcher = patterns[ i ].matcher( refined );
            if ( matcher.matches() )
            {
                result = fieldType;
            }
        }

        if ( result != FieldType.UNKNOWN )
        {
            break;
        }
    }

    return result;
}

}
