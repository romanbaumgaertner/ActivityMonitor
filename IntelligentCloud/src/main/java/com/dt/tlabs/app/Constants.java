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

public class Constants {
	
	// General Server constants
	
	public static int sSEVERE_ERROR = -1;
	public static int sGCM_KEY_ERROR = -2;
	public static int sPUSH_PROVIDER_ERROR = -3;
	public static int sDB_CONFIGURATION_ERROR = -4;
	
	public static int sDEFAULT_PORT = -1;
	
	public static final int sMAG_INVALID = -1;
	public static final int sPOLL_FREQUENCY_INVALID = -1;
	
	public static final String VAL_SSL_NO_KS = "";
	public static final String VAL_SSL_NO_KS_PW = "";
	
	public static final String sCONF_HOST = "host";
	public static final String sCONF_PORT = "port";
	public static final String sCONF_USER = "user";
	public static final String sCONF_PW = "password";
	
	
	public static final String sCONF_VAL_TRUE = "true";
	public static final String sCONF_VAL_FALSE = "false";
	public static final String sCONF_VAL_ENABLED = "enabled";
	public static final String sCONF_VAL_DISABLED = "disabled";
	public static final String sCONF_VAL_EMPTY = "";
	
	// TEST
	public static final String sCONF_PROP_TEST = "test";
	
	// Config Properties General
	public static final String sCONF_PROP_VERBOSE = "app.debugVerboseEnabled";
	
	// Config Properties Logging
	public static final String sCONF_PROP_LOGGING = "logging.level.com.dt.tlabs.app";
	public static final String sCONF_PROP_LOGGING_FILE = "logging.file";
			
	// Config Values		
	public static final String sCONF_VAL_LOGGING_DEBUG = "DEBUG";
	public static final String sCONF_VAL_LOGGING_FILE = "broadcaster.log";
	
	// Config Properties MySql
	public static final String sCONF_PROP_DB_SUPPORTED = "db.supported";
	public static final String sCONF_PROP_DB_HOST = "db.mysql.host";
	public static final String sCONF_PROP_DB_PORT = "db.mysql.port";
	public static final String sCONF_PROP_DB_USER = "db.mysql.user";
	public static final String sCONF_PROP_DB_PW = "db.mysql.pw";
	public static final String sCONF_PROP_DB_DBNAME = "db.mysql.dbName";
	
	public static final String sCONF_PROP_DB_MYSQL_CONNECTION_POOL = "db.mysql.connectionPool";
	public static final String sCONF_PROP_DB_MYSQL_POOLSIZE = "db.mysql.dbConnectionPoolSize";
	
	
	public static final String cPROP_DB               = "db";
	public static final String cPROP_DB_HOST          = "db.host";
	public static final String cPROP_DB_PORT          = "db.port";
	public static final String cPROP_DB_DBNAME        = "db.dbName";
	public static final String cPROP_DB_USERNAME      = "db.username";
	public static final String cPROP_DB_PASSWORD      = "db.password";
	public static final String cPROP_DB_COLLECTION    = "db.collection";
	public static final String cPROP_DB_TABLE         = "db.table";
	public static final String cPROP_DB_SSL           = "db.ssl";
	public static final String cPROP_DB_SSL_KEYSTORE  = "db.ssl.keystore";
	public static final String cPROP_DB_SSl_KEYPW     = "db.ssl.keyPw";
	
	public static final String [] sCONF_DB_PROPS = { cPROP_DB_HOST, cPROP_DB_PORT, cPROP_DB_DBNAME, cPROP_DB_COLLECTION, cPROP_DB_USERNAME,cPROP_DB_PASSWORD, cPROP_DB_SSL};
	
	
	public static final String [] sCONF_PROPS_DB = {
							sCONF_PROP_DB_SUPPORTED,
							sCONF_PROP_DB_HOST,
							sCONF_PROP_DB_PORT,
							sCONF_PROP_DB_USER,
							sCONF_PROP_DB_PW,
							sCONF_PROP_DB_DBNAME
						};
	
	public static final String cPROP_DB_MONGO_HOST_NAME_ALLOWED = "db.mongo.invalidHostNameAllowed";
	
