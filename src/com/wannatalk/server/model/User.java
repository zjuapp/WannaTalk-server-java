package com.wannatalk.server.model;

public class User {
	private String username;
	private int uid;
	
	User() {
		username = null;
		uid      = -1;
	}
	
	User(String _username, int _uid) {
		username = _username;
		uid 	 = _uid;
	}
	
}
