package com.gokhan.engcompany.repository;

import com.gokhan.engcompany.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {

    Optional<Boolean> existsByIdentityNumber (String identityNumber);

    Optional<Person> findByIdentityNumber (String identityNumber);



}
