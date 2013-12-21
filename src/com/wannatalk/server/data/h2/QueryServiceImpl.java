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
	public int judge(String userName, String password) {
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
	public Boolean updatemotion(int id, int motionid, int motionlevel) {
		// TODO Auto-generated method stub
		return h2DB.updatemotion(id, motionid, motionlevel);
	}
	@Override
	public Boolean updatesignature(int id, String signature) {
		// TODO Auto-generated method stub
		return h2DB.updatesignature(id, signature);
	}
}
