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
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * Set of test data for use between DDR application tests.
 * 
 */
public class TestGasWellDataSet
{
static Logger logger = Logger.getLogger( TestGasWellDataSet.class );
static DateParser dateParser = new AussieDateParser();

static final private DataSet[] nicksRawData = {
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

static final private DataSet[] smallSetOfData = {
        new DataSet( dateParser.parse( "30/JUL/09" ).getTime(), 0287.0, 0.19, 0952.0, 24.0 ),
        new DataSet( dateParser.parse( "31/JUL/09" ).getTime(), 0277.0, 0.19, 0924.0, 24.0 ),
        new DataSet( dateParser.parse( "01/AUG/09" ).getTime(), 0284.0, 0.19, 0927.0, 24.0 ),
        new DataSet( dateParser.parse( "02/AUG/09" ).getTime(), 0290.0, 0.19, 0936.0, 24.0 ),
        new DataSet( dateParser.parse( "03/AUG/09" ).getTime(), 0248.0, 0.17, 0783.0, 24.0 ),
        new DataSet( dateParser.parse( "04/AUG/09" ).getTime(), 0249.0, 0.18, 0840.0, 24.0 ),
        new DataSet( dateParser.parse( "05/AUG/09" ).getTime(), 0223.0, 0.15, 0764.0, 24.0 ),
        new DataSet( dateParser.parse( "06/AUG/09" ).getTime(), 0224.0, 0.15, 0754.0, 24.0 ),
        new DataSet( dateParser.parse( "07/AUG/09" ).getTime(), 0177.0, 0.16, 0741.0, 24.0 ),
        new DataSet( dateParser.parse( "08/AUG/09" ).getTime(), 0217.0, 0.16, 0744.0, 24.0 ),
        new DataSet( dateParser.parse( "09/AUG/09" ).getTime(), 0203.0, 0.15, 0699.0, 24.0 ),
        new DataSet( dateParser.parse( "10/AUG/09" ).getTime(), 0201.0, 0.16, 0692.0, 24.0 ),
        new DataSet( dateParser.parse( "11/AUG/09" ).getTime(), 0219.0, 0.15, 0633.0, 24.0 ),
        new DataSet( dateParser.parse( "12/AUG/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "13/AUG/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "14/AUG/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "15/AUG/09" ).getTime(), 0041.0, 0.00, 0151.0, 24.0 ),
        new DataSet( dateParser.parse( "16/AUG/09" ).getTime(), 0192.0, 0.15, 0673.0, 24.0 ),
        new DataSet( dateParser.parse( "17/AUG/09" ).getTime(), 0182.0, 0.14, 0633.0, 24.0 ),
        new DataSet( dateParser.parse( "18/AUG/09" ).getTime(), 0038.0, 0.03, 0133.0, 24.0 ),
        new DataSet( dateParser.parse( "19/AUG/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "20/AUG/09" ).getTime(), 0012.0, 0.01, 0041.0, 24.0 ),
        new DataSet( dateParser.parse( "21/AUG/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "22/AUG/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "23/AUG/09" ).getTime(), 0105.0, 0.09, 0384.0, 24.0 ),
        new DataSet( dateParser.parse( "24/AUG/09" ).getTime(), 0690.0, 0.34, 2087.0, 24.0 ),
        new DataSet( dateParser.parse( "25/AUG/09" ).getTime(), 0540.0, 0.31, 2043.0, 24.0 ),
        new DataSet( dateParser.parse( "26/AUG/09" ).getTime(), 0666.0, 0.32, 2018.0, 24.0 ),
        new DataSet( dateParser.parse( "27/AUG/09" ).getTime(), 0603.0, 0.30, 1878.0, 24.0 ),
        new DataSet( dateParser.parse( "28/AUG/09" ).getTime(), 0621.0, 0.31, 1844.0, 24.0 ),
        new DataSet( dateParser.parse( "29/AUG/09" ).getTime(), 0475.0, 0.26, 1637.0, 24.0 ),
        new DataSet( dateParser.parse( "30/AUG/09" ).getTime(), 0401.0, 0.23, 1439.0, 24.0 ),
        new DataSet( dateParser.parse( "31/AUG/09" ).getTime(), 0414.0, 0.23, 1429.0, 24.0 ),
        new DataSet( dateParser.parse( "01/SEP/09" ).getTime(), 0367.0, 0.21, 1256.0, 24.0 ),
        new DataSet( dateParser.parse( "02/SEP/09" ).getTime(), 0363.0, 0.21, 1242.0, 24.0 ),
        new DataSet( dateParser.parse( "03/SEP/09" ).getTime(), 0321.0, 0.21, 1089.0, 24.0 ),
        new DataSet( dateParser.parse( "04/SEP/09" ).getTime(), 0301.0, 0.20, 0993.0, 24.0 ),
        new DataSet( dateParser.parse( "05/SEP/09" ).getTime(), 0530.0, 0.29, 1982.0, 24.0 ),
        new DataSet( dateParser.parse( "06/SEP/09" ).getTime(), 0544.0, 0.30, 1962.0, 24.0 ),
        new DataSet( dateParser.parse( "07/SEP/09" ).getTime(), 0591.0, 0.30, 1873.0, 24.0 ),
        new DataSet( dateParser.parse( "08/SEP/09" ).getTime(), 0583.0, 0.30, 1859.0, 24.0 ),
        new DataSet( dateParser.parse( "09/SEP/09" ).getTime(), 0559.0, 0.30, 1874.0, 24.0 ),
        new DataSet( dateParser.parse( "10/SEP/09" ).getTime(), 0484.0, 0.26, 1627.0, 24.0 ),
        new DataSet( dateParser.parse( "11/SEP/09" ).getTime(), 0053.0, 0.03, 0194.0, 24.0 ),
        new DataSet( dateParser.parse( "12/SEP/09" ).getTime(), 0105.0, 0.06, 0343.0, 24.0 ),
        new DataSet( dateParser.parse( "13/SEP/09" ).getTime(), 0080.0, 0.06, 1400.0, 24.0 ),
        new DataSet( dateParser.parse( "14/SEP/09" ).getTime(), 0009.0, 0.01, 0153.0, 24.0 ),
        new DataSet( dateParser.parse( "15/SEP/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "16/SEP/09" ).getTime(), 0250.0, 0.12, 0937.0, 24.0 ),
        new DataSet( dateParser.parse( "17/SEP/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "18/SEP/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "19/SEP/09" ).getTime(), 0529.0, 0.23, 1693.0, 24.0 ),
        new DataSet( dateParser.parse( "20/SEP/09" ).getTime(), 0000.0, 0.00, 0000.0, 24.0 ),
        new DataSet( dateParser.parse( "21/SEP/09" ).getTime(), 4466.0, 1.85, 3958.0, 24.0 ),
        new DataSet( dateParser.parse( "22/SEP/09" ).getTime(), 5730.0, 2.26, 5157.0, 24.0 ),
        new DataSet( dateParser.parse( "23/SEP/09" ).getTime(), 5981.0, 2.33, 5160.0, 24.0 ),
        new DataSet( dateParser.parse( "24/SEP/09" ).getTime(), 6289.0, 2.16, 5225.0, 24.0 ),
        new DataSet( dateParser.parse( "25/SEP/09" ).getTime(), 5541.0, 2.08, 5158.0, 24.0 ),
        new DataSet( dateParser.parse( "26/SEP/09" ).getTime(), 5178.0, 2.04, 5159.0, 24.0 ),
        new DataSet( dateParser.parse( "27/SEP/09" ).getTime(), 5812.0, 2.04, 5107.0, 24.0 ),
        new DataSet( dateParser.parse( "28/SEP/09" ).getTime(), 5912.0, 2.06, 5181.0, 24.0 ),
        new DataSet( dateParser.parse( "29/SEP/09" ).getTime(), 5489.0, 2.05, 5112.0, 24.0 ),
        new DataSet( dateParser.parse( "30/SEP/09" ).getTime(), 5609.0, 2.05, 5094.0, 24.0 ),
        new DataSet( dateParser.parse( "01/OKT/09" ).getTime(), 5290.0, 2.03, 5216.0, 24.0 ),
        new DataSet( dateParser.parse( "02/OKT/09" ).getTime(), 5348.0, 1.91, 5594.0, 24.0 ),
        new DataSet( dateParser.parse( "03/OKT/09" ).getTime(), 5167.0, 1.90, 5570.0, 24.0 ),
        new DataSet( dateParser.parse( "04/OKT/09" ).getTime(), 5016.0, 1.89, 5537.0, 24.0 ),
        new DataSet( dateParser.parse( "05/OKT/09" ).getTime(), 5040.0, 1.90, 5532.0, 24.0 ),
        new DataSet( dateParser.parse( "06/OKT/09" ).getTime(), 4840.0, 1.83, 5595.0, 24.0 ),
        new DataSet( dateParser.parse( "07/OKT/09" ).getTime(), 4968.0, 1.75, 5550.0, 24.0 ),
        new DataSet( dateParser.parse( "08/OKT/09" ).getTime(), 4870.0, 1.51, 5573.0, 24.0 ),
        new DataSet( dateParser.parse( "09/OKT/09" ).getTime(), 4424.0, 1.61, 5674.0, 24.0 ),
        new DataSet( dateParser.parse( "10/OKT/09" ).getTime(), 4601.0, 1.63, 5652.0, 24.0 ),
        new DataSet( dateParser.parse( "11/OKT/09" ).getTime(), 4475.0, 1.60, 5622.0, 24.0 ),
        new DataSet( dateParser.parse( "12/OKT/09" ).getTime(), 4624.0, 1.59, 5553.0, 24.0 ),
        new DataSet( dateParser.parse( "13/OKT/09" ).getTime(), 4518.0, 1.57, 5574.0, 24.0 ),
        new DataSet( dateParser.parse( "14/OKT/09" ).getTime(), 4592.0, 1.71, 5629.0, 24.0 ),
        new DataSet( dateParser.parse( "15/OKT/09" ).getTime(), 4246.0, 1.57, 5645.0, 24.0 ),
        new DataSet( dateParser.parse( "16/OKT/09" ).getTime(), 4332.0, 1.57, 5450.0, 24.0 ),
        new DataSet( dateParser.parse( "17/OKT/09" ).getTime(), 4266.0, 1.55, 5472.0, 24.0 ),
        new DataSet( dateParser.parse( "18/OKT/09" ).getTime(), 4229.0, 1.55, 5461.0, 24.0 ),
        new DataSet( dateParser.parse( "19/OKT/09" ).getTime(), 4212.0, 1.48, 5525.0, 24.0 ),
        new DataSet( dateParser.parse( "20/OKT/09" ).getTime(), 4101.0, 1.45, 5333.0, 24.0 ),
        new DataSet( dateParser.parse( "21/OKT/09" ).getTime(), 4108.0, 1.45, 5328.0, 24.0 ),
        new DataSet( dateParser.parse( "22/OKT/09" ).getTime(), 4080.0, 1.41, 5473.0, 24.0 ),
        new DataSet( dateParser.parse( "23/OKT/09" ).getTime(), 3917.0, 1.39, 5435.0, 24.0 ),
        new DataSet( dateParser.parse( "24/OKT/09" ).getTime(), 3819.0, 1.38, 5422.0, 24.0 ),
        new DataSet( dateParser.parse( "25/OKT/09" ).getTime(), 3774.0, 1.36, 5313.0, 24.0 ),
        new DataSet( dateParser.parse( "26/OKT/09" ).getTime(), 3588.0, 1.22, 5130.0, 24.0 ),
        new DataSet( dateParser.parse( "27/OKT/09" ).getTime(), 3774.0, 1.36, 5272.0, 24.0 ),
        new DataSet( dateParser.parse( "28/OKT/09" ).getTime(), 3559.0, 1.37, 5269.0, 24.0 ),
        new DataSet( dateParser.parse( "29/OKT/09" ).getTime(), 3601.0, 1.34, 5266.0, 24.0 ),
        new DataSet( dateParser.parse( "30/OKT/09" ).getTime(), 3477.0, 1.29, 5240.0, 24.0 ),
        new DataSet( dateParser.parse( "31/OKT/09" ).getTime(), 3638.0, 1.28, 5138.0, 24.0 ),
        new DataSet( dateParser.parse( "01/NOV/09" ).getTime(), 3458.0, 1.26, 5160.0, 24.0 ),
        new DataSet( dateParser.parse( "02/NOV/09" ).getTime(), 3417.0, 1.26, 5168.0, 24.0 ),
        new DataSet( dateParser.parse( "03/NOV/09" ).getTime(), 3447.0, 1.09, 5136.0, 24.0 ),
        new DataSet( dateParser.parse( "04/NOV/09" ).getTime(), 3302.0, 1.25, 5087.0, 24.0 ),
        new DataSet( dateParser.parse( "05/NOV/09" ).getTime(), 3385.0, 1.20, 5033.0, 24.0 ),
        new DataSet( dateParser.parse( "06/NOV/09" ).getTime(), 3177.0, 1.21, 5033.0, 24.0 ),
        new DataSet( dateParser.parse( "07/NOV/09" ).getTime(), 3183.0, 1.19, 5036.0, 24.0 ),
        new DataSet( dateParser.parse( "08/NOV/09" ).getTime(), 3111.0, 1.19, 5067.0, 24.0 ),
        new DataSet( dateParser.parse( "09/NOV/09" ).getTime(), 3123.0, 1.18, 5028.0, 24.0 ),
        new DataSet( dateParser.parse( "10/NOV/09" ).getTime(), 3106.0, 1.14, 4887.0, 24.0 ),
        new DataSet( dateParser.parse( "11/NOV/09" ).getTime(), 3026.0, 1.14, 4947.0, 24.0 ),
        new DataSet( dateParser.parse( "12/NOV/09" ).getTime(), 2931.0, 1.11, 4874.0, 24.0 ),
        new DataSet( dateParser.parse( "13/NOV/09" ).getTime(), 2906.0, 1.09, 4854.0, 24.0 ),
        new DataSet( dateParser.parse( "14/NOV/09" ).getTime(), 2788.0, 1.10, 4864.0, 24.0 ),
        new DataSet( dateParser.parse( "15/NOV/09" ).getTime(), 2807.0, 1.10, 4867.0, 24.0 ),
        new DataSet( dateParser.parse( "16/NOV/09" ).getTime(), 2984.0, 1.17, 5100.0, 24.0 ),
        new DataSet( dateParser.parse( "17/NOV/09" ).getTime(), 2965.0, 1.21, 5256.0, 24.0 ),
        new DataSet( dateParser.parse( "18/NOV/09" ).getTime(), 3003.0, 1.23, 5312.0, 24.0 ),
        new DataSet( dateParser.parse( "19/NOV/09" ).getTime(), 3056.0, 1.22, 5345.0, 24.0 ),
        new DataSet( dateParser.parse( "20/NOV/09" ).getTime(), 3100.0, 1.20, 5304.0, 24.0 ),
        new DataSet( dateParser.parse( "21/NOV/09" ).getTime(), 3102.0, 1.21, 5332.0, 24.0 ),
        new DataSet( dateParser.parse( "22/NOV/09" ).getTime(), 2441.0, 0.91, 5724.0, 24.0 ),
        new DataSet( dateParser.parse( "23/NOV/09" ).getTime(), 2310.0, 0.90, 5721.0, 24.0 ),
        new DataSet( dateParser.parse( "24/NOV/09" ).getTime(), 2237.0, 0.89, 5644.0, 24.0 ),
        new DataSet( dateParser.parse( "25/NOV/09" ).getTime(), 2367.0, 0.90, 5474.0, 24.0 )

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
