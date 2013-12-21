/**
 * some query api with h2 database
 * 2013/12/2 chenyi 
 */
package com.wannatalk.server.data.h2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.wannatalk.server.ServerConfig;
import com.wannatalk.server.model.MapPoint;
import com.wannatalk.server.model.User;
import com.wannatalk.server.utils.StringUtils;

public class H2Database {
	static Logger log = Logger.getLogger(H2Database.class);
	
	private static final String DB_FILE_PATH = "~/wannatalk";
	private static final String DB_USERNAME  = "sa";
	private static final String DB_PWD       = "";
	private static H2Database h2Database = null;

	H2Database(){
	}
	public static H2Database getInstance(){
		if(h2Database == null) {
			h2Database = new H2Database();
		}
		return h2Database;
	}
	
	void init() throws SQLException {
		log.info("begin to init h2database");
		Statement st = null;
		try {
			st = getStatement();
			Connection conn = st.getConnection();
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getTables(null, null, null, new String[] {"TABLE"});
			ArrayList<String> tableList = new ArrayList<String>();
			while(rs.next()) {
				tableList.add(rs.getString("TABLE_NAME"));
			}
			log.debug("Existing tables - " + tableList.size() + "-" + tableList.toString());
			if(!tableList.contains("USER")) {
				log.info("create table user");
				st.execute(SQL_CREATE_TABLE_USER);
			}
			if(!tableList.contains("SERVER_INFO")) {
				log.info("create table server_info");
				st.execute(SQL_CREATE_TABLE_SERVER_INFO);
			}
			
			String sql = "select * from server_info";
			
			log.info("server id = " + ServerConfig.SERVER_ID);
		} catch(Exception e) {
			log.error("init error");
			e.printStackTrace();
			throw new SQLException("init error");
		} finally {
			if(st == null)
				return;
			try {
				Connection conn = st.getConnection();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List <User> search_user(MapPoint center, int r){
		try{
			log.debug("search_user is invoked");
			Statement statement = getStatement();
			ResultSet res = statement.executeQuery("select * from user");
			List <User> res_users = new ArrayList<User>();
			int i = 0;
			while(res.next()){
				log.debug("res has " + i++);
				res_users.add(new User(res.getInt("uid"), res.getString("username"), 
						res.getString("password"), res.getInt("motion"),
						res.getInt("motoinLevel"), res.getString("signature"),
						res.getInt("sex"), res.getInt("lat"), res.getInt("lon"),
						res.getInt("state")));
			}
			return res_users;
		}
		catch(SQLException E){
			E.printStackTrace();
		}
		return null;
		/*
		PreparedStatement statement = null;
		try{
			statement =  getPreparedStatement(SearchPerson);
			statement.setInt(1, center.lat);
			statement.setInt(2, center.lat);
			statement.setInt(3, center.lon);
			statement.setInt(4, center.lon);
			statement.setInt(5, r);
			statement.setInt(6, r);
			ResultSet res = statement.executeQuery();
			List <User> res_users = new ArrayList<User>();
			while(res.next()){
				res_users.add(new User(res.getInt("uid"), res.getString("username"), 
						res.getString("password"), res.getInt("motion"),
						res.getInt("motoinLevel"), res.getString("signature"),
						res.getInt("sex"), res.getInt("lat"), res.getInt("lon"),
						res.getInt("state")));
			}
			return res_users;
		}
		catch(SQLException E){
			E.printStackTrace();
		}finally {
			try {
				Connection conn = statement.getConnection();
				statement.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("Exception : close statement or connection error! in insert_server");
			}
		}
		return null;
		*/
	}
	
	public List <User> judge(String userName, String password) {
		log.debug("judge is invoked");
		PreparedStatement statement = null;
		try{
			statement = getPreparedStatement(JudgeLogin);
			statement.setString(1, userName);
			statement.setString(2, password);
			ResultSet res = statement.executeQuery();
			List <User> res_users = new ArrayList<User>();
			if(res.next()){
				log.debug("login uid is " + res.getInt("uid"));
				res_users.add(new User(res.getInt("uid"), res.getString("username"), 
						res.getString("password"), res.getInt("motion"),
						res.getInt("motoinLevel"), res.getString("signature"),
						res.getInt("sex"), res.getInt("lat"), res.getInt("lon"),
						res.getInt("state")));
				return res_users;
			}
			else
				return null;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		finally {
			if(statement == null)
				return null;
			try {
				Connection conn = statement.getConnection();
				statement.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean insert_user(String username, String password, String sex) {
		// TODO Auto-generated method stub
			PreparedStatement st = null;
			try {
				st = getPreparedStatement(RegisterPerson);
				st.setString(1, username);
				st.setString(2, password);
				st.setString(3, sex);
				st.executeUpdate();
				
			} catch (SQLException e) {
				log.error("Exception : insert into user error");
				e.printStackTrace();
				return false;
			} finally {
				try {
					if(st != null){
						Connection conn = st.getConnection();
						st.close();
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("Exception : close statement or connection error! in insert_server");
				}
			}
			log.debug("insert into user ok!");
			return true;
	}	
	
	public User get_user_by_id(int id){
		PreparedStatement st = null;
		String sql = "select * from user where uid = " + id;
		try{
			log.debug(sql);
			st = getPreparedStatement(sql);
			ResultSet res = st.executeQuery();
			if(res.next()){
				return new User(res.getInt("uid"), res.getString("username"), 
						res.getString("password"), res.getInt("motion"),
						res.getInt("motoinLevel"), res.getString("signature"),
						res.getInt("sex"), res.getInt("lat"), res.getInt("lon"),
						res.getInt("state"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("Exception : get user by id error!!");
			return null;
		}
		
		return null;
	}
	public Boolean updatesignatureandmotion(int id, String signature,
			int motionid, int motionlevel) {
		PreparedStatement st = null;
		try{
			st = getPreparedStatement(UpdateSignatureAndMotion);
			st.setInt(1, motionid);
			st.setInt(2, motionlevel);
			st.setString(3, signature);
			st.setInt(4, id);
			st.executeUpdate();
			return true;
		}
		catch(SQLException e){
			e.printStackTrace();
			log.error("Exception: update position error");
			return false;
		}
	}

	public boolean updatepos(int id, int lat, int lon){
		PreparedStatement st = null;
		try{
			st = getPreparedStatement(UpdatePos);
			st.setInt(1, lat);
			st.setInt(2, lon);
			st.setInt(3, id);
			st.executeUpdate();
			return true;
		}
		catch(SQLException e){
			e.printStackTrace();
			log.error("Exception: update position error");
			return false;
		}
	}
	private BoneCP connectionPool = null;
	
	private BoneCP getConnectionPool() throws SQLException {
		if(connectionPool == null){
			log.info("Creating BoneCP connection pool");
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl("jdbc:h2:file:" + DB_FILE_PATH);
			config.setUser(DB_USERNAME);
			config.setPassword(DB_PWD);		
			config.setMinConnectionsPerPartition(5);  // max connections in one partion
			config.setMaxConnectionsPerPartition(100); // min connections in one partion
			config.setPartitionCount(1);              // set one partion for this app
			connectionPool = new BoneCP(config);
		}
		return connectionPool;
	}
	
	private Statement getStatement() throws SQLException {
		Connection connection = getConnectionPool().getConnection();
		if(connection == null) {
			throw new SQLException("Exception : can not get connection from connection pool!");
		}
		return connection.createStatement();
	}
	
	private PreparedStatement getPreparedStatement(String sql) throws SQLException {
		Connection connection = getConnectionPool().getConnection();
		if(connection == null) {
			throw new SQLException("Exception : can not get connection from connection pool!");
		}
		return connection.prepareStatement(sql);
	}
	
	private static final String SQL_CREATE_TABLE_USER = 
              "create table if not exists user (" +
                              "uid int AUTO_INCREMENT," +
                              "username varchar(128)," + 
                              "password varchar(128)," +
                              "motion int," +
                              "motoinLevel int," +
                              "signature varchar(128)," +
                              "sex int," +
                              "lon int," +
                              "lat int," +
                              "state int," +
                              "primary key(uid)" + 
              ")";
	private static final String SQL_CREATE_TABLE_SERVER_INFO = 
			"create table server_info (" +
					"server_id int" +
			")";
	private static final String SearchPerson = "select * from user where (( lat - ?) * (lat - ?) + (lon - ?) * (lon - ?)) < ? * ? and state = 1;";
	private static final String InsertPerson = "insert into user (username,password,motion,motoinlevel,signature,sex,lat,lon,state) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?,?);";
	private static final String RegisterPerson = "insert into user (username, password, sex) values (?, ? ,?)";
	private static final String JudgeLogin = "select * from user where username = ? and password = ?";
	private static final String UpdatePos = "update user set lat = ?, lon = ? where uid = ?";	
	private static final String UpdateSignatureAndMotion = "update user set motion = ?, motoinlevel = ?, signature = ? where uid = ?";
	
	
}
