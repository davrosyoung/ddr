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


public class GraphConfig {

protected String title;
protected int width;
protected int height;
protected String sourceDataPath;
protected String imageTypeSuffix;
protected String destinationPath;

/**
*
* @return the title to display for this graph..
*/
public String getTitle() {
    return title;
}

    /**
     *
      * @param title the title to display for this graph..
     */
public void setTitle(String title) {
    this.title = title;
}

    /**
     *
     * @return the desired width of the generated graph, in pixels.
     */
public int getWidth() {
    return width;
}


    /**
     *
     * @param width the desired width of the generated graph, in pixels.
     */
public void setWidth(int width) {
    this.width = width;
}

/**
 *
 * @return the desired height of the generated graph, in pixels.
 */
public int getHeight() {
    return height;
}

/**
 *
 * @param height of the generated graph, in pixels.
 */
public void setHeight(int height) {
    this.height = height;
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
public void setSourceDataPath(String sourceDataPath) {
    this.sourceDataPath = sourceDataPath;
}

    /**
     *
      * @return should be png
     */
public String getImageTypeSuffix() {
    return imageTypeSuffix;
}

    /**
     *
     * @param imageTypeSuffix just set this to png for now.
     */
public void setImageTypeSuffix(String imageTypeSuffix) {
    this.imageTypeSuffix = imageTypeSuffix;
}

    /**
     *
     * @return the file where the graph is to be output.
     */
public String getDestinationPath() {
    return destinationPath;
}

    /**
     *
     * @param destinationPath the file where the graph is to be output.
     */
public void setDestinationPath(String destinationPath) {
    this.destinationPath = destinationPath;
}


}
