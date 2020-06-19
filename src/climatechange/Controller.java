package climatechange;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import climatechange.models.Coord;
import climatechange.models.ResourceManager;
import climatechange.models.ResourceManager.Degrade;
import climatechange.models.TypeAffichage;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

public class Controller implements Initializable {

	
    /** Instance unique pré-initialisée */
    public static Controller INSTANCE;
     
    /** Point d'accès pour l'instance unique du singleton */
    public static Controller getInstance()
    {
    	return INSTANCE;
    }
    
    private ResourceManager modelInstance;
    
	@FXML
	private Pane pane3D;

	@FXML
	private Pane paneGlob;
	
	@FXML
	private Button btnQuadri;
	
	@FXML
	private Button btnHisto;
	
	@FXML
	private Button btnChart;
	
	@FXML
	private Button btnPlay;
	
	@FXML
	private Button btnStop;
	
	@FXML
	private Label lblYear;
	
	@FXML
	private TextField inputSpeed;
	
	@FXML
	private Slider sliderTime;
	
	@FXML
	private VBox vbDegrade;
	
	@FXML
	private Label lblLat;
	@FXML
	private Label lblLon;
	@FXML
	private Label lblTemp;
	
	@FXML
	private Group groupLineChart;
	
	private EarthScene earthScene;
	
	private AnimationTimer anim;
	
	private ClimateLineChart lineChart;
	
	public void setSelectedCoord(Coord c) {
		modelInstance.zoneSelected = c;
		
		lblLat.setText("Lat :"+c.latitude);
		lblLon.setText("Lon :"+c.longitude);
		
		float temp = modelInstance.getTempsFromZoneYear(c, modelInstance.yearSelected);
		String val;
		if (Float.isNaN(temp)) {
			val = "temp : unknow";
			
		}else {
			val = ""+modelInstance.getTempsFromZoneYear(c, modelInstance.yearSelected);
			try {
				//ca arrive
				val = "temp :"+val.substring(0, 5);
			}catch(Exception e) {
				val = "temp :"+val;
			}
		}
		if (lineChart != null) {
			
			List<Float> temperatures = modelInstance.getTempsFromZone(modelInstance.zoneSelected);
			lineChart.setTemperatures(temperatures);	
		}
		
		lblTemp.setText(val);
	}
	
	public void ajouterDegrade(Color c, float f) {
		
		
		String truc = ""+f;
		if (f < 0) {
			truc = truc.substring(0, 4);	
		}else {
			truc = " "+truc.substring(0,3);
		}
		
		
		Label l = new Label(truc);
		l.setTextFill(Color.WHITE);
		l.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
		
		vbDegrade.getChildren().addAll(l);
		
		
	}
	
	private void SelectButton(Button b) {
		
		 btnHisto.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		 btnChart.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		 btnQuadri.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		 
		 b.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
		
	}
	
