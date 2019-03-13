/* Copyright BP - Weather Tracker Application. All rights reserved. */
package com.ibm.bpwt.core.utils;

/**
 * The Class for containing all Constant variables.
 */
public final class Constants {

    /** Private Constructor */
    private Constants() {
    }

    /** Initializing the Weather API URL */
    public static final String BPWT_URL = "http://api.openweathermap.org/data/2.5/forecast";

    /** Initializing the API Key */
    public static final String BPWT_API_KEY = "697957e6cfc5e6095392fcde33b1e348";

    /** The Constant LOC for location information. */
    public static final String LOC = "loc";

    /** The Constant JSON Property for DATE */
    public static final String JSON_PROP_DATE = "dt_txt";

    /** The Constant JSON Property for JSON Main Content Node */
    public static final String JSON_PROP_MAIN = "main";

    /** The Constant JSON Property for TEMP */
    public static final String JSON_PROP_TEMP = "temp";

    /** The Constant Integer for 5 */
    public static final int INT_5 = 5;

    /** The Constant Integer for 9 */
    public static final int INT_9 = 9;

    /** The Constant Integer for 32 */
    public static final int INT_32 = 32;

}
