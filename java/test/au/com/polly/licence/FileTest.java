package au.com.polly.licence;


import au.com.polly.util.AussieDateParser;
import au.com.polly.util.DateParser;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class FileTest
{
Logger logger = Logger.getLogger( FileTest.class );
DateParser dateParser;

@Before
public void setup()
{
    dateParser = new AussieDateParser();
}

@Test
public void writeOldFile()
{
    File newOldFile = new File( "/users/dave/projects/ddr/data/blah.txt" );
    if ( newOldFile.exists() )
    {
        newOldFile.delete();
    }
    long stamp =  dateParser.parse( "7/JUN/2002 15:40" ).getTime().getTime();
    FileOutputStream fos = null;
    PrintWriter writer = null;
    try
    {
        fos = new FileOutputStream( newOldFile );
        writer = new PrintWriter( fos );
        writer.println( "Oh yeah!!" );
        writer.close();
        fos.close();
    } catch (IOException e)
    {
        logger.error( "Ouch!! " + e.getClass().getName() + " - " + e.getMessage() );
    }

    newOldFile.setLastModified(stamp);
    newOldFile.setReadOnly();
}

@Test
public void embedDataInLogoFile()
{
    BufferedImage img = null;
    try {
        img = ImageIO.read(new File("images/logo.png"));
        assertNotNull( img );
        assertEquals( BufferedImage.TYPE_4BYTE_ABGR, img.getType() );
        assertEquals( 160, img.getHeight() );
        assertEquals( 160, img.getWidth() );

        logger.debug( "logo.type" + img.getType() + " - " + getImageColourType( img.getType() ) + ", img.colourModel=" + img.getColorModel() );



        BufferedImage image2 = new BufferedImage( img.getWidth(), img.getHeight() + 2, img.getType() );
        image2.setData( img.getTile( 0, 0 ) );

        assertNotNull( image2 );
        assertEquals( 160, image2.getWidth() );
        assertEquals( 162, image2.getHeight() );

        assertEquals( 0x000000, image2.getRGB( 0, 160 ) );
        assertEquals( 0x000000, image2.getRGB( 1, 160 ) );
        assertEquals( 0x000000, image2.getRGB( 2, 160 ) );

        assertEquals( 0x000000, image2.getRGB( 0, 161 ) );
        assertEquals( 0x000000, image2.getRGB( 1, 161 ) );
        assertEquals( 0x000000, image2.getRGB( 2, 161 ) );
 /*
        image2.setRGB(  0, 160, 0xFF00FF00 );
        image2.setRGB(  1, 160, 0xFF00FF00 );
        image2.setRGB(  2, 160, 0xFF00FF00 );
        image2.setRGB(  3, 160, 0xFF00FF00 );
        image2.setRGB(  4, 160, 0xFF00FF00 );
        image2.setRGB(  5, 160, 0x00FF00FF );
        image2.setRGB(  6, 160, 0x00FF00FF );
        image2.setRGB(  7, 160, 0x00FF00FF );
        image2.setRGB(  8, 160, 0x00FF00FF );
        image2.setRGB(  9, 160, 0x00FF00FF );
        image2.setRGB( 10, 160, 0x00FF00FF );
        image2.setRGB( 11, 160, 0x00FF0000 );
        image2.setRGB( 12, 160, 0x00FF0000 );
        image2.setRGB( 13, 160, 0x00FF0000 );
        image2.setRGB( 14, 160, 0x00FF0000 );
        image2.setRGB( 15, 160, 0x00FF0000 );

//        Raster raster = image2.getRaster();
//        DataBuffer data = raster.getDataBuffer();
//        logger.debug( "data.size=" + data.getSize() );
//        SampleModel sm = raster.getSampleModel();
//        Raster resultantRaster = Raster.createWritableRaster( sm, data, new Point( 0, 0 ) );
//        image2.

      ImageIO.write( image2, "png", new File( "images/logo2.png") );
      */

    } catch (IOException e) {
    }
}
@Test
public void readMangledLogoFile()
{
    BufferedImage img = null;
    try {
        img = ImageIO.read(new File("images/logo2.png"));
        assertNotNull( img );
        assertEquals( 162, img.getHeight());
        assertEquals( 160, img.getWidth() );
        assertEquals( BufferedImage.TYPE_4BYTE_ABGR, img.getType() );

        logger.debug( "logo.type" + img.getType() + " - " + getImageColourType( img.getType() ) + ", img.colourModel=" + img.getColorModel() );

        assertEquals( 0xFF00FF00, img.getRGB(  0, 160 ) );
        assertEquals( 0xFF00FF00, img.getRGB(  1, 160 ) );
        assertEquals( 0xFF00FF00, img.getRGB(  2, 160 ) );
        assertEquals( 0xFF00FF00, img.getRGB(  3, 160 ) );
        assertEquals( 0xFF00FF00, img.getRGB(  4, 160 ) );
        assertEquals( 0x00FF00FF, img.getRGB(  5, 160 ) );
        assertEquals( 0x00FF00FF, img.getRGB(  6, 160 ) );
        assertEquals( 0x00FF00FF, img.getRGB(  7, 160 ) );
        assertEquals( 0x00FF00FF, img.getRGB(  8, 160 ) );
        assertEquals( 0x00FF00FF, img.getRGB(  9, 160 ) );
        assertEquals( 0x00FF00FF, img.getRGB( 10, 160 ) );
        assertEquals( 0x00FF0000, img.getRGB( 11, 160 ) );
        assertEquals( 0x00FF0000, img.getRGB( 12, 160 ) );
        assertEquals( 0x00FF0000, img.getRGB( 13, 160 ) );
        assertEquals( 0x00FF0000, img.getRGB( 14, 160 ) );
        assertEquals( 0x00FF0000, img.getRGB( 15, 160 ) );


    } catch (IOException e) {
    }
}

private static String getImageColourType( int colourType )
{
    String result = "unknown(" + colourType + ")";
    switch( colourType )
    {
        case BufferedImage.TYPE_3BYTE_BGR:
            result="3BYTE_BGR";
            break;
        case BufferedImage.TYPE_4BYTE_ABGR:
            result = "4BYTE_ABGR";
            break;
        case BufferedImage.TYPE_4BYTE_ABGR_PRE:
            result = "4BYTE_ABGR_PRE";
            break;
        case BufferedImage.TYPE_BYTE_BINARY:
            result="BYTE_BINARY";
            break;
        case BufferedImage.TYPE_BYTE_GRAY:
            result = "BYTE_GRAY";
            break;
        case BufferedImage.TYPE_BYTE_INDEXED:
            result="BYTE_INDEXED";
            break;
        case BufferedImage.TYPE_CUSTOM:
            result="CUSTOM";
            break;
        case BufferedImage.TYPE_INT_ARGB:
            result="INT_ARGB";
            break;
        case BufferedImage.TYPE_INT_ARGB_PRE:
            result="INT_ARGB_PRE";
            break;
        case BufferedImage.TYPE_INT_BGR:
            result="INT_BGR";
            break;
        case BufferedImage.TYPE_INT_RGB:
            result="INT_RGB";
            break;
        case BufferedImage.TYPE_USHORT_555_RGB:
            result="USHORT_555_RGB";
            break;
        case BufferedImage.TYPE_USHORT_565_RGB:
            result="USHORT_565_RGB";
            break;
        case BufferedImage.TYPE_USHORT_GRAY:
            result="USHORT_GREY";
            break;
    }

    return result;
}

}
