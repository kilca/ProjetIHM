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
	
	public ClimateLineChart() {
		super (new NumberAxis(ResourceManager.getInstance().minYear,ResourceManager.getInstance().maxYear,1),
				new NumberAxis(ResourceManager.getInstance().minTemp,ResourceManager.getInstance().maxTemp,1));
		
		this.setLegendVisible(false);
		
		xAxis = (NumberAxis) this.getXAxis();
		yAxis = (NumberAxis) this.getYAxis();
		
		xAxis.setLabel("years");
		yAxis.setLabel("temperature variation");
		
		yAxis.setAutoRanging(false);
		
		this.setTitle("Temperature variation");
        //defining a series
        XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();
        series.setName("Temperature");

	}
	
	public ClimateLineChart(Axis<Number> xAxis, Axis<Number> yAxis) {
		super(xAxis, yAxis);
		this.setLegendVisible(false);
		// TODO Auto-generated constructor stub
	}
	
	public ClimateLineChart(List<Float> temperatures, boolean littleChart) {
		super (new NumberAxis(ResourceManager.getInstance().minYear,ResourceManager.getInstance().maxYear,
				20),
				new NumberAxis(ResourceManager.getInstance().minTemp,ResourceManager.getInstance().maxTemp,1));
		
		this.setLegendVisible(!littleChart);
		
		xAxis = (NumberAxis) this.getXAxis();
		yAxis = (NumberAxis) this.getYAxis();
        XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();
		
        //yAxis.setAutoRanging(littleChart);
		
		if (!littleChart) {
			xAxis.setLabel("years");
			yAxis.setLabel("temperature variation");
			this.setTitle("Temperature variation");
	        series.setName("Temperature");
		}
		
        //defining a series
		
        
        int annee = ResourceManager.getInstance().minYear;
        for(int i=0;i<temperatures.size();i++) {
        	if (!Float.isNaN(temperatures.get(i))) {
        		series.getData().add(new XYChart.Data<Number,Number>(annee, temperatures.get(i)));
        	}
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
        XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();

        
        int annee = ResourceManager.getInstance().minYear;
        for(int i=0;i<temperatures.size();i++) {
        	if (!Float.isNaN(temperatures.get(i)))
        		series.getData().add(new XYChart.Data<Number,Number>(annee, temperatures.get(i)));
            annee++;
        	
        }
        this.getData().set(0, series);
		
        for (Object data : series.getData()) {          
            Node node = ((XYChart.Data < Number, Number >)data).getNode();
            node.setScaleX(0.2);
            node.setScaleY(0.2);
           
       }
        
	}




}
