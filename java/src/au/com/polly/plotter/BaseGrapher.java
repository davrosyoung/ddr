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

package au.com.polly.plotter;

import java.awt.image.BufferedImage;

/**
 * Implments that small amount of commomn functionality
 * shared by the TimeBasedGrapher and XYScatterGrapher
 * classes.
 *
 * This replaces a bunch of procedural methods which previously
 * haunted the IntervalDataFormatter.
 *
 */
public abstract class BaseGrapher<T extends Number,U extends Number>
implements Grapher<T,U> {
    String title;
    int width;
    int height;

    public BufferedImage render()
       {
           BufferedImage result;

           // ping response time vs dispatcher linger time...
           // ... XY scatter plot with regression data.
           // -----------------------------------------------------------------------
           result = new BufferedImage( getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB );
           PlotCanvas canvas = new PlotCanvas();
           canvas.setBounds( 0, 0, result.getWidth(), result.getHeight() );
           canvas.setTitle( title );

           doRender( canvas );

           canvas.paint( result.getGraphics() );

           return result;
       }

    /**
     * @return A buffered image, containing the rendered graph.
     */
    public abstract void doRender( PlotCanvas canvas );

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
