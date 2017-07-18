package com.dt.tlabs.db;

import com.dt.tlabs.db.DbApplication.DatabaseType;

public class DbFactory {
	
	/**
	 * Returns a DbBase instance
	 * @return
	 * 		DbBase
	 * @throws DbException
	 */
	public static DbBase  getDb(DbConfigurationBase config) throws DbException {
		DbBase dbBase = null;
		DatabaseType dbType = DatabaseType.MYSQL; //default

		if (config != null) {
			dbType = config.getDbType();
			
			if(dbType == DatabaseType.MYSQL){
				// not supported
			}else if(dbType == DatabaseType.MONGO)
				dbBase = new MongoDb();

		} else {
			throw new DbException("invalid Db configuration");
		}
		
		return dbBase;
	}

}
