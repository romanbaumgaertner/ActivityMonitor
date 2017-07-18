/* Copyright © 2013, University of California, Berkeley
 *
 * Permission to use and use of this file may be granted only by written
 * request and approval from UC Berkeley Seismological Lab.
 */

/* Copyright © 2013, Deutsche Telekom, Inc.
 *
 * Permission to use and use of this file may be granted only by
 * written request and approval from Deutsche Telekom, Inc.
 * Silicon Valley Innovation Center.
 */

package com.dt.tlabs.db;

public class DbConfigurationBase {
	
	private DbApplication.DatabaseType dbType;
	private String host;
	private int port;
	private String user;
	private String pw;
	private String dbName;
	private String sslEnabled;
	private String sslKeystore;
	private String sslPw;
	
	// will be set after the configuration. Default is false
	private boolean isConfigured = false;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public DbApplication.DatabaseType getDbType() {
		return dbType;
	}
	public void setDbType(DbApplication.DatabaseType dbType) {
		this.dbType = dbType;
	}
	public String getSslEnabled() {
		return sslEnabled;
	}
	public void setSslEnabled(String sslEnabled) {
		this.sslEnabled = sslEnabled;
	}
	public String getSslKeystore() {
		return sslKeystore;
	}
	public void setSslKeystore(String sslKeystore) {
		this.sslKeystore = sslKeystore;
	}
	public String getSslPw() {
		return sslPw;
	}
	public void setSslPw(String sslPw) {
		this.sslPw = sslPw;
	}
	public boolean isConfigured() {
		return isConfigured;
	}
	public void setConfigured(boolean isConfigured) {
		this.isConfigured = isConfigured;
	}
	
	/*
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}*/

}
