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

import au.com.polly.util.DateRange;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object which encapsulates data for multiple wells in one object. Written to enable us to
 * serialize/deserialize multiple gas wells into a single serialization file.
 *
 * @author Dave Young
 * @version %I%
 *
 */
public class MultipleWellDataMap implements Serializable
{
transient Map<GasWell,GasWellDataSet> dataMap;

public MultipleWellDataMap()
{
    this.dataMap = new HashMap<GasWell,GasWellDataSet>();
}

protected Map<GasWell,GasWellDataSet> getDataMap()
{
    return dataMap;
}

public void addDataSet( GasWellDataSet dataSet )
{
    this.dataMap.put( dataSet.getWell(), dataSet );
}

/**
 * Instead of serializing this object, we serialize a proxy representation of it instead :-)
 *
 *
 * @return the serialization proxy to be written to the wire.
 */
private Object writeReplace()
{
    return new SerializationProxy( this );
}

/**
 *
 * @param writer where to write all of the data out to.
 */
protected void outputCSV( PrintWriter writer )
{
    boolean firstWell = true;
    
    for( GasWell well : dataMap.keySet() )
    {
        GasWellDataSet dataSet = dataMap.get( well );
        dataSet.outputCSV( writer, firstWell, true );
        firstWell = false;    
    }
}

/**
 *
 * Oh no you don't!!
 * @param stream
 * @throws java.io.InvalidObjectException
 */
private void readObject( ObjectInputStream stream )
        throws InvalidObjectException
{
    throw new InvalidObjectException( "Proxy required." );
}


/**
 * Provides the serialization and deserialization for a gas well data entry object.
 */
private static class SerializationProxy implements Serializable
{
    int numberWells;
    GasWellDataSet[] dataSetArray;
    
    SerializationProxy( MultipleWellDataMap mwdm )
    {
        this.numberWells = mwdm.dataMap.size();
        this.dataSetArray = new GasWellDataSet[ mwdm.dataMap.size() ];
        int i =0;
        for( GasWellDataSet dataSet : mwdm.dataMap.values() )
        {
            this.dataSetArray[ i++ ] = dataSet;
        }
    }

    /**
     *
     * @return a gas well data entry created from this proxy object...
     */
    private Object readResolve()
    {
        MultipleWellDataMap result = new MultipleWellDataMap();
        for( int i = 0; i < dataSetArray.length; i++ )
        {
            GasWellDataSet dataSet = dataSetArray[ i ];
            result.addDataSet(dataSet);
        }

        return result;
    }


    private static final long serialVersionID = 0x582609dc;
}

}
