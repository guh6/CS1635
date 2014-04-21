package com.example.homepage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RandomCountingActivity extends Activity 
{

	public int randomCountNumber;
	public int randomAnimalNumber;
	public int animalCode;
	public int animalsRemaining;
	public ArrayList<Integer> animalList;
	ArrayList<ImageView> stars;
	Context context;
	private String un; 
	Intent userIntent;
	private int difficulty;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_counting);
		animalsRemaining = 0;
		context = this;
		
		userIntent = getIntent();
		un = userIntent.getStringExtra("Username");
		difficulty = userIntent.getIntExtra("difficulty",1);
		//restore game if it exists 
		//this is crashing the app
		Game g = restoreGame();
		
		if(g != null)
		{
            //restore game session
			Log.d("Database", "is not null");
			
            
			animalsRemaining = g.getClickedCows();
			randomCountNumber = g.getCows();
			
			String gamestring = g.getCowsList();
			String [] parts = gamestring.split(":");
			animalCode = Integer.valueOf(parts[0]);
			
			animalList= new ArrayList<Integer>();
			for(int i = 1; i < parts.length; i++){
				int num = Integer.parseInt(parts[i]);
				animalList.add(num);
			}
			//set up the stars
			int starNum = g.getStars();
            
			setStars();
			for(int i = 0; i < stars.size(); i++){
				
				stars.get(i).setVisibility(View.GONE);
				stars.remove(i);
			}
			
			try {
				playInstructs();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		else{
			setStars();
			//start new game
			animalList = makeAnimalList();	
			//shuffle the arraylist
			long seed = System.nanoTime();
			Collections.shuffle(animalList, new Random(seed));
            try {
				playInstructs();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//initialize the gridview 
		//put the animals on screen
		GridView gridview = (GridView)findViewById(R.id.random_gridview);
		
		//set the array adapter for the background
		//pass context, array
		gridview.setAdapter(new ImageAdapter(this, animalList));
		
		//gridview onClick
		gridview.setOnItemClickListener( new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				//get view id by comparing drawable ints
				//if the animal is the type that we're looking for increment the counter
				//otherwise play its sound and decrement a star
				int animal = animalList.get(position);
				
				if(animal == R.drawable.abomination){
					
					if(animal == animalCode){
						animalsRemaining++;
						showToast();
						win();
					}
					else{
						wrong();
					}
				}
				else if(animal == R.drawable.bacon){
					if(animal == animalCode){
						animalsRemaining++;
						showToast();
						win();
					}
					else{
						wrong();
					}
				}
				else if(animal == R.drawable.cat){
					if(animal == animalCode){
						//it's the level animal
						animalsRemaining++;
						showToast();
						win();
					}
					else{
						wrong();
					}
				}
				else if(animal == R.drawable.cow){
					if(animal == animalCode){
						animalsRemaining++;
						showToast();
						win();
					}
					else{
						wrong();
					}

				}
				else if(animal == R.drawable.dog){
					if(animal == animalCode){
						animalsRemaining++;
						showToast();
						win();
					}
					else{
						wrong();
					}
				}
				else if(animal == R.drawable.evil_creature){
					if(animal == animalCode){
						animalsRemaining++;
						showToast();
						win();
					}
					else{
						wrong();
					}
				}			
				else if(animal == R.drawable.owl){
					if(animal == animalCode){
						animalsRemaining++;
						showToast();
						win();
					}
					else{
						wrong();
					}
				}
				else if(animal == R.drawable.supposed_fox){
					if(animal == animalCode){
						animalsRemaining++;
						showToast();
						win();
					}
					else{
						wrong();
					}
				}
				else if(animal == R.drawable.squirrel){
					if(animal == animalCode){
						animalsRemaining++;
						showToast();
						win();
					}
					else{
						wrong();
					}
				}
				else{
					if(animal == animalCode){
						animalsRemaining++;
						showToast();
						win();
					}
					else{
						wrong();
					}
				}
				//set selected element as invisible
				v.setVisibility(View.INVISIBLE);
				
				//set the position value to 0000 to show that it's been clicked
				animalList.set(position, 0000);
				
				//play sound 
				try {
					playAnimalSound(animal);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			
		});
	
	}
	
	public Game restoreGame()
	{
		DatabaseHandler db = new DatabaseHandler(getBaseContext());
		Game game = db.getGame(un);
		
		return game;
	}
			
	private void setStars(){
		//make star array
		stars = new ArrayList<ImageView>();
		ImageView imgv = (ImageView)findViewById(R.id.star1);
		stars.add(imgv);
		ImageView imgv2 = (ImageView)findViewById(R.id.star2);
		stars.add(imgv2);
		ImageView imgv3 = (ImageView)findViewById(R.id.star3);
		stars.add(imgv3);
		ImageView imgv4 = (ImageView)findViewById(R.id.star4);
		stars.add(imgv4);
		ImageView imgv5 = (ImageView)findViewById(R.id.star5);
		stars.add(imgv5);
	}
	
	public void showToast(){
		LayoutInflater  inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup)findViewById(R.id.toast_layout_root));
		TextView text = (TextView)layout.findViewById(R.id.score);
		text.setText(Integer.valueOf(animalsRemaining).toString());
		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();	
	}
	
	public void win(){
		
		if(animalsRemaining == randomCountNumber){
			LayoutInflater li = LayoutInflater.from(context);
			View v = li.inflate(R.layout.win_dialog, null);
			AlertDialog.Builder adb = new AlertDialog.Builder(context);
			
			//remember to get star count
		    
	    	/*
	    	 * Guo's edit
	    	 */
	    	
			DatabaseHandler db = new DatabaseHandler(getBaseContext());
	    	GameStat stat = db.getStat(un);
	    	int level = stat.getLevels();
	    	stat.setLevels(level++); // gets the level from the db and incre it. 
	    	db.updateStat(stat);
			
			adb.setTitle("YOU WIN!");
			adb.setCancelable(false);
			
			adb.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) 
					    {					    	
					    	Intent intent = new Intent(RandomCountingActivity.this, OptionsActivity.class);
							intent.putExtra("Username", un);
							setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
							startActivity(intent); 					    	
					    }
					  });
			AlertDialog ad = adb.create();
			ad.show();
		}
		else{
			
			/*
	    	 * Guo's edit
	    	 */
			DatabaseHandler db = new DatabaseHandler(getBaseContext());
	    	GameStat stat = db.getStat(un);
	    	int hit = stat.getHit();
	    	stat.setLevels(hit++); // gets the level from the db and incre it. 
	    	db.updateStat(stat);
		}
	
	}
	
	public void wrong()
	{
		//if 
		//if wrong animal type remove a star
		int starnum = stars.size() - 1;
		ImageView star = stars.get(stars.size() - 1);
		star.setVisibility(View.GONE);
		
		if(starnum > 0){
			
			/*
	    	 * Guo's edit
	    	 */
			DatabaseHandler db = new DatabaseHandler(getBaseContext());
	    	GameStat stat = db.getStat(un);
	    	int miss = stat.getMiss();
	    	stat.setLevels(miss++); // gets the level from the db and incre it. 
	    	db.updateStat(stat);
			
			stars.remove(stars.size() - 1);
		}
		else{
			//show game over dialog
			LayoutInflater li = LayoutInflater.from(context);
			View v = li.inflate(R.layout.game_over_dialog, null);
			AlertDialog.Builder adb = new AlertDialog.Builder(context);
			
			adb.setTitle("GAME OVER!");
			adb.setCancelable(false);
			
			adb.setPositiveButton("MAIN MENU", new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) 
					    {
					    	Intent intent = new Intent(RandomCountingActivity.this, OptionsActivity.class);
							intent.putExtra("Username", un);
							setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
							startActivity(intent);					    	
					    }
					  });
			
			adb.setNegativeButton("PLAY AGAIN", new DialogInterface.OnClickListener() 
			{
			    public void onClick(DialogInterface dialog,int id) 
			    {
			    	Intent intent = new Intent(RandomCountingActivity.this, RandomCountingActivity.class);
					intent.putExtra("Username", un);
					startActivity(intent);					    	
			    }
			  });
			AlertDialog ad = adb.create();
			ad.show();
		
		}
	}
	
	public ArrayList<Integer> makeAnimalList()
	{
		//get random number for generator
		long seed = System.nanoTime();
		Random random = new Random(seed);
		
		if(difficulty==1)
			randomCountNumber = random.nextInt(5)+1;
		if(difficulty==2)
			randomCountNumber = random.nextInt(5)+5; 
		if(difficulty==3)
			randomCountNumber = random.nextInt(5)+10; 
		
		
		//make the array of animals
		ArrayList<Integer> al = new ArrayList<Integer>();
				
		//the selected animal the child will be counting
		setAnimalId();
		
		
		//set the array with the random animal
		for(int i = 0; i < randomCountNumber; i++){	
			al.add(animalCode);
		}
			int temp = al.size();
			//fill the rest of the array with the other animals
			int animaliterator = 0;
			int end=0; 
			
			if(difficulty==1)
				end = 0; 
			if(difficulty==2)
				end = 30-randomCountNumber;
			if(difficulty==3)
				end = 78 - randomCountNumber;			
			
			for(int i = 0; i < end; i++){
				
				if(animaliterator == 0 && randomAnimalNumber != 0){
					al.add(R.drawable.cow);	
				}
				else if(animaliterator == 1 && randomAnimalNumber != 1){
					al.add(R.drawable.squirrel);
				}
				else if(animaliterator == 2 && randomAnimalNumber != 2){
					al.add(R.drawable.evil_creature);
				}
				else if(animaliterator == 3 && randomAnimalNumber != 3){
					al.add(R.drawable.bacon);
				}
				else if(animaliterator == 4 && randomAnimalNumber != 4){
					al.add(R.drawable.owl);
				}
				else if(animaliterator == 5 && randomAnimalNumber != 5){
					al.add(R.drawable.abomination);
				}
				else if(animaliterator == 6 && randomAnimalNumber != 6){
					al.add(R.drawable.dog);
				}
				else if(animaliterator == 7 && randomAnimalNumber != 7){
					al.add(R.drawable.cat);
				}
				else if(animaliterator == 8 && randomAnimalNumber != 8){
					al.add(R.drawable.vicious_creature);
				}
				else{
					al.add(R.drawable.supposed_fox);
					if(animaliterator == 9){
						animaliterator = 0;
						continue;
					}
					
				}
				animaliterator++;
			
			}
		int s = al.size();
		
		return al;
	}
	
	
	private void playAnimalSound(int animal) throws IOException{
		//every animal has its own sound
		final MediaPlayer player = new MediaPlayer();
		AssetFileDescriptor afd = null;
		
		if(animal == R.drawable.abomination){
			afd = getAssets().openFd("horse_sound.mp3");
	
		}
		else if(animal == R.drawable.bacon){
			afd = getAssets().openFd("pig_sound.mp3");
		}
		else if(animal == R.drawable.cat){
			afd = getAssets().openFd("meow.mp3");
		}
		else if(animal == R.drawable.cow){
			afd = getAssets().openFd("cow_sound.mp3");
		}
		else if(animal == R.drawable.evil_creature){
			afd = getAssets().openFd("rooster.mp3");
		}
		else if(animal == R.drawable.owl){
			afd = getAssets().openFd("owl_sound.mp3");
		}
		else if(animal == R.drawable.supposed_fox){
			afd = getAssets().openFd("fox_sound.mp3");
		}
		else if(animal == R.drawable.squirrel){
			afd = getAssets().openFd("squirrel_sound.mp3");
		}
		else if(animal == R.drawable.vicious_creature){
			afd = getAssets().openFd("llama_sound.mp3");
		}
		else{
			afd = getAssets().openFd("woof.mp3");
		}
		player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),afd.getLength());
		player.prepare();
		player.start();
		player.setOnCompletionListener( new OnCompletionListener(){

			@Override
			public void onCompletion(MediaPlayer mp) {
				player.reset();
				player.release();
				
			}
			
		});
	}
	
	
	private void setAnimalId(){
		
		long seed = System.nanoTime();
		Random random = new Random(seed);
		
		randomAnimalNumber = random.nextInt(10);
		
		if(randomAnimalNumber == 0){
			animalCode = R.drawable.cow;
		}
		else if(randomAnimalNumber == 1){
			animalCode = R.drawable.squirrel;
		}
		else if(randomAnimalNumber == 2){
			animalCode = R.drawable.evil_creature;
		}
		else if(randomAnimalNumber == 3){
			animalCode = R.drawable.bacon;
		}
		else if(randomAnimalNumber == 4){
			animalCode = R.drawable.owl;
		}
		else if(randomAnimalNumber == 5){
			animalCode = R.drawable.abomination;
		}
		else if(randomAnimalNumber == 6){
			animalCode = R.drawable.dog;
		}
		else if(randomAnimalNumber == 7){
			animalCode = R.drawable.cat;
		}
		else if(randomAnimalNumber == 8){
			animalCode = R.drawable.vicious_creature;
		}
		else{
			animalCode = R.drawable.supposed_fox;
		}

	}
	
	
	public void goHome(View v){
		//save everything
		//userName, randomCountNumber, animalsRemaining, animalList as string, stars.size()
		//animalCode is position 0 of the animalList string
		//: is delimiter
		//N means that the element is invisible
		//all of the other things are the animalCode's position in the array
		
		StringBuilder sb = new StringBuilder();
		
		//add the animalcode
		sb.append(Integer.valueOf(animalCode).toString() + ":");
		
		for(int i = 0; i < animalList.size(); i++){
			sb.append(animalList.get(i).toString() + ":");
		}
		DatabaseHandler db = new DatabaseHandler(getBaseContext());
		db.addGame(new Game(un, randomCountNumber, animalsRemaining, sb.toString(),stars.size()));
		
		//aftersaving, quit
		RandomCountingActivity.this.finish();
		
	}
	
	
	public void stopGame(View v){
		//kill the current task
		//do not save
		RandomCountingActivity.this.finish();
	}
	
	
	public void playInstructions(View v){
        try {
			playInstructs();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
    
    public void playInstructs() throws IOException{
        //every animal has its own sound
        final MediaPlayer player = new MediaPlayer();
        AssetFileDescriptor afd = null;
        
        if(animalCode == R.drawable.abomination){
            afd = getAssets().openFd("CountHorses.mp3");
			
        }
        else if(animalCode == R.drawable.bacon){
            afd = getAssets().openFd("CountPigs.mp3");
        }
        else if(animalCode == R.drawable.cat){
            afd = getAssets().openFd("CountCats.mp3");
        }
        else if(animalCode == R.drawable.cow){
            afd = getAssets().openFd("CountCows.mp3");
        }
        else if(animalCode == R.drawable.evil_creature){
            afd = getAssets().openFd("CountChickens.mp3");
        }
        else if(animalCode == R.drawable.owl){
            afd = getAssets().openFd("CountOwls.mp3");
        }
        else if(animalCode == R.drawable.supposed_fox){
            afd = getAssets().openFd("CountFoxes.mp3");
        }
        else if(animalCode == R.drawable.squirrel){
            afd = getAssets().openFd("CountSquirrels.mp3");
        }
        else if(animalCode == R.drawable.vicious_creature){
            afd = getAssets().openFd("CountLlamas.mp3");
        }
        else{
            afd = getAssets().openFd("CountDogs.mp3");
        }
        player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),afd.getLength());
        player.prepare();
        player.start();
        player.setOnCompletionListener( new OnCompletionListener(){
            
            @Override
            public void onCompletion(MediaPlayer mp) {
                player.reset();
                player.release();
                
            }
            
        });
    }

}
