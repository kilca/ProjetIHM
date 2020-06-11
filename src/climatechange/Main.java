package climatechange;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import climatechange.models.Coord;
import climatechange.models.ResourceManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	
	public static Stage primStage;
	
    @Override
    public void start(Stage primaryStage) {

    	
    	try {
    	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui2D.fxml"));
            
            Parent content = loader.load();
           
            Controller.INSTANCE = (Controller)loader.getController();

            /*
           //barre chargement (avant optimisation du CSVReader)
            new Thread() {

                // runnable for that thread
                public void run() {
                        ResourceManager.getInstance().readTemperatureFile("src\\climatechange\\tempanomaly_4x4grid.csv");
                        Controller.getInstance().FinishLoading();
                }
            }.start();
            */
            primStage = primaryStage;
            
            primaryStage.setTitle("Climate Change Application");
	        primaryStage.setScene(new Scene(content));
	        primaryStage.show();

	           
	        
	        
	        
    	}catch (IOException e ) {
    		e.printStackTrace();
    	}
        
;
    	
    }

    public static void main(String[] args) {
    	
    	//CSVParser.getDataFromCSVFile("tempanomaly_4x4grid.csv");
    
    	//CSVParser.getDataFromCSVFile("src\\climatechange\\tempanomaly_4x4grid.csv");
    	
    	//System.out.println("fin chargement");
    	
    	/*
    	SimpleTest st = new SimpleTest();
    	try {
			st.setUp();
			st.YearNumberTest();
			st.MinTemperatureTest();
			st.MaxTemperatureTest();
			st.TemperatureAnomalyTest();
			st.TemperatureAnomalyZoneTest();
			st.TemperatureAnomalyYearTest();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	*/
    	
    	//launch(args);

    	ResourceManager rm = ResourceManager.getInstance();
    	
    	CSVParser.getDataFromCSVFile("src\\climatechange\\tempanomaly_4x4grid.csv",ResourceManager.getInstance());

        
    }
	
}
