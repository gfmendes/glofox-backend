package com.glofox.platform.controller;

import com.glofox.platform.dto.ClassCreationRequestDto;
import com.glofox.platform.dto.ClassCreationResponseDto;
import com.glofox.platform.service.ClassesService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/classes")
@RequiredArgsConstructor
@Validated
public class ClassController {

  private final ClassesService classesService;

  @ApiOperation(
      value =
          "Creates one class of type 'name' per day considering interval 'startDate' and 'endDate'. "
              + "If the class type already exists in a given day of the interval then this class is not "
              + "created and it is marked with CONFLICT status in the service response."
              + "Requests with CONFLICT classes does not block the service execution, it will "
              + "create classes that does not conflict."
              + "A request fails if start date is greater then end date. "
              + "To keep it simple, a class is unique identified as name+date. Ex: Pilates2022-24-02")
  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ClassCreationResponseDto> createClasses(
      @Valid @RequestBody ClassCreationRequestDto classCreationRequestDto) {
    return classesService.createClasses(classCreationRequestDto);
  }
}
