package au.com.polly.ddr;

import au.com.polly.util.ReadInput;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Program to help us run the allocation sheet harness.
 */
public class Extractor extends JFrame
{

public static void createAndShowGUI()
{

}


public static void main( String... args )
{
    String filename = null;
    String sheetName = null;
    FileInputStream fis = null;
    AllocationSheetExplorer explorer;
    BasicConfigurator.configure();
    Logger logger = Logger.getLogger( Extractor.class );

    if ( ( args != null ) && ( args.length > 0 ) )
    {
        filename = args[ 0 ];
        if ( args.length > 1 )
        {
            sheetName = args[ 1 ];
        }
    }

    if ( filename == null )
    {
        filename = ReadInput.readLine( "Excel file?", "test001.xlsx" );
    }

    if ( sheetName == null )
    {
        sheetName = ReadInput.readLine( "Sheet name?", "allocation" );
    }

    try
    {
        logger.debug( "About to open excel spreadsheet file \"" + filename + "\"" );
        fis = new FileInputStream( filename );
    } catch (FileNotFoundException e)
    {
        logger.fatal( "Unable to locate spreadsheet file \"" + filename + "\"" );
        System.exit(1);
    }

    Workbook book = null;
    Sheet sheet = null;
    try
    {
        long a = System.currentTimeMillis();
        logger.debug( "About to create excel workbook from file \"" + filename + "\"" );
        book = WorkbookFactory.create(fis);
        long b = System.currentTimeMillis();
        logger.info( "It took " + ( b - a ) + "ms to load the spreadsheet." );
        sheet = book.getSheet( sheetName );
    } catch (Exception e)
    {
        logger.fatal( "Unable to process excel spreadsheet \"" + filename + "\"" );
        logger.fatal( e );
        System.exit( 1 );
    }

    if ( sheet != null )
    {
        List<GasWellDataLocator> locations;
        explorer = new AllocationSheetExplorer( sheet );
        explorer.process();

        locations = explorer.getLocations();
        if ( ( locations != null ) && ( locations.size() > 0 ) )
        {
            logger.info( "Found " + locations.size() + " data sets for gas/oil wells.");
            Iterator<GasWellDataLocator> iterator = locations.iterator();
            while( iterator.hasNext() )
            {
                GasWellDataLocator locator = iterator.next();
                System.out.println( locator );
            }

            // let's extract the data sets one by one ....
            // --------------------------------------------
            for( GasWellDataLocator location : locations )
            {
                GasWell well = new GasWell( location.getWellName() );
                GasWellDataSet data = explorer.obtainDataSet(well, location);
                String outputFilename = "data" + File.separator + location.getWellName().toLowerCase().replaceAll( "\\s+\\-_\\(\\)", "" ) + ".txt";
                try
                {
                    logger.debug( "About to open file '" + outputFilename + "' for writing data for well \"" + location.getWellName() );
                    FileOutputStream fos = new FileOutputStream( outputFilename );
                    PrintWriter writer = new PrintWriter( fos );
                    writer.print( data );
                    writer.close();
                    fos.close();

                    outputFilename = "data" + File.separator + location.getWellName().toLowerCase() .replaceAll( "\\s+\\-_\\(\\)", "" ) + ".obj";
                    fos = new FileOutputStream( outputFilename );
                    ObjectOutputStream oos = new ObjectOutputStream( fos );
                    oos.writeObject( data );
                    oos.close();
                } catch (IOException e)
                {
                    logger.error( "Failed to write out data file " + outputFilename );
                    logger.error(e);
                }

//                System.out.println( data );
            }
        } else {
            logger.error( "Unable to find any gas well locations!!" );
        }
    } else {
        logger.error( "Unable to locate sheet \"" + sheetName + "\". Available sheets are;" );
        for( int i = 0; i < book.getNumberOfSheets(); i++ )
        {
            logger.error( book.getSheetAt(i).getSheetName() );
        }
        System.err.flush();
        System.exit( 1 );
    }
    System.exit(  0 );
}
}
