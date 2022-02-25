package com.glofox.platform.integration_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.glofox.platform.dto.ClassCreationRequestDto;
import com.glofox.platform.dto.ClassCreationResponseDto;
import com.glofox.platform.service.enumeration.ClassCreationStatus;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@TestMethodOrder(OrderAnnotation.class)
class ClassesIntegrationTest extends AbstractIntegrationTest {

  @Test
  @Order(1)
  void whenAddingNewClassesThenCreateAllClasses() {
    ResponseEntity<ClassCreationResponseDto[]> response =
        restTemplate.postForEntity(
            getClassesServiceUrl(),
            new ClassCreationRequestDto("Spinning", LocalDate.now(), LocalDate.now(), 2),
            ClassCreationResponseDto[].class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, response.getBody().length);
    assertEquals(ClassCreationStatus.SUCCESS, response.getBody()[0].getStatus());
  }

  @Test
  @Order(2)
  void whenAddingExistingClassesThenConflictExistingClasses() {
    ResponseEntity<ClassCreationResponseDto[]> response =
        restTemplate.postForEntity(
            getClassesServiceUrl(),
            new ClassCreationRequestDto(
                "Spinning", LocalDate.now(), LocalDate.now().plusDays(1), 2),
            ClassCreationResponseDto[].class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().length);
    assertEquals(ClassCreationStatus.CONFLICT, response.getBody()[0].getStatus());
    assertEquals(ClassCreationStatus.SUCCESS, response.getBody()[1].getStatus());
  }

  @Test
  @Order(3)
  void whenInvalidRequestThenReturn400AndBody() {
    ResponseEntity<Map> response =
        restTemplate.postForEntity(
            getClassesServiceUrl(),
            new ClassCreationRequestDto("Spinning", null, LocalDate.now(), 2),
            Map.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  @Order(4)
  void whenInvalidDateIntervalThenReturn400AndBody() {
    ResponseEntity<Map> response =
        restTemplate.postForEntity(
            getClassesServiceUrl(),
            new ClassCreationRequestDto(
                "Spinning", LocalDate.now().plusDays(1), LocalDate.now(), 2),
            Map.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
  }
}
