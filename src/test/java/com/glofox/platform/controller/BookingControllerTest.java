package com.glofox.platform.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.glofox.platform.dto.BookingRequestDto;
import com.glofox.platform.dto.ClassCreationRequestDto;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BookingControllerTest extends AbstractControllerTest {
  @Test
  public void whenValidRequestThenReturn200AndBody() {

    LocalDate currentDate = LocalDate.now();
    restTemplate.postForEntity(
        getClassesServiceUrl(),
        new ClassCreationRequestDto("Pilates", currentDate, currentDate, 1),
        List.class);

    ResponseEntity<Object> response =
        restTemplate.postForEntity(
            getBookingServiceUrl(),
            new BookingRequestDto("Guilherme", "Pilates", currentDate),
            Object.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNull(response.getBody());
  }

  @Test
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
