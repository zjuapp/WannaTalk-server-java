/**
 * specific query apis for server with database
 * 2013/12/2 chenyi 
 */
package com.wannatalk.server.data;

import com.wannatalk.server.model.User;

public interface QueryService {
	public boolean registerUser(User user);
	public User getUser(String uid);
}
