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

import java.io.IOException;

import java.io.File;

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
