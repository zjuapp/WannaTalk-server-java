package com.wannatalk.server.model;

public class User {
	public int uid;
	public String username, password;
	public int motion, motionlevel;
	public String signature;
	public int sex, lat, lon, state;
	public User(int uid, String username, String password, int motion, int motionlevel, String signature, int sex, int lat, int lon, int state){
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.motion = motion;
		this.motionlevel = motionlevel;
		this.signature = signature;
		this.sex = sex;
		this.lat = lat;
		this.lon = lon;
		this.state = state;
	}
}
