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

package com.dt.tlabs.app;

import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dt.tlabs.db.DbConfigurationBase;
import com.dt.tlabs.db.DbApplication;
import com.dt.tlabs.db.DbApplication.DatabaseType;
import com.dt.tlabs.db.MongoConfiguration;
import com.dt.tlabs.utils.Logging;

public class Configuration {
	
	static final Logger LOG_DEBUG = LogManager.getLogger("com.dt.droidshake.appserver.debug");
	
	private String test;
	
	// General 
	private String verboseEnabled;
	private String runtimeEnabled;
	private String debugLogging;
	private String debugLoggingFile;
	
	// EEW support
	private String eewEnabled;
	private int eewInMemorySyncFrequency;
	private int eewInMemoryQueryDuration;
	private float eewMagnitudeThreshold;
	
	// Db
	private List<DbConfigurationBase> database;
	
	// Push
	private String fcmEnabled;
	private String gcmEnabled;
	
	private String fcmKey;
	private String gcmKey;
	
	private String fcmUrl;
	private String fcmServiceFile;
	private String fcmAppName; // not used, only here for completeness 
	
	private boolean topicsSupported;
	private List<String> topics;
	
	private static Configuration sInstance;
	
	private Configuration(){
		this.verboseEnabled = Constants.sCONF_VAL_ENABLED;
		this.runtimeEnabled = Constants.sCONF_VAL_DISABLED;
		this.debugLogging = Constants.sCONF_VAL_ENABLED;
		 
		//this.dbType = DatabaseType.MYSQL.getDbName();
		
		this.fcmEnabled = Constants.sCONF_VAL_DISABLED;
		this.fcmKey =  Constants.sCONF_VAL_EMPTY;
		this.fcmUrl =  Constants.sCONF_VAL_EMPTY;
		this.fcmServiceFile =  Constants.sCONF_VAL_EMPTY;
		topicsSupported = Constants.sCONF_VALUE_PUSH_TOPICS_SUPPORTED;
		
		this.gcmEnabled = Constants.sCONF_VAL_DISABLED;
		this.gcmEnabled = Constants.sCONF_VAL_EMPTY;
		
		this.eewEnabled = Constants.sCONF_VAL_DISABLED;
		
		this.database = null;
	}
	
	public static Configuration getInstance(){
		
		if(sInstance == null){
			sInstance = new Configuration();
		}
		return sInstance;
	}
	
	
	public String getVerboseEnabled() {
		return verboseEnabled;
	}
	public void setVerboseEnabled(String verboseEnabled) {
		this.verboseEnabled = verboseEnabled;
	}
	
	public String getEewEnabled() {
		return eewEnabled;
	}
	public void setEewEnabled(String eewEnabled) {
		this.eewEnabled = eewEnabled;
	}
	
	public String getRuntimeEnabled() {
		return runtimeEnabled;
	}
	public void setRuntimeEnabled(String runtimeEnabled) {
		this.runtimeEnabled = runtimeEnabled;
	}
	public int getEewInMemorySyncFrequency() {
		return eewInMemorySyncFrequency;
	}
	public void setEewInMemorySyncFrequency(int eewInMemorySyncFrequency) {
		this.eewInMemorySyncFrequency = eewInMemorySyncFrequency;
	}
	public int getEewInMemoryQueryDuration() {
		return eewInMemoryQueryDuration;
	}
	public void setEewInMemoryQueryDuration(int eewInMemoryQueryDuration) {
		this.eewInMemoryQueryDuration = eewInMemoryQueryDuration;
	}
	public float getEewMagnitudeThreshold() {
		return eewMagnitudeThreshold;
	}
	public void setEewMagnitudeThreshold(float eewMagnitudeThreshold) {
		this.eewMagnitudeThreshold = eewMagnitudeThreshold;
	}
	public String getFcmEnabled() {
		return fcmEnabled;
	}
	public void setFcmEnabled(String fcmEnabled) {
		this.fcmEnabled = fcmEnabled;
	}
	public String getGcmEnabled() {
		return gcmEnabled;
	}
	public void setGcmEnabled(String gcmEnabled) {
		this.gcmEnabled = gcmEnabled;
	}
	public String getFcmKey() {
		return fcmKey;
	}
	public void setFcmKey(String fcmKey) {
		this.fcmKey = fcmKey;
	}
	public String getGcmKey() {
		return gcmKey;
	}
	public void setGcmKey(String gcmKey) {
		this.gcmKey = gcmKey;
	}
	public String getFcmUrl() {
		return fcmUrl;
	}
	public void setFcmUrl(String fcmUrl) {
		this.fcmUrl = fcmUrl;
	}
	public String getFcmServiceFile() {
		return fcmServiceFile;
	}
	public void setFcmServiceFile(String fcmServiceFile) {
		this.fcmServiceFile = fcmServiceFile;
	}
	
