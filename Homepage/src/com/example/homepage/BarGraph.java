package com.example.homepage;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
public class BarGraph 
{
	private String UN; 
	private Context base; 
	public BarGraph(String username, Context context)
	{
		UN = username; 
		base = context;
	}
    
    public GraphicalView getIntent(Context context)
    {
    	DatabaseHandler db = new DatabaseHandler(base);    	
    	GameStat stat = db.getStat(UN);    	
    	
    	int countMiss = stat.getMiss();
    	int countHit = stat.getHit();
        double y[] = {countMiss,countHit,.4,.6};
        
        CategorySeries countSeriesOne = new CategorySeries("CountingHits");
        
        for(int i=0; i<4; i++)
        {
        	countSeriesOne.add("Bar"+(i+1),y[i]);
        }
        
        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();  // collection of series under one object.,there could any
        dataSet.addSeries(countSeriesOne.toXYSeries()); 
        
        //customization of the chart
    
        XYSeriesRenderer renderer = new XYSeriesRenderer();     // one renderer for one series
        renderer.setColor(Color.YELLOW);
        renderer.setDisplayChartValues(true);
        renderer.setChartValuesSpacing((float) 5.5d);
        renderer.setLineWidth((float) 10.5d);
        renderer.setShowLegendItem(false);         
        
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();   // collection multiple values for one renderer or series
        mRenderer.addSeriesRenderer(renderer);

        mRenderer.setChartTitle("PROGESS GRAPH");
        mRenderer.setXTitle("VARIABLES");
        mRenderer.setYTitle("VALUES");
        mRenderer.setZoomButtonsVisible(true);    
        //mRenderer.setShowLegend(true);
        mRenderer.setShowGridX(true);      // this will show the grid in  graph
        mRenderer.setShowGridY(true);              
        //mRenderer.setAntialiasing(true);
        mRenderer.setBarSpacing(.5);   // adding spacing between the line or stacks
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.BLUE);
        mRenderer.setXAxisMin(0);
        mRenderer.setYAxisMin(0);
        mRenderer.setXAxisMax(5);
        mRenderer.setYAxisMax(50);
    
        mRenderer.setXLabels(0);
        mRenderer.addXTextLabel(1,"COUNTS HIT");
        mRenderer.addXTextLabel(2,"COUNTS MISS");
        mRenderer.addXTextLabel(3,"TRACE HIT");
        mRenderer.addXTextLabel(4,"TRACE MISS");
        mRenderer.setPanEnabled(true, true);    // will fix the chart position
        
        GraphicalView intent = ChartFactory.getBarChartView(context, dataSet, mRenderer,Type.DEFAULT);
        
        return intent;
    }
}