package climatechange.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import climatechange.CSVParser;
import climatechange.Controller;
import javafx.util.Pair;

public class ResourceManager {

	
    public final int minYear = 1880;
    public final int maxYear = 2021;
	
	public ResourceManager() {
		
		mapData = new HashMap<Integer, HashMap<Coord,Float>>();
		
        for(int i=minYear;i<maxYear;i++) {
        	mapData.put(i,new HashMap<Coord,Float>());
        }
		
	}
	
    /** Instance unique pré-initialisée */
    public static ResourceManager INSTANCE = new ResourceManager();
     
    /** Point d'accès pour l'instance unique du singleton */
    public static ResourceManager getInstance()
    {
    	return INSTANCE;
    }
	
   
	
	public HashMap<Integer,HashMap<Coord,Float>> mapData;
	
	public float vitesseAnimation;
	public boolean enPause;
	public Coord zoneSelected;
	public int yearSelected;
	
	public TypeAffichage typeAffiche;
	
	public Pair<Float,Float> getMinMaxTemps(){
		
		float minTemp = 999;
		float maxTemp = -999;
		
		
		for(int i=minYear;i<maxYear;i++) {
			
			//pourrait etre optimisé
		    Collection<Float> values = mapData.get(i).values();
		    
		    
			//System.out.println(mapData.get(1900).values().size());
		    
		    Float[] targetArray = values.toArray(new Float[values.size()]);
			for(float d : targetArray) {
				
				if (d > maxTemp) {
					maxTemp = d;
				}
				if (d < minTemp) {
					minTemp = d;
				}
				
			}
		    
		}
		
		return new Pair<Float,Float>(minTemp,maxTemp);
		
	}
	
	public double getTempsFromZoneYear(Coord c, int year) {
		return this.mapData.get(year).get(c);
	}
	
	//pourrait etre plus generique avec keyset
	public List<Float> getTempsFromYear(int year){
		
		List<Float> retour = new ArrayList<Float>();
		
		for(int lat =-88;lat<=88;lat+=4) {
			for(int lon=-178;lon<=178;lon+=4) {
				retour.add((this.mapData.get(year).get(new Coord(lat,lon))));
			}
		}
		
		return retour;
		
		
	}
	
	//marche
	public List<Float> getTempsFromZone(Coord zone){
		
		List<Float> retour = new ArrayList<Float>();
		for(int i=minYear;i<maxYear;i++) {
			retour.add(this.mapData.get(i).get(zone));
		}
		return retour;
		
	}
	
	public void AddTempAnomaly(float temp, int year, Coord c) {
		
		
	}
	
	public void readTemperatureFile(String path) {
    	CSVParser.getDataFromCSVFile(path,this);
		
	}

}
