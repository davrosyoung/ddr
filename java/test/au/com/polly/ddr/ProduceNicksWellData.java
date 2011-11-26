package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;


/**
 * Spit out dummy data in serialized form to a file...
 *
 */
public class ProduceNicksWellData
{
static Logger logger = Logger.getLogger( ProduceNicksWellData.class );
static DateParser dateParser = new AussieDateParser();

static final private DataSet[] rawData = {
    new DataSet( dateParser.parse( "16/JUL/2006 20:47" ).getTime(), 0000.0, 0000.0, 0000.0, 0277.706 ),
    new DataSet( dateParser.parse( "28/JUL/2006 10:29" ).getTime(), 1788.0, 1370.0, 0000.0, 0117.932 ),
    new DataSet( dateParser.parse( "08/AUG/2006 08:25" ).getTime(), 1070.0, 0970.0, 0000.0, 0265.071 ),
    new DataSet( dateParser.parse( "13/AUG/2006 09:29" ).getTime(), 0000.0, 0000.0, 0000.0, 0029.831 ),
    new DataSet( dateParser.parse( "14/AUG/2006 15:19" ).getTime(), 1167.0, 1320.0, 0000.0, 0861.785 ),
    new DataSet( dateParser.parse( "19/SEP/2006 13:06" ).getTime(), 0692.0, 0800.0, 0000.0, 0745.017 ),
    new DataSet( dateParser.parse( "20/OKT/2006 14:07" ).getTime(), 0827.0, 1340.0, 0000.0, 4949.920 ),
    new DataSet( dateParser.parse( "14/MAI/2007 20:02" ).getTime(), 0538.0, 1040.0, 0000.0, 0765.863 ),
    new DataSet( dateParser.parse( "15/JUN/2007 17:54" ).getTime(), 0700.0, 1100.0, 0000.0, 0057.361 ),
    new DataSet( dateParser.parse( "18/JUN/2007 03:16" ).getTime(), 0841.0, 1170.0, 0000.0, 0098.843 ),
    new DataSet( dateParser.parse( "22/JUN/2007 06:06" ).getTime(), 0000.0, 0000.0, 0000.0, 0158.846 ),
    new DataSet( dateParser.parse( "28/JUN/2007 20:57" ).getTime(), 0001.0, 0001.0, 0000.0, 0080.000 ),
    new DataSet( dateParser.parse( "02/JUL/2007 04:57" ).getTime(), 0612.0, 0870.0, 0017.0, 5136.000 ),
    new DataSet( dateParser.parse( "01/FEB/2008 04:57" ).getTime(), 0701.0, 1010.0, 0012.0, 3641.780 ),
    new DataSet( dateParser.parse( "01/JUL/2008 04:57" ).getTime(), 0783.0, 1320.0, 0006.0, 0207.866 ),
    new DataSet( dateParser.parse( "10/JUL/2008 14:36" ).getTime(), 0923.0, 1550.0, 0012.0, 2683.880 ),
    new DataSet( dateParser.parse( "01/AUG/2008 00:37" ).getTime(), 0000.0, 0000.0, 0000.0, 0119.097 ),
    new DataSet( dateParser.parse( "05/AUG/2008 23:42" ).getTime(), 0923.0, 0001.0, 0001.0, 0170.000 ),
    new DataSet( dateParser.parse( "13/AUG/2008 01:42" ).getTime(), 0702.0, 0980.0, 0016.0, 3784.330 ),
    new DataSet( dateParser.parse( "17/JAN/2009 18:02" ).getTime(), 0000.0, 0000.0, 0000.0, 0111.560 ),
    new DataSet( dateParser.parse( "22/JAN/2009 09:36" ).getTime(), 0545.0, 0820.0, 0010.0, 1872.730 ),
    new DataSet( dateParser.parse( "12/MAR/2009 16:20" ).getTime(), 0000.0, 0000.0, 0000.0, 0077.336 ),
    new DataSet( dateParser.parse( "15/MAR/2009 21:40" ).getTime(), 0001.0, 0001.0, 0001.0, 0068.183 ),
    new DataSet( dateParser.parse( "18/MAR/2009 17:51" ).getTime(), 0564.0, 0890.0, 0012.0, 1555.860 ),
    new DataSet( dateParser.parse( "12/AUG/2009 03:03" ).getTime(), 0000.0, 0000.0, 0000.0, 0092.506 ),
    new DataSet( dateParser.parse( "15/AUG/2009 23:33" ).getTime(), 0564.0, 0810.0, 0007.0, 6826.670 ),
    new DataSet( dateParser.parse( "27/MAI/2010 23:33" ).getTime(), 0000.0, 0000.0, 0000.0, 0149.161 ),
    new DataSet( dateParser.parse( "02/JUN/2010 15:24" ).getTime(), 0513.0, 0620.0, 0006.0, 2561.560 ),
    new DataSet( dateParser.parse( "17/SEP/2010 08:57" ).getTime(), 0636.0, 0970.0, 0008.5, 0099.054 ),
    new DataSet( dateParser.parse( "21/SEP/2010 08:57" ).getTime(), 0497.0, 0600.0, 0009.0, 0132.413 ),
    new DataSet( dateParser.parse( "27/SEP/2010 00:25" ).getTime(), 0000.0, 0000.0, 0000.0, 8760.770 )
};

private final static GasWell well = new GasWell( "SA-11" );

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
    GasWellDataSet dataSet = new GasWellDataSet( well );
    GasWellDataEntry entry;
    Date from;
    Date until;

    for( int i = 0; i < rawData.length; i++ )
    {
        entry = new GasWellDataEntry();
        entry.setWell( well );

        from = rawData[ i ].from;
        entry.setStartInterval( from );
        if ( i < rawData.length - 1 )
        {
            until = rawData[ i + 1 ].from;
            double span = ( until.getTime() - from.getTime() ) / 1000.0;
            double delta = Math.abs( span - ( rawData[ i ].durationHours * 3600.0 ) );
            if ( span > ( rawData[ i ].durationHours * 3600 ) )
            {
                logger.error( "i=" + i + " ... duration is short by " + delta + "seconds." );
            }
            if ( span < ( rawData[ i ].durationHours * 3600 ) )
            {
                logger.error( "i=" + i + " ... duration is too long by " + delta + "seconds." );
            }
            entry.setIntervalLength( (long)span );
        } else {
            entry.setIntervalLength( (long)(rawData[ i ].durationHours * 3600 ) );
        }
        entry.setMeasurement( WellMeasurementType.OIL_FLOW, rawData[ i ].oilFlowRate );
        entry.setMeasurement( WellMeasurementType.GAS_FLOW, rawData[ i ].gasFlowRate );
        entry.setMeasurement( WellMeasurementType.WATER_FLOW, rawData[ i ].waterFlowRate );
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

protected final static class DataSet
{
    Date from;
    double oilFlowRate;
    double gasFlowRate;
    double waterFlowRate;
    double durationHours;

    protected DataSet( Date from, double oilFlowRate, double gasFlowRate, double waterFlowRate, double durationHours )
    {
        this.from = from;
        this.oilFlowRate = oilFlowRate;
        this.gasFlowRate = gasFlowRate;
        this.waterFlowRate = waterFlowRate;
        this.durationHours = durationHours;
    }
}

}
