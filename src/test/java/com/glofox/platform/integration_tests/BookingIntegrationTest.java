package com.glofox.platform.integration_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.glofox.platform.dto.BookingRequestDto;
import com.glofox.platform.dto.ClassCreationRequestDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@TestMethodOrder(OrderAnnotation.class)
public class BookingIntegrationTest extends AbstractIntegrationTest {
  @Test
  @Order(1)
  public void whenBookingValidClassThenReturnOK() {

    LocalDate currentDate = LocalDate.now();
    restTemplate.postForEntity(
        getClassesServiceUrl(),
        new ClassCreationRequestDto("Zoomba", currentDate, currentDate, 2),
        List.class);

    ResponseEntity<Map> response =
        restTemplate.postForEntity(
            getBookingServiceUrl(),
            new BookingRequestDto("Guilherme", "Zoomba", currentDate),
            Map.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNull(response.getBody());
  }

  @Test
  @Order(2)
  public void whenSameMemberBookingSameClassThenReturn400() {
    LocalDate currentDate = LocalDate.now();
    ResponseEntity<Map> response =
        restTemplate.postForEntity(
            getBookingServiceUrl(),
            new BookingRequestDto("Guilherme", "Zoomba", currentDate),
            Map.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  @Order(3)
  public void whenBookingFullClassThenReturn400() {
    LocalDate currentDate = LocalDate.now();

    restTemplate.postForEntity(
        getBookingServiceUrl(), new BookingRequestDto("Mendes", "Zoomba", currentDate), Map.class);

    ResponseEntity<Map> response =
        restTemplate.postForEntity(
            getBookingServiceUrl(),
            new BookingRequestDto("LateGuy", "Zoomba", currentDate),
            Map.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  @Order(4)
  public void whenBookingNotExistingClassThenReturn404() {

    LocalDate currentDate = LocalDate.now();

    ResponseEntity<Map> response =
        restTemplate.postForEntity(
            getBookingServiceUrl(),
            new BookingRequestDto("Guilherme", "Roomba", currentDate),
            Map.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  @Order(5)
  public void whenInvalidRequestThenReturn400AndBody() {

    ResponseEntity<String> response =
        restTemplate.postForEntity(
            getBookingServiceUrl(),
            new BookingRequestDto("Guilherme", null, LocalDate.now()),
            String.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
  }
}
