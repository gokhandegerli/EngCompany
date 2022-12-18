package com.gokhan.engcompany.repository;

import com.gokhan.engcompany.entity.Client;
import com.gokhan.engcompany.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {

}
