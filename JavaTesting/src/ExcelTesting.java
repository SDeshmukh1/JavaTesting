import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTesting {

	
	public static void main(String args[]) throws InvalidFormatException, IOException {
		
		XSSFWorkbook xwf = new XSSFWorkbook(new File("C:\\Users\\S820935\\"
				+ "Desktop\\HighValue Pathalogy E2E\\Reports\\Archive\\Verification_FHSH.xlsx"));
		XSSFSheet sh = xwf.getSheetAt(1);
		XSSFRow row1 = sh.getRow(1);
		XSSFCell cell1 = row1.getCell(1);
		
		
		XSSFCellStyle style = xwf.createCellStyle();
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		cell1.setCellStyle(style);
		
		//xwf.close();
		
		
		
		FileOutputStream flout = new FileOutputStream(new File("C:\\Users\\S820935\\"
				+ "Desktop\\HighValue Pathalogy E2E\\Reports\\Archive\\Verification_FHSH.xlsx"),true);
		xwf.write(flout);
		flout.close();
		
		
		
	}
}
