package com.gokhan.engcompany.repository;

import com.gokhan.engcompany.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    boolean existsByEmployeeId (int employeeId);


    //@Query("SELECT '*' FROM Employee e WHERE e.department.departmentId = :id") //( Buna gerek kalmadı. )
    List<Employee> findByDepartmentDepartmentId(int id);
}
