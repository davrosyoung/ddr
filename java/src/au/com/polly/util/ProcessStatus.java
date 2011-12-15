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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Dave Young
 *
 *
 * Contains errors, warnings and information messages about some computational process
 * (such as extracting
 */
public class ProcessStatus
{
List<String> infoMessages;
List<String> warningMessages;
List<String> errorMessages;

AtomicInteger percentageComplete = new AtomicInteger( 0 );
AtomicReference<String> phase = new AtomicReference<String>( "processing" ); 

public ProcessStatus()
{
    infoMessages = Collections.synchronizedList( new ArrayList<String>() );
    warningMessages = Collections.synchronizedList( new ArrayList<String>() );
    errorMessages = Collections.synchronizedList( new ArrayList<String>() );
}

public void setPhase( String phaseDescription, int complete )
{
    phase.set( phaseDescription );
    percentageComplete.set( complete );
}

public int getPercentageComplete()
{
    return percentageComplete.get();
}

public String getPhase()
{
    return phase.get();
}

public int numberWarnings()
{
    return warningMessages.size();
}

public int numberErrors()
{
    return errorMessages.size();
}

public List<String> getInfoMessages()
{
    return infoMessages;
}

public List<String> getWarningMessages()
{
    return warningMessages;
}

public List<String> getErrorMessages()
{
    return errorMessages;
}

public void addInfoMessage( String text )
{
    infoMessages.add( text );
}

public void addWarningMessage( String text )
{
    warningMessages.add( text );
}

public void addErrorMessage( String text )
{
    errorMessages.add( text );
}


}
