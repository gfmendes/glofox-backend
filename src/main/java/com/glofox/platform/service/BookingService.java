package com.glofox.platform.service;

import com.glofox.platform.dto.BookingRequestDto;
import com.glofox.platform.entity.ClassEntity;
import com.glofox.platform.exception.ClassDoesNotExistsException;
import com.glofox.platform.exception.ClassIsFullException;
import com.glofox.platform.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

  private final ClassRepository classRepository;

  public void bookClass(BookingRequestDto bookingRequestDto) {
    final var classId = buildClassId(bookingRequestDto);
    final ClassEntity classEntity =
        classRepository
            .findById(classId)
            .orElseThrow(
                () ->
                    new ClassDoesNotExistsException(
                        String.format("Class %s does not exists.", classId)));

    if (classEntity.isSlotAvailable()) {
      classEntity.addMember(bookingRequestDto.getMemberName());
    } else {
      throw new ClassIsFullException(
          String.format("Class %s is already full and can't accept new members", classId));
    }
  }

  private String buildClassId(BookingRequestDto bookingRequestDto) {
    return bookingRequestDto.getClassName() + bookingRequestDto.getDate();
  }
}
