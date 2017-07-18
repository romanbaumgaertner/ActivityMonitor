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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// ...

public class Logging {

	public static Logger sLOGGER(final  Class<?> customClass) {

		Logger logger = LoggerFactory.getLogger(customClass);

		return logger;
	}

}