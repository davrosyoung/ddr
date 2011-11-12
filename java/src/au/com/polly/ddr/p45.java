package au.com.polly.ddr;

import java.io.IOException;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class p45 {

	private String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void read() throws IOException
    {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);

            String sheetNames[] = w.getSheetNames();

            for ( int h = 0; h < sheetNames.length; h++ )
            {
                System.out.println( "About to process sheet name [" + sheetNames[ h ] + "]" );
                // Get the first sheet
/*                Sheet sheet = w.getSheet(h);
                // Loop over first 10 column and lines

                for (int j = 0; j < sheet.getColumns(); j++) {
                    for (int i = 0; i < sheet.getRows(); i++) {
                        Cell cell = sheet.getCell(j, i);
                        CellType type = cell.getType();
                        if (cell.getType() == CellType.LABEL) {
                            System.out.println("I got a label "
                                    + cell.getContents());
                        }

                        if (cell.getType() == CellType.NUMBER) {
                            System.out.println("I got a number "
                                    + cell.getContents());
                        }

                    }
                }     */
            }
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		p45 test = new p45();
		test.setInputFile( "/users/dave/documents/work/nick/JustAllocation.xlsx");
		test.read();
	}

}
