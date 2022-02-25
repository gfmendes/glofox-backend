package com.glofox.platform.controller;

import com.glofox.platform.dto.BookingRequestDto;
import com.glofox.platform.service.BookingService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {

  private final BookingService bookingService;

  @ApiOperation(value = "Books a member in a specific class. "
      + "If the class is full then bad request error is returned. "
      + "If the member is already registered in a class then a bad request is returned."
      + "To keep it simple, a member is unique identified by its 'name'."
      + "A class is unique identified as 'name'+'date'. Ex: Pilates2022-24-02")
  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/")
  public void bookClass(@Valid @RequestBody BookingRequestDto bookingRequestDto) {
    bookingService.bookClass(bookingRequestDto);
  }
}
