/*
 * Copyright (c) 2011-2012 Polly Enterprises Pty Ltd and/or its affiliates.
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

package au.com.polly.ddr.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class RotatedTextDemo extends JPanel
{
JFrame frame;

public RotatedTextDemo()
{
    super();
    
    setPreferredSize(new Dimension( 1200, 600 ) );
    setVisible( true );
}

public void paint( Graphics gfx )
{
    Graphics2D g2d = (Graphics2D)gfx;
    Font originalFont =  new Font("SansSerif", Font.PLAIN, 20 );

    

    for( double theta = 0.0; theta < ( 2 * Math.PI ); theta += ( Math.PI / 2.0 ) )
    {
        AffineTransform rat = new AffineTransform();
        rat.rotate( theta );
        Font rotatedFont = originalFont.deriveFont(rat);

        g2d.setFont( rotatedFont );

        g2d.drawString( "         23/MAR/07 15:25:13", 600, 300 );
        
        g2d.drawLine( 100, 100, 200, 200 );
        
        g2d.drawString( "  gnash!", 200, 300 );

    }
    
    g2d.setFont(originalFont);
    g2d.drawString( "What the !?!", 50, 50 );
    g2d.drawString( "What the !?!", 50, 500 );
    g2d.drawString( "What the !?!", 500, 50 );
    g2d.drawString( "What the !?!", 500, 500 );
}

public static void createAndShowGUI()
{
    JFrame f = new JFrame( "Rotated text" );
    f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    f.setSize( 1400, 800 );
    f.setContentPane( new RotatedTextDemo() );
    f.pack();
    f.setVisible( true );
}

public static void main( String... args )
{
    /* Don't use this!!
    try {
        UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
        System.err.println("Couldn't use the cross-platform "
                + "look and feel: " + e);
    }
    */


    Runnable doCreateAndShowGUI = new Runnable() {
        public void run() {
            createAndShowGUI();
        }
    };

    SwingUtilities.invokeLater( doCreateAndShowGUI );
}

}
