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

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Experimental scratchpad canvas, for practising placing text and circles
 *
 */
public class TestCanvas extends Canvas
{
final static int DEFAULT_POINT_SIZE = 4;
final static int LEFT_AXIS_BORDER = 40;
final static int RIGHT_AXIS_BORDER = 40;
final static int BOTTOM_AXIS_BORDER = 40;
final static int TITLE_BORDER = 20;

protected boolean showGrid = false;
protected double minY;
protected double minX;
protected double maxY;
protected double maxX;
protected double xInterval;
protected double yInterval;
protected double xScale;
protected double yScale;
protected boolean dirty = true;

enum PointMarkerStyle { CROSSHAIR, SQUARE, FILLED_SQUARE, CIRCLE, FILLED_CIRCLE };
TestCanvas.PointMarkerStyle markerStyle = TestCanvas.PointMarkerStyle.CROSSHAIR;
protected int pointSize = 5;



public void paint(Graphics g)
{
    super.paint(g);

    Graphics2D g2d = (Graphics2D)g;

    int minX;
    int midX;
    int maxX;
    int minY;
    int midY;
    int maxY;


    minX = (int) 0;
    maxX = (int) getSize().getWidth() - 1;
    midX = ( minX + maxX ) / 2;

    minY = (int) 0;
    maxY = (int) getSize().getHeight() - 1;
    midY = ( minY + maxY ) / 2;

    // create a white backdrop
    // ----------------------------
    g2d.setColor( Color.WHITE );
    g2d.fillRect( minX, minY, maxX, maxY );


    g2d.setColor( Color.BLUE );
    g2d.drawLine(  minX, minY, maxX, minY );
    g2d.drawLine(  minX, maxY, maxX, maxY );
    g2d.drawLine(  minX, minY, minX, maxY );
    g2d.drawLine(  maxX, minY, maxX, maxY );

    g2d.drawLine(  minX, midY, maxX, midY );
    g2d.drawLine(  midX, minY, midX, maxY );

    AffineTransform transformation = g2d.getTransform();

    transformation.setToQuadrantRotation( 1, midX , midY );
    g2d.setTransform( transformation );

    g2d.setColor( Color.GREEN );
    g2d.drawString( "hello world", midX, midY );
    transformation.setToIdentity();
    g2d.setTransform( transformation );


    g2d.setColor( Color.YELLOW );
    g2d.translate( midX, midY );
    g2d.rotate( Math.PI / 4 );
    g2d.drawString( "hello world", 0, 0);

/*
    g2d.setColor( Color.ORANGE );
//  g2d.translate( 40, 40 );
    g2d.rotate( Math.PI / 4 );
//  g2d.drawChars( "hello world".toCharArray(), 0, 11, midX, midY );
    g2d.drawString( "hello world", 0, 0 );

    g2d.setColor( Color.RED );
    g2d.translate( 0, 0 );
    g2d.rotate( Math.PI / 4 );
    g2d.drawString( "hello world", 0, 0  );
*/

    transformation.setToIdentity();
    g2d.setTransform( transformation );
    
}


}
