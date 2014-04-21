package com.example.homepage;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class LevelPage extends ActionBarActivity 
{
	boolean count, tracing, levelOne, levelTwo, levelThree = false;
	
	Intent userIntent;
	String username; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level);
		
		userIntent = getIntent();
		username = userIntent.getStringExtra("Username");
		
		ImageButton counting = (ImageButton) findViewById(R.id.button1);
		counting.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		        count = true;
		        tracing = false;
		        
		    }
		});
		
		ImageButton trace = (ImageButton) findViewById(R.id.button2);
		trace.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		        tracing = true;
		        count=false;
		        
		    }
		});
		
		ImageButton levelone = (ImageButton) findViewById(R.id.button3);
		levelone.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		        levelOne = true;
		        levelTwo=false;
		        levelThree=false;
		        
		    }
		});
		
		ImageButton leveltwo = (ImageButton) findViewById(R.id.button4);
		leveltwo.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		        levelTwo = true;
		        levelOne=false;
		        levelThree=false;
		        
		    }
		});

		ImageButton levelthree = (ImageButton) findViewById(R.id.button5);
		levelthree.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	levelThree = true;
		    	levelOne=false;
		    	levelTwo=false;
		        
		    }
		});
		
		ImageButton play = (ImageButton) findViewById(R.id.button6);
		play.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) 
		    {
		    	if(count)
		    	{
		    		int difficulty=1; 
		    		if(levelOne){}
		    		else if(levelTwo)
		    			difficulty=2; 
		    		else if(levelThree)
		    			difficulty=3; 
		    		
		    		Intent intent = new Intent(LevelPage.this, RandomCountingActivity.class);
					intent.putExtra("Username", username);
					intent.putExtra("difficulty",difficulty);
					startActivity(intent);
		    	}	
		    	if(tracing)
		    	{
		    		
			    	Intent intent = new Intent(LevelPage.this, TracingIntent.class);
			    	if(levelOne)
			    		intent.putExtra("difficulty", 1);
			    	if(levelTwo)
			    		intent.putExtra("difficulty", 2);
			    	if(levelThree)
			    		intent.putExtra("difficulty", 3);
			    	
			    	startActivity(intent);
		    	}
			    	
		    }
		});		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
