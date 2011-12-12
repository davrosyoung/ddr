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
