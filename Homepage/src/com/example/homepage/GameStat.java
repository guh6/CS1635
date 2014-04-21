package com.example.homepage;

public class GameStat {
	
	int _id;
	String _name;
	int _miss;
	int _hit;
	int _levels_completed;
	

	
	public GameStat( String name){
		
		this._name = name;
		this._miss = 0;
		this._hit = 0;
		this._levels_completed = 0;
			
		
	}
	public GameStat(int id, String name, int miss, int hit, int level){
		
		this._id = id;
		this._name = name;
		this._miss = miss;
		this._hit = hit;
		this._levels_completed = level;
			
		
	}
	
	public int getID(){
		return this._id;
	}
	public String getName(){
		return this._name;
	}
	public int getMiss(){
		return this._miss;
	}
	public int getHit(){
		return this._hit;
		
	}
	
	public int getLevels(){
		return this._levels_completed;
	}
	
	
	public void setMiss( int miss){
		this._miss = miss;
	}
	
	
	public void setHit( int _hit){
		this._hit = _hit;
	}
	
	public void setLevels( int levels){
		this._levels_completed = levels;
	}
	
}
