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

package au.com.polly.util.csv;


import au.com.polly.util.StringArmyKnife;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;


public abstract class Parser
implements Runnable
{
    private final static Logger logger = Logger.getLogger(Parser.class);

    /**
      * Registry of those objects who want to know when we process a record
      * or come across the end of the data source.
      */
    protected	HashMap	listeners			= null;

    /**
      * How many lines within the data source have we processed?
      */
    private 	int					lineCount			= 0;

    /**
      * How many records have been generated (remember that the lineCount
      * included blank lines, column headings and comment lines).
      */
    private 	int					recordCount			= 0;

    /**
      * Maps a column index to the label of the field alias used
      * for that field.
      *
      */
    private		String[]			columnLabelLUT		= null;

    /**
      * Maps a column labe to the alias used.
      *
      */
    private		HashMap			labelAliasLUT		= null;

    /**
      * Maps a column index to the index within the aliases array.
      *
      */
    private		int[]				columnLUT			= null;

    private		BufferedReader		reader				= null;
    private		LineSplitter		splitter			= null;


    /**
      * Supplied by caller, determines what tag data fields get mapped to
      * according to what column heading they fall under and whether or not
      * the the data columns are mandatory or not.
      */
    private		FieldAliases[]		aliases				= null;

    private		boolean				finished			= false;

    /**
      * The last exception generated. Flushed clean when starting to process
      * a new line of data.
      */
    private		Throwable			error				= null;

    /**
      * error messages.
      */
    private		Vector				errors				= null;

    /**
      * What sequence of characters is used to separate one line of output
      * from another?
      */
    private static	String			lineSeparator		= null;

    /**
     * Character sequence defining the start of a substitution field
     */
    private String substitutionFieldStart = "${";
    /**
     * Character sequence defining the end of a substitution field
     */
    private String substitutionFieldEnd = "}";

    /**
      * Default constructor ... reader and aliases will need to be set.
      */
    public Parser()
    {
    }

    /**
      * @param stream data source to extract the comma separated input from.
      * @param aliases specifies how the columns are named...
      */
    public Parser(
            InputStream 	stream,
            FieldAliases[]	aliases
        )
    {
        this( new InputStreamReader( stream ), aliases );
    }

    /**
      * @param reader data source to extract the comma separated input from.
      * @param aliases specifies how the columns are named...
      *
      */
    public Parser(
            Reader				reader,
            FieldAliases[]		aliases
        )
    {
        setReader( reader );
        this.aliases	= aliases;
        this.splitter	= new LineSplitter();
        this.errors		= new Vector();

        if ( logger.isDebugEnabled() ) { logger.debug( "aliases.length=" + ( ( aliases != null ) ? Integer.toString( aliases.length ) : "<NULL>" ) ); }
    }

    public void setReader( java.io.Reader reader )
    {
        this.reader = ( this.reader instanceof BufferedReader )
                        ? (BufferedReader) reader
                        : new BufferedReader( reader );
    }

    public void setAliases( FieldAliases[] aliases )
    {
        this.aliases = aliases;
    }

    /**
      * Kicks off the file parser. If the file parser is made the target of a
      * thread, this can also be accomplished by calling the thread's
      * start method.
      *
      *
      */
    public void run()
    {
        String[]		columns			=	null;
        String			line			=	null;
        boolean			match			=	false;
        int				j				=	0;
        int				i				=	0;
        int				k				=	0;
        HashMap			record			=	null;
        boolean			engaged			=	false;
        boolean			someData		=	false;
        Vector			missing			=	null;
        FieldAliases	fieldAliases	=	null;
        boolean			mandatory		=	false;

        try {
            interrogateColumnHeadings();
        } catch( ColumnHeadingsMissingException e ) {
            this.error = e;
            errors.add( e.getMessage() );
        } catch( NoColumnHeadingsException e ) {
            this.error = e;
            errors.add( "No column headings found" );
        } catch( ParseSyntaxException e ) {
            this.error = e;
            errors.add( "Found syntax error at line " + e.getLine() );
        }

        //	go no further if we could not successfully process the
        //	column headings....
        //	--------------------------------------------------------
        if ( errors.size() > 0 )
        {
            synchronized( this )
            {
                this.finished = true;
            }
            notifyListeners( null, true );
            return;
        }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "we have " + columnLabelLUT.length + " columns" );
            for( i = 0; i < columnLabelLUT.length; i++ )
            {
                logger.debug( "columnLabelLUT[" + i + "]=" + ( ( columnLabelLUT[ i ] != null ) ? columnLabelLUT[ i ] : "<null>" ) );
            }

        }


        //	now process the data...
        //	----------------------------
        while( ( line = grabLine() ) != null )
        {
            this.error = null;
            missing = null;
            incrementLine();

            if ( logger.isDebugEnabled() ) { logger.debug( "now, linecount=" + getLineNumber() ); }

            someData = false;
            line = line.trim();
            if ( ( line.length() > 0 ) && ( ! line.startsWith( "#" ) ) )
            {
                incrementRecord();
                try {
                    columns = splitter.splitLine( line );

                    if ( logger.isTraceEnabled() ) { logger.trace( "got " + columns.length + " bits from input line" ); }

                    record = new HashMap();
                    //	populate the record with data...
                    //	--------------------------------
                    for ( i = 0; i < columns.length; i++ )
                    {
                        if ( logger.isTraceEnabled() )
                        {
                            logger.trace( "i=" + i + ", columnLabelLUT.length=" + columnLabelLUT.length + ", columnLabelLUT[" + i + "]=" + columnLabelLUT[ i ] + ", columns[" + i + "]=\"" + columns[ i ] + "\", + columnLUT[" + i + "]=" + columnLUT[ i ] );
                        }

                        if ( columnLUT[ i ] < 0 )
                        {
                            // this may be an optional field for field substitution don't skip it
                            if ( logger.isTraceEnabled() ) { logger.trace( "column " + i + " has no data field associated with it" ); }
                            record.put( columnLabelLUT[ i ], columns[ i ] );
                            //continue;
                        }

                        if (		( i < columnLabelLUT.length )
                                &&	( columnLabelLUT[ i ] != null )
                                &&	( columnLabelLUT[ i ].length() > 0 )
                                &&	( columns[ i ] != null )
                                &&	( columns[ i ].length() > 0 )
                            )
                        {
                            record.put( columnLabelLUT[ i ], columns[ i ] );
                            someData = true;
                            if ( logger.isTraceEnabled() ) { logger.trace( "placing \"" + columns[ i ] + "\" into record field \"" + columnLabelLUT[ i ] + "\"" ); }
                        }

                    }

                    //	now check for mandatory data columns...
                    //	---------------------------------------
                    for ( i = 0; i < columns.length; i++ )
                    {

                        if ( columnLUT[ i ] < 0 ) { continue; }

                        if (		( columns[ i ] == null )
                                ||	( columns[ i ].length() == 0 ) )
                        {

                            if ( logger.isTraceEnabled() ) { logger.trace( "checking for mandatory columns ... i=" + i + ", columnLUT[" + i + "]=" + columnLUT[ i ] ); }

                            fieldAliases	= aliases[ columnLUT[ i ] ];

                            if ( logger.isTraceEnabled() ) { logger.trace( "no data for i=" + i + ", label=\"" + fieldAliases.getLabel() + "\", mandatory=" + fieldAliases.mandatory ); }

                            if ( isMandatory( fieldAliases, record, line ) )
                            {
                                if ( missing == null )
                                {
                                    missing = new Vector();
                                }

                                missing.add( fieldAliases.getLabel() );
                            }
                        }
                    }


                } catch( ParseSyntaxException e ) {
                    if ( logger.isDebugEnabled() ) { logger.debug( "caught exception " + e.getClass().getName() ); }
                    if ( logger.isDebugEnabled() ) { logger.debug( e.getClass().getName() + " - " + e.getMessage() ); }
                    e.setLine( getLineNumber() );
                    e.setFile( getFile() );
                    error = e;
                    errors.add( e.getMessage() );
                }

                if ( logger.isTraceEnabled() ) { logger.trace( "someData=" + someData + ", missing!=null=" + ( missing != null ) + ", this.error==null=" + ( this.error == null ) ); }

                // for each field in the record do field substitutions
                doFieldSubstitutions(record);


                if ( someData ) {

                    //	if we haven't already got a parse syntax exception,
                    //	and there are missing fields, then put together an
                    //	appropriate exception and inform the listener.
                    //	-------------------------------------------------
                    if ( someData && ( this.error == null ) && ( missing != null ) )
                    {
                        this.error = new ColumnDataMissingException( "data missing", getFile(), getLineNumber(), missing );
                        this.errors.add( this.error.getMessage() );
                    }

                    if ( logger.isDebugEnabled() )
                    {
                        logger.debug( "about to inform our listeners..." );
                    }

                    notifyListeners( record, this.error != null );
                } else {
                    if ( logger.isDebugEnabled() )
                    {
                        logger.debug( "empty record ... don't bother informing listeners" );
                    }
                }
            } else {
                if ( logger.isDebugEnabled() ) { logger.debug( "skipping comment line" ); }
            }
        }

        synchronized( this )
        {
            this.finished = true;
        }

        notifyListeners( null );

        if ( logger.isDebugEnabled() ) { logger.debug( "File parser returns..." ); }
    }

    /**
     * Method for unit test. Not to be used for other purposes
     */
    protected void _doFieldSubstitutions(HashMap record) {
        doFieldSubstitutions(record);
    }

    /**
     * @param record HashMap containing name/value pairs.  The name is the
     * column label.  The value is the value of the field for that column
     */
    private void doFieldSubstitutions(
        HashMap record
    )
    {
        // get a iterator containing all the column labels for the record
        Iterator keyIter = record.keySet().iterator();
        Vector visited = new Vector();

        // for each column call doSubstitution
        while(keyIter.hasNext()) {
            String key = (String)keyIter.next();

            try {
                doSubstitution(key,record, visited);
            } catch (InvalidSubstitutionConstructException isce) {
                if ( logger.isDebugEnabled() ) { logger.debug( isce ); }
                this.error=isce;
                errors.add( isce.getMessage() );
            }
        }
    }

    /**
     * Method for unit test. Not to be used for other purposes
     */
    protected void _doSubstitution(String key, HashMap record)
        throws InvalidSubstitutionConstructException
    {
        doSubstitution(key, record, new Vector());
    }

    /**
     * @param key The column the substitution will be preformed on
     * @param record HashMap containing name/value pairs.  The name is the
     * column label.  The value is the value of the field for that column
     * @param visited A vector containing previous visisted columns used
     */
    private void doSubstitution(
        String key,
        HashMap record,
        Vector visited
    ) throws InvalidSubstitutionConstructException
    {
        String	fieldValue = null;
        String	substitutionField = null;
        String	substitutionFieldValue = null;
        String	substitutionTag = null;

        // if we have previously visited this column before while trying to
        // resolve a substitution field stop as this is a recursive substitution
        // and cannot be resolved
        // ----------------------------------------------------------------
        if ( logger.isDebugEnabled() ) { logger.debug( "visited=[" + visited + "]" ); }

        if ( visited.contains( key ) )
        {
            throw new InvalidSubstitutionConstructException(
                    "A recursive substitution has been found",
                    getFile(),
                    getLineNumber()
                );
        }

        // get the value of the field
        // --------------------------------
        fieldValue = (String)record.get( key );
        substitutionField = getNextSubstitutionField( fieldValue );

        while( ( fieldValue != null ) && ( substitutionField != null ) )
        {
            visited.add(key);
            if ( logger.isDebugEnabled() ) { logger.debug("key=["+key+"], value=["+fieldValue+"]"); }

            // get the column name of a substitution field if one exists in
            // the field value
            // ----------------------------------------------------------------

            // if the key is the same as the substitution field then we are
            // trying to substitute a sub string in the current column with
            // current column.
            // This is not good.
            // ----------------------------------------------------------------
            if (
                        ( substitutionField !=null )
                    &&	( key.compareTo( substitutionField ) == 0 )
                )
            {
                throw new InvalidSubstitutionConstructException(
                                "A field contained a reference to itself",
                                getFile(),
                                getLineNumber()
                            );
            }


            // ensure the field value we are going to use does not require any
            // substitutions to be made.  If it does do these substitutions first
            // ----------------------------------------------------------------
            substitutionFieldValue = (String)record.get( substitutionField );
            if (
                    ( substitutionFieldValue != null )
                 &&	( getNextSubstitutionField( substitutionFieldValue ) != null )
            )
            {
                doSubstitution( substitutionField, record, visited );
            }

            substitutionTag = substitutionFieldStart
                        +	(String)labelAliasLUT.get( substitutionField )
                        +	substitutionFieldEnd;

            if ( logger.isDebugEnabled() )
            {
                logger.debug( "substitution field=[" + substitutionField + "]");
                logger.debug( "substitution tag=[" + substitutionTag + "]");
                logger.debug( "substitution value=[" +(String)record.get(substitutionField)+"]");
            }

            // do the substitution of the substring in the current column
            // ----------------------------------------------------------------
            fieldValue = StringArmyKnife.replace(
                    fieldValue,
                    substitutionTag,
                    (substitutionFieldValue != null)
                            ? substitutionFieldValue
                            : ""
            );

            if ( logger.isDebugEnabled() ) { logger.debug( "value after=[" + fieldValue + "]"); }

            // update the record
            // ----------------------------------------------------------------
            record.put( key, fieldValue );

            // remove the current column from the list
            // ----------------------------------------------------------------
            visited.remove( key );
            substitutionField = getNextSubstitutionField( fieldValue );
        }
    }


    /**
     * Method for unit test. Not to be used for other purposes
     */
    protected String _getNextSubstitutionField(String field) {
        return getNextSubstitutionField(field);
    }

    /**
     * @return the column alias of the column containing the value to be use to
     * replace the substitution field or null if no substitution field is found
     * @param field The string to find the substitution field in
     */
    private String getNextSubstitutionField(
        String field
    ) {

        // if the field is null there is a problem
        // ----------------------------------------------------------------
        if( field == null )
        {
            return null;
        }

        // for each column label look for ${columnAlias} in string
        // ----------------------------------------------------------------
        for( int i = 0; i < columnLabelLUT.length; i++ )
        {

            if( field.indexOf( substitutionFieldStart + labelAliasLUT.get( columnLabelLUT[ i ] ) + substitutionFieldEnd) >=0) {
                return columnLabelLUT[ i ];
            }
        }
        return null;
    }


    /**
      * Trawls through the data source looking for column headings...
      * ... ignores any lines which do not match at least one column heading..
      *
      * @throws NoColumnHeadingsException if we totally failed to locate anything
      *   within the data source that looked like column headings
      * @throws au.com.polly.util.csv.ColumnHeadingsMissingException if we found some, but not all of the
      *   required columns headings.
      * @throws ParseSyntaxException if any of the lines in the data source
      *   violate the expected form of a CSV data source.
      *
      */
    protected void interrogateColumnHeadings()
    throws NoColumnHeadingsException,
    ColumnHeadingsMissingException,
            ParseSyntaxException
    {
        String		line			=	null;
        String[]	columns			=	null;
        boolean		missingFields	=	false;
        boolean		matchedOneField	=	false;
        boolean		matched[]		=	null;
        Vector		missing			=	null;
        int			i				=	0;
        int			j				=	0;
        int			k				=	0;
        boolean		engaged			=	false;
        Vector		mandatory		=	null;
        int			mandatoryCount	=	0;

        matched			=	new boolean[ aliases.length ];

        if ( logger.isDebugEnabled() )
        {
            for ( j = 0; j < aliases.length; j++ )
            {
                logger.debug( "aliases[" + j + "]=<<" + aliases[ j ] + ">>" );
            }
        }

        //	keep searching for column headings until we get at least one
        //	pretty good column heading match....
        //	------------------------------------------------------------
        do {

            do {
                line = grabLine();
                incrementLine();
                if ( ( line != null ) && ( line.length() > 0 ) && ( ! line.startsWith( "#" ) ) )
                {
                    line = line.trim();
                }
            } while(		( line != null )
                        &&	(		( line.length() == 0 )
                                ||	( line.startsWith( "#" ) )
                            )
                    );

            if ( logger.isDebugEnabled() ) { logger.debug( "about to analyze line [" + line + "] for column headings.." ); }
            matchedOneField	=	false;

            if ( line != null )
            {
                columns			= splitter.splitLine( line );
                columnLabelLUT	= new String[ columns.length ];
                columnLUT		= new int[ columns.length ];
                labelAliasLUT   = new HashMap( columns.length );

                for ( i = 0; i < columns.length; i ++ )
                {
                    columnLUT[ i ] = -1;

                    if ( logger.isDebugEnabled() ) { logger.debug("about to analyze column [" +  columns[i] + "] for a column heading match..."); }

                    if ( ( columns[ i ] != null ) && ( columns[ i ].length() > 0 ) )
                    {
                        // try and find a match against a field alias...
                        // ----------------------------------------------
                        for ( j = 0; j < aliases.length; j++ )
                        {
                            if ( aliases[ j ].matches( columns[ i ] ) )
                            {
                                columnLabelLUT[ i ]	=	aliases[ j ].getLabel();
                                columnLUT[ i ]		=	j;
                                matched[ j ]		=	true;
                                matchedOneField		=	true;
                                labelAliasLUT.put(columnLabelLUT[ i ],columns[ i ]);
                                break;
                            }
                        }

                        //	if we didn't get an exact match, try for something
                        //	less exacting...
                        //	---------------------------
                        if ( columnLabelLUT[ i ] == null )
                        {
                            // try and find a match against a field alias...
                            // ----------------------------------------------
                            for ( j = 0; j < aliases.length; j++ )
                            {
                                if ( aliases[ j ].almostMatches( columns[ i ] ) )
                                {
                                    //	make sure nobody else is already using it...
                                    //	---------------------------------------------
                                    engaged = false;
                                    for ( k = 0; k < columnLabelLUT.length; k++ )
                                    {
                                        if ( ( columnLabelLUT[ i ] != null ) && columnLabelLUT[ i ].equals( aliases[ j ].getLabel() ) )
                                        {
                                            engaged = true;
                                            break;
                                        }
                                    }

                                    //	noone else is using this tag yet, so quick,
                                    //	let's grab it!!
                                    //	----------------------------------------------
                                    if ( !engaged )
                                    {
                                        columnLabelLUT[ i ] = aliases[ j ].getLabel();
                                        columnLUT[ i ] 		= j;
                                        labelAliasLUT.put(columnLabelLUT[ i ],columns[ i ]);
                                        matched[ j ]		= true;
                                        break;
                                    }
                                }
                            }
                        }

                        // assume we have a custom column
                        if ( columnLabelLUT[ i ] == null )
                        {
                            columnLabelLUT[ i ] = columns[ i ];
                            labelAliasLUT.put(columnLabelLUT[ i ],columns[ i ]);
                        }
                    }
                }
            }
        } while( ( ! matchedOneField ) && ( line != null ) );

        if ( logger.isDebugEnabled() ) { logger.debug( "matchedOneField=" + matchedOneField + ", line=[" + line + "]"); }

        //	if we didn't match any fields, then we got to the end of the
        //	data source without any column headings ... chuck a fit!!
        //	--------------------------------------------------------------
        if ( !matchedOneField )
        {
            throw new NoColumnHeadingsException( "Failed to locate column headings.", getFile() );
        }

        //	now see if all of the mandatory columns are represented...
        //	--------------------------------------------------------
        missingFields = false;
        missing = new Vector();
        for( i = 0; i < aliases.length; i++ )
        {

            if ( logger.isDebugEnabled() ) { logger.debug( "aliases[" + i + "].mandatory=" + aliases[ i ].mandatory + ", matched=" + matched[ i ] ); }

            if ( aliases[ i ].mandatory )
            {
                if ( ! matched[ i ] )
                {
                    missing.add( aliases[ i ].getLabel() );
                    missingFields = true;
                }
            }
        }


        if ( logger.isDebugEnabled() ) { logger.debug( "missingFields=" + missingFields ); }
        if ( logger.isDebugEnabled() )
        {
            Iterator matilda = null;

            matilda = missing.iterator();
            while( matilda.hasNext() )
            {
                logger.debug( "missing field label=\"" + matilda.next() + "\"" );
            }
        }

        if ( missingFields )
        {
            throw new ColumnHeadingsMissingException( "Failed to locate all mandatory column headings", getFile(), getLineNumber(), missing );
        }

        return;
    }

    public Vector getErrors()
    {
        return errors;
    }

    public synchronized boolean isFinished()
    {
        return this.finished;
    }

    protected synchronized void incrementLine()
    {
        this.lineCount++;
    }

    protected synchronized void incrementRecord()
    {
        this.recordCount++;
    }


    public synchronized int getLineNumber()
    {
        return this.lineCount;
    }

    public synchronized int getRecordNumber()
    {
        return this.recordCount;
    }


    /**
      * @return whether or not an error has been recorded for the current record.
      *   this method is intended to be called by listeners.
      *
      */
    public boolean isError()
    {
        return( getError() != null );
    }

    /**
      * @return either null, if no error has occurred, or the last error
      *		that occurred whilst processing data. The
      *		<a href="#isFinished()">isFinished()</a> method should be checked
      *		upon receiving a null record, before checking the error state.
      *
      */
    public synchronized Throwable getError()
    {
        return this.error;
    }

    /**
      * @return line of input from the source/data file or stream
      *
      *
      * This method allows strings to be input in a manner which seems
      * to work OK for both UNIX and DOS machines.
      */
    protected String grabLine()
    {
        int				khar;
        StringBuffer	line	= new StringBuffer();
        String			buffer	= null;

        try {
            while( ( khar = this.reader.read() ) >= 0 )
            {
                if ( khar == '\r' )
                {
                    continue;
                }
                if ( khar == '\n' )
                {
                    break;
                }
                line.append( (char)khar );
            }
            buffer = ( khar < 0 ) ? null : line.toString();
        } catch( java.io.IOException e ) {
            buffer = null;
        }
        return buffer;
    }


    /**
      * @param eavesDropper listener to be notified when the parser has a record
      * to be processed.
      *
      */
    public void addListener( ParserListener eavesDropper )
    {
        addListener( eavesDropper, null );
    }

    /**
      * @param eavesDropper listener to be notified when the parser has
      *  a record to process.
      *
      */
    public void addListener( ParserListener eavesDropper, Object extra )
    {
        if ( listeners == null )
        {
            listeners = new HashMap();
        }

        synchronized( listeners )
        {
            listeners.put( eavesDropper, extra );
        }
    }

    /**
      *
      * @param eavesDropper listener to be removed from the file parser.
      *
      */
    public void removeListener( ParserListener eavesDropper )
    {
        if ( listeners != null )
        {
            synchronized( listeners )
            {
                listeners.remove( eavesDropper );
            }
        }
    }


    public void notifyListeners( HashMap record )
    {
        notifyListeners( record, false );
    }


    public void notifyListeners( HashMap record, boolean error )
    {
        Iterator			waltzer 		= null;
        ParserListener		eavesDropper	= null;
        Set					keys			= null;
        Object				auxillary		= null;

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "called with " + ( ( record != null ) ? record.size() + " fields" : "<null record>" ) + ", error=" + error );

            if ( listeners != null )
            {
                logger.debug( "listeners.size=" + listeners.size() );
            } else {
                logger.debug( "listeners is <null>." );
            }
        }

        if ( ( listeners != null ) && ( listeners.size() > 0 ) )
        {

            synchronized( listeners )
            {
                keys	= listeners.keySet();
            }

            waltzer	= keys.iterator();

            while( waltzer.hasNext() )
            {
                eavesDropper	= (ParserListener) waltzer.next();
                auxillary		= listeners.get( eavesDropper );
                if ( logger.isDebugEnabled() )
                {
                    logger.debug( "about to call eavesdropper, class=" + eavesDropper.getClass().getName() );
                }
                eavesDropper.processRecord( this, record, auxillary, error );
            }
        }
    }

    public abstract File getFile();

    /**
      * @return string to use when separating lines of output.
      *
      */
    public static String getLineSeparator()
    {
        return ( ( lineSeparator != null ) ? lineSeparator : System.getProperty( "line.separator" ) );
    }

    /**
      * @param separator string to use when separating lines of output.
      *
      */
    public static void setLineSeparator( String separator )
    {
        lineSeparator = separator;
    }

    /**
      * @return whether or not the specified field is mandatory...
      *
      * This method is only called if a field is empty, that is, it is only
      * called in the context of actually processing a record.
      *
      * Override this method if some fields marked as mandatory might lose their
      * mandatoryness depending upon what else is in the data record. The
      * record hashmap should be fully populated with all of the field values.
      *
      *
      */
    public boolean isMandatory( FieldAliases alias, HashMap record, String line )
    {
        return alias.isMandatory();
    }

    /**
     * @param start Character sequence defining the start of a substitution field
     */
    public void setSubsitutionFieldStart( String start )
    {
         substitutionFieldStart = start;
    }

    /**
     * @param end Character sequence defining the end of a substitution field
     */
    public void setSubsitutionFieldEnd( String end )
    {
         substitutionFieldEnd = end;
    }

}
