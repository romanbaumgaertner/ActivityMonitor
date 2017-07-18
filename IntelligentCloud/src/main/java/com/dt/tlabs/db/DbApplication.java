package com.dt.tlabs.db;

import java.util.ArrayList;
import java.util.List;

import com.dt.tlabs.app.IntelligentCloudApplication;
import com.dt.tlabs.utils.Logging;
import com.mongodb.client.MongoDatabase;

public class DbApplication {
	
	public static enum DatabaseType {
		MYSQL("mysql"), MONGO("mongodb"), NDF("notDefined");
		
		private String dbName;
		
		private DatabaseType(String dbName){
			this.dbName = dbName;
		}
		
		public String getDbName(){
			return dbName;
		}
	}
	
	/////
	// member variables
	/////
	private List<DbConfigurationBase> listDbConfigBase;
	public List<DbConfigurationBase> getDbConfiguration(){
		return listDbConfigBase;
	}
	public void setDbConfiguration(List<DbConfigurationBase> listDbConfiguration){
		this.listDbConfigBase = listDbConfiguration;
	}	
	
	private List<DbBase> listDb;
	public List<DbBase> getDbList(){
		return listDb;
	}
	public void setDbList(List<DbBase> listDb){
		this.listDb = listDb;
	}
	
	
	/**
	 * Constructs a DBApplication 
	 * @param listConfigBase
	 */
	public DbApplication( List<DbConfigurationBase> listConfigBase){
		this.listDbConfigBase = listConfigBase;
		listDb = new ArrayList<DbBase>();
	}
	
	/**
	 * Configures the databases in configure
	 * 
	 * The configuration status for each individual DB can be found in attribute
	 * isConfigured.
	 * 
	 * @param configuration
	 */
	public boolean configureDb() throws DbException{
		boolean executed = false;

		if (listDbConfigBase != null) {
			
			// loop through available DB configurations
			for (DbConfigurationBase dbConfigBase : this.listDbConfigBase) {
				try {
					// get a DB instance
					DbBase dbInstance = DbFactory.getDb(dbConfigBase);
					
					// configure the DB with config information
					DbConfigurationBase dbConfigUpdated = dbInstance.configureDb(dbConfigBase);
					
					// adding DB to supported list of DBs
					this.listDb.add(dbInstance);
				} catch (DbException e) {

					e.printStackTrace();
					Logging.sLOGGER(DbApplication.class).warn("DbApplication - Not able to configure " + dbConfigBase.getDbName() );
				}
				
				executed = true;
			}
		} else {
			Logging.sLOGGER(IntelligentCloudApplication.class).warn("DbApplication - No DB configuration possible");
			throw new DbException("DB configuration not possible");
		}

		return executed;
	}

}
