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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileParser
extends Parser
{
    protected	File				file		= null;

    /**
      * @param path the absolute filename of the data file to be processed.
      * @param aliases specifies how the columns are named...
      */
    public FileParser(
                String			path,
                FieldAliases[]	aliases
    ) throws FileNotFoundException
    {
        this( path, aliases, null, null );
    }


    /**
      * @param file represents the data source.
      * @param aliases specifies how the columns are named...
      */
    public FileParser(
                File			file,
                FieldAliases[]	aliases
    ) throws FileNotFoundException
    {
        this( file, aliases, null, null );
    }

    /**
      * @param path the absolute filename of the data file to be processed.
      * @param aliases specifies how the columns are named...
      * @param listener somebody to call whenever a line is ready...
      * @param auxillary will be passed to the listener when they are called.
      */
    public FileParser(
                String				path,
                FieldAliases[]		aliases,
                ParserListener listener,
                Object				auxillary
    ) throws FileNotFoundException
    {
        this( new File( path ), aliases, listener, auxillary );
    }

    /**
      * @param file represents the data source.
      * @param aliases specifies how the columns are named...
      * @param listener somebody to call whenever a line is ready...
      * @param auxillary will be passed to the listener when they are called.
      */
    public FileParser(
                File				file,
                FieldAliases[]		aliases,
                ParserListener listener,
                Object				auxillary
    ) throws FileNotFoundException
    {
        super( new FileReader( file ), aliases );


        if ( listener != null )
        {
            addListener( listener, auxillary );
        }
    }

    public File getFile()
    {
        return this.file;
    }


}
