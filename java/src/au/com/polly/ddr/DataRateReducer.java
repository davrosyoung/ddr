package au.com.polly.ddr;

/**
 * Produces a copy of the original data, but with fewer intervals...
 *
 */
public interface DataRateReducer
{
public GasWellDataSet reduce( GasWellDataSet original, int maxIntervals );
}
