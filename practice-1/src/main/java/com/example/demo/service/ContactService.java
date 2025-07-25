package com.example.demo.service;
import java.util.List;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;

//implで書いた内容をここでインターフェースとして格納しておくスペース

public interface ContactService {

    void saveContact(ContactForm contactForm);
    void updateContact(ContactForm contactForm, Long id);
    void deleteContact(ContactForm contactForm, Long id);
    List<Contact> getAllContacts();
	Contact getContactById(Long id);
    

}