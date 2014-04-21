package com.example.homepage;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

public class TracingIntent extends Activity implements DrawClass.sendIntent {
	Thread thread;
	RelativeLayout RL; 
	public DrawClass newDraw;
	Message msg = new Message();
	boolean state = false;
	boolean five = false;
	int difficulty,random_number;
	final MediaPlayer player = new MediaPlayer();
	AssetFileDescriptor afd = null;
	boolean loaded = false;
	private int theSound;
	private SoundPool sp;
	private String actualNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.tracing);	//this
		
		/**************************************************NEW CODE***********************************/
		Intent intent = getIntent();
		
		// Retrieve personal information stored in intent
		difficulty = intent.getIntExtra("difficulty", 1);
		
		Random r = new Random();
    	int ran = r.nextInt(10-5)+5;
    	if(ran==5)
    		ran=1;
    	if(ran==6)
    		ran=2; 
    	
    	random_number=ran;
    	
		newDraw = new DrawClass(this,random_number);
		RL = (RelativeLayout)this.findViewById(R.id.drawlayout);			
		
    	
		if(difficulty == 1)
		{
			if(random_number==1)
				RL.setBackgroundResource(R.drawable.oneeasy);
			else if(random_number==2)
				RL.setBackgroundResource(R.drawable.twoeasy);
			else if(random_number==7)
				RL.setBackgroundResource(R.drawable.seveneasy);
			else if(random_number==8)
				RL.setBackgroundResource(R.drawable.eighteasy);
			else if(random_number==9)
				RL.setBackgroundResource(R.drawable.nineeasy);
										
		}
		else if(difficulty == 2)
		{
			if(random_number==1)
				RL.setBackgroundResource(R.drawable.onemedium);
			else if(random_number==2)
				RL.setBackgroundResource(R.drawable.twomedium);
			else if(random_number==7)
				RL.setBackgroundResource(R.drawable.sevenmedium);
			else if(random_number==8)
				RL.setBackgroundResource(R.drawable.eightmedium);
			else if(random_number==9)
				RL.setBackgroundResource(R.drawable.ninemedium);
		}
				
		
/************************************END NEW CODE************************************/
		RL.addView(newDraw);
		newDraw.setIntentListener(this);
		
		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
		    @Override
		    public void onLoadComplete(SoundPool soundPool, int mySoundId, int status) {
		        loaded = true;
		    }
		});
		
		if(random_number==1)
			theSound = sp.load(getApplicationContext(), R.raw.traceone, 1);
		if(random_number==2)
			theSound = sp.load(getApplicationContext(), R.raw.tracetwo, 1);
		if(random_number==7)
			theSound = sp.load(getApplicationContext(), R.raw.traceseven, 1);
		if(random_number==8)
			theSound = sp.load(getApplicationContext(), R.raw.traceeight, 1);
		if(random_number==9)
			theSound = sp.load(getApplicationContext(), R.raw.tracenine, 1);
		
		if(loaded)
			sp.play(theSound, 1.f, 1.f, 1, 0, 1.f);		
		
/************************************VOICE OVER NEEDED***************/

	}
	
	@Override
	public void sendIntent() {
		AlertDialog.Builder newDialog = new AlertDialog.Builder(
				TracingIntent.this);
		// Change the R.layout.* below with the corresponding different stars.
		// image_dialog_box to image_dialog_box5.
		newDialog.setView(getLayoutInflater().inflate(
				R.layout.image_dialog_box5, null));

		// method that takes the user to the progress page. Use another redirect
		// here
		newDialog.setNeutralButton("Main Menu",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						System.out.println("Going to progress page");
						dialog.dismiss();
						
						Intent retrieve_intent = new Intent(TracingIntent.this, OptionsActivity.class);
						startActivity(retrieve_intent);	
						
					}
				});
		// method that takes the user to the game screen. Use redirection here
		newDialog.setPositiveButton("Play Again",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						Intent intent = new Intent(TracingIntent.this, TracingIntent.class);
						intent.putExtra("difficulty", difficulty);
						startActivity(intent);
						System.out.println("Going to game screen");
						dialog.dismiss();
					}
				});

		newDialog.show();
		// TODO Auto-generated method stub
		
	}
	
	public void PlaySoundAgain(View view)
	{
		if(loaded)
			sp.play(theSound, 1.f, 1.f, 1, 0, 1.f);
	}
}
