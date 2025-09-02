package com.example.spring09.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring09.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
