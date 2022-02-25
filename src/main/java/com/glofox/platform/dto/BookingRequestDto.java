package com.glofox.platform.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {

  @NotNull @NotEmpty private String memberName;
  @NotNull @NotEmpty private String className;
  @NotNull private LocalDate date;
}
