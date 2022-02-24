package com.glofox.platform.controller;

import com.glofox.platform.dto.ClassCreationRequestDto;
import com.glofox.platform.dto.ClassCreationResponseDto;
import com.glofox.platform.service.ClassesService;
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

  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ClassCreationResponseDto> createClasses(
      @Valid @RequestBody ClassCreationRequestDto classCreationRequestDto) {
    return classesService.createClasses(classCreationRequestDto);
  }
}
