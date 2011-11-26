package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;


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
