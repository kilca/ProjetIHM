package climatechange.models;

import java.util.HashMap;
import java.util.List;

import javafx.util.Pair;

public class ResourceManager {

	public Object sampleNumber;

	
	public HashMap<Integer,HashMap<Coord,Double>> mapFromYear;
	
	public float vitesseAnimation;
	public boolean enPause;
	public Coord zoneSelected;
	public int yearSelected;
	
	public TypeAffichage typeAffiche;
	
	public Pair<Integer,Integer> getMinMaxTemps(){
		
		return null;
	}
	public double getTempsFromZoneYear(Coord c, int year) {
		return (Double) null;
	}
	public List<Double> getTempsFromYear(int year){
		return null;
		
	}
	
	public List<Double> getTempsFromZone(Coord zone){
		
		return null;
	}
	
	public void AddTempAnomaly(double temp, int year, Coord c) {
		
		
	}
	
	public void readTemperatureFile(String path) {
		// TODO Auto-generated method stub
		
	}

}
