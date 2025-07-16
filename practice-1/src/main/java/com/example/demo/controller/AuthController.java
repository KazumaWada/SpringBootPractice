package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	
	@GetMapping("/signin")
	public String signin() {
		// NOTE: Spring Security実装時に動的の処理を書く。
		return "signin";
	}
}
