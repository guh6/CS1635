package com.example.homepage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

// Custom class that extends view
// Holds all of the canvas properties for drawing. 
public class DrawClass extends View
{
	// Declare global variables
	private Paint mPaint;
	private Canvas  mCanvas;
    private Path    mPath;
    private ArrayList<Path> myPaths = new ArrayList<Path>();
    private ArrayList<Paint> myPaints = new ArrayList<Paint>();
    private ArrayList<Float> coords = new ArrayList<Float>();
    private MyCountDownTimer myTimer;
    private boolean set = false;
    AlertDialog.Builder myAlert;
    AlertDialog alert;
/*****************************************NEW LINE**************************************/
    int random_number; 
/********************************************END NEW LINE*******************************/
    public sendIntent SI;

    Timer t = new Timer();
    //Basic DrawClass constructor
  
/************************************************CHANGE CONSTRUCTOR LINE*****************************/
    public DrawClass(Context c, int num) 
    {
        super(c);
        mPath = new Path();
        mCanvas = new Canvas();
        Path tempPath = new Path();

        mPaint = new Paint();
        mPaint.setColor(-16776961);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        myTimer = new MyCountDownTimer(3000,1000);
        
        myAlert = new AlertDialog.Builder(c);
        myAlert.setTitle("TRACING");
        myAlert.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
        	public void onClick(DialogInterface dialog, int id)
        	{
        		
        	}
        });    
        
        alert = myAlert.create();

/*******************************************NEW LINE**************************************/
        random_number = num;
/*******************************************END NEW LINE********************************/
    }
    
    public interface sendIntent
 	{
 		void sendIntent();
 	}
    
    public void setIntentListener(sendIntent listener)
    {
    	SI = listener;
    }

  
    
    // Handles resizing of canvas
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) 
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mCanvas = new Canvas();
    }

    // Called for drawing actual paths on canvas 
    // with specific paint properties
    @Override
    protected void onDraw(Canvas canvas) 
    {    	
       myPaths.add(mPath);
   	   myPaints.add(mPaint);
       for(int i=0; i<myPaths.size(); i++)
       {
    	   canvas.drawPath(myPaths.get(i),myPaints.get(i));
       }
    }
    
    
    private float mX, mY;

    // Called when the user first touches the screen
    private void touch_start(float x, float y) 
    {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
    
    // Called when the user is moving finger across screen
    private void touch_move(float x, float y) 
    {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= 4 || dy >= 4) 
        {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }
    
    // Called when user lifts finger off of screen
    private void touch_up() 
    {
        mPath.lineTo(mX, mY);
    }

    // Called when user touches the screen. 
    // Add coordinates to ArrayList and update canvas 
    @Override
    public boolean onTouchEvent(MotionEvent event) 
    {
        float x = event.getX();
        float y = event.getY();
        Path tempPath = new Path();
        switch (event.getAction()) 
        {
            case MotionEvent.ACTION_DOWN:
            	coords.add(x/255);
            	coords.add(y/255);
                touch_start(x, y);                
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
            	coords.add(x/255);
            	coords.add(y/255);
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            	coords.add((float)255);
            	coords.add((float)0);
                touch_up();
                if(set==true)
                	myTimer.cancel();
                
                set=true;
                myTimer.start();
                invalidate();
                break;
        }
        return true;
    }
    
 // Completele erases all data for all current variables
    public void reset()
    {
    	mPath = new Path();
    	myPaths = new ArrayList<Path>();
    	myPaints = new ArrayList<Paint>();
    	coords = new ArrayList<Float>();
    	invalidate();
    }
    
    public class MyCountDownTimer extends CountDownTimer
	{

		public MyCountDownTimer(long startTime, long interval)
		{
			super(startTime, interval);
		}

		@Override
		public void onFinish() 
		{
/************************************************NEW CODE ADDED****************************/
			if(coords.size()==0)
				return;
/**************************************************END NEW CODE***************************/
			myHttpPost mHP = new myHttpPost();
			
			StringBuilder stringCords = new StringBuilder();
			stringCords.append("["+ coords.get(0).toString() + ", " + coords.get(1).toString()+ ",");
			for(int i=2; i<coords.size(); i++)
			{
				stringCords.append(" " + coords.get(i).toString() + ",");
				i++; 
				stringCords.append(" " + coords.get(i).toString() + ",");
			}
			stringCords.append(" 255, 255]");
			
			String temp = stringCords.toString();
			mHP.execute(temp);
		}

		@Override
		public void onTick(long millisUntilFinished)
		{
			System.out.println("Seconds remaining: " + millisUntilFinished / 1000);
		}
	}  
    
    // Makes http request asynchronous so application doesn't 
 	// freeze. 
 	public class myHttpPost extends AsyncTask<String, Void, String>
 	{
 		// Create httpPost request and sends request to the server
 		@Override 
 		protected String doInBackground(String... params)
 		{
 			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
 			String actualkey = "11773edfd643f813c18d82f56a8104ed";
 			String qdata = params[0];
 			pairs.add(new BasicNameValuePair("key",actualkey));
 			pairs.add(new BasicNameValuePair("q",qdata));
 			HttpClient HC = new DefaultHttpClient();
 			HttpPost HP = new HttpPost("http://cwritepad.appspot.com/reco/usen");
 						
 			// Try catch blocks for actually sending request
 			try
 			{
 				HP.setEntity(new UrlEncodedFormEntity(pairs));
 			}catch (Exception e)
 			{}
 			try
 			{
 				// If successful return string results from request
 				HttpResponse HR;
 				HR = HC.execute(HP);
 				String temp = EntityUtils.toString(HR.getEntity());
 				return temp;
 			}catch(Exception e)
 			{}
 			
 			// If failure show text that request was not successful
 			String temp = "not successful request from server";
 			return temp;
 		} 
 		
 		// Handle update on textview is successful result
 		protected void onPostExecute(String result) 
 		{
 			boolean trace=false;
 			for(int i=0; i<result.length();i++)
 			{
 				char c = result.charAt(i);
/**************************************************NEW CODE***********************************/
 				StringBuilder S = new StringBuilder();
 				S.append(random_number);
 				
 				char tmp = S.charAt(0);
 				System.out.println("tmp is: " + tmp);
 				System.out.println("c is: " + c);
 				
 				if(c==tmp)
 				{
 					System.out.println("You wrote correct number");
 					trace=true;
 					break;
 				}
/*************************************************END NEW CODE**********************************/
 			}
 			 			
 			if(trace == true)
 				SI.sendIntent();
 			else 
 				reset();
         }
 	}
    
}