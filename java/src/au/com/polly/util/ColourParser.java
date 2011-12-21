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

package au.com.polly.util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Allows us to determine a colour object to use, for a given colour string..
 *
 */
public class ColourParser
{
static Map<String,Color> colourLUT = new HashMap<String,Color>();

static {
    colourLUT.put( "black", Color.BLACK );
    colourLUT.put( "grey", Color.GRAY );
    colourLUT.put( "gray", Color.GRAY );
    colourLUT.put( "white", Color.WHITE );
    colourLUT.put( "red", Color.RED );
    colourLUT.put( "orange", Color.ORANGE );
    colourLUT.put( "yellow", Color.YELLOW );
    colourLUT.put( "green", Color.GREEN );
    colourLUT.put( "blue", Color.BLUE );
    colourLUT.put( "cyan", Color.CYAN );
    colourLUT.put( "magenta", Color.MAGENTA);
}

/**
*
* @param name the name of the colour desired
* @return our best guess at the colour you wanted, or null if we couldn't figure it out.
*/
static public Color parse( String name )
{
    Color result = null;
    String filteredName = name.toLowerCase().trim();

    if ( colourLUT.containsKey( filteredName ) )
    {
        result = colourLUT.get( filteredName );
    }

    return result;
}

}
