package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // configの宣言
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	// filterのインターフェース
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("admin/signin", "admin/signup").permitAll()
						.anyRequest().authenticated()) // 上記で許可されたパス以外は、すべて認証を要求 
				.httpBasic(Customizer.withDefaults())
				.csrf(Customizer.withDefaults())
				.formLogin(form -> form
						.loginPage("/admin/signin")
						.usernameParameter("email")
						.loginProcessingUrl("/admin/signin")
						.defaultSuccessUrl("/admin/contacts", true)
						.failureUrl("/admin/signin?error")
						.permitAll())
				// NOTE: LogoutFilter はフィルターチェーン内で AuthorizationFilter の前に現れるため、デフォルトでは /logout エンドポイントを明示的に認可する必要ない(公式より)
				.logout(logout -> logout
						.logoutSuccessUrl("/admin/signin")
						);
				
				return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
