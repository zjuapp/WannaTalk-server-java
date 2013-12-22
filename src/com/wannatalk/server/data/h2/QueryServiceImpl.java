package com.wannatalk.server.data.h2;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.wannatalk.server.data.QueryService;
import com.wannatalk.server.model.MapPoint;
import com.wannatalk.server.model.User;

public class QueryServiceImpl implements QueryService{
	H2Database h2DB = null;
	Logger log = Logger.getLogger(getClass());
	public QueryServiceImpl(){
		h2DB = H2Database.getInstance();
		try {
			h2DB.init();
		} catch (SQLException e) {
		}
	}
	@Override
	public List<User> search_user(MapPoint center, int r) {
		return h2DB.search_user(center, r);
	}
	
	@Override
	public List <User> judge(String userName, String password) {
		// TODO Auto-generated method stub
		return h2DB.judge(userName, password);
	}
	@Override
	public boolean registerUser(String userName, String password, String sex) {
		return h2DB.insert_user(userName, password, sex);
	}
	@Override
	public boolean registerUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public User getUser(String uid) {
		return h2DB.get_user_by_id(Integer.parseInt(uid));
	}
	@Override
	public boolean add(String string, String string2, int j, int k,
			String string3, int l, int m, int n, int o) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean updatepos(int id, int lat, int lon){
		return h2DB.updatepos(id, lat, lon);
	}

	@Override
	public Boolean updatesignatureandmotion(int id, String signature,
			int motionid, int motionlevel) {
		return h2DB.updatesignatureandmotion(id, signature, motionid, motionlevel);
	}
	@Override
	public boolean updatestate(int id, int state) {
		return h2DB.updatestate(id, state);
	}
}
