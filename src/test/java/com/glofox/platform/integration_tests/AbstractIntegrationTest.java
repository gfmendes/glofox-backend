package com.glofox.platform.integration_tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AbstractIntegrationTest {

  @LocalServerPort protected int port;
  @Autowired protected TestRestTemplate restTemplate;

  protected String getClassesServiceUrl() {
    return "http://localhost:" + port + "/api/v1/classes/";
  }

  protected String getBookingServiceUrl() {
    return "http://localhost:" + port + "/api/v1/bookings/";
  }
}
