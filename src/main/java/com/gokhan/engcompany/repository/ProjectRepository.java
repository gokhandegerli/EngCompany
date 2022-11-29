package com.gokhan.engcompany.repository;

import com.gokhan.engcompany.entity.Project;
import com.gokhan.engcompany.enums.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Boolean> existsByName(String name);
}
