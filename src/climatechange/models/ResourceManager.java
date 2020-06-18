package climatechange.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import climatechange.CSVParser;
import climatechange.Controller;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class ResourceManager {

	
    public final int minYear = 1880;
    public final int maxYear = 2021;
	
    public float minTemp;
    public float maxTemp;
    
	public HashMap<Integer,HashMap<Coord,Float>> mapData;
	
	public float vitesseAnimation;
	public boolean isAnimPlaying;
	public Coord zoneSelected;
	public int yearSelected;
	
	public TypeAffichage typeAffiche;
	
	public List<Degrade> degradeBleuList;
	public List<Degrade> degradeRougeList;
    
	public Color nanColor = new Color(0.1f,0.1f,0.1f,0.1f);
	
	private static final int NB_DEGRADE_BLEU = 5;
	private static final int NB_DEGRADE_ROUGE = 5;
	
    public class Degrade{

    	public Color color;
    	public float value;
    	
    	public Degrade(Color c, float f) {
    		this.color = c;
    		this.value =f;
    		
    	}
    	
    }
    
    public void initDegrade() {
    	
    	degradeBleuList = new ArrayList<Degrade>();
    	degradeRougeList = new ArrayList<Degrade>();
    	
    	float rgb;
    	float val;
    	
    	/*
    	Color[] colorBleuList = new Color[5];
    	colorBleuList[0] = Color.ALICEBLUE;
    	colorBleuList[1] = Color.BLUEVIOLET;
    	colorBleuList[2] = Color.CADETBLUE;
    	colorBleuList[3] = Color.DEEPSKYBLUE;
    	colorBleuList[4] = Color.ANTIQUEWHITE;
    	for(int i=colorBleuList.length-1;i>1;i--) {
    		val = ((float)i/(NB_DEGRADE_BLEU+1))*minTemp;
    		degradeBleuList.add(new Degrade(colorBleuList[i],val));
    	}
    	*/
    	
    	for(int i=NB_DEGRADE_BLEU+1;i>1;i--) {
    		val = ((float)i/(NB_DEGRADE_BLEU+1))*minTemp;
    		if (val == 0) {
    			rgb = 0.05f;
    		}else {
    			rgb = (val/minTemp);
    		}
    		//Color c = new Color(0.0f,0.0f,rgb,0.4f);
    		Color c = new Color(0.1f,rgb/2,rgb,0.4f);
    		degradeBleuList.add(new Degrade(c,val));
    		
    	}
    	
    	for(int i=1;i<NB_DEGRADE_ROUGE+1;i++) {

    		val = ((float)i/(NB_DEGRADE_ROUGE+1))*maxTemp;
    		if (val == 0) {
    			rgb = 0.05f;
    		}else {
    			rgb = (val/maxTemp);
    		}
    		
    		Color c = new Color(rgb,0.0f,0.0f,0.4f);
    		degradeRougeList.add(new Degrade(c,val));
    	}
    	
    	
    	
    }
	
	public ResourceManager() {

    	
		yearSelected = 1880;
		vitesseAnimation = 1.0f;
		
		mapData = new HashMap<Integer, HashMap<Coord,Float>>();
		
        for(int i=minYear;i<maxYear;i++) {
        	mapData.put(i,new HashMap<Coord,Float>());
        }
        
    	this.readTemperatureFile("src\\climatechange\\tempanomaly_4x4grid.csv");
		
    	Pair<Float,Float> minMax= this.getMinMaxTemps();
    	
    	this.minTemp = minMax.getKey();
    	this.maxTemp = minMax.getValue();
    	
    	initDegrade();
        
		
	}
	
    /** Instance unique pré-initialisée */
    public static ResourceManager INSTANCE = new ResourceManager();
     
    /** Point d'accès pour l'instance unique du singleton */
    public static ResourceManager getInstance()
    {
    	return INSTANCE;
    }
	
	
	
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
	
	public float getTempsFromZoneYear(Coord c, int year) {
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
