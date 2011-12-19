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

package au.com.polly.util.csv;

import au.com.polly.util.StringArmyKnife;

/**
  *  Represents the various names that a column heading might go by. Allows
  *  us to be a little more flexible when determining which columns go where..
  * 
  *
  */
public class FieldAliases
{
	private		String		label;
	protected	boolean		mandatory;
	private		String[]	aliases;

    /**
      *  @param label the actual label that this field will be know by
      *    internally.
      *  @param aliases array of strings identifying other forms that the
      *    field might by identified by. case is independant and leading and
      *    trailing whitespace is removed.
      *
      * This constructor assumes that this field is not mandator, ie: it is
      * <b>not</b> an error condition of this field is not present within the
      * column headings of the input data.
      *
      */
    public FieldAliases( String label, String[] aliases )
    {
        this( label, false, aliases );
    }

    /**
      *  @param label the actual label that this field will be know by
      *    internally.
      *  @param mandatory whether or not this field <b>must</b> be present
      *    within the input data.
      *  @param aliases array of strings identifying other forms that the
      *    field might by identified by. case is independant and leading and
      *    trailing whitespace is removed.
      *
      */
    public FieldAliases( String label, boolean mandatory, String[] aliases )
    {
        int	i			=	0;
        this.label 		=	label;
        this.aliases	=	aliases;
        this.mandatory	=	mandatory;

        for ( i = 0; i < this.aliases.length; i++ )
        {
            this.aliases[ i ] = StringArmyKnife.removeUnwantedCharacters( this.aliases[ i ].trim().toLowerCase(), ".:,;\"" );
        }
    }

    /**
      * @return actual field label for this bunch of aliases.
      */
    public String getLabel()
    {
        return this.label;
    }

    public boolean isMandatory()
    {
        return this.mandatory;
    }

    /**
      * @return whether or not the proposed text matches any of the aliases.
      *
      * @param text the string to be interrogated.
      *
      * Use this method to determine whether the column heading/field name
      * supplied unambiguously matches this field. The method
      * <a href="#almostMatches(String)">almostMatches</a> allows you to test
      * for weaker matching criteria.
      */
    public boolean matches( String text )
    {
        int 	i		=	0;
        String	refined =	StringArmyKnife.removeUnwantedCharacters(text.trim().toLowerCase(), ",.:;\"");
        boolean	result	=	false;

        //	we don't need to know which aliases matches, just whether or not
        //	it matches or not...
        //	-----------------------------------------------------------------
        for ( i = 0; ( result == false ) && ( i < aliases.length ); i++ )
        {

            if ( refined.equals( aliases[ i ] ) )
            {
                result = true;
                continue;
            }

        }

        return result;
    }


    /**
      * @return whether or not the proposed text loosely matches any of the aliases.
      *
      * @param text the string to be interrogated.
      *
      * This method uses weaker matching critiera. Rather than checking that
      * the supplied string exactly matches any aliases held, it only compares
      * as many characters as there are in the alias specifier. It will
      * also return true if the strings exactly match.
      *
      */
    public boolean almostMatches( String text )
    {
        int 	i		=	0;
        String	refined =	StringArmyKnife.removeUnwantedCharacters( text.trim().toLowerCase(), ",.:\"" );
        boolean	result	=	false;

        //	we don't need to know which aliases matches, just whether or not
        //	it matches or not...
        //	-----------------------------------------------------------------
        for ( i = 0; ( result == false ) && ( i < aliases.length ); i++ )
        {

            if ( refined.equals( aliases[ i ] )
                 || (		( refined.length() > aliases[ i ].length() )
                        &&	( refined.startsWith( aliases[ i ] ) )
                    )
                )
            {
                result = true;
                continue;
            }

        }

        return result;
    }

    /**
      * @return human readable respresentation of the field aliases.
      *
      *
      */
    public String toString()
    {
        StringBuffer 	out = null;
        int				i	=	0;

        out = new StringBuffer();

        out.append( "label=[" + getLabel() + "]" );

        out.append( mandatory ? ", mandatory" : ", not mandatory" );

        for (  i = 0; i < aliases.length; i++ )
        {
            out.append( ", aliases[" + i + "]=\"" + aliases[ i ] + "\"" );
        }

        return out.toString();
    }

    /**
      * @return the array of aliases (case independant) which will match
      *  for this field.
      */
    public String[] getAliases()
    {
        return aliases;
    }

}
