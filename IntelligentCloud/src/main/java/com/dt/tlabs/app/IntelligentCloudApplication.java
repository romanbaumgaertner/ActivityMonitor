package com.dt.tlabs.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dt.tlabs.db.DbApplication;
import com.dt.tlabs.db.DbBase;
import com.dt.tlabs.db.DbConfigurationBase;
import com.dt.tlabs.db.DbException;
import com.dt.tlabs.db.MongoConfiguration;
import com.dt.tlabs.db.MongoDb;
import com.dt.tlabs.utils.Logging;
import com.dt.tlabs.utils.Utils;
import com.mongodb.client.MongoDatabase;

@SpringBootApplication
public class IntelligentCloudApplication {

	private static DbApplication dbApplication;

	private static Configuration sConfiguration;
	private static List<DbBase> sListDb;
	public  List<DbBase> getListDb(){
		return sListDb;
	}
	public void setListDb(List<DbBase> listDb){
		sListDb = listDb;
	}

	public static void main(String[] args) {
		SpringApplication.run(IntelligentCloudApplication.class, args);
	}

	// that other components can access configuration properties
	/**
	 * Gets the configuration properties
	 * 
	 * @return configuration properties
	 */
	public static Configuration getConfiguration(){
		return sConfiguration;
	}

	/**
	 * Sets the configuration
	 * @param configuration
	 */
	public  void setConfiguration(final Configuration configuration){
		sConfiguration = configuration;
	}

	// main entry point of Spring Boot application
	@Autowired
	public boolean setEnvironmentAndConfigure(Environment e) {
		boolean isConfigured = false;

		Configuration configuration = readConfiguration(e);
		this.setConfiguration(configuration);

		// configure the supported DB
		isConfigured = configureDb(configuration.getDatabase());

		return isConfigured;
	}


	/////
	// helper function for setup and configuration
	/////
	public Configuration readConfiguration(final Environment e){

		Configuration configuration =  null;

		if( e != null){

			configuration = Configuration.getInstance();

			if(e.containsProperty(Constants.sCONF_PROP_TEST)){
				configuration.setTest( e.getProperty(Constants.sCONF_PROP_TEST, Constants.sCONF_VAL_DISABLED ));
			} else {
				configuration.setTest(Constants.sCONF_VAL_DISABLED);
			}			

			// loop through DB attributes and create dbList
			ArrayList<DbConfigurationBase> dbList = getDbConfiguration(e);

			if( dbList != null && dbList.size() > 0)
				configuration.setDatabase(dbList);
		}

		return configuration;
	}

	/**
	 * Reads the configuration file and sets the DB attributes
	 * @param env
	 */
	public ArrayList<DbConfigurationBase> getDbConfiguration(final Environment env) {

		ArrayList<DbConfigurationBase> dataBaseList = new ArrayList<DbConfigurationBase>();
		// create instance
		//BroadcasterApplication.getConfiguration().setDatabase(new ArrayList<ConfigurationBase>());

		if (env != null) {

			try {
				// getting the supported DBs
				//Comparator<String> byName =
				//		(String o1, String o2)->o1.getName().compareTo(o2.getName());
				List<String> supportedDb = getSupportedDb(env);

				// sort the list
				supportedDb.sort(null);
				DbConfigurationBase configuration = null;

				// filling supported DBs with properties from config file
				for (int ii = 0; ii < supportedDb.size(); ii++) {

					DbApplication.DatabaseType dbType = DbApplication.DatabaseType.NDF;

					if ( env.getProperty(supportedDb.get(ii)).equals(DbApplication.DatabaseType.MONGO.getDbName()) ) {
						dbType = DbApplication.DatabaseType.MONGO;
					} else if( env.getProperty(supportedDb.get(ii)).equals(DbApplication.DatabaseType.MYSQL.getDbName()) ){
						dbType = DbApplication.DatabaseType.MYSQL;
					}	

					// setting DB attributes and a configuration instance
					configuration = this.setDbConfigurationAttributes(ii, env, dbType);

					// add configuration instance to supported list of DBs
					if( configuration != null){

						dataBaseList.add(configuration);

					}
				}

			} catch (IOException e) {
				e.printStackTrace();
				Logging.sLOGGER(IntelligentCloudApplication.class)
				.info("Problem with config file. DB properties not valid");
			}
		}

		return dataBaseList;

	}

