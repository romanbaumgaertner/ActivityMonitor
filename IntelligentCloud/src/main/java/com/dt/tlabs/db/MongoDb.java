package com.dt.tlabs.db;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dt.tlabs.app.Configuration;
import com.dt.tlabs.app.Constants;
import com.dt.tlabs.app.IntelligentCloudApplication;
import com.dt.tlabs.utils.Logging;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.MongoWriteException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDb implements DbBase{
	
	private MongoConfiguration mongoConfig;
	private MongoClient mongoClient = null;
	private MongoDatabase db = null;

	@Override
	public DbConfigurationBase getDbConfiguration() throws DbException {
		return mongoConfig;
	}

	@Override
	public void setDbConfiguration(DbConfigurationBase dbConfig) throws DbException {
		
		if( dbConfig instanceof MongoConfiguration)
			mongoConfig = (MongoConfiguration)dbConfig;
	}

	@Override
	public DbConfigurationBase configureDb(DbConfigurationBase config) throws DbException {
		if( config != null && config instanceof MongoConfiguration){
			
			// set MongoDb configuration to true
			config.setConfigured(true);
			this.mongoConfig = (MongoConfiguration) config;
			
			connectInternal();
		} else {
			Logging.sLOGGER(MongoDb.class).warn("Not able to configure MongoDb");
			throw new DbException("Not able to configure MongoDb");
		}
		
		return config;
	}

	@Override
	public Object connect() throws DbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertSensorData(JSONObject sensorData) throws DbException {

		Document entry = new Document();

		if(sensorData != null ){
			
			try {
				
				JSONObject hdr = sensorData.getJSONObject(Constants.cIC_HDR);
				//JSONArray acc = sensorData.getJSONArray(Constants.cIC_ACC);
				String acc = sensorData.getString(Constants.cIC_ACC);
				JSONArray gyr = sensorData.getJSONArray(Constants.cIC_GYR);
				
				Logging.sLOGGER(MongoDb.class).warn(hdr.toString());
				Logging.sLOGGER(MongoDb.class).warn(acc.toString());
				Logging.sLOGGER(MongoDb.class).warn(gyr.toString());
				
				entry.append(
						Constants.cIC_HDR, new Document().
							append(Constants.cIC_DEVICEID, hdr.getString(Constants.cIC_DEVICEID) ).
							append(Constants.cIC_TS, hdr.getLong(Constants.cIC_TS) ) 
							).
					append(Constants.cIC_ACC, acc).
					append(Constants.cIC_GYR, gyr.toString().replace(Pattern.quote("\\"), ""));
				
				MongoCollection<Document> collection = db.getCollection(this.mongoConfig.getCollection() );
				collection.insertOne(entry);
			
			} catch (MongoWriteException e) {

				Logging.sLOGGER(MongoDb.class).warn(e.toString());
				throw new DbException("Mongo Write Error: not able to write heartbeat entry");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//////////////////
	
	public MongoClient getMongoClient(){
		return this.mongoClient;
	}
	
	public MongoDatabase getMongoDb(){
		return this.db;
	}
	
	
	public void connectInternal() throws DbException {

		// http://blog.carl.pro/2016/07/connecting-to-mongodb-using-ssl-but-without-locally-installed-server-ssl-certificate/
		SSLContext sslContext = null;
		boolean isSslEnabled = false;

		/* Cool hack to get around Cert-Check when using a self-signed cert
		 */
		
		Configuration configuration = IntelligentCloudApplication.getConfiguration();
		
		if (configuration != null && configuration.getTest().equals(Constants.sCONF_VAL_ENABLED)) {
			TrustManager[] trustManagers = new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String t) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String t) {
				}
			} };

			try {
				sslContext = SSLContext.getInstance("TLS");
				sslContext.init(null, trustManagers, new SecureRandom());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//END OF HACK

		if (this.mongoConfig != null) {
			
			MongoClientOptions.Builder optionBuilder  = MongoClientOptions.builder();
			
			// in case SSL/TLS enabled we set the truststore (needed for cert-check)
			if( this.mongoConfig.getSslEnabled().equals( Constants.sCONF_VAL_ENABLED)){

				// if we have a KS defined
				if( !this.mongoConfig.getSslKeystore().equals( Constants.VAL_SSL_NO_KS) ){
					Logging.sLOGGER(MongoDb.class).debug(" with KS ");
					System.setProperty("javax.net.ssl.trustStore", this.mongoConfig.getSslKeystore() );
					System.setProperty("javax.net.ssl.trustStorePassword", this.mongoConfig.getSslPw() );
				} 

				Logging.sLOGGER(MongoDb.class).debug("MongoDb - client connection with SSL");

				boolean invalidHostNameAllowed = ((MongoConfiguration) this.mongoConfig).isInvalidHostNameAllowed();
				
				isSslEnabled = true;
				optionBuilder.sslEnabled(isSslEnabled).sslInvalidHostNameAllowed(invalidHostNameAllowed);
				
				// only in test mode 
				if( IntelligentCloudApplication.getConfiguration().getTest().equals( Constants.sCONF_VAL_ENABLED) ){
					// use the socketFactory for the Custom TrustManager changes
					optionBuilder.sslEnabled(true).sslInvalidHostNameAllowed(invalidHostNameAllowed).socketFactory(sslContext.getSocketFactory());
				}
			} else {
				Logging.sLOGGER(MongoDb.class).debug(" client connection without SSL");
			}

			List<ServerAddress> seeds = new ArrayList<>();
			seeds.add(new ServerAddress(this.mongoConfig.getHost(), this.mongoConfig.getPort()));

			MongoClientOptions o = optionBuilder.build();
			MongoCredential credential = MongoCredential.createCredential(this.mongoConfig.getUser(), this.mongoConfig.getDbName() ,this.mongoConfig.getPw().toCharArray() );

			mongoClient = new MongoClient(seeds, Arrays.asList(credential), o);
			db = mongoClient.getDatabase(this.mongoConfig.getDbName());
		} else {
			Logging.sLOGGER(MongoDb.class).warn("connect - config data not valid");
			throw new DbException("MongoDb - configuration data not valid");
		}
		
	}


}
