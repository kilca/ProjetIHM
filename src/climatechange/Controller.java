package climatechange;

import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Group;
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
	
	@FXML
	private Pane paneLoad;
	
	@FXML
	private ProgressBar progLoad;
	
	public void FinishLoading() {
		
		paneGlob.setVisible(true);
		paneGlob.setDisable(false);
		
		paneLoad.setVisible(false);
		paneLoad.setDisable(true);
		
	}
	
	public void SetProgess(double d) {
		
		if (progLoad != null) {
			progLoad.setProgress(d);
		}else {
			System.err.println("error progress bar is null");
			
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
    	//-----
		
        //Create a Pane et graph scene root for the 3D content
        Group root3D = new Group();

        //Create cube shape
        Box greenCube = new Box(1, 1, 1);
        
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.GREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
        //Set it to the cube

        greenCube.setMaterial(greenMaterial);

        //Add the cube to this node

        root3D.getChildren().add(greenCube);

        //Add a camera group
        PerspectiveCamera camera = new PerspectiveCamera(true);
        
        CameraManager camManager = new CameraManager(camera,pane3D,root3D);
        
        // ...

        // Add point light
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(-180);
        light.setTranslateY(-90);
        light.setTranslateZ(-120);
        light.getScope().addAll(root3D);
        root3D.getChildren().add(light);
        // ...

        // Create scene
        SubScene subscene = new SubScene(root3D,600,600,true,SceneAntialiasing.BALANCED);
        subscene.setCamera(camera);
        subscene.setFill(Color.GREY);
        pane3D.getChildren().addAll(subscene);
        // ...

        //Add the scene to the stage and show it
        /*
        primaryStage.setTitle("Cubes Test");
        primaryStage.setScene(scene);
        primaryStage.show();
        */
        // ...
        
    	
        final long startNanoTime = System.nanoTime();
        new AnimationTimer(){
            public void handle(long currentNanoTime){
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                //For the animation
                greenCube.setRotationAxis(new Point3D(0,1,0));
                greenCube.setRotate(20.0 * t);
            }
        }.start();
        
		
	}
	
		
		
	
	
	
}

