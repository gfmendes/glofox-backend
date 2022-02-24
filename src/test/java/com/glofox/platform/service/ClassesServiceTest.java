package com.glofox.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.glofox.platform.dto.ClassCreationRequestDto;
import com.glofox.platform.dto.ClassCreationResponseDto;
import com.glofox.platform.entity.ClassEntity;
import com.glofox.platform.exception.InvalidDateIntervalException;
import com.glofox.platform.repository.ClassRepository;
import com.glofox.platform.service.enumeration.ClassCreationStatus;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClassesServiceTest {

  @Autowired private ClassRepository repository;
  @Autowired private ClassesService service;

  @AfterEach
  void cleanUp() {
    repository.clear();
  }

  @Test
  void whenNoConflictsThenCreateAllClasses() {

    // Given
    final ClassCreationRequestDto requestDto = buildBaseRequestDto();

    // When
    var result = service.createClasses(requestDto);

    // Then
    assertEquals(7, result.size());
    for (ClassCreationResponseDto responseDto : result) {
      assertEquals(requestDto.getName(), responseDto.getName());
      assertEquals(ClassCreationStatus.SUCCESS, responseDto.getStatus());
    }

    assertEquals(7, repository.getAll().size());
    LocalDate date = requestDto.getStartDate();
    for (ClassEntity c : repository.getAll()) {
      assertEquals(requestDto.getName(), c.getName());
      assertEquals(requestDto.getCapacity(), c.getCapacity());
      assertEquals(date, c.getDate());
      date = date.plusDays(1);
    }
  }

  @Test
  void whenConflictsThenPersistValidClasses() {

    // Given
    final ClassCreationRequestDto baseDto = buildBaseRequestDto();
    service.createClasses(baseDto); // 7 classes
    final Integer countCurrent = repository.getAll().size();

    // 1 day before and 1 day after existing classes
    final ClassCreationRequestDto dto =
        buildRequestDto("Pilates", LocalDate.of(2022, 02, 21), LocalDate.of(2022, 03, 01), 5);

    // When
    var result = service.createClasses(dto);

    // Then
    Long countConflicted =
        result.stream().filter(c -> c.getStatus() == ClassCreationStatus.CONFLICT).count();
    Long countSuccess =
        result.stream().filter(c -> c.getStatus() == ClassCreationStatus.SUCCESS).count();
    assertEquals(9, result.size());
    assertEquals(7, countConflicted);
    assertEquals(2, countSuccess);
    assertEquals(countCurrent + countSuccess, repository.getAll().size());
  }

  @Test
  void whenInvalidDateIntervalThenThrowsError() {

    final ClassCreationRequestDto dto =
        buildRequestDto("Pilates", LocalDate.of(2022, 03, 01), LocalDate.of(2022, 02, 23), 5);

    // When
    assertThrows(InvalidDateIntervalException.class, () -> service.createClasses(dto));
    assertEquals(0, repository.getAll().size());
  }

  private ClassCreationRequestDto buildBaseRequestDto() {
    final String className = "Pilates";
    final LocalDate startDate = LocalDate.of(2022, 02, 22);
    final LocalDate endDate = LocalDate.of(2022, 02, 28);
    final Integer capacity = 5;
    return buildRequestDto(className, startDate, endDate, capacity);
  }

  private ClassCreationRequestDto buildRequestDto(
      String className, LocalDate startDate, LocalDate endDate, Integer capacity) {
    final ClassCreationRequestDto dto = new ClassCreationRequestDto();
    dto.setName(className);
    dto.setStartDate(startDate);
    dto.setEndDate(endDate);
    dto.setCapacity(capacity);
    return dto;
  }
}
