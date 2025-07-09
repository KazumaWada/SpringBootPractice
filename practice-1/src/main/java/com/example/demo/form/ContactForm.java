package com.example.demo.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class ContactForm {
	@NotBlank //Null、空文字ではないこと
	private String lastName;
	
	@NotBlank
    private String firstName;
	
	@NotBlank
	  @Email //正しいメールアドレス形式であること
    private String email;
	
	@NotBlank
	@Size(min = 10, max = 11)
    private String phone;
	
	@NotBlank
	@Pattern(regexp = "[0-9]{3}[-]{0,1}[0-9]{4}")
    private String zipCode;
	
	@NotBlank
    private String address;
	
	@NotBlank
    private String buildingName;
	
	@NotEmpty //空文字のスペースでもok
    private String contactType;
    
	@NotBlank
	private String body;
}
