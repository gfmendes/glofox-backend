package com.glofox.platform.service;

import com.glofox.platform.dto.BookingRequestDto;
import com.glofox.platform.entity.ClassEntity;
import com.glofox.platform.exception.ClassIsFullException;
import com.glofox.platform.exception.MemberAlreadyInClassException;
import com.glofox.platform.repository.ClassesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

  private final ClassesRepository classesRepository;
  private final ClassesService classesService;

  public void bookClass(BookingRequestDto bookingRequestDto) {
    final var classId = buildClassIdFromBookingRequest(bookingRequestDto);
    final var member = bookingRequestDto.getMemberName();
    final ClassEntity classEntity = classesService.findClass(classId);

    if (classEntity.getMembers().contains(member)) {
      throw new MemberAlreadyInClassException(
          String.format("Member %s is already in the class %s", member, classId));
    }

    if (classEntity.isSlotAvailable()) {
      classEntity.addMember(member);
      log.info("Member {} added to the class {}", member, classId);
    } else {
      throw new ClassIsFullException(
          String.format("Class %s is already full and can't accept new members", classId));
    }
  }

  private String buildClassIdFromBookingRequest(BookingRequestDto bookingRequestDto) {
    return bookingRequestDto.getClassName() + bookingRequestDto.getDate();
  }
}
