package com.gokhan.engcompany.repository;

import com.gokhan.engcompany.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {

    public Boolean existsByIdentityNumber (String identityNumber);

    public Person findByIdentityNumber (String identityNumber);



}
