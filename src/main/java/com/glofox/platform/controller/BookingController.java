package com.glofox.platform.controller;

import com.glofox.platform.dto.BookingRequestDto;
import com.glofox.platform.service.BookingService;
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

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/")
  public void bookClass(@Valid @RequestBody BookingRequestDto bookingRequestDto) {
    bookingService.bookClass(bookingRequestDto);
  }
}
