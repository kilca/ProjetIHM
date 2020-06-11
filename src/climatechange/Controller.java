package climatechange;

import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;

public class Controller implements Initializable {

	
    /** Instance unique pré-initialisée */
    public static Controller INSTANCE;
     
    /** Point d'accès pour l'instance unique du singleton */
    public static Controller getInstance()
    {
    	return INSTANCE;
    }
	
	@FXML
	private Pane pane3D;

	@FXML
	private Pane paneGlob;
	
	
	private EarthScene earthScene;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		

		
		
		
		
		
        //Create a Pane et graph scene root for the 3D content

        // ...

        Group root3D = new Group();
		
        // Create scene
        //SubScene subscene = new SubScene(root3D,600,600,true,SceneAntialiasing.BALANCED);
        earthScene = new EarthScene(root3D,600,600,true,SceneAntialiasing.BALANCED);
        earthScene.init();
       
        
        pane3D.getChildren().addAll(earthScene);
        
    	/*
        final long startNanoTime = System.nanoTime();
        new AnimationTimer(){
            public void handle(long currentNanoTime){
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                //For the animation
                greenCube.setRotationAxis(new Point3D(0,1,0));
                greenCube.setRotate(20.0 * t);
            }
        }.start();
        */
		
	}
	
		
		
	
	
	
}

