package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Contact;

public interface AdminRepository extends JpaRepository<Contact, Long> {

}
