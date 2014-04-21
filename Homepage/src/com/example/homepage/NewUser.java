package com.example.homepage;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class NewUser extends Activity {

	static Uri capturedImageUri=null;
	final int CAMERA_SAVE_REQUEST = 888;
    private static final int CAMERA_REQUEST = 1888; 
    Bitmap profile_pic = null;
	boolean bird,blacksheep,bunny,cat,chick1,chick2,cow,giraffe,hatchlings,hippo,penguin,pig,rabbit,rooster,sheep = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_user);
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
				
		/*
		 * Guo's edit 4/9
		 */
		
		//Button to take the picture, and set the picture. 
		Button selfie = (Button) findViewById(R.id.selfie_submit);
				
				
				selfie.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						getImage();

					}
				});
	
		ImageView self_pic = (ImageView) findViewById(R.id.selfie_imageview);
		self_pic.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getImage();
			}
			
			
			
		});
		
	
				
				
		Button save = (Button) findViewById(R.id.save_submit);
		save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				DatabaseHandler db = new DatabaseHandler(getBaseContext()); // Make DB object
				EditText text = (EditText)findViewById(R.id.editText1);
				String name = text.getText().toString();
				
				if(text.equals("")){
					
				}
				else{
					
					
					db.addUser(new User(name,bitmapToByte(profile_pic))); // save to db
/*****************************************************************ADDED CODE*************************************/
					Intent intent = new Intent(NewUser.this, SignIn.class);
					startActivity(intent);
/***************************************************************END ADDED CODE**********************************/
				}
				
				
			}
			
		});
		
	
	Button debug = (Button) findViewById(R.id.DEBUG);
	
	debug.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			System.out.println("In debug");
			// TODO Auto-generated method stub
			DatabaseHandler db = new DatabaseHandler(getBaseContext()); // Make DB object
		  	 List<User> users = db.getAllUsers();       
			           
			          for (User cn : users ){
			              String log = 	  "Id: "+cn.getUsername() +
			            		  		  "pic size: " +cn.getImage().length; //Other getters, use it here.
			            		  
			            		  
			                  // Writing Contacts to log
			          Log.d("Name: ", log);
			          	}

		
		
		
		}
	});
		
	
	GridView gv = (GridView) findViewById(R.id.picturesGrid);
	gv.setAdapter(new NewUserAdapter(this));
	gv.setOnItemClickListener(new OnItemClickListener() {
	   
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			
				System.out.println(position);
				switch(position){
				case 0:
					setProfile( R.drawable.blacksheep);
					break;
				
				case 1:
					setProfile( R.drawable.penguin);
					break;
					
					
				case 2:
					setProfile( R.drawable.bird);

					break;
					
					
				case 3:
					setProfile( R.drawable.bunny);

					break;
					
					
				case 4:
					setProfile( R.drawable.cat);

					break;
					
				case 5:
					setProfile( R.drawable.chick2);

					break;
					
				case 6:
					setProfile( R.drawable.cow);

					break;
					
				case 7:
					setProfile( R.drawable.rabbit);

					break;
					
				case 8:
					setProfile( R.drawable.sheep);

					break;
					
				case 9:
					setProfile( R.drawable.hippo);

					break;
					
				case 10:
					setProfile( R.drawable.hatchlings);

					break;
					
				case 11:
					setProfile( R.drawable.giraffe);

					break;
					
				case 12:
					setProfile( R.drawable.rooster);

					break;
					
				default:
					break;
						
				}
			
			
			
			
			
		}
	});
	
		
		
	}
	
	public void setProfile(int drawable){
			profile_pic =  BitmapFactory.decodeResource(getResources(), drawable);		
		  	ImageView imageView = (ImageView) findViewById(R.id.selfie_imageview);
		    imageView.setImageBitmap(profile_pic); // Sets the picture here. 
			
		
	}
	
	
	
	public byte[] bitmapToByte(Bitmap bmp){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return byteArray;
	}

	
	public void getImage(){
		   Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
           startActivityForResult(cameraIntent, CAMERA_REQUEST); 
       
	}
	
	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
	            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	            ImageView imageView = (ImageView) findViewById(R.id.selfie_imageview);
			    imageView.setImageBitmap(photo); // Sets the picture here. 
			    profile_pic = photo; // set it to global.
	        }  
	    } 
	
		/*
		 * End 4/9
		 */
		
		
		
	
	/*
	 * I've commented below the two functions. They are used to save the image to the device.
	public void getImage(){
		System.out.println("In getImage()");
		Calendar cal = Calendar.getInstance();
		File file = new File(Environment.getExternalStorageDirectory(),  (cal.getTimeInMillis()+".jpg"));
		    if(!file.exists()){
		    try {
		        file.createNewFile();
		    } catch (IOException e) {
		    // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		    }else{
		       file.delete();
		    try {
		       file.createNewFile();
		    } catch (IOException e) {
		    // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		    }
		    capturedImageUri = Uri.fromFile(file);
		    Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		    i.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
		    startActivityForResult(i, CAMERA_RESULT);	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		System.out.println("requestCode" + requestCode);
		
	    if (requestCode == CAMERA_RESULT) {  
	        //Bitmap photo = (Bitmap) data.getExtras().get("data");
	        //imageView.setImageBitmap(photo);
	     try {
		    Bitmap bitmap = MediaStore.Images.Media.getBitmap( getApplicationContext().getContentResolver(),  capturedImageUri);
		    ImageView imageView = (ImageView) findViewById(R.id.selfie_imageview);
		    imageView.setImageBitmap(bitmap);
	    } catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    	e.printStackTrace();
	    } catch (IOException e) {
	    // TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
	    }  
	}
	*
	*/
	
	/*
	 * End
	 */
	
	
	
	/*
	 * Guo's edit
	 */
	public void nextIntent()
	{
		EditText name = (EditText) findViewById(R.id.editText1);
		System.out.println("name: " +name.getText().toString());
		
		//If no name is entered
		if(name.getText().toString().trim().equals("")){
			Toast toast = Toast.makeText(getBaseContext(), "Please enter a name", 2);
			toast.show();
			
			
		}
		else{
		//Do something with name?
		//Should actually be game choosing screen, but it's not implemented, so it'll go there
		Intent intent = new Intent(NewUser.this, SignIn.class);
		startActivity(intent);
		System.out.println("Going to game screen");
		}
	}


}
