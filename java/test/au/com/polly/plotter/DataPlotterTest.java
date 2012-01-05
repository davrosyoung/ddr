/*
 * Copyright (c) 2011-2012 Polly Enterprises Pty Ltd and/or its affiliates.
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

package au.com.polly.plotter;

import junit.framework.JUnit4TestAdapter;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.awt.*;
import java.sql.SQLTransactionRollbackException;
import java.util.EnumSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * Battery of tests of the data series class.
 *
 */
@RunWith(JUnit4.class)
public class DataPlotterTest
{
private final static Logger logger = Logger.getLogger( DataPlotterTest.class );
private final static double ACCEPTABLE_ERROR = 1E-6;

protected BasicStroke bs;
protected Stroke stroke;
protected float[] dashArray;


@Test
public void testObtainAppropriateStrokeNoLine()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.NONE ) );
    assertNull( stroke );
}

@Test
public void testObtainAppropriateStokeDashedJoined()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.DASHED, PlotData.LineStyle.JOINED ) );
    assertNotNull(stroke);
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getLineWidth(), 2.0f, ACCEPTABLE_ERROR );

    dashArray = bs.getDashArray();
    assertNotNull( dashArray );
    assertEquals( 4, dashArray.length );
    assertEquals( 6.0f, dashArray[ 0 ], ACCEPTABLE_ERROR );
    assertEquals( 6.0f, dashArray[ 1 ], ACCEPTABLE_ERROR );
    assertEquals( 6.0f, dashArray[ 2 ], ACCEPTABLE_ERROR );
    assertEquals( 6.0f, dashArray[ 3 ], ACCEPTABLE_ERROR );

}

@Test
public void testObtainAppropriateStrokeJoined()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.JOINED ) );
    assertNotNull( stroke );
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getDashArray(), null );
    assertEquals( bs.getLineWidth(), 2.0f, ACCEPTABLE_ERROR );
}

@Test
public void testObtainAppropriateStrokeJoinedFineDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.FINE_DASHED, PlotData.LineStyle.JOINED ) );
    assertNotNull(stroke);
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    dashArray = bs.getDashArray();
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getLineWidth(), 2.0f, ACCEPTABLE_ERROR );

    assertNotNull(dashArray);
    assertEquals(4, dashArray.length);
    assertEquals(3.0f, dashArray[0], ACCEPTABLE_ERROR);
    assertEquals( 3.0f, dashArray[ 1 ], ACCEPTABLE_ERROR );
    assertEquals( 3.0f, dashArray[ 2 ], ACCEPTABLE_ERROR );
    assertEquals( 3.0f, dashArray[ 3 ], ACCEPTABLE_ERROR );
}

@Test
public void testObtainAppropriateStrokeThinJoinedNoDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.JOINED, PlotData.LineStyle.THIN ) );
    assertNotNull( stroke );
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getDashArray(), null );
    assertEquals( bs.getLineWidth(), 1.0f, ACCEPTABLE_ERROR );
}

@Test
public void testObtainAppropriateStrokeThinJoinedFineDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.FINE_DASHED, PlotData.LineStyle.JOINED, PlotData.LineStyle.THIN ) );
    assertNotNull( stroke );
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getLineWidth(), 1.0f, ACCEPTABLE_ERROR );

    dashArray = bs.getDashArray();
    assertNotNull( dashArray );
    assertEquals( 4, dashArray.length );
    assertEquals( 3.0f, dashArray[ 0 ], ACCEPTABLE_ERROR );
    assertEquals( 3.0f, dashArray[ 1 ], ACCEPTABLE_ERROR );
    assertEquals( 3.0f, dashArray[ 2 ], ACCEPTABLE_ERROR );
    assertEquals( 3.0f, dashArray[ 3 ], ACCEPTABLE_ERROR );
}

@Test
public void testObtainAppropriateStrokeThinJoinedRegularDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.DASHED, PlotData.LineStyle.JOINED, PlotData.LineStyle.THIN ) );
    assertNotNull(stroke);
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getLineWidth(), 1.0f, ACCEPTABLE_ERROR );

    dashArray = bs.getDashArray();
    assertNotNull( dashArray );
    assertEquals( 4, dashArray.length );
    assertEquals( 6.0f, dashArray[ 0 ], ACCEPTABLE_ERROR );
    assertEquals( 6.0f, dashArray[ 1 ], ACCEPTABLE_ERROR );
    assertEquals( 6.0f, dashArray[ 2 ], ACCEPTABLE_ERROR );
    assertEquals( 6.0f, dashArray[ 3 ], ACCEPTABLE_ERROR );

}

@Test
public void testObtainAppropriateStrokeThinSteppedNoDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.STEPPED, PlotData.LineStyle.THIN ) );
    assertNotNull( stroke );
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getDashArray(), null );
    assertEquals( bs.getLineWidth(), 1.0f, ACCEPTABLE_ERROR );
}

@Test
public void testObtainAppropriateStrokeThinSteppedFineDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.FINE_DASHED, PlotData.LineStyle.STEPPED, PlotData.LineStyle.THIN ) );
    assertNotNull( stroke );
    assertTrue(stroke instanceof BasicStroke);
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getLineWidth(), 1.0f, ACCEPTABLE_ERROR );
    dashArray = bs.getDashArray();
    assertNotNull( dashArray );
    assertEquals( 4, dashArray.length );
    assertEquals( 3.0f, dashArray[ 0 ], ACCEPTABLE_ERROR );
    assertEquals( 3.0f, dashArray[ 1 ], ACCEPTABLE_ERROR );
    assertEquals( 3.0f, dashArray[ 2 ], ACCEPTABLE_ERROR );
    assertEquals( 3.0f, dashArray[ 3 ], ACCEPTABLE_ERROR );
}