	public static final String sCONF_VAL_DB_SUPPORTED_NONE = "";
	
	
	// Config Properties Push GCM
	public static final String sCONF_PROP_PUSH_PROVIDER_GCM = "push.provider.gcm";
	public static final String sCONF_PROP_PUSH_PROVIDER_GCM_KEY = "push.provider.gcm.key";
	
	// Config Values Push GCM
	public static final String sCONF_VALUE_PUSH_PROVIDER_GCM = "enabled";
	public static final String sCONF_VALUE_PUSH_PROVIDER_GCM_KEY = "AIzaSyAG8cfi0coC-Rmq6cRQ-fAWwdCPq2dxwJo";
	
	
	// Config Properties Push FCM	
	public static final String sCONF_PROP_PUSH_PROVIDER_FCM = "push.provider.fcm";
	public static final String sCONF_PROP_PUSH_PROVIDER_FCM_KEY = "push.provider.fcm.key";	
	public static final String sCONF_PROP_PUSH_PROVIDER_FCM_URL = "push.provider.fcm.url";
	public static final String sCONF_PROP_PUSH_PROVIDER_FCM_PROPERTY_FILE = "push.provider.fcm.propertyFile";
	public static final String sCONF_PROP_PUSH_TOPICS_SUPPORETED = "push.provider.fcm.topics.supported";
	public static final String sCONF_PROP_PUSH_TOPIC = "push.provider.fcm.topic.";
	public static final String sCONF_PROP_PUSH_PROVIDER_FCM_APPNAME = "push.provider.fcm.appname";  // this is only used for testing, FB complains if FB instance with the same name is used
	
	
	public static final String[] sCONF_PROPS_PUSH_FCM = {
			sCONF_PROP_PUSH_PROVIDER_FCM_KEY,
			sCONF_PROP_PUSH_PROVIDER_FCM_URL, 
			sCONF_PROP_PUSH_PROVIDER_FCM_PROPERTY_FILE};
	
	// Config Values Push FCM
	public static final boolean sCONF_VALUE_PUSH_TOPICS_SUPPORTED = true;
	public static final String sCONF_VALUE_PUSH_PROVIDER_FCM = "enabled";
	public static final String sCONF_VALUE_PUSH_PROVIDER_FCM_KEY = "key=AIzaSyD3KQxhc8c-ghQRlxzFyL31weBLecLzcZk";
	
		// deployed firebase
	public static final String sCONF_VALUE_PUSH_PROVIDER_FCM_URL = "https://myshake-1086.firebaseio.com/";
	
		// test property file
	public static final String sCONF_VALUE_PUSH_PROVIDER_FCM_PROP_FILE = "/Users/romanbaumgaertner/DT/MYSHAKE/roman_fork/JUL_22/myshake/MyShakeAppServer/FcmPrototype.json";
	
	// Config Properties AMQ
	public static final String sCONF_PROP_AMQ = "interface.amq";
	public static final String sCONF_PROP_AMQ_HOST  = "interface.amq.host";
	public static final String sCONF_PROP_AMQ_PORT  = "interface.amq.port";
	public static final String sCONF_PROP_AMQ_USER  = "interface.amq.user";
	public static final String sCONF_PROP_AMQ_PW    = "interface.amq.password";
	public static final String sCONF_PROP_AMQ_TYPE  = "interface.amq.type";
	public static final String sCONF_PROP_AMQ_TOPIC = "interface.amq.topic";
	
	public static final String [] sCONF_PROPS_AMQ = {
			sCONF_PROP_AMQ_HOST, 
			sCONF_PROP_AMQ_PORT, 
			sCONF_PROP_AMQ_USER, 
			sCONF_PROP_AMQ_PW};
			//sCONF_PROP_AMQ_TOPIC};
	
	
	// Config Values AMQ
	public static final String sCONF_VAL_AMQ_HOST_DEFAULT = "127.0.0.1";
	public static final int sCONF_VAL_AMQ_PORT_DEFAULT = 61616;
	public static final String sCONF_VAL_USER_EMPTY = "";
	public static final String sCONF_VAL_PW_EMPTY = "";
	public static final String sCONF_VAL_TOPIC = "";
	
