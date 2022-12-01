package com.gokhan.engcompany.repository;

import com.gokhan.engcompany.entity.HeadDepartment;
import com.gokhan.engcompany.enums.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeadDepartmentRepository extends JpaRepository<HeadDepartment,Integer> {

    boolean existsByDepartmentType (DepartmentType departmentType);

    boolean existsByHeadDepartmentId (int headDepartmentId);

    HeadDepartment findByHeadDepartmentId(int headDepartmentId);

    HeadDepartment findHeadDepartmentByManager_EmployeeId(int employeeId);
}
