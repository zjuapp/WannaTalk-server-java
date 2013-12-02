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

import org.apache.log4j.Logger;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.wannatalk.server.ServerConfig;
import com.wannatalk.server.utils.StringUtils;

public class H2Database {
	static Logger log = Logger.getLogger(H2Database.class);
	private static final String DB_FILE_PATH = "~/wannatalk";
	private static final String DB_USERNAME  = "sa";
	private static final String DB_PWD       = "";
	
	private H2Database h2Database = null;
	H2Database(){
		
	}
	
	H2Database getInstance(){
		if(h2Database == null) {
			h2Database = new H2Database();
		}
		return h2Database;
	}
	
	void init() throws SQLException {
		log.info("begin to init h2database");
		
		try {
			Class.forName("org.h2.driver");
		} catch(Exception e) {
			log.info("Exception : can not find h2 driver, please make sure the h2.jar is in the correct dir");
			throw new SQLException("h2 driver can not be found");
		}
	
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
			
			if(!tableList.contains("user")) {
				log.info("create table user");
				st.execute(SQL_CREATE_TABLE_USER);
			}
			if(!tableList.contains("server_info")) {
				log.info("create table server_info");
				st.execute(SQL_CREATE_TABLE_SERVER_INFO);
			}
			
			String sql = "select * from server_info";
			
			rs = st.executeQuery(sql);
			if(rs.next()) {
				ServerConfig.SERVER_ID = rs.getString(1);;
			} else {
				ServerConfig.SERVER_ID = StringUtils.getRandomString();
				sql = "insert into server_info values ('" + ServerConfig.SERVER_ID + " ')";
				int ret = st.executeUpdate(sql);
				if(ret <= 0) {
					log.error("Database Error: failed insert into server_info!");
					throw new SQLException("Exception : insert into server_info error");
				}
			}
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
	
	
	
	
	private BoneCP connectionPool = null;
	
	private BoneCP getConnectionPool() throws SQLException {
		if(connectionPool == null){
			log.info("Creating BoneCP connection pool");
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl("jdbc:h2:file:" + DB_FILE_PATH);
			config.setUser(DB_USERNAME);
			config.setPassword(DB_PWD);
			
			config.setMinConnectionsPerPartition(5);  // max connections in one partion
			config.setMaxConnectionsPerPartition(10); // min connections in one partion
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
	
	private PreparedStatement getPrepareStatement(String sql) throws SQLException {
		Connection connection = getConnectionPool().getConnection();
		if(connection == null) {
			throw new SQLException("Exception : can not get connection from connection pool!");
		}
		return connection.prepareStatement(sql);
	}
	
	
	private static final String SQL_CREATE_TABLE_USER = 
			"create table user (" +
					"uid int," +
					"username varchar(128)" + 
					"password varchar(128)" + 
			")";
	
	private static final String SQL_CREATE_TABLE_SERVER_INFO = 
			"create table server_info (" +
					"server_id int" +
			")";
					
									
}
