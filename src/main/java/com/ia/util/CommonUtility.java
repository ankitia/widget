package com.ia.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.ia.Dao.ExportDao;
import com.ia.modal.MasterData;

public class CommonUtility {

	
	public static int count(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	    byte[] c = new byte[1024];
	    int count = 0;
	    int readChars = 0;
	    boolean empty = true;
	    while ((readChars = is.read(c)) != -1) {
	        empty = false;
	        for (int i = 0; i < readChars; ++i) {
	            if (c[i] == '\n') {
	                ++count;
	            }
	        }
	    }
	    return (count == 0 && !empty) ? 1 : count;
	    } finally {
	    is.close();
	   }
	}
	
	public static void exportMasterData(String tableName,String tableId,String csvFileName,HttpServletResponse response,ExportDao exportDao) throws IOException {
		
		csvFileName = csvFileName+".csv";
		String headerKey = "Content-Disposition";
	    String headerValue = String.format("attachment; filename=\"%s\"",csvFileName);
	    response.setHeader(headerKey, headerValue);
	    
	    // uses the Super CSV API to generate CSV data from the model data
	    ICsvBeanWriter csvWriter;
   	   try {
  			csvWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
  			String[] header = { "urlId","url","userId","status"};
  	        csvWriter.writeHeader(header);
  	        List<MasterData> tempUrls = exportDao.exportMasterData(tableName, tableId);
  	        for (MasterData aBook : tempUrls) {
  	            csvWriter.write(aBook, header);
  	        }
  	     csvWriter.close();
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
	}
	
}
