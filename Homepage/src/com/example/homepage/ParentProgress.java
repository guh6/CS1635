package com.example.homepage;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ParentProgress extends Activity 
{
	Button  barGraph;
    private GraphicalView mChartView;
    private String username; 
    private Intent userIntent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_parent);     
        
        userIntent = getIntent();
        username=userIntent.getStringExtra("Username");
        Context base = getBaseContext();
        BarGraph bar = new BarGraph(username,base);
        mChartView = bar.getIntent(this);
        LinearLayout LL = (LinearLayout)findViewById(R.id.chart);
        LL.addView(mChartView);
    }
    
    public void GoBack(View view)
    {
    	Intent intent = new Intent(ParentProgress.this, OptionsActivity.class);
		intent.putExtra("Username", username);
		startActivity(intent);
    }
}
