/**
 * specific query apis for server with database
 * 2013/12/2 chenyi 
 */
package com.wannatalk.server.data;



import java.util.List;

import com.wannatalk.server.model.MapPoint;
import com.wannatalk.server.model.User;

public interface QueryService {
	public boolean registerUser(User user);
	public User getUser(String uid);
	public List <User> search_user(MapPoint center, int r);
	public boolean add(String string, String string2, int j, int k,
			String string3, int l, int m, int n, int o);
	public int judge(String userName, String password);
	public boolean registerUser(String userName, String password, String sex);
}
