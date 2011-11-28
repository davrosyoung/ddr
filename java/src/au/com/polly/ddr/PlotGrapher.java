package au.com.polly.ddr;

import java.util.Date;

/**
 * The class which plots the mesurement data implements this interface....
 *
 */
public interface PlotGrapher
{

/**
 *
 * @param wmt
 * @return whether or not the specified measurement type is being displayed or not.
 */
public boolean getDisplayMeasurementType( WellMeasurementType wmt );

/**
 *
 * @param wmt
 * @param display whether or not to display the specified measurement type.
 */
public void setDisplayMeasurementType( WellMeasurementType wmt, Boolean display );

/**
 *
 * Specifies the date range to zoom into.
 *
 * @param from
 * @param until
 */
public void setDisplayDateRange( Date from, Date until );

/**
 *
 * @param dataSet the gas well data set to be displayed.
 */
public void loadData( GasWellDataSet dataSet );

/**
 *
 * @param overlayDataSet the gas well data set to display
 * over the top of the original data set.
 */
public void loadOverlayData( GasWellDataSet overlayDataSet );

/**
 *
 * @return the data measurement set being overlayed against the
 * background/original data. may be null if there isn't any!!
 */
public GasWellDataSet getOverlayData();

/**
 *
 * Interrogate the original data set and produce a set of reduced
 * data from it...
 *
 * @param reducer
 */
public void reduce( DataRateReducer reducer );

}
