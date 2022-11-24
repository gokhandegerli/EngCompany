package com.gokhan.engcompany.repository;

import com.gokhan.engcompany.entity.Department;
import com.gokhan.engcompany.enums.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    public Boolean existsByDepartmentType (DepartmentType departmentType);

}
