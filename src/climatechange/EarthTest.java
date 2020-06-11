package climatechange;

import java.net.URL;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class EarthTest extends Application {

    private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;

    @Override
    public void start(Stage primaryStage) {

    	
    	ObjModelImporter objImporter = new ObjModelImporter();
    	try {
    		URL modelUrl = this.getClass().getResource("Earth/earth.obj");
    		objImporter.read(modelUrl);
    	}catch (ImportException e ) {
    		//handle exception
    		System.out.println(e.getMessage());
    	}
    	MeshView[] meshViews = objImporter.getImport();
    	
    	Group earth = new Group(meshViews);
    	
        //Create a Pane et graph scene root for the 3D content
        Group root3D = new Group();
        Pane pane3D = new Pane(root3D);

        //moi : ajout du groupe earth au graphe de la scene 3D
        root3D.getChildren().addAll(earth);
        
        Group cities = new Group();
        
        final float latMarseille = 43.447f;
        final float longMarseille = 5.213f;
        displayTown(cities,"Marseille",latMarseille,longMarseille);
        
        final float latNY = 40.63f;
        final float longNY = -73.77f;
        displayTown(cities,"New York",latNY,longNY);

        final float latIstanbul = 40.97f;
        final float longIstanbul = 28.81f;
        displayTown(cities,"Istanbul",latIstanbul,longIstanbul);
        
        final float latSeoul = 37.46f;
        final float longSeoul = 126.45f;
        //displayTown(cities,"Seoul",latSeoul,longSeoul);
        
        root3D.getChildren().addAll(cities);
        
        
        Group quadriGroup = new Group();
        
        Color c1 = new Color(0.1,0.0,0.0,0.1);
        Color c2 = new Color(0.0,0.1,0.0,0.1);
        
        int pas = 4;
        
        for(int i=-180;i<180;i+=pas) {
        	
        	for(int j=-180;j<180;j+=pas) {
        		
        		if (((i+j)%(pas*2)) == 0) {
        			CallQuadri(quadriGroup,i,j,c1,pas/2);
        		}else {
        			CallQuadri(quadriGroup,i,j,c2,pas/2);
        		}
        		
        	}
        	
        }        
        root3D.getChildren().addAll(quadriGroup);
        
        Group lineGroup = new Group();
        
        for(int i=-180;i<180;i+=pas) {
        	
        	for(int j=-180;j<180;j+=pas) {
        		
        		Point3D p1 = geoCoordTo3dCoord(i,j,1);
        		Point3D p2 = geoCoordTo3dCoord(i,j,2);
        		Cylinder c = createLine(p1,p2);
        		
                final PhongMaterial mat = new PhongMaterial(c1);
                c.setMaterial(mat);
        		lineGroup.getChildren().addAll(c);
        		
        	}
        	
        }  
        root3D.getChildren().addAll(lineGroup);
        // Load geometry

        // Draw a line

        // Draw an helix

        // Draw city on the earth


        // Add a camera group
        PerspectiveCamera camera = new PerspectiveCamera(true);
        new CameraManager(camera, pane3D, root3D);

        // Add point light
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(-180);
        light.setTranslateY(-90);
        light.setTranslateZ(-120);
        light.getScope().addAll(root3D);
        root3D.getChildren().add(light);

        // Add ambient light
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.getScope().addAll(root3D);
        root3D.getChildren().add(ambientLight);

        // Create scene
        Scene scene = new Scene(pane3D, 600, 600, true);
        scene.setCamera(camera);
        scene.setFill(Color.gray(0.2));

        //Add the scene to the stage and show it
        primaryStage.setTitle("Earth Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    // From Rahel Lüthy : https://netzwerg.ch/blog/2015/03/22/javafx-3d-line/
    public Cylinder createLine(Point3D origin, Point3D target) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(0.01f, height);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }

    public static Point3D geoCoordTo3dCoord(float lat, float lon, float radius) {
        float lat_cor = lat + TEXTURE_LAT_OFFSET;
        float lon_cor = lon + TEXTURE_LON_OFFSET;
        return new Point3D(
                -java.lang.Math.sin(java.lang.Math.toRadians(lon_cor))
                        * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor)) * radius,
                -java.lang.Math.sin(java.lang.Math.toRadians(lat_cor))* radius,
                java.lang.Math.cos(java.lang.Math.toRadians(lon_cor))
                        * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor))* radius);
    }
    
    public void setColor(Shape3D s, Color c) {
        final PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseColor(c);
        mat.setSpecularColor(c);
        s.setMaterial(mat);
    }
    
    private void CallQuadri(Group parent,float lat, float lon, Color c, int pas) {
    	
 	
    	PhongMaterial material = new PhongMaterial(c);
    	
    	
    	//lat hauteur
    	Point3D topRight = geoCoordTo3dCoord(lat-pas,lon+pas,1.02f);
    	Point3D bottomRight = geoCoordTo3dCoord(lat+pas,lon+pas,1.02f);
    	Point3D bottomLeft = geoCoordTo3dCoord(lat+pas,lon-pas,1.02f);
    	Point3D topLeft = geoCoordTo3dCoord(lat-pas,lon-pas,1.02f);
    	//AddQuadrilateral(parent,topRight,bottomRight,bottomLeft,topLeft,material);
    	AddQuadrilateral(parent,topRight,topLeft,bottomLeft,bottomRight,material);
    }
    
    private void AddQuadrilateral(Group parent, Point3D topRight, Point3D bottomRight,
    		Point3D bottomLeft, Point3D topLeft, PhongMaterial material) {
    	
    	final TriangleMesh triangleMesh = new TriangleMesh();
    	
    	float[] points = {
    			(float)topRight.getX(),(float)topRight.getY(),(float)topRight.getZ(),    			
    			(float)topLeft.getX(),(float)topLeft.getY(),(float)topLeft.getZ(), 
    			(float)bottomLeft.getX(),(float)bottomLeft.getY(),(float)bottomLeft.getZ(), 
    			(float)bottomRight.getX(),(float)bottomRight.getY(),(float)bottomRight.getZ(),
    	};
    	
    	final float[] texCoords = {
    			
    			1,1,
    			1,0,
    			0,1,
    			0,0		
    	};
    	
    	final int[] faces = {
    			0,1,1,0,2,2,
    			0,1,2,2,3,3
    			
    	};
    	
    	triangleMesh.getPoints().setAll(points);
    	triangleMesh.getTexCoords().setAll(texCoords);
    	triangleMesh.getFaces().setAll(faces);
    	
    	
    	
    	final MeshView meshView = new MeshView(triangleMesh);
    	meshView.setMaterial(material);
    	parent.getChildren().addAll(meshView);
    	
    }
    
    private void displayTown(Group parent, String name, float latitude, float longitude) {
    	//name ???
    	
    	Point3D villePos = geoCoordTo3dCoord(latitude, longitude,1.0f);
    	Sphere sphere = new Sphere(0.01);
    	
    	Group g = new Group();
    	
    	g.getChildren().addAll(sphere);
    	if (name.equals("Marseille")) {
	    	System.out.println(villePos.getX());
	    	
	    	System.out.println(villePos.getY());
	    	System.out.println(villePos.getZ());
    	}
    	
    	g.setTranslateX(villePos.getX());
    	g.setTranslateY(villePos.getY());
    	g.setTranslateZ(villePos.getZ());
    	
    	g.setId(name);
    	
    	parent.getChildren().addAll(g);
   
    	
    	setColor(sphere,Color.RED);
    	
    	
    			
    }

}
