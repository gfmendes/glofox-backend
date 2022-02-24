package com.glofox.platform.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.glofox.platform.dto.ClassCreationRequestDto;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ClassesControllerTest extends AbstractControllerTest {

  @Test
  void whenValidRequestThenReturn200AndBody() {
    ResponseEntity<List> response =
        restTemplate.postForEntity(
            getClassesServiceUrl(),
            new ClassCreationRequestDto("Pilates", LocalDate.now(), LocalDate.now(), 2),
            List.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  void whenInvalidRequestThenReturn400AndBody() {
    ResponseEntity response =
        restTemplate.postForEntity(
            getClassesServiceUrl(),
            new ClassCreationRequestDto("Pilates", null, LocalDate.now(), 2),
            Object.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
  }

}
