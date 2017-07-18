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

package com.dt.tlabs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import com.dt.tlabs.db.DbConfigurationBase;

public class Utils {
	
	/**
	 * Creates the endpoint string for a given configuration
	 * @param config     mysql configuration
	 * @param withDb     endpoint with database specified or not
	 * @return
	 */
	public static String getDbEndpoint(final DbConfigurationBase config, boolean withDb){
		
		StringBuffer endpoint = new StringBuffer("");
		
		if ( config != null){
		//TODO: add formatter instead appending	
			endpoint = new StringBuffer("jdbc:mysql://");
		
				endpoint.append(config.getHost())
				.append(":")
				.append(config.getPort());
				
				if (withDb)
					endpoint.append("/"+(config).getDbName());
					
		}
		
		return endpoint.toString();
	}
	
	/**
	 * Convenience method to convert an InputStream to a String.
	 * <p>
	 * If the stream ends in a newline character, it will be stripped.
	 * <p>
	 * If the stream is {@literal null}, returns an empty string.
	 */
	public  static String getString(InputStream stream) throws IOException {
		if (stream == null) {
			return "";
		}
		BufferedReader reader =
				new BufferedReader(new InputStreamReader(stream));
		StringBuilder content = new StringBuilder();
		String newLine;

		try {
			do {
				newLine = reader.readLine();
				if (newLine != null) {
					content.append(newLine).append('\n');
				}
			} while (newLine != null);
			if (content.length() > 0) {
				// strip last newline
				content.setLength(content.length() - 1);
			}

		} finally {
			if ( reader != null){
				reader.close();
			}

			if( stream != null){
				stream.close();
			}
		}

		return content.toString();
	}
	
	public static enum DataFormat{
		JSON("json"),
		XML("xml"),
		UNKNOWN("unknown");
		
		private String type;
		
		DataFormat(String type){
			this.type = type;
		}
		
		public String getType() {
			return type;
		}
		
	}
	
	/**
	 * Returns the data format of a received AMQ message
	 * @param payload
	 * @return
	 */
	public static DataFormat getDataFormat(String payload) {
		DataFormat type = DataFormat.UNKNOWN;
		
		if(payload != null && payload.length() > 0) {
			if (payload.startsWith("{", 0)){
				type = DataFormat.JSON;
			} else if ( (payload.startsWith("<", 0)) ) {
				type = DataFormat.XML;
			}
		}
		
		return type;
	}
	
	/**
	 * Returns the known properties of the environment
	 * @param env
	 * @return
	 */
	public static Map<String, Object> getAllKnownProperties(Environment env) {
	    Map<String, Object> rtn = new HashMap<>();
	    if (env instanceof ConfigurableEnvironment) {
	        for (PropertySource<?> propertySource : ((ConfigurableEnvironment) env).getPropertySources()) {
	            if (propertySource instanceof EnumerablePropertySource) {
	                for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
	                    rtn.put(key, propertySource.getProperty(key));
	                }
	            }
	        }
	    }
	    return rtn;
	}
	
}
