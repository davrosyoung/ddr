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

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * Graph a data set.
 *
 * authored by dave young, out of frustration on the lack of available graphing software.
 */
public class p47 {
    private TestCanvas canvas;

    public static void main(String[] args)
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        System.out.println("Headless mode: " + ge.isHeadless());
        System.out.flush();

        BufferedImage image = new BufferedImage( 800, 800, BufferedImage.TYPE_INT_RGB );
        Graphics2D grafix = image.createGraphics();

        TestCanvas canvas = new TestCanvas();
        canvas.setBounds( 0, 0, 600, 600 );
        canvas.paint( grafix );

        Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("PNG");
        ImageWriter imageWriter = iterator.next();

        try {
            if ( ! ImageIO.write(image, "png", new File("dave.png")) )
            {
                System.err.println( "Failed to obtain image writer!!" );
                System.err.flush();
                System.exit(1);
            } else {
                System.out.println( "Yayy!!" );
                System.out.flush();
            }
        } catch (IOException e) {
            System.err.println( "D'oh!! Failed to write image." );
            e.printStackTrace( System.err );
            System.err.flush();
            System.exit(1);
        }

        String[] suffixes = ImageIO.getWriterFileSuffixes();
        System.out.println( "Suffixes:" );
        System.out.println( "---------");
        for( String suffix : suffixes )
        {
            System.out.println( suffix );
            System.out.flush();
        }

        String[] formats = ImageIO.getWriterFormatNames();
        System.out.println( "Formats:" );
        System.out.println( "---------");
        for( String format : formats)
        {
            System.out.println( format );
            System.out.flush();
        }

        System.exit(0);
    }

    

}