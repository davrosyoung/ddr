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

public GasWellDataSet getData();

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
