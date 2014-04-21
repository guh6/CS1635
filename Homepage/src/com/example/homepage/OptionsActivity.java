package com.example.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OptionsActivity extends Activity {

	Intent userIntent; 
	String username; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		findViewById(R.id.exit).setOnClickListener(new exit());
		findViewById(R.id.exitimg).setOnClickListener(new exit());		
		findViewById(R.id.trophytextview).setOnClickListener(new trophies());
		findViewById(R.id.trophyimg).setOnClickListener(new trophies());
		findViewById(R.id.play).setOnClickListener(new play());
		findViewById(R.id.playimg).setOnClickListener(new play());
		findViewById(R.id.progress).setOnClickListener(new progress());
		findViewById(R.id.progressimg).setOnClickListener(new progress());
	
		
		userIntent = getIntent();
		username = userIntent.getStringExtra("Username");
	}

	
	private class play implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {	
			
			Intent intent = new Intent(OptionsActivity.this, LevelPage.class);
			intent.putExtra("Username", username);
			startActivity(intent);					
		}		
	}
	
	private class trophies implements View.OnClickListener
	{

		@Override
		public void onClick(View arg0) 
		{	
			//Intent intent = new Intent(OptionsActivity.this, Progress.class);
			//startActivity(intent);
		}
		
	}	

	
	private class exit implements View.OnClickListener
	{

		@Override
		public void onClick(View arg0) {	
			finish();
		}
		
	}
	
	private class progress implements View.OnClickListener
	{

		@Override
		public void onClick(View arg0) 
		{	
			Intent intent = new Intent(OptionsActivity.this, ParentProgress.class);
			intent.putExtra("Username", username);
			startActivity(intent);
		}
		
	}
	
	

}