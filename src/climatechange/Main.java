package climatechange;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	
    @Override
    public void start(Stage primaryStage) {

    	
    	try {
    	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui2D.fxml"));
            System.out.println(loader);
            Parent content = loader.load();
           
            Controller.INSTANCE = (Controller)loader.getController();

            
            new Thread() {

                // runnable for that thread
                public void run() {
                        CSVParser.getDataFromCSVFile("src\\climatechange\\tempanomaly_4x4grid.csv",Controller.getInstance());
                        Controller.getInstance().FinishLoading();
                }
            }.start();
            
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
    	System.out.println("Working Directory = " + System.getProperty("user.dir"));
    
    	launch(args);
    	System.out.println("test");
    	//CSVParser.getDataFromCSVFile("src\\climatechange\\tempanomaly_4x4grid.csv");	
    	
    	
        
    }
	
}
