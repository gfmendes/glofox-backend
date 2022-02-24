package com.glofox.platform.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.glofox.platform.dto.BookingRequestDto;
import com.glofox.platform.entity.ClassEntity;
import com.glofox.platform.repository.ClassRepository;
import java.time.LocalDate;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookingServiceTest {

  @Autowired private ClassRepository repository;
  @Autowired private BookingService service;

  @AfterEach
  void cleanUp() {
    repository.clear();
  }

  @Test
  void whenBookingValidClassThenAddMemberToClass() {
    // Given
    repository.save(new ClassEntity("Pilates", LocalDate.of(2022, 02, 23), 1));
    BookingRequestDto bookingRequestDto =
        new BookingRequestDto("Member", "Pilates", LocalDate.of(2022, 02, 23));

    // When
    service.bookClass(bookingRequestDto);

    // Then
    ClassEntity entity = repository.findById("Pilates2022-02-23").get();
    assertTrue(entity.getMembers().contains("Member"));
  }

  @Test
  void whenBookingInvalidClassThenReturnError() {
    // Given
    repository.save(new ClassEntity("Pilates", LocalDate.of(2022, 02, 23), 1));
    BookingRequestDto bookingRequestDto =
        new BookingRequestDto("Member", "Pilates", LocalDate.of(2022, 02, 22));

    // When
    assertThrows(RuntimeException.class, () -> service.bookClass(bookingRequestDto));

  }

  @Test
  void whenBookingFullClassThenReturnError() {
    // Given
    repository.save(new ClassEntity("Pilates", LocalDate.of(2022, 02, 22), 1));
    BookingRequestDto bookingRequestDto =
        new BookingRequestDto("Member", "Pilates", LocalDate.of(2022, 02, 22));

    // When / Then
    service.bookClass(bookingRequestDto);
    assertThrows(RuntimeException.class, () -> service.bookClass(bookingRequestDto));

  }
}
