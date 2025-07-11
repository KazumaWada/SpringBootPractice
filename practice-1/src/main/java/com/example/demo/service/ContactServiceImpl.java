package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.repository.ContactRepository;
//Controllerがごちゃごちゃにならないように、ビジネスロジックをここに分けて書くためのファイル。

@Service//SpringがここをServiceとして認識する。
public class ContactServiceImpl implements ContactService {
	//ログを実装するファイル(このファイル)の定義
//	private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);
    @Autowired
      private ContactRepository contactRepository;

    @Override
    public void saveContact(ContactForm contactForm) {
        Contact contact = new Contact();

        contact.setLastName(contactForm.getLastName());
        contact.setFirstName(contactForm.getFirstName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhone(contactForm.getPhone());
        contact.setZipCode(contactForm.getZipCode());
        contact.setAddress(contactForm.getAddress());
        contact.setBuildingName(contactForm.getBuildingName());
        contact.setContactType(contactForm.getContactType());
        contact.setBody(contactForm.getBody());
        
        contactRepository.save(contact);
    }
    
    public List<Contact> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();

        // ログ出力して確認
//        logger.info("DBから連絡先データを{}件取得しました。", contacts.size());
//
//        if (logger.isDebugEnabled()) {
//            contacts.forEach(contact -> logger.debug("取得データ: ID={}, 名前={}", contact.getId(), contact.getLastName()));
//        }

        return contacts;
    }
    
}






























