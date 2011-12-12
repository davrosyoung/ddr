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

/**
 * Created by IntelliJ IDEA.
 * User: dave
 * Date: 12/12/11
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringArmyKnife
{

public static boolean areStringsEqual( String alpha, String beta )
{
    boolean result = false;
    
    do {
        // if both strings are null, then they are equal!!
        // ------------------------------------------------
        if ( result = ( ( alpha == null ) && ( beta == null ) ) )
        {
            break;
        }
        
        // if one or tuther is null, then they are not equal!!
        // --------------------------------------------------
        if ( ( alpha == null ) || ( beta == null ) )
        {
            result = false;
            break;
        }
        
        result = alpha.equals( beta );
        
    } while( false );
    
    return result;
}

}