	public String getDebugLogging() {
		return debugLogging;
	}

	public void setDebugLogging(String debugLogging) {
		this.debugLogging = debugLogging;
	}

	public String getDebugLoggingFile() {
		return debugLoggingFile;
	}

	public void setDebugLoggingFile(String debugLoggingFile) {
		this.debugLoggingFile = debugLoggingFile;
	}

	public String getFcmAppName() {
		return fcmAppName;
	}

	public void setFcmAppName(String fcmAppName) {
		this.fcmAppName = fcmAppName;
	}

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}
	
	public boolean isTopicsSupported() {
		return topicsSupported;
	}

	public void setTopicsSupported(boolean topicsSupported) {
		this.topicsSupported = topicsSupported;
	}



	
	public void debugPrint(){
		Logging.sLOGGER(Configuration.class).debug("Configuration properties");
		Logging.sLOGGER(Configuration.class).debug("verbose Enabled:" + this.verboseEnabled );
		Logging.sLOGGER(Configuration.class).debug("runtime Config Enabled:" + this.runtimeEnabled );
		Logging.sLOGGER(Configuration.class).debug("Test:" + this.test );
		
		
		for( DbConfigurationBase db: this.getDatabase() ){
			Logging.sLOGGER(Configuration.class).debug ("DB type:" + db.getDbType().getDbName() );
			Logging.sLOGGER(Configuration.class).debug ("Host:" + db.getHost() );
			Logging.sLOGGER(Configuration.class).debug ("Post:" + db.getPort() );
			//Logging.sLOGGER(Configuration.class).debug ("User" + db.getUser() );
			//Logging.sLOGGER(Configuration.class).debug ("Pw" + db.getPw() );
			Logging.sLOGGER(Configuration.class).debug ("DbName:" + db.getDbName() );
			
			if( db.getSslEnabled().equals( Constants.sCONF_VAL_ENABLED) ){
				Logging.sLOGGER(Configuration.class).debug ("Collection:" + ((MongoConfiguration)db).getCollection());
			}
		
			if(db.getDbType().getDbName().equals( DbApplication.DatabaseType.MONGO.getDbName() ) ){
				Logging.sLOGGER(Configuration.class).debug("Mongo enabled");
				Logging.sLOGGER(Configuration.class).debug ("Collection:" + ((MongoConfiguration)db).getCollection());
			}
			
		}
		
		if(fcmEnabled.equals( Constants.sCONF_VAL_ENABLED)){
			Logging.sLOGGER(Configuration.class).debug("FCM Enabled:" + this.fcmEnabled );
			//Logging.sLOGGER(Configuration.class).debug("FCM Key:" + this.fcmKey );
			Logging.sLOGGER(Configuration.class).debug("FCM Service File:" + this.fcmServiceFile );
			Logging.sLOGGER(Configuration.class).debug("FCM Url:" + this.fcmUrl );
			
			if( this.getTopics() != null){
				Logging.sLOGGER(Configuration.class).debug("FCM # topics supported:" + this.getTopics().size() );
			}
		}
		Logging.sLOGGER(Configuration.class).debug("");
		
		if(gcmEnabled.equals( Constants.sCONF_VAL_ENABLED)){
			Logging.sLOGGER(Configuration.class).debug("GCM Enabled:" + this.gcmEnabled );
			//Logging.sLOGGER(Configuration.class).debug("GCM Key:" + this.gcmKey );
		}
		
		Logging.sLOGGER(Configuration.class).debug("");
		Logging.sLOGGER(Configuration.class).debug("Component configuration properties");
		Logging.sLOGGER(Configuration.class).debug("");
	
		Logging.sLOGGER(Configuration.class).debug("EEW Enabled:" + this.eewEnabled);
		if(this.eewEnabled.equals( Constants.sCONF_VAL_ENABLED)){

			Logging.sLOGGER(Configuration.class).debug("EEW Min Mag:" + this.eewMagnitudeThreshold );
			Logging.sLOGGER(Configuration.class).debug("EEW Query Duration:" + this.eewInMemoryQueryDuration );
			Logging.sLOGGER(Configuration.class).debug("EEW Sync Frequency:" + this.eewInMemorySyncFrequency );
		}
	}

	public List<DbConfigurationBase> getDatabase() {
		return database;
	}

	public void setDatabase(List<DbConfigurationBase> database) {
		this.database = database;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

}
