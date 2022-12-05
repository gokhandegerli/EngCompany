package com.gokhan.engcompany.repository;

import com.gokhan.engcompany.entity.Employee;
import com.gokhan.engcompany.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    boolean existsByName(String name);

    boolean existsByProjectId (int projectId);

    @Query("SELECT p from Project p WHERE p.employee IN :employees") //Listeyse IN kullan. @Query verince
    //alttaki lojik(derived query=findProjectFreeEmployees), devre dışı kalır, jpql olarak
        // bizim yazmamızı bekler. jpql de "SELECT ..." olan sql kodu. jpql kodtaki p de Project'in bir instance
        // gibi davranıyor.
    List<Project> findProjectsWithEmployees(List<Employee> employees);

}
