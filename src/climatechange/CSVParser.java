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
import java.util.HashMap;
import java.util.List;

import climatechange.models.Coord;
import climatechange.models.ResourceManager;

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
	
	public static void getDataFromCSVFile(String csvFilePath, ResourceManager rm)
	{
		
		
		Controller c = Controller.getInstance();
		
        String line = "";
        String[] data = null;
        
        //String separator = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        String separator = ",";
        
        //Document data
        int lat = 0;
        int lon = 0;
        
        int nbLine = 0;
        int currLine = 0;
        
        HashMap<Integer, HashMap<Coord,Float>> map = rm.mapData;
        
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
            		
            	try {
            		//Get the ISBN number
            		lat = Integer.parseInt(data[0]);
            		
            		//Get the EAN number
            		lon = Integer.parseInt(data[1]);
            	
            		//Get the title of the document
            		
            	}catch(Exception e) {
            		e.printStackTrace();
            	}	
            	
            	Coord coord = new Coord(lat,lon);
            	

            	
            	for(int i=2;i<data.length;i++) {
            		
            		float temper = Float.NaN;
            		
                	try {
                		if (data[i].equals("0")) {
                			temper = 0;
                		}
                		else if (!data[i].equals("NA")) {
                			temper = Float.parseFloat(data[i]);
                		}
                	}catch (Exception e) {
                		System.err.println("Exception found in CSV Parser1");
                		System.err.println(data[i]);
                	}
            		
                	//System.out.println(temper);
                	
            		try {	
            			
            			map.get(1878+i).put(coord, temper);

            		}catch (Exception e) {
                		System.err.println("Exception found in CSV Parser2");
            			System.err.println(i);
            		}
            	}
            		
            	currLine++;
            	
            }
            
                

        }
        catch (IOException exception) 
        {
            System.err.println(exception);
        }
        
	}
}
