package au.com.polly.ddr;

import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 21/11/11
 * Time: 2:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class DummyGasWellDataSet implements DataVsTimeSource
{
static GasWellDataSet data;

static{
    DateParser dateParser = new AussieDateParser();
    GasWellDataEntry entry = null;
    GasWell well = null;
    Date date = null;
    Calendar cal = null;

    well = new GasWell("dummy" );
    data = new GasWellDataSet( well );

// 23/APR/2011 00:00:00,1305,0.420,7727
    cal = dateParser.parse( "23/APR/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1305 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.420 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7727 );
    data.addDataEntry( entry );
// 24/APR/2011 00:00:00,0,0.000,0
    cal = dateParser.parse( "24/APR/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 0 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.000 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 0 );
    data.addDataEntry( entry );
// 25/APR/2011 00:00:00,0,0.000,0
    cal = dateParser.parse( "25/APR/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 0 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.000 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 0 );
    data.addDataEntry( entry );
// 26/APR/2011 00:00:00,378,0.190,2154
    cal = dateParser.parse( "26/APR/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 378 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.190 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 2154 );
    data.addDataEntry( entry );
// 27/APR/2011 00:00:00,1195,0.360,7818
    cal = dateParser.parse( "27/APR/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1195 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.360 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7818 );
    data.addDataEntry( entry );
// 28/APR/2011 00:00:00,1199,0.380,7895
    cal = dateParser.parse( "28/APR/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1199 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7895 );
    data.addDataEntry( entry );
// 29/APR/2011 00:00:00,1282,0.420,7654
    cal = dateParser.parse( "29/APR/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1282 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.420 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7654 );
    data.addDataEntry( entry );
// 30/APR/2011 00:00:00,1272,0.420,7740
    cal = dateParser.parse( "30/APR/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1272 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.420 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7740 );
    data.addDataEntry( entry );
// 01/MAY/2011 00:00:00,1299,0.410,7667
    cal = dateParser.parse( "01/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1299 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.410 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7667 );
    data.addDataEntry( entry );
// 02/MAY/2011 00:00:00,1328,0.420,7636
    cal = dateParser.parse( "02/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1328 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.420 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7636 );
    data.addDataEntry( entry );
// 03/MAY/2011 00:00:00,1269,0.460,7967
    cal = dateParser.parse( "03/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1269 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.460 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7967 );
    data.addDataEntry( entry );
// 04/MAY/2011 00:00:00,1298,0.440,8032
    cal = dateParser.parse( "04/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1298 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.440 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8032 );
    data.addDataEntry( entry );
// 05/MAY/2011 00:00:00,1271,0.380,8056
    cal = dateParser.parse( "05/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1271 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8056 );
    data.addDataEntry( entry );
// 06/MAY/2011 00:00:00,1367,0.400,8291
    cal = dateParser.parse( "06/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1367 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8291 );
    data.addDataEntry( entry );
// 07/MAY/2011 00:00:00,1320,0.420,8044
    cal = dateParser.parse( "07/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1320 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.420 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8044 );
    data.addDataEntry( entry );
// 08/MAY/2011 00:00:00,1322,0.400,8236
    cal = dateParser.parse( "08/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1322 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8236 );
    data.addDataEntry( entry );
// 09/MAY/2011 00:00:00,1327,0.400,8071
    cal = dateParser.parse( "09/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1327 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8071 );
    data.addDataEntry( entry );
// 10/MAY/2011 00:00:00,1254,0.370,8160
    cal = dateParser.parse( "10/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1254 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.370 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8160 );
    data.addDataEntry( entry );
// 11/MAY/2011 00:00:00,140,0.040,925
    cal = dateParser.parse( "11/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 140 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.040 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 925 );
    data.addDataEntry( entry );
// 12/MAY/2011 00:00:00,0,0.000,0
    cal = dateParser.parse( "12/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 0 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.000 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 0 );
    data.addDataEntry( entry );
// 13/MAY/2011 00:00:00,926,0.330,5890
    cal = dateParser.parse( "13/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 926 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.330 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 5890 );
    data.addDataEntry( entry );
// 14/MAY/2011 00:00:00,245,0.070,1709
    cal = dateParser.parse( "14/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 245 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.070 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 1709 );
    data.addDataEntry( entry );
// 15/MAY/2011 00:00:00,1271,0.380,8270
    cal = dateParser.parse( "15/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1271 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8270 );
    data.addDataEntry( entry );
// 16/MAY/2011 00:00:00,794,0.200,4952
    cal = dateParser.parse( "16/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 794 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.200 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 4952 );
    data.addDataEntry( entry );
// 17/MAY/2011 00:00:00,497,0.140,3029
    cal = dateParser.parse( "17/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 497 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.140 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 3029 );
    data.addDataEntry( entry );
// 18/MAY/2011 00:00:00,694,0.220,4334
    cal = dateParser.parse( "18/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 694 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.220 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 4334 );
    data.addDataEntry( entry );
// 19/MAY/2011 00:00:00,1238,0.360,6816
    cal = dateParser.parse( "19/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1238 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.360 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 6816 );
    data.addDataEntry( entry );
// 20/MAY/2011 00:00:00,1560,0.430,8470
    cal = dateParser.parse( "20/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1560 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.430 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8470 );
    data.addDataEntry( entry );
// 21/MAY/2011 00:00:00,1570,0.420,8350
    cal = dateParser.parse( "21/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1570 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.420 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8350 );
    data.addDataEntry( entry );
// 22/MAY/2011 00:00:00,1580,0.420,8296
    cal = dateParser.parse( "22/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1580 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.420 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8296 );
    data.addDataEntry( entry );
// 23/MAY/2011 00:00:00,1530,0.440,8304
    cal = dateParser.parse( "23/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1530 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.440 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8304 );
    data.addDataEntry( entry );
// 24/MAY/2011 00:00:00,1502,0.410,8257
    cal = dateParser.parse( "24/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1502 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.410 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8257 );
    data.addDataEntry( entry );
// 25/MAY/2011 00:00:00,1491,0.430,8178
    cal = dateParser.parse( "25/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1491 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.430 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8178 );
    data.addDataEntry( entry );
// 26/MAY/2011 00:00:00,1368,0.410,8263
    cal = dateParser.parse( "26/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1368 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.410 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8263 );
    data.addDataEntry( entry );
// 27/MAY/2011 00:00:00,1011,0.310,6214
    cal = dateParser.parse( "27/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1011 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.310 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 6214 );
    data.addDataEntry( entry );
// 28/MAY/2011 00:00:00,1285,0.390,8031
    cal = dateParser.parse( "28/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1285 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.390 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8031 );
    data.addDataEntry( entry );
// 29/MAY/2011 00:00:00,1336,0.400,8161
    cal = dateParser.parse( "29/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1336 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8161 );
    data.addDataEntry( entry );
// 30/MAY/2011 00:00:00,1371,0.420,8138
    cal = dateParser.parse( "30/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1371 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.420 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8138 );
    data.addDataEntry( entry );
// 31/MAY/2011 00:00:00,1179,0.420,8404
    cal = dateParser.parse( "31/MAY/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1179 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.420 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8404 );
    data.addDataEntry( entry );
// 01/JUN/2011 00:00:00,1209,0.420,8478
    cal = dateParser.parse( "01/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1209 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.420 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8478 );
    data.addDataEntry( entry );
// 02/JUN/2011 00:00:00,1206,0.420,8640
    cal = dateParser.parse( "02/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1206 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.420 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8640 );
    data.addDataEntry( entry );
// 03/JUN/2011 00:00:00,1168,0.410,8438
    cal = dateParser.parse( "03/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1168 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.410 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8438 );
    data.addDataEntry( entry );
// 04/JUN/2011 00:00:00,1137,0.410,8426
    cal = dateParser.parse( "04/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1137 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.410 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8426 );
    data.addDataEntry( entry );
// 05/JUN/2011 00:00:00,1154,0.400,8406
    cal = dateParser.parse( "05/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1154 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8406 );
    data.addDataEntry( entry );
// 06/JUN/2011 00:00:00,1204,0.400,8416
    cal = dateParser.parse( "06/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1204 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8416 );
    data.addDataEntry( entry );
// 07/JUN/2011 00:00:00,1123,0.390,8340
    cal = dateParser.parse( "07/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1123 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.390 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8340 );
    data.addDataEntry( entry );
// 08/JUN/2011 00:00:00,1209,0.390,8412
    cal = dateParser.parse( "08/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1209 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.390 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8412 );
    data.addDataEntry( entry );
// 09/JUN/2011 00:00:00,1263,0.380,8565
    cal = dateParser.parse( "09/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1263 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8565 );
    data.addDataEntry( entry );
// 10/JUN/2011 00:00:00,1080,0.380,8374
    cal = dateParser.parse( "10/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1080 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8374 );
    data.addDataEntry( entry );
// 11/JUN/2011 00:00:00,1121,0.370,8381
    cal = dateParser.parse( "11/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1121 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.370 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8381 );
    data.addDataEntry( entry );
// 12/JUN/2011 00:00:00,1145,0.370,8346
    cal = dateParser.parse( "12/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1145 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.370 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8346 );
    data.addDataEntry( entry );
// 13/JUN/2011 00:00:00,1204,0.380,8347
    cal = dateParser.parse( "13/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1204 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8347 );
    data.addDataEntry( entry );
// 14/JUN/2011 00:00:00,1220,0.380,7914
    cal = dateParser.parse( "14/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1220 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7914 );
    data.addDataEntry( entry );
// 15/JUN/2011 00:00:00,1258,0.390,7914
    cal = dateParser.parse( "15/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1258 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.390 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7914 );
    data.addDataEntry( entry );
// 16/JUN/2011 00:00:00,1263,0.390,7940
    cal = dateParser.parse( "16/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1263 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.390 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7940 );
    data.addDataEntry( entry );
// 17/JUN/2011 00:00:00,1188,0.320,8114
    cal = dateParser.parse( "17/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1188 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.320 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8114 );
    data.addDataEntry( entry );
// 18/JUN/2011 00:00:00,1173,0.350,7723
    cal = dateParser.parse( "18/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1173 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.350 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7723 );
    data.addDataEntry( entry );
// 19/JUN/2011 00:00:00,1262,0.320,8114
    cal = dateParser.parse( "19/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1262 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.320 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8114 );
    data.addDataEntry( entry );
// 20/JUN/2011 00:00:00,1224,0.280,7967
    cal = dateParser.parse( "20/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1224 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.280 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7967 );
    data.addDataEntry( entry );
// 21/JUN/2011 00:00:00,1305,0.290,8022
    cal = dateParser.parse( "21/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1305 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.290 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8022 );
    data.addDataEntry( entry );
// 22/JUN/2011 00:00:00,1260,0.370,8083
    cal = dateParser.parse( "22/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1260 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.370 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 8083 );
    data.addDataEntry( entry );
// 23/JUN/2011 00:00:00,1241,0.380,7995
    cal = dateParser.parse( "23/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1241 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7995 );
    data.addDataEntry( entry );
// 24/JUN/2011 00:00:00,1252,0.410,7818
    cal = dateParser.parse( "24/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1252 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.410 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7818 );
    data.addDataEntry( entry );
// 25/JUN/2011 00:00:00,1231,0.400,7851
    cal = dateParser.parse( "25/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1231 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7851 );
    data.addDataEntry( entry );
// 26/JUN/2011 00:00:00,1228,0.400,7870
    cal = dateParser.parse( "26/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1228 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7870 );
    data.addDataEntry( entry );
// 27/JUN/2011 00:00:00,1304,0.410,7832
    cal = dateParser.parse( "27/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1304 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.410 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7832 );
    data.addDataEntry( entry );
// 28/JUN/2011 00:00:00,1292,0.400,7785
    cal = dateParser.parse( "28/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1292 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7785 );
    data.addDataEntry( entry );
// 29/JUN/2011 00:00:00,1301,0.400,7807
    cal = dateParser.parse( "29/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1301 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7807 );
    data.addDataEntry( entry );
// 30/JUN/2011 00:00:00,1239,0.410,7817
    cal = dateParser.parse( "30/JUN/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1239 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.410 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7817 );
    data.addDataEntry( entry );
// 01/JUL/2011 00:00:00,1278,0.400,7695
    cal = dateParser.parse( "01/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1278 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7695 );
    data.addDataEntry( entry );
// 02/JUL/2011 00:00:00,1266,0.410,7765
    cal = dateParser.parse( "02/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1266 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.410 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7765 );
    data.addDataEntry( entry );
// 03/JUL/2011 00:00:00,1280,0.400,7613
    cal = dateParser.parse( "03/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1280 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7613 );
    data.addDataEntry( entry );
// 04/JUL/2011 00:00:00,1309,0.390,7676
    cal = dateParser.parse( "04/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1309 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.390 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7676 );
    data.addDataEntry( entry );
// 05/JUL/2011 00:00:00,1259,0.390,7674
    cal = dateParser.parse( "05/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1259 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.390 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7674 );
    data.addDataEntry( entry );
// 06/JUL/2011 00:00:00,1274,0.390,7583
    cal = dateParser.parse( "06/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1274 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.390 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7583 );
    data.addDataEntry( entry );
// 07/JUL/2011 00:00:00,1266,0.380,7575
    cal = dateParser.parse( "07/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1266 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7575 );
    data.addDataEntry( entry );
// 08/JUL/2011 00:00:00,1270,0.390,7453
    cal = dateParser.parse( "08/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1270 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.390 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7453 );
    data.addDataEntry( entry );
// 09/JUL/2011 00:00:00,667,0.280,5223
    cal = dateParser.parse( "09/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 667 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.280 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 5223 );
    data.addDataEntry( entry );
// 10/JUL/2011 00:00:00,0,0.000,0
    cal = dateParser.parse( "10/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 0 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.000 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 0 );
    data.addDataEntry( entry );
// 11/JUL/2011 00:00:00,0,0.000,0
    cal = dateParser.parse( "11/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 0 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.000 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 0 );
    data.addDataEntry( entry );
// 12/JUL/2011 00:00:00,0,0.000,0
    cal = dateParser.parse( "12/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 0 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.000 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 0 );
    data.addDataEntry( entry );
// 13/JUL/2011 00:00:00,0,0.000,0
    cal = dateParser.parse( "13/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 0 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.000 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 0 );
    data.addDataEntry( entry );
// 14/JUL/2011 00:00:00,833,0.320,2524
    cal = dateParser.parse( "14/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 833 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.320 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 2524 );
    data.addDataEntry( entry );
// 15/JUL/2011 00:00:00,1231,0.350,4607
    cal = dateParser.parse( "15/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1231 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.350 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 4607 );
    data.addDataEntry( entry );
// 16/JUL/2011 00:00:00,1258,0.360,6756
    cal = dateParser.parse( "16/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1258 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.360 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 6756 );
    data.addDataEntry( entry );
// 17/JUL/2011 00:00:00,1257,0.370,7052
    cal = dateParser.parse( "17/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1257 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.370 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7052 );
    data.addDataEntry( entry );
// 18/JUL/2011 00:00:00,1262,0.370,6437
    cal = dateParser.parse( "18/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1262 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.370 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 6437 );
    data.addDataEntry( entry );
// 19/JUL/2011 00:00:00,1269,0.380,6921
    cal = dateParser.parse( "19/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1269 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 6921 );
    data.addDataEntry( entry );
// 20/JUL/2011 00:00:00,1233,0.370,7238
    cal = dateParser.parse( "20/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1233 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.370 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7238 );
    data.addDataEntry( entry );
// 21/JUL/2011 00:00:00,1246,0.380,7488
    cal = dateParser.parse( "21/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1246 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7488 );
    data.addDataEntry( entry );
// 22/JUL/2011 00:00:00,1287,0.380,7470
    cal = dateParser.parse( "22/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1287 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7470 );
    data.addDataEntry( entry );
// 23/JUL/2011 00:00:00,1108,0.400,7616
    cal = dateParser.parse( "23/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1108 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7616 );
    data.addDataEntry( entry );
// 24/JUL/2011 00:00:00,1228,0.380,7503
    cal = dateParser.parse( "24/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1228 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7503 );
    data.addDataEntry( entry );
// 25/JUL/2011 00:00:00,1219,0.370,7543
    cal = dateParser.parse( "25/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1219 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.370 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7543 );
    data.addDataEntry( entry );
// 26/JUL/2011 00:00:00,1229,0.360,7103
    cal = dateParser.parse( "26/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1229 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.360 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7103 );
    data.addDataEntry( entry );
// 27/JUL/2011 00:00:00,1234,0.400,7239
    cal = dateParser.parse( "27/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1234 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.400 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7239 );
    data.addDataEntry( entry );
// 28/JUL/2011 00:00:00,1092,0.380,7541
    cal = dateParser.parse( "28/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1092 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7541 );
    data.addDataEntry( entry );
// 29/JUL/2011 00:00:00,1172,0.370,7449
    cal = dateParser.parse( "29/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1172 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.370 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7449 );
    data.addDataEntry( entry );
// 30/JUL/2011 00:00:00,1206,0.380,7508
    cal = dateParser.parse( "30/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1206 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.380 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7508 );
    data.addDataEntry( entry );
// 31/JUL/2011 00:00:00,1224,0.390,7448
    cal = dateParser.parse( "31/JUL/2011 00:00:00" );
    entry = new GasWellDataEntry();
    entry.setWell( well );
    entry.setStartInterval( cal.getTime() );
    entry.setIntervalLength( 86400 );
    entry.setMeasurement( WellMeasurementType.OIL_FLOW, 1224 );
    entry.setMeasurement( WellMeasurementType.GAS_FLOW, 0.390 );
    entry.setMeasurement( WellMeasurementType.WATER_FLOW, 7448 );
    data.addDataEntry( entry );


}

public GasWellDataSet getData()
{
    return data;
}

}
