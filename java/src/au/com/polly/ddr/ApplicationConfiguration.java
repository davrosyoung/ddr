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

package au.com.polly.ddr;

import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * User modifiable settings for the ddr application.
 * 
 * 
 */
public class ApplicationConfiguration
{
private final static Logger logger = Logger.getLogger( ApplicationConfiguration.class );
private static Properties defaultProperties = null;

private File defaultExcelDirectory;
private File defaultOriginalCSVDirectory;
private File defaultAveragedCSVDirectory;
private String intervalAddButtonLabel;
private String intervalDeleteButtonLabel;
private String intervalEditorTitle;
private String openBackgroundDataButtonLabel;
private String editOverlayDataButtonLabel;
private String openOverlayDataButtonLabel;
private String saveOverlayDataButtonLabel;
private String openBackgroundDataDialogueTitle;
private String openOverlayDataDialogueTitle;
private String saveOverlayDataDialogueTitle;
private boolean displayIntervalEditorCSVButton;

static {
    // default properties
    // -------------------------------------------------------------------------
    defaultProperties = new Properties();
    defaultProperties.setProperty( "original_excel_directory", "data" );
    defaultProperties.setProperty( "original_csv_directory", "data" );
    defaultProperties.setProperty( "averaged_csv_directory", "data" );
    defaultProperties.setProperty( "interval_add_button_label", "split" );
    defaultProperties.setProperty( "interval_delete_button_label", "merge up" );
    defaultProperties.setProperty( "interval_editor_title", "averaged data interval editor" );
    defaultProperties.setProperty( "open_background_data_button_label", "open original data" );
    defaultProperties.setProperty( "open_background_data_dialogue_title", "open original data" );
    defaultProperties.setProperty( "open_overlay_data_button_label", "open averaged data" );
    defaultProperties.setProperty( "open_overlay_data_dialogue_title", "open averaged data" );
    defaultProperties.setProperty( "edit_overlay_data_button_label", "edit averaged data" );
    defaultProperties.setProperty( "save_overlay_data_button_label", "save averaged data" );
    defaultProperties.setProperty( "save_overlay_data_dialogue_title", "save averaged data" );
    defaultProperties.setProperty( "display_interval_editor_csv_button", "false" );
}

/**
 * Use inner class to ensure thread-safe initialisation of singleton. The singleton will only be created
 * upon request and does not require synchronization locking for each call to getInstance().
 */
private static class InstanceHolder {
    public static ApplicationConfiguration instance = loadFromProperties();
}

/**
 *
 * @return application configuration.
 */
public static ApplicationConfiguration getInstance()
{
    return InstanceHolder.instance;
}


protected static ApplicationConfiguration loadFromProperties()
{
    File propertiesFile = null;
    ApplicationConfiguration result = null;
    Properties properties = null;

    do {
        
        // step 1 ... interrogate ddr.properties command line argument
        // ------------------------------------------------------------
        
        if ( System.getProperty( "ddr.properties" ) != null )
        {
            propertiesFile = new File( System.getProperty( "ddr.properties" ) );
            if ( propertiesFile.exists() && propertiesFile.isFile() && propertiesFile.canRead() )
            {
                logger.debug( "found readable file [" + propertiesFile + "] from system property ddr.properties" );
                break;
            } else {
                logger.error( "system property ddr.properties [" + propertiesFile.getAbsolutePath() + "] does not identify a readable file!!" );
            }
        }
        
        // step 2 ... check for properties/ directory and ddr.properties file..
        // ---------------------------------------------------------------------
        propertiesFile = new File( "properties/ddr.properties" );
        if ( propertiesFile.exists() && propertiesFile.isFile() && propertiesFile.canRead() )
        {
            logger.debug( "found readable file properties/ddr.properties" );
            break;
        } else {
            logger.error( "properties/ddr.properties [" + propertiesFile.getAbsolutePath() + "] does not identify a readable file!!" );
        }

        // step 3 ... check for conf/ directory and ddr.properties file..
        // ---------------------------------------------------------------------
        propertiesFile = new File( "conf/ddr.properties" );
        if ( propertiesFile.exists() && propertiesFile.isFile() && propertiesFile.canRead() )
        {
            logger.debug( "found readable file conf/ddr.properties" );
            break;
        } else {
            logger.error( "conf/ddr.properties [" + propertiesFile.getAbsolutePath() + "] does not identify a readable file!!" );
        }

        
        // step 4 ... check for any ddr.properties file in the current directory.
        // --------------------------------------------------------------------------
        propertiesFile = new File( "ddr.properties" );
        if ( propertiesFile.exists() && propertiesFile.isFile() && propertiesFile.canRead() )
        {
            logger.debug( "found readable file ./ddr.properties" );
            break;
        } else {
            logger.error( "ddr.properties [" + propertiesFile.getAbsolutePath() + "] does not identify a readable file!!" );
        }
        

            
    } while( false );
    
    if ( propertiesFile.exists() && propertiesFile.isFile() && propertiesFile.canRead() )
    {
        properties = new Properties();
        try
        {
            FileReader reader = new FileReader( propertiesFile );
            properties.load( reader );
        } catch (IOException e) {
            logger.error( "Failed to load properties from file \"" + propertiesFile + "\", [" + e.getClass().getName() + ":" + e.getMessage() + "]" );
            properties = new Properties( defaultProperties );
        }
    } else {
        properties = new Properties( defaultProperties );
    }
    
    result = new ApplicationConfiguration( properties );

    return result;
}
        

protected ApplicationConfiguration()
{
    super();    
}

protected ApplicationConfiguration( Properties properties )
{
    this();
    
    File file;
    String tag;

    tag = "original_excel_directory";
    
    if ( properties.containsKey( tag ) )
    {
        file = new File( properties.getProperty( tag ) );
        if ( file.exists() && file.isDirectory() )
        {
            setDefaultExcelDirectory( file );
        } else {
            logger.error( "property [" + tag + "]=\"" + file.getAbsolutePath() + "\" does not refer to a readable directory!!" );
        }
    }

    tag = "original_csv_directory";
    if ( properties.containsKey( tag ) )
    {
        file = new File( properties.getProperty( tag ) );
        if ( file.exists() && file.isDirectory() )
        {
            setDefaultOriginalCSVDirectory( file );
        } else {
            logger.error( "property [" + tag + "]=\"" + file.getAbsolutePath() + "\" does not refer to a readable directory!!" );
        }
    }

    tag = "averaged_csv_directory";
    if ( properties.containsKey( tag ) )
    {
        file = new File( properties.getProperty( tag ) );
        if ( file.exists() && file.isDirectory() )
        {
            setDefaultAveragedCSVDirectory(file);
        } else {
            logger.error( "property [" + tag + "]=\"" + file.getAbsolutePath() + "\" does not refer to a readable directory!!" );
        }
    }

    tag = "interval_add_button_label";
    if ( properties.containsKey( tag ) )
    {
        setIntervalAddButtonLabel( properties.getProperty( tag, defaultProperties.getProperty( tag )));
    }

    tag = "interval_delete_button_label";
    if ( properties.containsKey( tag ) )
    {
        setIntervalDeleteButtonLabel( properties.getProperty( tag, defaultProperties.getProperty( tag ) )  );
    }
    
    tag = "open_background_data_button_label";
    if ( properties.containsKey( tag ) )
    {
        setOpenBackgroundDataButtonLabel( properties.getProperty( tag, defaultProperties.getProperty( tag ) ) );
    }
    
    tag = "open_background_dialogue_title_label";
    if ( properties.containsKey( tag ) )
    {
        setOpenBackgroundDataDialogueTitle( properties.getProperty( tag, defaultProperties.getProperty( tag ) ) );
    }
    
    tag = "open_overlay_data_button_label";
    if ( properties.containsKey( tag ) )
    {
        setOpenOverlayDataButtonLabel( properties.getProperty( tag, defaultProperties.getProperty( tag ) ) );
    }
    
    tag = "open_overlay_dialogue_title_label";
    if ( properties.containsKey( tag ) )
    {
        setOpenOverlayDataDialogueTitle( properties.getProperty( tag, defaultProperties.getProperty( tag ) ) );
    }
    
    tag = "save_overlay_data_button_label";
    if ( properties.containsKey( tag ) )
    {
        setSaveOverlayDataButtonLabel( properties.getProperty( tag, defaultProperties.getProperty( tag ) ) );
    }
    
    tag = "save_overlay_data_dialogue_title";
    if ( properties.containsKey( tag ) )
    {
        setSaveOverlayDataDialogueTitle( properties.getProperty( tag, defaultProperties.getProperty( tag ) ) );
    }
    
    tag = "edit_overlay_data_button_label";
    if ( properties.containsKey( tag ) )
    {
        setEditOverlayDataButtonLabel( properties.getProperty( tag, defaultProperties.getProperty( tag ) ) );
    }

    tag = "interval_editor_title";
    if ( properties.containsKey( tag ) )
    {
        setIntervalEditorTitle(properties.getProperty(tag, defaultProperties.getProperty(tag)));
    }

    tag = "display_interval_editor_csv_button";
    if ( properties.containsKey( tag ) )
    {
        String text = properties.getProperty(tag, defaultProperties.getProperty(tag));
        boolean flag = ( text.trim().toLowerCase().startsWith( "yes" ) ) || ( text.trim().toLowerCase().startsWith( "true" ) );
        setDisplayIntervalEditorCSVButton( flag );
    }


}


public File getDefaultExcelDirectory()
{
    return defaultExcelDirectory;
}

protected void setDefaultExcelDirectory(File defaultExcelDirectory)
{
    this.defaultExcelDirectory = defaultExcelDirectory;
}

public File getDefaultOriginalCSVDirectory()
{
    return defaultOriginalCSVDirectory;
}

protected void setDefaultOriginalCSVDirectory(File defaultOriginalCSVDirectory)
{
    this.defaultOriginalCSVDirectory = defaultOriginalCSVDirectory;
}

public File getDefaultAveragedCSVDirectory()
{
    return defaultAveragedCSVDirectory;
}

protected void setDefaultAveragedCSVDirectory(File directory )
{
    this.defaultAveragedCSVDirectory = directory ;
}

public String getIntervalAddButtonLabel()
{
    return intervalAddButtonLabel;
}

protected void setIntervalAddButtonLabel( String label )
{
    this.intervalAddButtonLabel = label;
}

public String getIntervalDeleteButtonLabel()
{
    return intervalDeleteButtonLabel;
}

protected void setIntervalDeleteButtonLabel( String label )
{
    this.intervalDeleteButtonLabel = label ;
}

public String getIntervalEditorTitle()
{
    return intervalEditorTitle;
}

protected void setIntervalEditorTitle(String intervalEditorTitle)
{
    this.intervalEditorTitle = intervalEditorTitle;
}

public String getOpenBackgroundDataButtonLabel()
{
    return openBackgroundDataButtonLabel;
}

protected void setOpenBackgroundDataButtonLabel(String openBackgroundDataButtonLabel)
{
    this.openBackgroundDataButtonLabel = openBackgroundDataButtonLabel;
}

public String getEditOverlayDataButtonLabel()
{
    return editOverlayDataButtonLabel;
}

protected void setEditOverlayDataButtonLabel(String editOverlayDataButtonLabel)
{
    this.editOverlayDataButtonLabel = editOverlayDataButtonLabel;
}

public String getOpenOverlayDataButtonLabel()
{
    return openOverlayDataButtonLabel;
}

protected void setOpenOverlayDataButtonLabel(String openOverlayDataButtonLabel)
{
    this.openOverlayDataButtonLabel = openOverlayDataButtonLabel;
}

public String getSaveOverlayDataButtonLabel()
{
    return saveOverlayDataButtonLabel;
}

protected void setSaveOverlayDataButtonLabel(String saveOverlayDataButtonLabel)
{
    this.saveOverlayDataButtonLabel = saveOverlayDataButtonLabel;
}

public String getOpenBackgroundDataDialogueTitle()
{
    return openBackgroundDataDialogueTitle;
}

protected void setOpenBackgroundDataDialogueTitle(String openBackgroundDataDialogueTitle)
{
    this.openBackgroundDataDialogueTitle = openBackgroundDataDialogueTitle;
}

public String getOpenOverlayDataDialogueTitle()
{
    return openOverlayDataDialogueTitle;
}

protected void setOpenOverlayDataDialogueTitle(String openOverlayDataDialogueTitle)
{
    this.openOverlayDataDialogueTitle = openOverlayDataDialogueTitle;
}

public String getSaveOverlayDataDialogueTitle()
{
    return saveOverlayDataDialogueTitle;
}

protected void setSaveOverlayDataDialogueTitle(String saveOverlayDataDialogueTitle)
{
    this.saveOverlayDataDialogueTitle = saveOverlayDataDialogueTitle;
}

public boolean isDisplayIntervalEditorCSVButton()
{
    return displayIntervalEditorCSVButton;
}

protected void setDisplayIntervalEditorCSVButton(boolean displayIntervalEditorCSVButton)
{
    this.displayIntervalEditorCSVButton = displayIntervalEditorCSVButton;
}
}
