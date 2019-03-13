/* Copyright BP - Weather Tracker Application. All rights reserved. */
package com.ibm.bpwt.core.dao;

/**
 * Model for BP Weather Tracker Data Object
 */
public class WeatherInfoDAO {

    /** Property Weather Date */
    private String date;

    /** Property Weather Temperature */
    private String temp;

    public final String getDate() {
        return date;
    }

    public final void setDate(final String dateParam) {
        this.date = dateParam;
    }

    public final String getTemp() {
        return temp;
    }

    public final void setTemp(final String tempParam) {
        this.temp = tempParam;
    }
}
