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
import au.com.polly.util.HashCodeUtil;
import org.apache.log4j.Logger;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
private final static Logger logger = Logger.getLogger( MultipleWellDataMap.class );
transient Map<GasWell,GasWellDataSet> dataMap;

public MultipleWellDataMap()
{
    this.dataMap = new HashMap<GasWell,GasWellDataSet>();
}

public Map<GasWell,GasWellDataSet> getDataMap()
{
    return dataMap;
}

public void addDataSet( GasWellDataSet dataSet )
{
    if ( dataSet == null )
    {
        throw new NullPointerException( "NULL dataSet specified!! Unable to add it to this multiple well data set!!");
    }
    this.dataMap.put( dataSet.getWell(), dataSet );
}

/**
 *
 * @return the wells as a list.  will be in alphabetical order.
 */
public List<GasWell> getWellList()
{
    List<GasWell> result = new ArrayList<GasWell>();
    Iterator<GasWell> i = dataMap.keySet().iterator();
    while( i.hasNext() )
    {
        GasWell well = i.next();
        result.add( well );
    }
    Collections.sort( result );
    return result;
}

/**
 * 
 * @return the gas well data sets as  a list.... returned alphabetically by gas well name
 */
public List<GasWellDataSet> getDataSetList()
{
    List<GasWellDataSet> result = new ArrayList<GasWellDataSet>();
    List<GasWell> wellList = getWellList();
    
    for( GasWell well : wellList )
    {
        result.add( dataMap.get( well ) );
    }

    return result;
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
public void outputCSV( PrintWriter writer )
{
    boolean firstWell = true;
    
    for( GasWell well : dataMap.keySet() )
    {
        GasWellDataSet dataSet = dataMap.get( well );
        dataSet.outputCSV( writer, firstWell, true );
        firstWell = false;    
    }
}


@Override
public boolean equals( Object other )
{
    boolean result = true;
    MultipleWellDataMap otherMwdm;

    do {
        if ( ! ( other instanceof MultipleWellDataMap ) )
        {
            result = false;
            break;
        }

        otherMwdm = (MultipleWellDataMap)other;
        
        if ( dataMap.size() != otherMwdm.dataMap.size() )
        {
            result = false;
            break;
        }
        
        List<GasWell> wellList = getWellList();
        List<GasWell> otherWellList = otherMwdm.getWellList();
        
        if ( ( ( wellList == null ) && ( otherWellList != null ) )
            || ( ( wellList != null ) && ( otherWellList == null ) )
            )
        {
            result = false;
            break;
        }
        
        if ( ( wellList != null ) && ( otherWellList != null ) )
        {
            if ( wellList.size() != otherWellList.size() )
            {
                result = false;
                break;
            }
            
            for( int i = 0; ( i < wellList.size() ) && result; i++ )
            {
                result = ( wellList.get( i ).equals( otherWellList.get( i ) ) );
            }
        }
        
        if ( result == false ) { break; }
        
        List<GasWellDataSet> dataSetList = getDataSetList();
        List<GasWellDataSet> otherDataSetList = otherMwdm.getDataSetList();

        if ( ( ( dataSetList == null ) && ( otherDataSetList != null ) )
                || ( ( dataSetList != null ) && ( otherDataSetList == null ) )
                )
        {
            result = false;
            break;
        }

        if ( ( dataSetList != null ) && ( otherDataSetList != null ) )
        {
            if ( dataSetList.size() != otherDataSetList.size() )
            {
                result = false;
                break;
            }

            for( int i = 0; ( i < dataSetList.size() ) && result ; i++ )
            {
                result = ( dataSetList.get( i ).equals( otherDataSetList.get( i ) ) );
            }
        }

    } while( false );

    logger.debug( "method returns with result=" + result );

    return result;
}


@Override
public int hashCode()
{
    int result = HashCodeUtil.SEED;
    if ( getDataSetList() != null )
    {
        for( GasWellDataSet dataSet : getDataSetList() )
        {
            result = HashCodeUtil.hash( result, dataSet );
        }
    }
    return result;
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
