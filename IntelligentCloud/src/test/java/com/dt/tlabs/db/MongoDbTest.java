package com.dt.tlabs.db;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.dt.tlabs.app.Configuration;
import com.dt.tlabs.app.Constants;
import com.dt.tlabs.app.IntelligentCloudApplication;
import com.dt.tlabs.db.DbApplication.DatabaseType;

public class MongoDbTest {
	
	public static MongoConfiguration _getDbConfiguration(){
		MongoConfiguration config = new MongoConfiguration();
		
		config.setCollection("walking");
		config.setConfigured(true);
		config.setDbName("intelligentCloud");
		config.setDbType(DatabaseType.MONGO);
		config.setHost("127.0.0.1");
		config.setPort(27017);
		config.setUser("ic");
		config.setPw("ic!2017");
		config.setSslEnabled(Constants.sCONF_VAL_DISABLED);
		
		return config;
	}
	
	@Test
	public void testConfigureDb(){
		
		IntelligentCloudApplication icApp = new IntelligentCloudApplication();
		Configuration configuration = Configuration.getInstance();
		configuration.setTest(Constants.sCONF_VAL_DISABLED);
		icApp.setConfiguration(configuration);
		
		MongoDb db = new MongoDb();
		try {
			DbConfigurationBase configDbBase= db.configureDb(_getDbConfiguration());
			
			Assert.assertNotNull(configDbBase);
			Assert.assertNotNull(db.getMongoClient() );
			Assert.assertNotNull(db.getMongoDb());
			
		} catch (DbException e) {
			e.printStackTrace();
			Assert.fail("Problem with configuration");
		}
		
	}
	
	@Test
	public void testInsertSensorData(){
		
		// test data
		
		String acc = "[{\"ts\":123123123, \"x\":234, \"y\":234, \"z\":234},"
				+ "{\"ts\":123123123, \"x\":234, \"y\":234, \"z\":234},"
				+ "{\"ts\":123123123, \"x\":234, \"y\":234, \"z\":234}"
				+ "]}";
		
		String gyr = "[{\"ts\":123123123, \"x\":234, \"y\":234, \"z\":234},"
				+ "{\"ts\":123123123, \"x\":234, \"y\":234, \"z\":234},"
				+ "{\"ts\":123123123, \"x\":234, \"y\":234, \"z\":234}"
				+ "]}";
		
		JSONObject hdrObj = new JSONObject();
		JSONObject sensorDataObj = new JSONObject();
		
		
		try {
			hdrObj.put(Constants.cIC_DEVICEID, "aDeviceId");
			hdrObj.put(Constants.cIC_TS, System.currentTimeMillis());
			
			JSONArray accObj = new JSONArray(acc);
			JSONArray gyrObj = new JSONArray(gyr);
			
			sensorDataObj.put(Constants.cIC_ACC, accObj);
			
			sensorDataObj.put(Constants.cIC_GYR, gyrObj);
			
			sensorDataObj.put("hdr", hdrObj);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		IntelligentCloudApplication icApp = new IntelligentCloudApplication();
		Configuration configuration = Configuration.getInstance();
		configuration.setTest(Constants.sCONF_VAL_DISABLED);
		icApp.setConfiguration(configuration);
		
		MongoDb db = new MongoDb();
		try {
			DbConfigurationBase configDbBase= db.configureDb(_getDbConfiguration());
			
			if (configDbBase == null)
				Assert.fail("DataBase not correctly configured");
			
			db.insertSensorData(sensorDataObj);
			
		} catch (DbException e) {
			e.printStackTrace();
			Assert.fail("Problem with configuration");
		}	
	}

}