@Test
public void testObtainAppropriateStrokeThinSteppedRegularDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.DASHED, PlotData.LineStyle.STEPPED, PlotData.LineStyle.THIN ) );
    assertNotNull(stroke);
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getLineWidth(), 1.0f, ACCEPTABLE_ERROR );
    dashArray = bs.getDashArray();
    assertNotNull( dashArray );
    assertEquals( 4, dashArray.length );
    assertEquals( 6.0f, dashArray[ 0 ], ACCEPTABLE_ERROR );
    assertEquals( 6.0f, dashArray[ 1 ], ACCEPTABLE_ERROR );
    assertEquals( 6.0f, dashArray[ 2 ], ACCEPTABLE_ERROR );
    assertEquals( 6.0f, dashArray[ 3 ], ACCEPTABLE_ERROR );
}

@Test
public void testObtainAppropriateStrokeThinNoDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.THIN ) );
    assertNull(stroke);
}

@Test
public void testObtainAppropriateStrokeThinFineDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.THIN, PlotData.LineStyle.FINE_DASHED ) );
    assertNull( stroke );

}

@Test
public void testObtainAppropriateStrokeThinRegularDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.THIN, PlotData.LineStyle.DASHED ) );
    assertNull( stroke );
}

@Test
public void testObtainAppropriateStrokeThickJoinedRegularDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.DASHED, PlotData.LineStyle.JOINED, PlotData.LineStyle.THICK ) );
    assertNotNull(stroke);
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getLineWidth(), 3.0f, ACCEPTABLE_ERROR );

    dashArray = bs.getDashArray();
    assertNotNull( dashArray );
    assertEquals( 4, dashArray.length );
    assertEquals( 8.0f, dashArray[ 0 ], ACCEPTABLE_ERROR );
    assertEquals( 8.0f, dashArray[ 1 ], ACCEPTABLE_ERROR );
    assertEquals( 8.0f, dashArray[ 2 ], ACCEPTABLE_ERROR );
    assertEquals( 8.0f, dashArray[ 3 ], ACCEPTABLE_ERROR );
}

@Test
public void testObtainAppropriateStrokeThickSteppedNoDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.STEPPED, PlotData.LineStyle.THICK ) );
    assertNotNull( stroke );
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getDashArray(), null );
    assertEquals( bs.getLineWidth(), 3.0f, ACCEPTABLE_ERROR );
}

@Test
public void testObtainAppropriateStrokeThickSteppedFineDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.FINE_DASHED, PlotData.LineStyle.STEPPED, PlotData.LineStyle.THICK ) );
    assertNotNull(stroke);
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getLineWidth(), 3.0f, ACCEPTABLE_ERROR );

    dashArray = bs.getDashArray();
    assertNotNull( dashArray );
    assertEquals( 4, dashArray.length );
    assertEquals( 4.0f, dashArray[ 0 ], ACCEPTABLE_ERROR );
    assertEquals( 4.0f, dashArray[ 1 ], ACCEPTABLE_ERROR );
    assertEquals( 4.0f, dashArray[ 2 ], ACCEPTABLE_ERROR );
    assertEquals( 4.0f, dashArray[ 3 ], ACCEPTABLE_ERROR );
}


@Test
public void testObtainAppropriateStrokeExtraThickJoinedRegularDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.DASHED, PlotData.LineStyle.JOINED, PlotData.LineStyle.EXTRA_THICK ) );
    assertNotNull(stroke);
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getLineWidth(), 5.0f, ACCEPTABLE_ERROR );

    dashArray = bs.getDashArray();
    assertNotNull( dashArray );
    assertEquals( 4, dashArray.length );
    assertEquals( 10.0f, dashArray[ 0 ], ACCEPTABLE_ERROR );
    assertEquals( 10.0f, dashArray[ 1 ], ACCEPTABLE_ERROR );
    assertEquals( 10.0f, dashArray[ 2 ], ACCEPTABLE_ERROR );
    assertEquals( 10.0f, dashArray[ 3 ], ACCEPTABLE_ERROR );
}

@Test
public void testObtainAppropriateStrokeExtraThickSteppedNoDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.STEPPED, PlotData.LineStyle.EXTRA_THICK ) );
    assertNotNull( stroke );
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getDashArray(), null );
    assertEquals( bs.getLineWidth(), 5.0f, ACCEPTABLE_ERROR );
}

@Test
public void testObtainAppropriateStrokeExtraThickSteppedFineDash()
{
    stroke = DataPlotter.obtainAppropriateStroke(EnumSet.of( PlotData.LineStyle.FINE_DASHED, PlotData.LineStyle.STEPPED, PlotData.LineStyle.EXTRA_THICK ) );
    assertNotNull(stroke);
    assertTrue( stroke instanceof BasicStroke );
    bs = (BasicStroke)stroke;
    assertEquals( bs.getDashPhase(), 0.0f, ACCEPTABLE_ERROR );
    assertEquals( bs.getLineWidth(), 5.0f, ACCEPTABLE_ERROR );

    dashArray = bs.getDashArray();
    assertNotNull( dashArray );
    assertEquals( 4, dashArray.length );
    assertEquals( 5.0f, dashArray[ 0 ], ACCEPTABLE_ERROR );
    assertEquals( 5.0f, dashArray[ 1 ], ACCEPTABLE_ERROR );
    assertEquals( 5.0f, dashArray[ 2 ], ACCEPTABLE_ERROR );
    assertEquals( 5.0f, dashArray[ 3 ], ACCEPTABLE_ERROR );
}



public static junit.framework.Test suite()
{
    return new JUnit4TestAdapter( DataPlotterTest.class );
}

}