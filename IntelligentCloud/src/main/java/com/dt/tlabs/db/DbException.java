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

public class DbException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DbException(String message)
	{
		super(message);
	}

}
