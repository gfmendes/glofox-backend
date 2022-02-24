package com.glofox.platform.dto;

import com.glofox.platform.service.enumeration.ClassCreationStatus;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassCreationResponseDto {

  private String name;
  private LocalDate date;
  private ClassCreationStatus status;
}
