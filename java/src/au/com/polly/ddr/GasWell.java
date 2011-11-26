package au.com.polly.ddr;

import au.com.polly.util.HashCodeUtil;

import java.io.Serializable;

/**
 * Represents a gas well.
 *
 *
 */
public final class GasWell implements Serializable
{
private final String name;

public GasWell(String name)
{
    if ( name == null )
    {
        throw new NullPointerException( "Gas Well needs a non-NULL name!! Give me a break!!" );
    }

    String trimmed = name.trim();
    if ( trimmed.length() == 0 )
    {
        throw new IllegalArgumentException( "Gas well name is blank!" );
    }

    this.name = name.trim();
}

public String getName()
{
    return name;
}

public boolean equals( Object other )
{
    boolean result = false;
    if ( other instanceof GasWell )
    {
        GasWell otherWell = (GasWell)other;
        do {
            if ( ( otherWell.getName() == null ) && ( this.getName() == null ) )
            {
                result = true;
                break;
            }

            if ( ( otherWell.getName() == null ) || ( this.getName() == null ) )
            {
                result = false;
                break;
            }

            result = this.getName().equals( ((GasWell) other).getName() );
        } while( false );
    }
    return result;
}


@Override
public int hashCode()
{
    int result = HashCodeUtil.SEED;
    result = HashCodeUtil.hash( result, name );
    return result;
}


}
