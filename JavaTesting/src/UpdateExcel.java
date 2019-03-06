import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class UpdateExcel {

	static String excelFilePath;
	public static void main(String[] args) throws IOException {
		
		
		
		excelFilePath = "C:\\Users\\S820935\\Desktop\\Macro Update\\FinderResult.xlsx";
		
		       
                
        Summary_Excel(excelFilePath);
		

	}
	
	public static void Summary_Excel(String excelFilePath) throws IOException {
		
		
		Workbook SummaryWB = null;
		FileInputStream fis = new FileInputStream(excelFilePath);
		SummaryWB = new XSSFWorkbook(fis);
        
		
		Multimap<String, String> multimap_values = LinkedListMultimap.create();
		 
		 
			
		for (int i=0; i<SummaryWB.getNumberOfSheets(); i++) {
		    
			int rowcount = 0;
			int colcount =0;
			int flag = 0;
			int coltraverse;
			int CountAvailable = 0;
			int CountFail =0;
						
			rowcount = SummaryWB.getSheetAt(i).getPhysicalNumberOfRows();
			
			colcount = SummaryWB.getSheetAt(i).getRow(0).getPhysicalNumberOfCells();
			
			for(coltraverse=1; coltraverse<=colcount;coltraverse++) {
				
				if(SummaryWB.getSheetAt(i).getRow(0).getCell(coltraverse).toString().equalsIgnoreCase("Result")) {
					
					flag = 1;
					break;
					
				}
				
			}
			
			if(flag==1) {
				
				for(int j=1;j<rowcount;j++) {
					
					
					if(SummaryWB.getSheetAt(i).getRow(j).getCell(coltraverse).
							toString().equalsIgnoreCase("Available")) {
						
						CountAvailable++;
					}
					
					
					if(SummaryWB.getSheetAt(i).getRow(j).getCell(coltraverse).
							toString().equalsIgnoreCase("Fail")) {
						//System.out.println("Test");
						
						CountFail++;
					}
					
				}
				
			}
			
			
			
			
			multimap_values.put(SummaryWB.getSheetName(i), Integer.toString((rowcount-1)));
			
			multimap_values.put(SummaryWB.getSheetName(i), Integer.toString(CountAvailable));
			
			multimap_values.put(SummaryWB.getSheetName(i), Integer.toString(CountFail));
			
		}
		
		ExcelSheetCreation(SummaryWB,multimap_values);
		
				
	}
	
	
	static void ExcelSheetCreation(Workbook SummaryWB,Multimap<String, String> multimap_values) throws IOException {
		
		Sheet sheet = SummaryWB.createSheet("Summary");
		
		SummaryWB.setSheetOrder("Summary", 0);
		
		
		
		
		 Row headerRow = sheet.createRow(0);
		 
		 Cell cell1 = headerRow.createCell(0);
		 cell1.setCellValue("Mapping Document");
		 
		 
		 Cell cell2 = headerRow.createCell(1);		 
		 cell2.setCellValue("Total Transformations");
		 
		 Cell cell3 = headerRow.createCell(2);		 
		 cell3.setCellValue("Transformation covered");
		 
		 Cell cell4 = headerRow.createCell(3);		 
		 cell4.setCellValue("Failed");
		 
		 
		 
		 
		 int head = 0;
		 
		 for (String name: multimap_values.keySet()){

	            String key =name.toString();
	            
	            Row VarValues = sheet.createRow(head+1);
				 
				Cell cellval1 = VarValues.createCell(0);
				
				cellval1.setCellValue(key);
				
				CreationHelper createHelper = SummaryWB.getCreationHelper();
				
				Hyperlink link2 = createHelper.createHyperlink(null);
				
				
	            
	            String value = multimap_values.get(name).toString(); 
	            
	            String[] newvalues = value.split(",");
	          
	           	            
	            
	            Cell cellval2 = VarValues.createCell(1);
	            cellval2.setCellValue(newvalues[0].replace("[", ""));
	            
	            Cell cellval3 = VarValues.createCell(2);
	            cellval3.setCellValue(newvalues[1]);
	            
	            Cell cellval4 = VarValues.createCell(3);
	            cellval4.setCellValue(newvalues[2].replace("]", ""));
	            
	            //System.out.println(key);
	            System.out.println();  
	           // System.out.println(key);
	            
	            head++;
	          } 
		 
		 
		
		FileOutputStream fileOut = new FileOutputStream(excelFilePath);
		SummaryWB.write(fileOut);
	    fileOut.close();
	    System.out.println("File is written successfully");   
		
	}

}
