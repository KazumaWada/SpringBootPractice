package com.example.demo.repository;

import java.util.List;
//これを押したらこれが取れるという自動販売機のようなファイル//
//Serviceで、実際にボタンを押して取ってくる//
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{
	// jdbc: JavaとDBをつなぐコネクタ(repository内でよく使われる。)
	
	
	List<Contact> findAll();//ModelのContact.
	Optional<Contact> findById(Long id); 
}
