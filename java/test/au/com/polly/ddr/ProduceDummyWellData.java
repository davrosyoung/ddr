package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import org.apache.log4j.Logger;
import org.junit.Before;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;


/**
 * Spit out dummy data in serialized form to a file...
 *
 */
public class ProduceDummyWellData
{
static Logger logger = Logger.getLogger( ProduceDummyWellData.class );
static private final double oilFlowRates[] = {
        0000.0, 0000.0, 0000.0, 1567.5, 1342.1, 1152.6, 1133.6, 1132.8, 1127.6, 1128.3,
        1129.5, 1128.6, 1153.5, 1132.4, 1129.1, 1128.5, 1131.2, 1130.0, 1131.5, 1132.1,
        1132.7, 1133.1, 1135.8, 1138.5, 1139.1, 1142.6, 1140.7, 1141.2, 1141.8, 1140.3,
        0320.5, 0000.0, 0000.0, 0000.0, 0000.0, 0000.0, 0762.5, 1762.4, 1482.3, 1312.5,
        1274.7, 1082.5, 0995.7, 1127.5, 1138.5, 1137.6, 1139.6, 1140.6, 1137.8, 1137.6,
        1138.2, 1139.1, 1142.6, 1140.7, 1135.7, 0970.5, 0790.7, 0608.2, 0432.5, 0402.2,
        0395.7, 0403.1, 0397.5, 0399.9, 0401.5, 0400.0, 0408.2, 0405.3, 0397.5, 0401.1,
        0402.6, 0490.0, 0580.0, 0670.0, 0760.0, 0850.0, 0940.0, 1030.0, 1120.0, 1200.0,
        1200.0, 1203.8, 1197.6, 1199.9, 1202.0, 1205.5, 1203.2, 1202.0, 1201.0, 1200.0,
        1203.0, 1205.0, 1207.0, 1209.0, 1211.0, 1210.0, 1210.0, 1210.0, 1210.0, 1210.0
};
private final static GasWell dummyWell = new GasWell( "Dummy" );

public static void main( String... args )
{
    String filename;
    File file;
    FileOutputStream fos;
    ObjectOutputStream oos;
    Calendar when;
    DateParser dateParser = new AussieDateParser();


    if ( ( args == null ) || ( args.length < 1 ) )
    {
        System.err.println( "Must specify an output filename!!" );
        System.err.flush();
        System.exit( 1 );
    }

    // populate some dummy data into the gas well data set....
    // --------------------------------------------------------
    GasWellDataSet dataSet = new GasWellDataSet( dummyWell );
    GasWellDataEntry entry;

    when = dateParser.parse( "13/06/2011 04:00" );

    for( int i = 0; i < oilFlowRates.length; i++ )
    {
        entry = new GasWellDataEntry();
        entry.setWell( dummyWell );
        entry.setStartInterval( when.getTime() );
        when.add( Calendar.DATE, 1 );
        entry.setIntervalLength( 86400 );
        entry.setMeasurement( WellMeasurementType.OIL_FLOW, oilFlowRates[ i ] );
        dataSet.addDataEntry( entry );
    }

    long then = System.currentTimeMillis();
    try
    {
        filename = args[ 0 ];
        file = new File( filename );
        fos = new FileOutputStream( file );
        oos = new ObjectOutputStream( fos );
        oos.writeObject( dataSet );
        oos.close();
        fos.close();
    } catch (IOException e) {
        System.err.println( e.getClass().getName() + " - " + e.getMessage() );
        e.printStackTrace();
        System.exit( 1 );
    }
    long now = System.currentTimeMillis();

    logger.info("It took " + ( now - then ) + "ms to write out data set.");

    System.exit( 0 );
}

}
