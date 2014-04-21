package com.example.homepage;

public class User {
	int _id;
	String username;
	byte[] image;
	
	
	public User(int id, String username, byte[]image){
		this._id = id;
		this.username = username;
		this.image= image;
		
	}
	
	
	public User( String username, byte[]image){
		this.username = username;
		this.image= image;
		
	}
	
	
	
	public User(){
		
	}
	//Setters
	public void setUsername(String name){
		this.username = name;
	}
	public void setImage(byte[]image){
		this.image = image;
	}
	
	public int getID(){
		return this._id;
	}
	
	//Getters
	public String getUsername(){
		return this.username;
	}
	public byte[] getImage(){
		return this.image;
	}
	public void setID(int id){
		this._id = id;
	}
	
}
