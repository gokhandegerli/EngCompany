package com.gokhan.engcompany.repository;

import com.gokhan.engcompany.entity.HeadDepartment;
import com.gokhan.engcompany.enums.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadDepartmentRepository extends JpaRepository<HeadDepartment,Integer> {

    Boolean existsByDepartmentType (DepartmentType departmentType);
}
