package au.com.polly.util;

import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Exercise the weighted averge calculator
 */
@RunWith(JUnit4.class)
public class WeightedAverageCalculatorTest
{

@Before
public void setUp() throws Exception
{
    //To change body of created methods use File | Settings | File Templates.
}

@Test( expected = IllegalArgumentException.class )
public void testZeroSizedCalculator() throws Exception
{
    WeightedAverageCalculator wac = new WeightedAverageCalculator( 0 );
}

@Test( expected = IllegalArgumentException.class )
public void testSingleElementCalculator() throws Exception
{
    WeightedAverageCalculator wac = new WeightedAverageCalculator( 1 );
}

@Test
public void testNoArgsConstructor()
{
     WeightedAverageCalculator wac = new WeightedAverageCalculator();
    assertNotNull( wac );
    assertEquals( 5, wac.getSampleSize() );
}

@Test
public void testPopulatingCalculator()
{
    WeightedAverageCalculator wac = new WeightedAverageCalculator( 10 );
    double x[];
    wac.add( 1.0 );

    x=wac.__unit_test_only_getEntries();
    assertNotNull( x );
    assertEquals( 1, x.length );
    assertEquals( 1.0, x[ 0 ], 0.0001 );

    wac.add( 2.0 );
    x=wac.__unit_test_only_getEntries();
    assertNotNull( x );
    assertEquals( 2, x.length );
    assertEquals( 2.0, x[ 0 ], 0.0001 );
    assertEquals( 1.0, x[ 1 ], 0.0001 );

    wac.add( 3.0 );
    x=wac.__unit_test_only_getEntries();
    assertNotNull( x );
    assertEquals( 3, x.length );
    assertEquals( 3.0, x[ 0 ], 0.0001 );
    assertEquals( 2.0, x[ 1 ], 0.0001 );
    assertEquals( 1.0, x[ 2 ], 0.0001 );


    wac.add( 4.0 );
    x=wac.__unit_test_only_getEntries();
    assertNotNull( x );
    assertEquals( 4, x.length );
    assertEquals( 4.0, x[ 0 ], 0.0001 );
    assertEquals( 3.0, x[ 1 ], 0.0001 );
    assertEquals( 2.0, x[ 2 ], 0.0001 );
    assertEquals( 1.0, x[ 3 ], 0.0001 );

    wac.add( 5.0 );
    x=wac.__unit_test_only_getEntries();
    assertNotNull( x );
    assertEquals( 5, x.length );
    assertEquals( 5.0, x[ 0 ], 0.0001 );
    assertEquals( 4.0, x[ 1 ], 0.0001 );
    assertEquals( 3.0, x[ 2 ], 0.0001 );
    assertEquals( 2.0, x[ 3 ], 0.0001 );
    assertEquals( 1.0, x[ 4 ], 0.0001 );

    wac.add( 6.0 );
    wac.add( 7.0 );
    wac.add( 8.0 );
    wac.add( 9.0 );
    wac.add( 10.0 );

    x=wac.__unit_test_only_getEntries();
    assertNotNull( x );
    assertEquals( 10, x.length );
    assertEquals( 10.0, x[ 0 ], 0.0001 );
    assertEquals( 9.0, x[ 1 ], 0.0001 );
    assertEquals( 8.0, x[ 2 ], 0.0001 );
    assertEquals( 7.0, x[ 3 ], 0.0001 );
    assertEquals( 6.0, x[ 4 ], 0.0001 );
    assertEquals( 5.0, x[ 5 ], 0.0001 );
    assertEquals( 4.0, x[ 6 ], 0.0001 );
    assertEquals( 3.0, x[ 7 ], 0.0001 );
    assertEquals( 2.0, x[ 8 ], 0.0001 );
    assertEquals( 1.0, x[ 9 ], 0.0001 );


    wac.add( 11.0 );
    x=wac.__unit_test_only_getEntries();
    assertNotNull( x );
    assertEquals( 10, x.length );
    assertEquals( 11.0, x[ 0 ], 0.0001 );
    assertEquals( 10.0, x[ 1 ], 0.0001 );
    assertEquals( 9.0, x[ 2 ], 0.0001 );
    assertEquals( 8.0, x[ 3 ], 0.0001 );
    assertEquals( 7.0, x[ 4 ], 0.0001 );
    assertEquals( 6.0, x[ 5 ], 0.0001 );
    assertEquals( 5.0, x[ 6 ], 0.0001 );
    assertEquals( 4.0, x[ 7 ], 0.0001 );
    assertEquals( 3.0, x[ 8 ], 0.0001 );
    assertEquals( 2.0, x[ 9 ], 0.0001 );


}

@Test
public void testWithSameValue()
{
    WeightedAverageCalculator wac = new WeightedAverageCalculator();
    wac.add( 23.0 );
    assertEquals( 23.0, wac.getWeightedAverage() );
    wac.add( 23.0 );
    assertEquals( 23.0, wac.getWeightedAverage() );
    wac.add( 23.0 );
    assertEquals( 23.0, wac.getWeightedAverage() );
    wac.add( 23.0 );
    assertEquals( 23.0, wac.getWeightedAverage() );
    wac.add( 23.0 );
    assertEquals( 23.0, wac.getWeightedAverage() );
    wac.add( 23.0 );
    assertEquals( 23.0, wac.getWeightedAverage() );
    wac.add( 23.0 );
    assertEquals( 23.0, wac.getWeightedAverage() );
    wac.add( 23.0 );
    assertEquals( 23.0, wac.getWeightedAverage() );
}

@Test
public void testSomeDifferentValues()
{
    WeightedAverageCalculator wac = new WeightedAverageCalculator( 5 );
    // last entry = 0.5
    // n-2 := 0.25
    // n=3 : 0.125
    // n=4 : 0.0625
    // n=5 : 0.03125 + 0.3125

    wac.add( 100.0 );
    assertEquals( 100.0, wac.getWeightedAverage(), 0.00001 );

    wac.add( 200.0 );
    assertEquals( 175.0, wac.getWeightedAverage(), 0.00001  );

    wac.add( 200.0 );
    assertEquals( 187.5, wac.getWeightedAverage(), 0.00001 );

    wac.add( 200.0 );
    assertEquals( 193.75, wac.getWeightedAverage(), 0.00001 );

    wac.add( 200.0 );
    assertEquals( 196.875, wac.getWeightedAverage(), 0.00001 );

    wac.add( 200.0 );
    assertEquals( 200.0, wac.getWeightedAverage(), 0.00001 );

    wac.add( 100.0 );
    assertEquals( 146.875, wac.getWeightedAverage(), 0.00001 );

    wac.add( 100.0 );
    assertEquals( 121.875, wac.getWeightedAverage(), 0.00001 );

    wac.add( 100.0 );
    assertEquals( 109.375, wac.getWeightedAverage(), 0.00001 );

    wac.add( 100.0 );
    assertEquals( 103.125, wac.getWeightedAverage(), 0.00001 );

    wac.add( 100.0 );
    assertEquals( 100.0, wac.getWeightedAverage(), 0.00001 );
}

public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( WeightedAverageCalculatorTest.class );
}


}
