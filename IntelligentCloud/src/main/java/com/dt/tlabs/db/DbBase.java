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

import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;

public interface DbBase {
	
	/**
	 * Gets the DB configuration
	 * @return
	 * @throws DbException
	 */
	public DbConfigurationBase getDbConfiguration()  throws DbException;
	
	/**
	 * Sets the DB Configuration
	 * 
	 * @throws DbException
	 */
	public void setDbConfiguration(DbConfigurationBase dbConfig)  throws DbException;
	
	/**
	 * Configures the DB
	 * 
	 * @param config
	 * @return
	 * @throws DbException
	 */
	public DbConfigurationBase configureDb(final DbConfigurationBase config) throws DbException;
	
	/**
	 * Returns the connection object
	 * @return
	 */
	public Object connect() throws DbException;
	
	/**
	 * Insert sensor data
	 */
	public void insertSensorData(final JSONObject sensorData) throws DbException;
		
}
