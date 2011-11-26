package au.com.polly.util;

import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Exercise the sequencer....
 */
@RunWith(JUnit4.class)
public class SequencerTest
{

@Before
public void setUp() throws Exception
{
    //To change body of created methods use File | Settings | File Templates.
}

@Test( expected = IllegalArgumentException.class )
public void testEmptySequencer() throws Exception
{
    Sequencer seq = new Sequencer( 0, 0 );
}

@Test( expected = IllegalArgumentException.class )
public void testTooManyNodesSequencer() throws Exception
{
    Sequencer seq = new Sequencer( 11, 10 );
}

@Test
public void testTinySequencer() throws Exception
{
    int x[];
    Sequencer seq = new Sequencer( 1, 2 );
    assertNotNull( seq );

    x = seq.getNext();
    assertNotNull( x );
    assertEquals( 1, x.length );
    assertEquals( 0, x[0]);

    x = seq.getNext();
    assertNotNull( x );
    assertEquals( 1, x.length );
    assertEquals( 1, x[0]);

    x = seq.getNext();
    assertNull(x);
}

@Test
public void testSmallSequencer() throws Exception
{
    int x[];
    Sequencer seq = new Sequencer( 2, 4 );
    assertNotNull( seq );

    x = seq.getNext();
    assertNotNull( x );
    assertEquals( 2, x.length );
    assertEquals( 0, x[0] );
    assertEquals( 1, x[1] );

    x = seq.getNext();
    assertNotNull( x );
    assertEquals( 2, x.length );
    assertEquals( 0, x[0] );
    assertEquals( 2, x[1] );

    x = seq.getNext();
    assertNotNull( x );
    assertEquals( 2, x.length );
    assertEquals( 0, x[0] );
    assertEquals( 3, x[1] );

    x = seq.getNext();
    assertNotNull( x );
    assertEquals( 2, x.length );
    assertEquals( 1, x[0] );
    assertEquals( 2, x[1] );

    x = seq.getNext();
    assertNotNull( x );
    assertEquals( 2, x.length );
    assertEquals( 1, x[0] );
    assertEquals( 3, x[1] );

    x = seq.getNext();
    assertNotNull( x );
    assertEquals( 2, x.length );
    assertEquals( 2, x[0] );
    assertEquals( 3, x[1] );

    x = seq.getNext();
    assertNull(x);
}

public static junit.framework.Test suite() {
    return new JUnit4TestAdapter( SequencerTest.class );
}


}
