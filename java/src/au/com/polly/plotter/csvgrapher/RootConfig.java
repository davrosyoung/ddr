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

package au.com.polly.plotter.csvgrapher;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RootConfig
{

protected String sourceDataPath;
protected List<GraphConfig> graphConfig;

public RootConfig()
{
    this.graphConfig = new ArrayList<GraphConfig>();
}


/**
 *
 * @param props populate the configuration based upon the set of
 * property associations given.
 */
public void populate( Properties props )
{
    if ( props == null )
    {
        throw new NullPointerException( "Properties must not be null!!");
    }
    //todo: write the implementation!!
    return;
}
    
/**
*
* @return the path of the file in which the source csv data is to be found.
*/
public String getSourceDataPath() {
    return sourceDataPath;
}

/**
 *
 * @param sourceDataPath  the path of the file in which the source csv data is to be found.
 */
public void setSourceDataPath(String sourceDataPath)
{
    this.sourceDataPath = sourceDataPath;
}

/**
 *
 * @param config graph configuration to be added to this root configuration.
 */
protected void addGraphConfig( GraphConfig config )
{
    graphConfig.add( config );
}


/**
*
* @return list of configurations for graphs.
*/
public List<GraphConfig> getGraphConfig()
{
    return graphConfig;
}

}