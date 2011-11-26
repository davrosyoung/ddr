package au.com.polly.util;


import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 24/11/11
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Sequencer
{
private final static Logger logger = Logger.getLogger( Sequencer.class );
private int positions;
private int[] node;
private boolean finished = false;
private boolean started = false;

public Sequencer( int nodes, int positions )
{
    if ( nodes < 1 )
    {
        throw new IllegalArgumentException( "Must have at least ONE node" );
    }

    if ( positions <= nodes )
    {
        throw new IllegalArgumentException( "You specified only " + positions + " positions, must be greater than number of nodes=" + nodes );
    }

    node = new int[ nodes ];
    this.positions = positions;
    for( int i = 0; i < node.length; i++ )
    {
        node[ i ] = i;
    }
}

public int[] getNext()
{
    int[] result = null;

    do {

        if ( !started )
        {
            result = node;
            started = true;
            break;
        }

        if ( this.finished ) { break; }

        next();

        result = node;

    } while( false );

    return result;
}


/**
 * Occupy the next sequence in the pattern space..
 */
protected void next()
{
    int n = node.length;
    int[] result = new int[ n ];
    boolean resolved = false;

    // rightmost location = positions - 1
    // number of nodes = nodes


    do {
        // work from the rightmost node to the leftmost node...
        // ----------------------------------------------------
        for ( int i = n - 1; ( i >= 0 ) && !resolved; i-- )
        {

            int rightLimit = ( positions - 1 ) - ( ( n - 1 ) - i );

            // if this node is not at the rightmost location it can occupy ...
            // .... then move it one position to the right. if there are any
            // nodes to it's right, then set them to be adjacent to it...
            // --------------------------------------------------------
            if ( node[ i ] < rightLimit )
            {
                result[ i ] = node[ i ] + 1;

                // place the nodes to the right of this one in the leftmost
                // pattern that they can occupy.... we've made sure that
                // there is sufficient space above...
                // -----------------------------------------------------
                for( int j = i + 1; j <= ( n - 1 ); j++ )
                {
                    result[ j ] = result[ i ] + ( j - i );
                }

                // leave any nodes to the left where they currently are...
                // -------------------------------------------------------
                for( int j = 0; j < i; j++ )
                {
                    result[ j ] = node[ j ];
                }

                // mark the fact that we've found the next sequence!!
                // ---------------------------------------------------
                resolved = true;
            }
        }

        // if we couldn't manage to find the next sequence, then we've run
        // out of pattern space!!
        // -------------------------------------
        if  ( !resolved )
        {
            result = null;
            this.finished = true;
        }

    } while( false );

    this.node = result;
}

}
