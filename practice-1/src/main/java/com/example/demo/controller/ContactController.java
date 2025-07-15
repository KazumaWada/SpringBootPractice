package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.service.ContactService;


@Controller
public class ContactController {
	//頻繁に使うので、コメントアウトで保持
	//private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired  
    private ContactService contactService;

    @GetMapping("/contact")
    public String contact(Model model) {
    	
    	//entityのmodelにcontactFormを貼っている？
        model.addAttribute("contactForm", new ContactForm());
        //viewを返す。
        return "contact";
    }

    // '/contact'にあるformのデータをDBに飛ばす処理。
    @PostMapping("/contact")
    public String contact(@Validated @ModelAttribute("contactForm") ContactForm contactForm, BindingResult errorResult, HttpServletRequest request) {
        if (errorResult.hasErrors()) {
          return "contact";
        }
        
        //セッション自体を取得している
        HttpSession session = request.getSession();
        //contactFormで入力された値をsessionに保持しておくよ！
        //え、もう送信したデータなのになぜわざわざ保持する？？->二重に'送信'ボタンをクリックされた場合.空のformが送られたらユーザーもアプリも困るから。
        session.setAttribute("contactForm", contactForm);

        return "redirect:/contact/confirm";
    }

    @GetMapping("/contact/confirm")
    public String confirm(Model model, HttpServletRequest request) {
    	//セッション自体を取得
        HttpSession session = request.getSession();
        
        //contactFormに保存されたセッションデータを取得
        ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
        model.addAttribute("contactForm", contactForm);
        return "confirmation";
    }

    @PostMapping("/contact/register")
    public String register(Model model, HttpServletRequest request) {
    	
    	//sessionを自体を取得している
        HttpSession session = request.getSession();
        //contactFormにある内容のsessionを取得。
        ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");

        contactService.saveContact(contactForm);
        
        //saveできたら、sessionはここで一旦消しといたほうがいいと思う。
        //いや、でもcompleteで結果を取得するときにそのsessionが欲しいのかも。

        return "redirect:/contact/complete";
    }
    
    //データ送信が完了したページを表示する。
    @GetMapping("/contact/complete")
    public String complete(Model model, HttpServletRequest request) {
    	
    	//もし、セッションのデータが存在せずnullだったらredirect
        if (request.getSession(false) == null) {
          return "redirect:/contact";
        }
        
        //sessionを取得
        HttpSession session = request.getSession();
        //contactFormに存在しているsessionから値を取得して表示するためにsessionから取得。
        ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
        //contactFormのsessionデータをmodelに格納してviewへ送信
        model.addAttribute("contactForm", contactForm);
        
        //セッションを破棄(completeしたから)
        session.invalidate();

        return "completion";
      }
    
    
    @GetMapping("/admin/contacts")
    public String contacts(Model model) {    	
    	
    	model.addAttribute("getAllContacts", contactService.getAllContacts());
    	return "contacts";
    }
    
    //詳細ページ
    //https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/web.html#mvc-ann-requestmapping
    @GetMapping("/admin/contact/{id}")
    //@ResponseBodyこれを書くと自動的にmappinngしてくれる？？後で確認。
    public String contactDetail(Model model, @PathVariable Long id) { //@PathVariable: https://www.baeldung.com/spring-pathvariable
    	
    	Contact contact = contactService.getContactById(id);
      	model.addAttribute("getContact", contact);
    	    
    	return "contactDetail";
    }
    
    //maybe later
    @GetMapping("/admin/contact/{id}/edit")
    public String contactEdit(Model model, @PathVariable Long id) {
    	
    	Contact contact = contactService.getContactById(id);
      	model.addAttribute("getContact", contact);
    	
    	return "contactEdit";
    }
    
    @PostMapping("/admin/contact/{id}/edit")
    public String contactEdit(@Validated @ModelAttribute("contactForm") ContactForm contactForm, @PathVariable Long id, BindingResult errorResult, HttpServletRequest request) {
    	//@validationの通過
    	if (!errorResult.hasErrors()) {
    		contactService.updateContact(contactForm, id);
    		 return "redirect:/admin/contact/" + id;
          }else {
        	return "redirect:/admin/contacts";
          }
    }
    
    @PostMapping("/admin/contact/{id}/delete")
    public String deleteContact(@PathVariable Long id, ContactForm contactForm) {
    	
    	contactService.deleteContact(contactForm, id);
    	
    	return "redirect:/admin/contacts";
    }
    
}







































