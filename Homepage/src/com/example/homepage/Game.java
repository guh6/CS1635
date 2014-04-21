package com.example.homepage;

/*
 * @authors: 
 * @description: class that contains the server information
 */
public class Game {

	int _id;
	String username;
	int total_cows;
	int clicked_cows;
	
	String cows_list;
	int stars;
	// Empty constructor
	public Game() {

	}

	// 2nd constructor
	public Game(int id, String username, int cows, int clicked_cows, String cows_list, int stars) {
		this._id = id;
		this.username = username;
		this.total_cows = cows;
		this.clicked_cows = clicked_cows;
		this.cows_list = cows_list;
		this.stars = stars;
	}

	// 3rd constructor
	public Game( String username, int cows, int clicked_cows, String cows_list, int stars) {

		this.username = username;
		this.total_cows = cows;
		this.clicked_cows = clicked_cows;
		this.cows_list = cows_list;
		this.stars = stars;
	}
	

	// Getters
	public int getCows(){
		return this.total_cows;
	}
	public int getClickedCows(){
		return this.clicked_cows;
	}
	public String getCowsList(){
		return this.cows_list;
	}
	public String getUsername(){
		return this.username;
	}
	public int getID(){
		return this._id;
	}
	public int getStars(){
		return this.stars;
	}
	// Setters
	public void setCows(int cows){
		 this.total_cows = cows;
	}
	public void setClickedCows(int cows){
		 this.clicked_cows = cows;
	}
	public void setCowsList(String list){
		 this.cows_list = list;
	}
	public void setUsername(String name){
		 this.username = name;
	}
	public void setID(int id){
		this._id = id;
	}
	public void setStars(int stars){
		this.stars = stars;
	}
}
