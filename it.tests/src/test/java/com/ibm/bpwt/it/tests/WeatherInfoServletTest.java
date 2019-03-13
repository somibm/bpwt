/* Copyright BP - Weather Tracker Application. All rights reserved. */
package com.ibm.bpwt.it.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ibm.bpwt.core.controller.WeatherInfoServlet;

import io.wcm.testing.mock.aem.junit5.AemContext;

/**
 * The JUnit5 test class and Code Coverage for BP Weather Info Servlet.
 */
@ExtendWith(MockitoExtension.class)
public class WeatherInfoServletTest {

    /**
     * The Weather Info Servlet.
     */
    private WeatherInfoServlet weatherInfoServlet;

    /**
     * Initializing JUnit5 AEM Mock
     * Options: Blank Default Constructor, RESOURCERESOLVER_MOCK, JCR_MOCK, JCR_OAK
     */
    private AemContext context = new AemContext();

    /**
     * Sling Request
     */
    private SlingHttpServletRequest request;

    /**
     * Sling Response
     */
    private SlingHttpServletResponse response;

    /**
     * Preparation for running the check logout Test
     */
    @BeforeEach
    public void init() {

        // AEM MOCK Request and Response Objects
        request             = context.request();
        response            = context.response();

        // Initializing the Servlet with Location Parameter
        weatherInfoServlet  = new WeatherInfoServlet("London");
    }

    /**
     * Test Logout feature.
     * @throws ServletException the Servlet exception.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void getWeatherInfo() throws ServletException, IOException {

        // Trigger Servlet GET Method.
        weatherInfoServlet.doGet(request, response);

        // JUnit5 Test that Response is NOT Empty.
        assertNotNull(response.getWriter());
    }
}
