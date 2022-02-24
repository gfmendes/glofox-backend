package com.glofox.platform.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ClassEntity {

  private final String name;
  private final LocalDate date;
  private final Integer capacity;
  private final List<String> members = new ArrayList<>();

  public String getId() {
    return this.name + this.date;
  }

  public void addMember(String name) {
    members.add(name);
  }

  public boolean isSlotAvailable() {
    return capacity > members.size();
  }
}
