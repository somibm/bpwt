/* Copyright BP - Weather Tracker Application. All rights reserved. */
package com.ibm.bpwt.core.bao;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.bpwt.core.dao.WeatherInfoDAO;
import com.ibm.bpwt.core.utils.Constants;

/**
 * Processor for BP Weather Tracker
 */
public class WeatherProcessorBAO {

    /** Initializing the BP WT Logger */
    private static final Logger BPWT_LOGGER = LoggerFactory.getLogger(WeatherProcessorBAO.class);

    /**
     * This method processes fetched Weather Information and prepares it for UI to render it.
     * @param  weatherInfo         List<WeatherInfoDAO>
     * @return weatherDataJsonObj  JSONObject
     */
    public JSONObject processWeatherInfo(final List<WeatherInfoDAO> weatherInfo) {

        // Perform all data transformations here, based on source data and send to UI
        JSONObject weatherDataJsonObj = new JSONObject();
        JSONArray processedWeatherJson = new JSONArray();

        try {
            // Extract and Process data model to produce JSON for UI Display.
            for (int i = 0; i < weatherInfo.size(); i++) {
                WeatherInfoDAO eachDayWeatherInfo = weatherInfo.get(i);

                Double tempFahrenheit    = Double.parseDouble(eachDayWeatherInfo.getTemp());
                String tempFahrenheitStr = String.format("%.1f", tempFahrenheit);

                // Conversion Formula: C/5 = (F - 32)/9
                Double tempCelsius = ((tempFahrenheit - Constants.INT_32) * Constants.INT_5) / Constants.INT_9;
                String tempCelsiusStr = String.format("%.1f", tempCelsius);

                JSONObject eachDayWeatherEntryJson = new JSONObject();
                eachDayWeatherEntryJson.put("bp_wt_date", eachDayWeatherInfo.getDate());
                eachDayWeatherEntryJson.put("bp_wt_temp_c", tempCelsiusStr);
                eachDayWeatherEntryJson.put("bp_wt_temp_f", tempFahrenheitStr);

                processedWeatherJson.put(eachDayWeatherEntryJson);
                weatherDataJsonObj.put("weather", processedWeatherJson);
            }

        } catch (JSONException jsonEx) {
            BPWT_LOGGER.error("JSONException: Method processWeatherInfo(): ", jsonEx);
        }

        return weatherDataJsonObj;
    }
}
