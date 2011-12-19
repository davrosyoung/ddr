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

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
protected String wellName = null;
enum FieldType { OIL_FLOW, GAS_FLOW, WATER_FLOW, CONDENSATE_FLOW, TIMESTAMP, INTERVAL_LENGTH, WELL_NAME, UNKNOWN };
enum ProcessMode { BEFORE_HEADINGS, AFTER_HEADINGS };
protected List<FieldType> columnList = null;
protected ProcessMode mode = ProcessMode.BEFORE_HEADINGS;
protected GasWellDataSet dataSet = null;
private static Map<FieldType,Pattern[]> headingPatterns = null;
protected BufferedReader reader = null;


static {
  headingPatterns.put( FieldType.TIMESTAMP, new Pattern[] { Pattern.compile( "^time$"), Pattern.compile( "^date/time$" ), Pattern.compile( "^date") } );
  headingPatterns.put( FieldType.INTERVAL_LENGTH, new Pattern[] { Pattern.compile( "^interval"), Pattern.compile( "^duration" ) } );
  headingPatterns.put( FieldType.OIL_FLOW, new Pattern[] { Pattern.compile( "^oil flow" ), Pattern.compile( "^oil rate") } );
  headingPatterns.put( FieldType.GAS_FLOW, new Pattern[] { Pattern.compile( "^gas flow" ), Pattern.compile( "^gas rate") } );
  headingPatterns.put( FieldType.CONDENSATE_FLOW, new Pattern[] { Pattern.compile( "^gas cond[^\\w]*\\s+rate" ), Pattern.compile( "^gas cond\\. rate" ), Pattern.compile( "^cond[^\\w]*\\s+rate"),Pattern.compile( "^cond\\.*\\s+rate") } );
  headingPatterns.put( FieldType.WATER_FLOW, new Pattern[] { Pattern.compile( "^water flow" ), Pattern.compile( "^water rate") } );
  headingPatterns.put( FieldType.WELL_NAME, new Pattern[] { Pattern.compile( "^well" ) } ); 
};

// if any of the input lines matches this regexp, then it identifies a well to extract data for;
private final static Pattern wellIDPattern = Pattern.compile( "^#\\s*well[:=]\\(.*\\)$" );


protected CSVGasWellDataExtractor( Reader reader )
{
}

protected void process()
{
    String line;
    int lineCount = 0;
    BufferedReader br;
    
    br = ( reader instanceof BufferedReader ) ? (BufferedReader)reader : new BufferedReader( reader );
    
    if ( mwdm == null )
    {
        mwdm = new MultipleWellDataMap();
        try
        {
            while( ( line = br.readLine() ) != null )
            {
                lineCount++;
                
                // ignore comment lines..
                // -----------------------
                if ( line.startsWith( "#") )
                {
                    continue;
                }
                
                if ( mode == ProcessMode.BEFORE_HEADINGS )
                {
                    
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
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

protected static FieldType resolveFieldType( String columnHeading )
{
    return null;
}

}
