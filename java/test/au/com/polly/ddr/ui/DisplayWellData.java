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

package au.com.polly.ddr.ui;

import au.com.polly.ddr.GasWellDataSet;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;


/**
 * Spit out dummy data in serialized form to a file...
 *
 */
public class DisplayWellData
{
static Logger logger = Logger.getLogger( DisplayWellData.class );

public static void main( String... args )
{
    String filename;
    File file;
    FileInputStream fis;
    ObjectInputStream ois;
    GasWellDataSet dataSet = null;


    if ( ( args == null ) || ( args.length < 1 ) )
    {
        System.err.println( "Must specify an input filename!!" );
        System.err.flush();
        System.exit( 1 );
    }

    try
    {
        filename = args[ 0 ];
        file = new File( filename );
        fis = new FileInputStream( file );
        ois = new ObjectInputStream( fis );
        dataSet = (GasWellDataSet)ois.readObject();
        System.out.println( dataSet );
        System.out.flush();
        ois.close();
        fis.close();
    } catch (Exception e) {
        System.err.println( e.getClass().getName() + " - " + e.getMessage() );
        e.printStackTrace();
        System.exit( 1 );
    }
    System.exit( 0 );
}

}
