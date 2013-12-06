package com.wannatalk.server.data.h2;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.wannatalk.server.data.QueryService;
import com.wannatalk.server.model.User;

public class QueryServiceImpl implements QueryService{
	H2Database h2DB = null;
	Logger log = Logger.getLogger(getClass());
	public QueryServiceImpl() throws SQLException {
		h2DB = H2Database.getInstance();
		try {
			h2DB.init();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Exception : h2Db init error");
		}
	}
	
	@Override
	public boolean registerUser(User user) {
		return h2DB.insert_user(user);
	}

	@Override
	public User getUser(String uid) {
		User user = h2DB.getUser(uid);
		return user;
	}

	
	
	


}
