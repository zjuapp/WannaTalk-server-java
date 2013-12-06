package com.wannatalk.server.model;

public class User {
	public String username = null;
	public String password = null;
	public int 	  uid	   = -1;
	public int    sex 	   = -1;
	public int 	  motion   = -1;
	public User() {
	}
	
	public User(String _username, String _password, int _sex, int _motion) {
		username = _username;
		password = _password;
		sex      = _sex;
		motion   = _motion;	
	}
	
}
