package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.AdminForm;
import com.example.demo.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AuthController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;


	@GetMapping("/signup")
	public String signup(Model model) {

		model.addAttribute("adminForm", new AdminForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String signup(@Validated @ModelAttribute("adminForm") AdminForm adminForm, BindingResult errorResult) {

		if (errorResult.hasErrors()) {
			return "signup";
		}

		// passwordのhash化
		String encodedPassword = passwordEncoder.encode(adminForm.getPassword());
		adminForm.setPassword(encodedPassword);
		adminService.saveAdmin(adminForm);

		return "redirect:/admin/complete";
	}

	@GetMapping("/signin")
	public String signin(Model model) {
		model.addAttribute("adminForm", new AdminForm());

		return "signin";
	}
	// NOTE: @PostMapping("/admin/signin")Spring Securityが処理してくれるから、書かなくていい。

	@GetMapping("/complete")
	public String adminComplete() {
		//NOTE: もし今度新規登録完了者のデータを表示する場合があったら、SecurityContextHolderって言う場所に認証されたUserの情報が保管されているから、それをmodelに突っ込む。
		return "adminCompletion";
	}

	@GetMapping("/signin")
	public String signin() {
		// TODO: Spring Security実装時に動的の処理を書く。
		return "signin";
	}

}
