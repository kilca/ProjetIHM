package climatechange;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
	/**
	 * recupere les donnee d'un fichier csv et les mets dans les donnees
	 * ne les prend pas en compte si leur identifiant existent deja
	 * Ici on recupere le csv transmit avec des bibliotheques existantes
	 * 
	 * @param csvFilePath		Le repertoire du fichier csv
	 */
	
	
	public static int countLinesOld(String filename) throws IOException {
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
	
	public static void getDataFromCSVFile(String csvFilePath,Controller c)
	{
        String line = "";
        String[] data = null;
        String separator = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        
        //Document data
        String lat;
        String lon;
        List<String> temp = new ArrayList<String>();
        
        int nbLine = 0;
        int currLine = 0;
        
        
        try {
			nbLine = countLinesOld(csvFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(csvFilePath), StandardCharsets.ISO_8859_1)) 
        {
        	//Read the first line
        	line = bufferedReader.readLine();
        	
        	//Get data from the line
        	data = line.split(separator, -1);
        	
        	
        	//Read the file line by line
            while ((line = bufferedReader.readLine()) != null)
            {
            	//Get data from the line
            	data = line.split(separator, -1);
            	
            	//Sort data
            		
            		//Get the ISBN number
            		lat = data[0];
            		
            		//Get the EAN number
            		lon = data[1];
            		
            		//Get the title of the document
            		
            		for(int i=2;i<data.length;i++) {
            			
            			temp.add(data[i]);
            		}
            		
            	currLine++;
            	
            	if (c == null) {
            		Controller.getInstance().SetProgess((double)currLine/nbLine);
            	}else {
            		c.SetProgess((double)currLine/nbLine);  		
            	}
            }
                

        }
        catch (IOException exception) 
        {
            System.err.println(exception);
        }
        
	}
}
