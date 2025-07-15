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
    
    @Override
    public void updateContact(ContactForm contactForm, Long id) {
    	//ここからnewではなく、idを取得する？つまり仮引数にidを貸与する？
    	  Contact contact = contactRepository.findById(id)
                  .orElseThrow(() -> new IllegalArgumentException("このIdがエラーを起こしている:" + id));
    	
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
    
    @Override
    public void deleteContact(ContactForm contactForm, Long id) {
    	  Contact contact = contactRepository.findById(id)
                  .orElseThrow(() -> new IllegalArgumentException("このIdがエラーを起こしている:" + id));
    	
    	contact.setLastName(contactForm.getLastName());
        contact.setFirstName(contactForm.getFirstName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhone(contactForm.getPhone());
        contact.setZipCode(contactForm.getZipCode());
        contact.setAddress(contactForm.getAddress());
        contact.setBuildingName(contactForm.getBuildingName());
        contact.setContactType(contactForm.getContactType());
        contact.setBody(contactForm.getBody());
        
        contactRepository.delete(contact);
    }
    @Override
//    public Contact getContactById(Long id) {
//         //Contact contact_id = contactRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("このIDがエラーの原因: " + id));
//         
//         return contactRepository.findById(id);
//    }
    public Contact getContactById(Long id) {
        //なかった場合を考慮して、oprtionalという箱に入れている。
    	//だから例外処理を書いて、optionalから取り出す必要がある。
    	return contactRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("指定されたIDの連絡先が見つかりません: " + id));
    }
    
    public List<Contact> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();

        return contacts;
    }
    
}






























