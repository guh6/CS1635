package com.example.homepage;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);	

		Button new_player = (Button) findViewById(R.id.imageButton1);

		//New User Button Clicked
		new_player.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(SignIn.this, NewUser.class);
				startActivity(intent);

			}
		});		
		
		
		DatabaseHandler db = new DatabaseHandler(getBaseContext()); // Make DB object
		List<User> user_list = db.getAllUsers();       
				          
		for (User cn : user_list ){

			byte[] byteArray = cn.getImage();
			Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
			
			//get users and pictures from database
			// Dynamically add a user			
			LinearLayout users = (LinearLayout) findViewById(R.id.linearLayout1);
			LinearLayout individual_user = new LinearLayout(SignIn.this);
			individual_user.setOrientation(LinearLayout.HORIZONTAL);
			
			TextView name = new TextView (SignIn.this);
			name.setTypeface(null, Typeface.BOLD);
			name.setText(cn.getUsername());
			name.setTag(cn.getUsername());
			name.setClickable(true);
			
			ImageView profile_pic = new ImageView (SignIn.this);
			profile_pic.setImageBitmap(bm);
			//profile_pic.setImageBitmap(picture_bitmap);
			profile_pic.setClickable(true);	
			profile_pic.setTag(cn.getUsername());
						
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.height = 70;
			params.width = 70;
			params.rightMargin = 30;
			params.leftMargin = 150;
			//params.gravity = Gravity.CENTER;
			params.bottomMargin = 20;
			profile_pic.setLayoutParams(params);

			LinearLayout.LayoutParams name_params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			name_params.topMargin = 20;
			name.setLayoutParams(name_params);
		
			individual_user.addView(profile_pic);
			individual_user.addView(name);
			users.addView(individual_user);
			
			
			View line = new View(SignIn.this);
			LinearLayout.LayoutParams line_params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 2);
			line.setLayoutParams(line_params);
			line.setBackgroundColor(Color.LTGRAY);
			users.addView(line);
			 
		
			name.setOnClickListener(new View.OnClickListener(){         
				public void onClick(View view)
				{
					//Toast.makeText(view.getContext() , "clicked = " + view.getTag() , Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(SignIn.this, OptionsActivity.class);
					intent.putExtra("Username",view.getTag().toString());
					startActivity(intent);

				}
			}); 

			profile_pic.setOnClickListener(new View.OnClickListener(){         
				public void onClick(View view)
				{
					//Toast.makeText(view.getContext() , "clicked = " + view.getTag() , Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(SignIn.this, OptionsActivity.class);
					intent.putExtra("Username",view.getTag().toString());
					startActivity(intent);
				}
			});
			
		}

	}

}