	/**
	 * Gets the list of db attributes (db.1, ..., dbN)
	 * 
	 * The property list is used to set all the DB attributes
	 * 
	 * @param prop
	 * @return
	 */
	public List<String> getSupportedDb(final Environment  env) {
		List<String> dbs = null;

		Map<String, Object> map = Utils.getAllKnownProperties(env);

		Pattern pattern = Pattern.compile("db.[1-9]");
		if (env != null) {

			dbs = new ArrayList<String>();

			for (String value : map.keySet()) {
				Matcher matcher = pattern.matcher(value);

				// if we find a matching pattern add to topic list
				if (matcher.find()) {
					dbs.add(value);
				}
			}
		}

		return dbs;
	}

	/**
	 * Sets all DB attributes.
	 * 
	 * @param index
	 * @param prop
	 * @return
	 * @throws IOException
	 */
	public DbConfigurationBase setDbConfigurationAttributes(
			int index,
			Environment env,
			DbApplication.DatabaseType dbType) throws IOException{

		DbConfigurationBase configuration = null;
		boolean invalidHostNameAllowed = false;

		if( env != null && index >= 0){

			if( dbType.getDbName().equals(DbApplication.DatabaseType.MONGO.getDbName() )){
				configuration = new MongoConfiguration();

				configuration.setDbType(DbApplication.DatabaseType.MONGO );

			}else if( dbType.getDbName().equals(DbApplication.DatabaseType.MYSQL.getDbName() )){
				// not needed
			}

			for(String attrPref: Constants.sCONF_DB_PROPS){
				String attr = attrPref + "." + (index+1);
				String value = env.getProperty(attr);

				if( attr.contains(Constants.cPROP_DB_DBNAME))
					configuration.setDbName( value);
				else if ( attr.contains(Constants.cPROP_DB_DBNAME))
					configuration.setDbName( value);
				else if ( attr.contains(Constants.cPROP_DB_HOST))
					configuration.setHost( value);
				else if ( attr.contains(Constants.cPROP_DB_PORT)){
					String strPort = value;
					configuration.setPort( Integer.parseInt(strPort));
				} else if ( attr.contains(Constants.cPROP_DB_USERNAME))
					configuration.setUser( value);
				else if ( attr.contains(Constants.cPROP_DB_PASSWORD))
					configuration.setPw(value);
				else if( attr.contains(Constants.cPROP_DB_COLLECTION) && env.containsProperty( attr ))
					((MongoConfiguration)configuration).setCollection(value); 
				else if ( attr.contains(Constants.cPROP_DB_SSL)){

					if( value.equals(Constants.sCONF_VAL_ENABLED)){
						configuration.setSslEnabled(Constants.sCONF_VAL_ENABLED);

						String keystore = Constants.VAL_SSL_NO_KS;
						String pw = Constants.VAL_SSL_NO_KS_PW;

						if( env.containsProperty( Constants.cPROP_DB_SSL_KEYSTORE + "." + (index+1))){
							keystore = env.getProperty( Constants.cPROP_DB_SSL_KEYSTORE + "." + (index+1));
						} 

						if( env.containsProperty( Constants.cPROP_DB_SSl_KEYPW + "." + (index+1))){
							pw = env.getProperty( Constants.cPROP_DB_SSl_KEYPW + "." + (index+1));
						} 

						configuration.setSslKeystore( keystore );	
						configuration.setSslPw( pw);

						if( env.containsProperty(Constants.cPROP_DB_MONGO_HOST_NAME_ALLOWED )){

							Object isAllowed = env.getProperty(Constants.cPROP_DB_MONGO_HOST_NAME_ALLOWED);
							if ((isAllowed instanceof String)
									&& (((String) isAllowed).equals("true") || ((String) isAllowed).equals("false"))) {

								if (((String) isAllowed).equals("true"))
									invalidHostNameAllowed = true;
								else if (((String) isAllowed).equals("false")) {
									invalidHostNameAllowed = false;
								}
							}
						}

					} else {
						configuration.setSslEnabled(Constants.sCONF_VAL_DISABLED);
					}

				}

			}

		}

		return configuration;
	}

