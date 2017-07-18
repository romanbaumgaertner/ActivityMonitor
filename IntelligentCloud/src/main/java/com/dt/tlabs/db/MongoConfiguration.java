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

public class MongoConfiguration extends DbConfigurationBase {
	
	private String collection;
	private boolean invalidHostNameAllowed;

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public boolean isInvalidHostNameAllowed() {
		return invalidHostNameAllowed;
	}

	public void setInvalidHostNameAllowed(boolean hostNameAllowed) {
		this.invalidHostNameAllowed = hostNameAllowed;
	}

}
