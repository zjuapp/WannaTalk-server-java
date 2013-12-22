package com.wannatalk.server.model;

import java.util.List;

public class User implements Comparable<User>{
	public static int ref_motion;
	public static int ref_motionlevel;
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
	public int value = -1;
	public void setvalue(){
		value = 10 * Math.abs(motion - ref_motion) + Math.abs(motionlevel - motionlevel);
	}
	@Override
	public int compareTo(User o) {
		int res = value - o.value;
		if(res < 0) return -1;
		if(res > 0) return 1;
		return 0;
	}
	public static <T extends Comparable<T>> void  sort(int i, int j, List <T> s){
		if(i >= j)
			return;
		int rand = (i + j) / 2;
		T tmp = s.get(rand);
		s.set(rand, s.get(j));
		s.set(j,  tmp);
		T cmp = s.get(j);
		int st = i;
		int ed = j;
		while(true){
			while(st < ed && s.get(st).compareTo(cmp) <= 0)st++;
			while(st < ed && s.get(ed).compareTo(cmp) >= 0)ed--;
			if(st < ed){
				T a = s.get(st);
				s.set(st, s.get(ed));
				s.set(ed, a);
			}
			else
				break;
			st++;
			ed--;
		}
		T a = s.get(st);
		s.set(st, s.get(j));
		s.set(j, a);
		sort(i, st - 1, s);
		sort(st + 1, j, s);
	}
}
