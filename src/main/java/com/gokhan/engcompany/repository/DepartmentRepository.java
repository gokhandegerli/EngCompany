package com.gokhan.engcompany.repository;

import com.gokhan.engcompany.entity.Department;
import com.gokhan.engcompany.enums.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    Optional<Boolean> existsByDepartmentType (DepartmentType departmentType);

    Optional<Boolean> existsByDepartmentId (int DepartmentId);

}
