package com.glofox.platform.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequestDto {

  @NotEmpty private final String memberName;
  @NotEmpty private final String className;
  @NotNull private final LocalDate date;
}
