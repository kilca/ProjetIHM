package climatechange;

import java.util.List;

import climatechange.models.ResourceManager;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ClimateLineChart extends LineChart<Number,Number>{
	
	private NumberAxis xAxis;
	private NumberAxis yAxis;
	
	public ClimateLineChart(List<Float> temperatures) {
		super (new NumberAxis(ResourceManager.getInstance().minYear,ResourceManager.getInstance().maxYear,1),
				new NumberAxis(ResourceManager.getInstance().minTemp,ResourceManager.getInstance().maxTemp,1));
		
		xAxis = (NumberAxis) this.getXAxis();
		yAxis = (NumberAxis) this.getYAxis();
		
		xAxis.setLabel("years");
		yAxis.setLabel("temperature variation");
		
		yAxis.setAutoRanging(false);
		
		this.setTitle("Temperature variation");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Temperature");

        
        int annee = ResourceManager.getInstance().minYear;
        for(int i=0;i<temperatures.size();i++) {
        	if (!Float.isNaN(temperatures.get(i)))
        		series.getData().add(new XYChart.Data(annee, temperatures.get(i)));
            annee++;
        	
        }

        this.getData().add(series);
             
        for (Object data : series.getData()) {          
             Node node = ((XYChart.Data < Number, Number >)data).getNode();
             node.setScaleX(0.2);
             node.setScaleY(0.2);
            
        }
        
		
	}
	
	public void setTemperatures(List<Float> temperatures) {
        XYChart.Series series = new XYChart.Series();

        
        int annee = ResourceManager.getInstance().minYear;
        for(int i=0;i<temperatures.size();i++) {
        	if (!Float.isNaN(temperatures.get(i)))
        		series.getData().add(new XYChart.Data(annee, temperatures.get(i)));
            annee++;
        	
        }
        this.getData().set(0, series);
		
        for (Object data : series.getData()) {          
            Node node = ((XYChart.Data < Number, Number >)data).getNode();
            node.setScaleX(0.2);
            node.setScaleY(0.2);
           
       }
        
	}
	
	public ClimateLineChart(Axis<Number> xAxis, Axis<Number> yAxis) {
		super(xAxis, yAxis);
		// TODO Auto-generated constructor stub
	}



}
