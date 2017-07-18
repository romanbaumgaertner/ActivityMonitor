package com.dt.tlabs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;


import com.dt.tlabs.app.Configuration;
import com.dt.tlabs.app.Constants;
import com.dt.tlabs.app.IntelligentCloudApplication;
import com.dt.tlabs.db.DbApplication;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = IntelligentCloudApplication.class)
@ContextConfiguration(classes = MockServletContext.class)
public class IntelligentCloudApplicationTests {
	
	public static Environment _EnvironmentSetup(){
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		Environment e = context.getEnvironment();
		
		// Mongo
		EnvironmentTestUtils.addEnvironment(context, Constants.cPROP_DB +".1" + ":" + DbApplication.DatabaseType.MONGO.getDbName());
		EnvironmentTestUtils.addEnvironment(context, Constants.cPROP_DB_HOST +".1" + ":" + "127.0.0.1");
		EnvironmentTestUtils.addEnvironment(context, Constants.cPROP_DB_PORT +".1" + ":" + 27017);
		EnvironmentTestUtils.addEnvironment(context, Constants.cPROP_DB_USERNAME +".1" + ":" + "nobody");
		EnvironmentTestUtils.addEnvironment(context, Constants.cPROP_DB_PASSWORD +".1" + ":" + "nobody");
		EnvironmentTestUtils.addEnvironment(context, Constants.cPROP_DB_DBNAME +".1" + ":" + "myshake");
		EnvironmentTestUtils.addEnvironment(context, Constants.cPROP_DB_COLLECTION +".1" + ":" + "deviceCache");
		
		EnvironmentTestUtils.addEnvironment(context, Constants.cPROP_DB_SSL +".1" + ":" + "disabled");
		EnvironmentTestUtils.addEnvironment(context, Constants.cPROP_DB_SSL_KEYSTORE +".1" + ":" + "keystore");
		EnvironmentTestUtils.addEnvironment(context, Constants.cPROP_DB_SSl_KEYPW +".1" + ":" + "keypw");
	
		return e;
	}
	
	@Test
	public void configureEnvironment(){
		
		Environment e = _EnvironmentSetup();
		
		IntelligentCloudApplication app = new IntelligentCloudApplication();
		Configuration configuration = app.readConfiguration(e);
		
		Assert.assertNotNull(configuration);
		
	}

	@Test
	public void contextLoads() {
	}

}