	// Config Properties EEW
	public static final String sCONF_PROP_EEW = "eew";
	public static final String sCONF_PROP_EEW_TRIGGER_MINMAG = "eew.trigger.minMagnitude";
	public static final String sCONF_PROP_EEW_QUERY_DURATION = "eew.queryDuration";
	public static final String sCONF_PROP_EEW_IN_MEM_SYNC_TIME = "eew.inMemorySyncTime";
	
	public static final String [] sCONF_PROPS_EEW = {
			sCONF_PROP_EEW_TRIGGER_MINMAG,
			sCONF_PROP_EEW_QUERY_DURATION,
			sCONF_PROP_EEW_IN_MEM_SYNC_TIME};
	
	// Config Values EEW
	public static final int sCONF_VAL_TRIGGER_EEW_MINMAG = 2;
	public static final int sCONF_VAL_EEW_TRIGGER_DURATION = 250;
	public static final int sCONF_VAL_EEW_IN_MEM_SYNC_TIME = 36000;
	
	// Earthquake Alert providers
	
	// Config  properties USGS
	public static final String sCONF_PROP_USGS = "usgs";
	public static final String sCONF_PROP_USGS_FREQUENCY = "usgs.poll.frequency";
	public static final String sCONF_PROP_USGS_DURATION = "usgs.poll.duration";
	public static final String sCONF_PROP_USGS_MINMAG = "usgs.poll.minmag";
	
	// when to clean up cache table 
	public static final String sCONF_PROP_USGS_CLEARCACHE_DURATION = "usgs.clearcache.duration";
	public static final String sCONF_PROP_USGS_CLEARCACHE_FREQUENCY = "usgs.clearcache.frequency";
	
	public static final String[] sCONF_PROPS_USGS = { 
			sCONF_PROP_USGS_FREQUENCY,
			sCONF_PROP_USGS_DURATION,
			sCONF_PROP_USGS_DURATION,
			sCONF_PROP_USGS_MINMAG,
			sCONF_PROP_USGS_CLEARCACHE_DURATION,
			sCONF_PROP_USGS_CLEARCACHE_FREQUENCY
			};
	
	// Config Values USGS
	
	public static final String sCONF_VAL_USGS = "enabled";
	// in milli seconds -> 1.5 min
	public static final int sCONF_VAL_USGS_FREQUENCY = 90000;
	
	// in min
	public static final int sCONF_VAL_USGS_DURATION = 15;
	public static final int sCONF_VAL_USGS_MINMAG = 3;
	
	// in min
	public static final int sCONF_VAL_USGS_CLEARCACHE_DURATION = 720;
	// in sec
	public static final int sCONF_VAL_USGS_CLEARCACHE_FREQUENCY = 86400;
	
	public static final String cUSGS_PROVIDER = "USGS";
	
	// hardcoded channel names
	public static final String cAMQ_TEST      = "test";
	public static final String cAMQ_TOPIC_EEW = "eew.sys.dm.data";
	public static final String cAMQ_TOPIC_EEW_PREF = "eew";
	public static final String cAMQ_TOPIC_NEWSFEED = "newsfeed";
	// runtime configuration  is pretty complex because we need to support 
	// several services like MAS, SDB, LLS and Broadcaster
	public static final String cAMQ_TOPIC_RUNTIME = "config.runtime";
	public static final String cAMQ_TOPIC_EARTHQUAKE = "earthquake";
	
	public static final String cAMQ_MAP_KEY_SERVER_LIST = "amqList";
	public static final String cAMQ_MAP_KEY_SET_TYPE    = "setType";
	
	public static  enum cAMQ_TYPES {NOT_DEFINED, EEW, NEWSFEED, EARTHQUAKE, RUNTIME, TEST};
	
	
	// intelligent Cloud 
	// JSON?MongoDb constants
	
	public static final String cIC_HDR = "hdr";
	public static final String cIC_DEVICEID = "deviceId";
	public static final String cIC_TS = "ts";
	public static final String cIC_TOKEN = "token";
	public static final String cIC_VERSION = "version";
	public static final String cIC_TYPE = "type";
	public static final String cIC_NETWORK = "network";
	
	public static final String cIC_ACC="accelerometer";
	public static final String cIC_GYR="gyroscope";
	
	public static final String cIC_X = "x";
	public static final String cIC_Y = "y";
	public static final String cIC_Z = "z";
}
