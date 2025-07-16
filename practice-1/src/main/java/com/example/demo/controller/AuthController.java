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
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.AdminForm;
import com.example.demo.service.AdminService;

@Controller
public class AuthController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/signup")
	public String signup(Model model) {

		model.addAttribute("adminForm", new AdminForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String signup(@Validated @ModelAttribute("adminForm") AdminForm adminForm, BindingResult errorResult,
			HttpServletRequest request) {
		
		if (errorResult.hasErrors()) {
			return "signup";
		}
		
		adminService.saveAdmin(adminForm);
		
		HttpSession session = request.getSession();
		session.setAttribute("adminForm", adminForm);
		
		return "redirect:/admin/complete";
	}

	@GetMapping("/admin/complete")
	public String adminComplete(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();

		AdminForm adminForm = (AdminForm) session.getAttribute("adminForm");
		model.addAttribute("adminForm", adminForm);

		return "adminCompletion";
	}

	@GetMapping("/signin")
	public String signin() {
		// NOTE: Spring Security実装時に動的の処理を書く。
		return "signin";
	}
}
