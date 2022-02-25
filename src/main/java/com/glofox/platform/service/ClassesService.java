package com.glofox.platform.service;

import com.glofox.platform.dto.ClassCreationRequestDto;
import com.glofox.platform.dto.ClassCreationResponseDto;
import com.glofox.platform.entity.ClassEntity;
import com.glofox.platform.exception.ClassDoesNotExistsException;
import com.glofox.platform.exception.InvalidDateIntervalException;
import com.glofox.platform.repository.ClassesRepository;
import com.glofox.platform.service.enumeration.ClassCreationStatus;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassesService {

  private final ClassesRepository classesRepository;

  public List<ClassCreationResponseDto> createClasses(ClassCreationRequestDto requestDto) {
    validateDatesRange(requestDto);

    final var allClasses = convertDtoIntoEntityClasses(requestDto);
    final var validClasses =
        allClasses.stream().filter(c -> classesRepository.findById(c.getId()).isEmpty()).toList();
    classesRepository.save(validClasses);
    return buildClassCreationResponse(allClasses, validClasses);
  }

  private List<ClassCreationResponseDto> buildClassCreationResponse(
      List<ClassEntity> allClasses, List<ClassEntity> validClasses) {
    final var result = new ArrayList<ClassCreationResponseDto>();
    for (ClassEntity c : allClasses) {
      if (validClasses.contains(c)) {
        result.add(buildResponseDto(c, ClassCreationStatus.SUCCESS));
        log.info("Class {} was created.", c.getId());
      } else {
        result.add(buildResponseDto(c, ClassCreationStatus.CONFLICT));
        log.warn("Class {} creation conflicted and not created.", c.getId());
      }
    }
    return result;
  }

  private ClassCreationResponseDto buildResponseDto(ClassEntity c, ClassCreationStatus status) {
    return ClassCreationResponseDto.builder()
        .name(c.getName())
        .date(c.getDate())
        .status(status)
        .build();
  }

  private void validateDatesRange(ClassCreationRequestDto classCreationRequestDto) {
    if (classCreationRequestDto.getStartDate().isAfter(classCreationRequestDto.getEndDate())) {
      throw new InvalidDateIntervalException(
          String.format(
              "Start date %s is after end date %s.",
              classCreationRequestDto.getStartDate(), classCreationRequestDto.getEndDate()));
    }
  }

  private List<ClassEntity> convertDtoIntoEntityClasses(ClassCreationRequestDto requestDto) {
    return Stream.iterate(requestDto.getStartDate(), date -> date.plusDays(1))
        .limit(
            ChronoUnit.DAYS.between(requestDto.getStartDate(), requestDto.getEndDate().plusDays(1)))
        .map(date -> new ClassEntity(requestDto.getName(), date, requestDto.getCapacity()))
        .toList();
  }

  ClassEntity findClass(String classId) {
    return classesRepository
        .findById(classId)
        .orElseThrow(
            () ->
                new ClassDoesNotExistsException(
                    String.format("Class %s does not exists.", classId)));
  }
}
