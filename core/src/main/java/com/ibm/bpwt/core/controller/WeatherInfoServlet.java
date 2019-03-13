/* Copyright BP - Weather Tracker Application. All rights reserved. */
package com.ibm.bpwt.core.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.bpwt.core.bao.WeatherProcessorBAO;
import com.ibm.bpwt.core.dao.WeatherInfoDAO;
import com.ibm.bpwt.core.utils.Constants;

/**
 * This Servlet fetches weather information from REST API as JSON for display on
 * BP Weather Information page.
 */
@Component(service = Servlet.class,
    property = {
    org.osgi.framework.Constants.SERVICE_DESCRIPTION + "=BP Weather Information Servlet",
    "sling.servlet.methods=" + HttpConstants.METHOD_GET,
    "sling.servlet.resourceTypes=" + "cq/Page",
    "sling.servlet.paths=" + "/bin/weatherinfo"
    }
)
public class WeatherInfoServlet extends SlingAllMethodsServlet {

    /** Serialization ID for Servlet */
    private static final long serialVersionUID = 2L;

    /** Initializing the BP WT Logger */
    private static final Logger BPWT_LOGGER = LoggerFactory.getLogger(WeatherInfoServlet.class);

   /**
    * Location reference Injected through Constructor, from the corresponding JUnit Test Class.
    */
    private transient String testLocationParam;

   /**
    * Initializing Business Object (Logic Implementation, Processing)
    */
    private transient WeatherProcessorBAO weatherProcessor = new WeatherProcessorBAO();

    /** Default COnstructor */
    public WeatherInfoServlet() {
    }
    
   /**
    * DI Technique - Constructor-Based Dependency Injection
    * @param testLocation String
    */
    public WeatherInfoServlet(final String testLocation) {
        this.testLocationParam = testLocation;
    }

    @Override
    public final void doGet(
            final SlingHttpServletRequest request,
            final SlingHttpServletResponse response)
            throws ServletException, IOException {

        String location = null;
        BPWT_LOGGER.info("BP Weather Information Servlet");

        if (null == request.getParameter("loc")) {
            location = this.testLocationParam;
        } else {
            location = request.getParameter("loc").trim();
        }

        // Get Weather Information data from External Source via API.
        List<WeatherInfoDAO> weatherInfoData = getWeatherInfo(location);

        // Post Processing of data encapsulated into POJO Data Object
        JSONObject weatherJsonData = weatherProcessor.processWeatherInfo(weatherInfoData);

        // Output JSONArray encapsulated in Data Model to UI
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(weatherJsonData.toString());
    }


   /**
    * This method fetches Weather API as JSON format data
    * and is responsible for the post-processing of data to feed to the UI Model.
    * @param location          String
    * @return weatherInfoList  List<WeatherInfo>
    */
    private List<WeatherInfoDAO> getWeatherInfo(final String location) {

        // Formulate the API URL
        // OpenWeather has been subscribed to get 5 days 3 hrs weather forecast.
        String weatherApiUrl = Constants.BPWT_URL
                             + "?q=" + location
                             + "&APPID=" + Constants.BPWT_API_KEY;

        List<WeatherInfoDAO> weatherInfoList = new ArrayList<>();

        URL weatherInfoApiUrl = null;
        try {
            weatherInfoApiUrl = new URL(weatherApiUrl);
        } catch (MalformedURLException me) {
            BPWT_LOGGER.error("JSON Malformed URL Exception: Method getWeatherInfo(): ", me);
        }

        if (null != weatherInfoApiUrl) {
            try (

                // Process JSON data from Weather Info REST API
                InputStream openStream = weatherInfoApiUrl.openStream();
                BufferedReader bufferedReader = new BufferedReader(
                                                new InputStreamReader(openStream, StandardCharsets.UTF_8))) {
                StringBuilder infoBuilder     = new StringBuilder();
                String reader                 = null;

                while ((reader = bufferedReader.readLine()) != null) {
                    infoBuilder.append(reader);
                }

                JSONObject weatherJson  =  new JSONObject(infoBuilder.toString());

                // Feed JSON data to the Data Model
                JSONArray weatherJsonArray = weatherJson.getJSONArray("list");

                for (int i = 0; i < weatherJsonArray.length(); i++) {
                    WeatherInfoDAO weatherInfo = new WeatherInfoDAO();
                    JSONObject eachDayWeatherEntryJson = (JSONObject) weatherJsonArray.get(i);
                    JSONObject mainJSONEntry = eachDayWeatherEntryJson.getJSONObject(Constants.JSON_PROP_MAIN);

                    weatherInfo.setDate(eachDayWeatherEntryJson.getString(Constants.JSON_PROP_DATE));
                    weatherInfo.setTemp(mainJSONEntry.get(Constants.JSON_PROP_TEMP).toString());
                    weatherInfoList.add(weatherInfo);
                }

            } catch (IOException ioex) {
                BPWT_LOGGER.error("IOException: Method getWeatherInfo(): ", ioex);

            } catch (JSONException jsonEx) {
                BPWT_LOGGER.error("JSONException: Method getWeatherInfo(): ", jsonEx);
            }
        }

        return weatherInfoList;
    }
}
