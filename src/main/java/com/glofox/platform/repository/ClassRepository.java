package com.glofox.platform.repository;

import com.glofox.platform.entity.ClassEntity;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ClassRepository {
  private Map<String, ClassEntity> availableClasses = new HashMap<>();

  public void save(List<ClassEntity> listClassEntity) {
    availableClasses.putAll(
        listClassEntity.stream()
            .collect(Collectors.toMap(ClassEntity::getId, Function.identity())));
  }

  public void save(ClassEntity classEntity) {
    availableClasses.put(classEntity.getId(), classEntity);
  }

  public Optional<ClassEntity> findById(String classId) {
    return Optional.ofNullable(availableClasses.getOrDefault(classId, null));
  }

  public List<ClassEntity> getAll() {
    return availableClasses.values().stream()
        .sorted(Comparator.comparing(ClassEntity::getDate))
        .toList();
  }

  public void clear() {
    availableClasses.clear();
  }
}
