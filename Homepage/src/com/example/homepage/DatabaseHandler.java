package com.example.homepage;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	static final int DATABASE_VERISON = 50; // DB version
	static final String DATABASE_NAME = "GamesDB"; // DB name
	static final String TABLE_GAME = "Games";
	// Columns
	static final String KEY_ID = "id";
	static final String KEY_NUM_OF_COWS = "total_cows";
	static final String KEY_COWS_CLICKED = "num_clicked";
	static final String KEY_LIST_OF_CLICKED = "clicked_cows_list";
	static final String KEY_USERNAME = "username";
	static final String KEY_STARS = "stars";

	//User DB
	static final String KEY_USER_ID = "id";
	static final String KEY_PROFILE_NAME = "username";
	static final String KEY_PICTURE = "picture";
	static final String TABLE_USER = "UserDB";
	
	//GameStats
	static final String KEY_MISS = "misses";
	static final String KEY_HIT = "hits";
	static final String KEY_LEVELS = "levels_completed";
	static final String TABLE_STATS = "UserStats";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERISON);
	}

	// Creates the tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_GameS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GAME + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"			
				+ KEY_USERNAME + " TEXT,"
				+ KEY_NUM_OF_COWS + " INTEGER,"
				+ KEY_COWS_CLICKED + " INTEGER, "
				+ KEY_LIST_OF_CLICKED + " TEXT, "
				+ KEY_STARS +" INTEGER "
				+ ");";

		db.execSQL(CREATE_GameS_TABLE);
		
		
		
		String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
				+ KEY_USER_ID + " INTEGER PRIMARY KEY,"			
				+ KEY_PROFILE_NAME + " TEXT,"
				+ KEY_PICTURE + " BLOB "				
				+ ");";

		db.execSQL(CREATE_USER_TABLE);

		
		
		String CREATE_STATS = "CREATE TABLE IF NOT EXISTS " + TABLE_STATS + "("
				+ KEY_USER_ID + " INTEGER PRIMARY KEY,"			
				+ KEY_PROFILE_NAME + " TEXT,"
				+ KEY_MISS + " INTEGER,"
				+ KEY_HIT + " INTEGER, "	
				+ KEY_LEVELS + " INTEGER "		
				+ ");";

		db.execSQL(CREATE_STATS);
	}

	// upgrades database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVerison, int newVerison) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * @description Adds a Game to the table
	 * @param Game
	 */
	public void addGame(Game game) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		// putting the Game information
		
		values.put(KEY_USERNAME, game.getUsername());
		values.put(KEY_NUM_OF_COWS, game.getCows());
		values.put(KEY_COWS_CLICKED, game.getClickedCows());
		values.put(KEY_LIST_OF_CLICKED, game.getCowsList());
		values.put(KEY_STARS, game.getStars());

		// inserting it into db
		db.insert(TABLE_GAME, null, values);
		db.close();

	}
	
	
	
	/**
	 * @description Adds a Game to the table
	 * @param Game
	 */
	public void addStat(GameStat stat) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		// putting the Game information
		
		values.put(KEY_PROFILE_NAME, stat.getName());
		values.put(KEY_MISS, stat.getMiss());
		values.put(KEY_HIT, stat.getHit());
		values.put(KEY_LEVELS, stat.getLevels());

		// inserting it into db
		db.insert(TABLE_STATS, null, values);
		db.close();

	}
	
	
	public void addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		// putting the Game information
		
		values.put(KEY_PROFILE_NAME, user.getUsername());
		values.put(KEY_PICTURE, user.getImage());

		// inserting it into db
		db.insert(TABLE_USER, null, values);
		db.close();

	}

	/**
	 * @description gets a single Game
	 * @param id
	 * @return
	 */
	public Game getGame(int id) 
	{
		SQLiteDatabase db = this.getReadableDatabase();

		// SEARCH query
		Cursor cursor = db.query(TABLE_GAME,
				new String[] { KEY_ID,  KEY_PROFILE_NAME,KEY_PICTURE, KEY_COWS_CLICKED, KEY_LIST_OF_CLICKED, KEY_STARS
						}, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		

		if (cursor != null)
			cursor.moveToFirst();

		// Finds the Game result
		Game Game = new Game(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
				Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)),cursor.getString(4),Integer.parseInt(cursor.getString(5))     );

		// Now return it
		return Game;

	}
	
	
	/**
	 * @description gets a single User
	 * @param id
	 * @return
	 */
	public User getUser(String username) {
		SQLiteDatabase db = this.getReadableDatabase();

		// SEARCH query
		Cursor cursor = db.query(TABLE_USER,
				new String[] { KEY_USER_ID,  KEY_PROFILE_NAME, KEY_PICTURE
						}, KEY_PROFILE_NAME + "=?",
				new String[] { String.valueOf(username) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		// Finds the Game result
		User user = new User(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
				cursor.getBlob(2)  );

		// Now return it
		return user;

	}
	/**
	 * @description gets a single Game
	 * @param id
	 * @return
	 */
	public Game getGame(String username) {
		SQLiteDatabase db = this.getReadableDatabase();

		System.out.println("INside getGame");
		// SEARCH query
		Cursor cursor = db.query(TABLE_GAME,
				new String[] { KEY_ID,  KEY_USERNAME,KEY_NUM_OF_COWS, KEY_COWS_CLICKED, KEY_LIST_OF_CLICKED, KEY_STARS
						}, KEY_USERNAME + "=?",
				new String[] { String.valueOf(username) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();
		
/************************If NO GAMES EXIST - ADD TO DATABASE**********/
		if(cursor.getCount()==0)
		{
			System.out.println("CURSOR == NULL");
			return null;
		}
/**************************END NEW ADDING STUFF**********************/

		// Finds the Game result
		Game Game = new Game(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
				Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)),cursor.getString(4),Integer.parseInt(cursor.getString(5))     );

		// Now return it
		return Game;

	}
	
	
	/**
	 * @description gets a single Game
	 * @param id
	 * @return
	 */
	public GameStat getStat(String username) {
		SQLiteDatabase db = this.getReadableDatabase();

		System.out.println("INside GETsTATS");
		// SEARCH query
		Cursor cursor = db.query(TABLE_STATS,
				new String[] { KEY_ID,  KEY_PROFILE_NAME,KEY_MISS, KEY_HIT, KEY_LEVELS
						}, KEY_PROFILE_NAME + "=?",
				new String[] { String.valueOf(username) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();
		
/************************If NO GAMES EXIST - ADD TO DATABASE**********/
		if(cursor.getCount()==0)
		{			
			GameStat stat = new GameStat(username);
			System.out.println("CURSOR == NULL");
			addStat(stat);
			return stat;			
		}
/**************************END NEW ADDING STUFF**********************/
		else{
		// Finds the Game result
		GameStat stat = new GameStat(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
				Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4))    );
		System.out.println(stat);
		// Now return it
		return stat;
		}
	}

	/**
	 * @description returns all of the Games in a list
	 * @return
	 */
	public List<Game> getAllGames() {
		List<Game> GameList = new ArrayList<Game>();

		String selectQuery = "SELECT  * FROM " + TABLE_GAME;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Game Game = new Game();
				Game.setID(Integer.parseInt(cursor.getString(0)));
				Game.setUsername(cursor.getString(1));
				Game.setCows(Integer.parseInt(cursor.getString(2)));
				Game.setClickedCows(Integer.parseInt(cursor.getString(3)));
				Game.setCowsList((cursor.getString(4)));
				Game.setStars(Integer.parseInt(cursor.getString(5)));

				// Adding contact to list
				GameList.add(Game);
			} while (cursor.moveToNext());
		}
		return GameList;
	}
	
	
	/**
	 * @description returns all of the Users in a list
	 * @return
	 */
	public List<User> getAllUsers() {
		List<User> UserList = new ArrayList<User>();

		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User user = new User();
				user.setID(Integer.parseInt(cursor.getString(0)));
				user.setUsername(cursor.getString(1));
				user.setImage((cursor.getBlob(2)));
			

				// Adding contact to list
				UserList.add(user);
			} while (cursor.moveToNext());
		}
		return UserList;
	}
	

	/**
	 * @description returns the number of Games
	 * @return
	 */
	public int getGamesCount() {
		String countQuery = "SELECT  * FROM " + TABLE_GAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	/**
	 * @description update the Game with Game parameter 
	 * @param Game
	 * @return
	 */
	public int updateGame(Game game) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(KEY_USERNAME, game.getUsername());
		values.put(KEY_NUM_OF_COWS, game.getCows());
		values.put(KEY_COWS_CLICKED, game.getClickedCows());
		values.put(KEY_LIST_OF_CLICKED, game.getCowsList());
		values.put(KEY_STARS, game.getStars());

		return db.update(TABLE_GAME, values, KEY_ID + " = ?",
				new String[] { String.valueOf(game.getID()) });

	}
	
	/**
	 * @description update the Game with Game parameter 
	 * @param Game
	 * @return
	 */
	public int updateStat(GameStat game) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(KEY_PROFILE_NAME, game.getName());
		values.put(KEY_MISS, game.getMiss());
		values.put(KEY_HIT, game.getHit());
		values.put(KEY_LEVELS, game.getLevels());

		return db.update(TABLE_STATS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(game.getID()) });

	}


	/**
	 * @description deletes a Game record
	 * @param Game
	 */
	public void deleteGame(Game Game) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_GAME, KEY_ID + " = ?",
				new String[] { String.valueOf(Game.getID()) });
		db.close();
	}

}
