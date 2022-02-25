package com.glofox.platform.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassCreationRequestDto {

  @NotNull @NotEmpty private String name;
  @NotNull private LocalDate startDate;
  @NotNull private LocalDate endDate;
  @NotNull private Integer capacity;
}
