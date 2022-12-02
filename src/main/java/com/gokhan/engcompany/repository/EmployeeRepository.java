package com.gokhan.engcompany.repository;

import com.gokhan.engcompany.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    boolean existsByEmployeeId (int employeeId);


    Optional<Employee> findByEmployeeId(int employeeId);
}
