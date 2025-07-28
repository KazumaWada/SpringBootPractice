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
	// NOTE: 頻繁に使うので、コメントアウトで保持
	//private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

	@Autowired
	private ContactService contactService;

	@GetMapping("/contact")
	public String contact(Model model) {

		model.addAttribute("contactForm", new ContactForm());
		return "contact";
	}

	@PostMapping("/contact")
	public String contact(@Validated @ModelAttribute("contactForm") ContactForm contactForm, BindingResult errorResult,
			HttpServletRequest request) {
		if (errorResult.hasErrors()) {
			return "contact";
		}

		HttpSession session = request.getSession();
		session.setAttribute("contactForm", contactForm);

		return "redirect:/contact/confirm";
	}

	@GetMapping("/contact/confirm")
	public String confirm(Model model, HttpServletRequest request) {
		//セッションを取得
		HttpSession session = request.getSession();
		//contactFormに保存されたセッションデータを取得
		ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
		model.addAttribute("contactForm", contactForm);
		return "confirmation";
	}

	@PostMapping("/contact/register")
	public String register(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");

		contactService.saveContact(contactForm);

		return "redirect:/contact/complete";
	}

	@GetMapping("/contact/complete")
	public String complete(Model model, HttpServletRequest request) {

		if (request.getSession(false) == null) {
			return "redirect:/contact";
		}

		HttpSession session = request.getSession();
		ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
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
	public String contactEdit(@Validated @ModelAttribute("contactForm") ContactForm contactForm, @PathVariable Long id,
			BindingResult errorResult, HttpServletRequest request) {
		//@validationの通過
		if (!errorResult.hasErrors()) {
			contactService.updateContact(contactForm, id);
			return "redirect:/admin/contact/" + id;
		} else {
			return "redirect:/admin/contacts";
		}
	}

	@PostMapping("/admin/contact/{id}/delete")
	public String deleteContact(@PathVariable Long id, ContactForm contactForm) {

		contactService.deleteContact(contactForm, id);

		return "redirect:/admin/contacts";
	}

}






