	private void InitHandlers() {
		
		
        lblYear.setText(""+modelInstance.yearSelected);
	     
        inputSpeed.textProperty().setValue(""+modelInstance.vitesseAnimation);
        
        sliderTime.setValue(modelInstance.yearSelected);
		
		
        
        inputSpeed.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
          	  String val = inputSpeed.getText();
          	  val+=e.getText();
          	 
          	  float speedVal = ResourceManager.getInstance().vitesseAnimation;
          	  
          	  try {
          		  
          		speedVal = Float.parseFloat(val);
          		  
          	  }catch(Exception ex) {
          		  
          	  }
          	  
          	ResourceManager.getInstance().vitesseAnimation = speedVal;
            }
        });
        
        
		btnQuadri.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                earthScene.afficherQuadri();
                SelectButton(btnQuadri);
                
                earthScene.afficherQuadri();
            }
        });
		btnHisto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                earthScene.afficherHisto();
                SelectButton(btnHisto);
                
                earthScene.afficherHisto();
            }
        });
		
		
		btnChart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

				if (modelInstance.zoneSelected != null) {
		            Pane root;
		            root = new Pane();
		            Stage stage = new Stage();
					stage.setTitle("Evolution Temperature");
					stage.setScene(new Scene(root, 400, 500));
					stage.show();
						
					//todo label "select a zone to print values"
						
					List<Float> temperatures = modelInstance.getTempsFromZone(modelInstance.zoneSelected);
					ClimateLineChart lChart = new ClimateLineChart(temperatures,false);
						
					root.getChildren().addAll(lChart);
				
            	}
				// Hide this current window (if this is what you want)
				//((Node)(event.getSource())).getScene().getWindow().hide();
            	
            	
            }
        });
		
	     sliderTime.valueProperty().addListener(new ChangeListener<Number>() {

               @Override
               public void changed(
                  ObservableValue<? extends Number> observableValue, 
                  Number oldValue, 
                  Number newValue) { 
            	   	
                     modelInstance.yearSelected = newValue.intValue();
                     lblYear.setText(""+newValue.intValue());
                     
                     if (modelInstance.typeAffiche == TypeAffichage.Quadri)
                    	 earthScene.setQuadri();
                     else if (modelInstance.typeAffiche == TypeAffichage.Histo)
                    	 earthScene.setHisto();
                 }
           }
       );
	     
	     
		btnPlay.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	
	            	if (!modelInstance.isAnimPlaying) {
	            		
	            		modelInstance.isAnimPlaying = true;
	            		
	                	long startNanoTime = System.nanoTime();
	                	
	                	//probleme AnimationTimer : update non fixés
	                	anim = new AnimationTimer(){
	                    	
		                	int secondElapsed = 0;
	                        public void handle(long currentNanoTime){
	                        	
	                        	double t = Math.abs(modelInstance.vitesseAnimation) * (currentNanoTime - startNanoTime) / 1000000000.0;

	                        	if (t > secondElapsed+1) {
	                        		secondElapsed+=1.0;
	                        		
	                        		if (modelInstance.vitesseAnimation > 0) {
	                        			if (modelInstance.yearSelected+1 > modelInstance.maxYear) {
	                        				
			                        		modelInstance.yearSelected=(int)modelInstance.minYear;
	                        			}else {
		                        			modelInstance.yearSelected+=1;	
	                        				
	                        			}
		                        		sliderTime.setValue(modelInstance.yearSelected);
	                        			
	                        		}else {
	                        			if (modelInstance.yearSelected-1 < modelInstance.minYear) {
	                        				
			                        		modelInstance.yearSelected=(int)modelInstance.maxYear;
	                        			}else {
		                        			modelInstance.yearSelected-=1;	
	                        				
	                        			}
		                        		sliderTime.setValue(modelInstance.yearSelected);
	                        		}

	                        	}
	                        }
	                    };
	                   anim.start();
	                   
	                   inputSpeed.setText(ResourceManager.getInstance().vitesseAnimation+"");
	                   btnPlay.setText("Pause");
	                    
	            	}else {
	            		modelInstance.isAnimPlaying = false;
	            		anim.stop();
		                btnPlay.setText("Play");
	            	}
	            	
	            	
	            }
	        });
	     
		btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            		
            		modelInstance.isAnimPlaying = false;
            		if (anim != null)
            			anim.stop();
           			modelInstance.yearSelected = modelInstance.minYear;
           			sliderTime.setValue(modelInstance.yearSelected);
	                   btnPlay.setText("Play");
            }
        });
	     
		
	}
	
	public void modifGraph() {
		
		List<Float> temperatures = modelInstance.getTempsFromZone(modelInstance.zoneSelected);
		
		lineChart = new ClimateLineChart(temperatures,true);
		lineChart.setPrefWidth(150);
		lineChart.setPrefHeight(100);
		groupLineChart.getChildren().remove(0);
		groupLineChart.getChildren().addAll(lineChart);
		
	}

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		
		
		modelInstance = ResourceManager.getInstance();
		System.out.println(modelInstance);
		
        Group root3D = new Group();
		
        // Create scene
        //SubScene subscene = new SubScene(root3D,600,600,true,SceneAntialiasing.BALANCED);
        earthScene = new EarthScene(root3D,600,600,true,SceneAntialiasing.BALANCED);
        earthScene.init();
        
        pane3D.getChildren().addAll(earthScene);
        
        this.InitHandlers();
        
        earthScene.afficherQuadri();
        this.SelectButton(btnQuadri);
        
        //earthScene.setQuadri();
        
        for(Degrade d : modelInstance.degradeBleuList) {
        	
        	this.ajouterDegrade(d.color,d.value);
        }
        for(Degrade d : modelInstance.degradeRougeList) {
        	
        	this.ajouterDegrade(d.color,d.value);
        }
		
	}
	
		
		
	
	
	
}