	/**
	 * Configures the databases in configure
	 * 
	 * The configuration status for each individual DB can be found in attribute
	 * isConfigured.
	 * 
	 * @param configuration
	 */
	public boolean configureDb(final List<DbConfigurationBase> listConfigBase){
		boolean isConfigured = false;

		// get a dbApplication instance
		dbApplication = new DbApplication(listConfigBase);
		boolean isExecuted = false;
		try {
			isExecuted = dbApplication.configureDb();

			// check whether general config is successfull and all needed DBs are configured
			if( isExecuted ) {

				for( DbBase dbBase: dbApplication.getDbList() ){
					dbBase.getDbConfiguration().isConfigured();

					this.setListDb(dbApplication.getDbList());
					isConfigured = true;
				}

			}
		} catch (DbException e) {
			e.printStackTrace();

		}	

		return isConfigured;
	}

	// TODO: extend REST Controller with management APIs
	@RestController
	@EnableConfigurationProperties()
	class ProjectNameRestController {

		@Value("${intelligentCloud.configuration.projectName}")
		private String projectName;

		@RequestMapping("/")
		String projectName() {
			return this.projectName;
		}

		@RequestMapping(method = RequestMethod.PUT, value = "/data")
		ResponseEntity<?> data(@RequestBody String data) {


			JSONObject dataJson;
			try {
				dataJson = new JSONObject(data);

				List<DbBase> dbList = getListDb();
				if( dbList != null){
					if( dbList.get(0) instanceof MongoDb){
						MongoDb db = ((MongoDb) dbList.get(0));

						if( db != null){
							if ( db.getMongoDb() == null){
								Logging.sLOGGER(IntelligentCloudApplication.class).warn("Problem with MongodDb");
							} else {
								db.insertSensorData(dataJson);
							}
						}

					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ResponseEntity<String> responseEntity = new ResponseEntity<String>("added",  HttpStatus.OK); 

			return responseEntity;
		}

		@RequestMapping(method = RequestMethod.GET, value = "/network/version")
		String networkVersion() {
			return "version";
		}

		@RequestMapping(value = "/network/last", method = RequestMethod.GET)
		@ResponseBody
		FileSystemResource  networkLast() {
			
			FileSystemResource fsr = getFileSystemResource("last");

			ResponseEntity<String> responseEntity = new ResponseEntity<String>(getNetworkResponse(),  HttpStatus.OK); 
			return fsr;
		}

		@RequestMapping(value = "/network/{file_name}", method = RequestMethod.GET)
		@ResponseBody
		public FileSystemResource getFile(@PathVariable("file_name") String fileName) {

			FileSystemResource fsr = getFileSystemResource(fileName);

			return fsr;

		}
	}

	///////////
	// Helper
	//////////
	public String getNetworkResponse(){

		JSONObject response = new JSONObject();
		JSONObject hdrContents = new JSONObject();
		try {
			hdrContents.put(Constants.cIC_VERSION, "1");
			hdrContents.put(Constants.cIC_TYPE, "1");

			response.put(Constants.cIC_HDR, hdrContents);
			response.put(Constants.cIC_NETWORK, Constants.cIC_NETWORK);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response.toString();
	}
	
	public FileSystemResource getFileSystemResource(final String name){
		String fileSystemResourcePath = "NWS/"+name;
		FileSystemResource fileSystemResource = new FileSystemResource(fileSystemResourcePath);     

		if (! fileSystemResource.exists()) {
			Logging.sLOGGER(IntelligentCloudApplication.class).debug("Got file");
		}
		
		return fileSystemResource;
	}


}
