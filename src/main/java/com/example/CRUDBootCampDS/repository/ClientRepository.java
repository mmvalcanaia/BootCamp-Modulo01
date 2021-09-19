package com.example.CRUDBootCampDS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CRUDBootCampDS.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
