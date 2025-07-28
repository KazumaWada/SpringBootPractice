package com.example.demo.config; 

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;

@Service 
@Configuration
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private AdminRepository adminRepository;
    @Override 
    // @Bean: Spring Security側で探してくるものだから、ここではわざわざ使わない。 
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{    	
    	// Optional書くと、例外処理書かなくていいから便利。
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        // entityにあるAdminがあるかどうか。無かったら例外処理を返す。(reositoryはインスタンスではないから、そっからは取ってこれない)       
        Admin admin = adminOptional.orElseThrow(() ->
        new UsernameNotFoundException(email + "←このユーザーは見つかりませんでした")
        );
        
        return org.springframework.security.core.userdetails.User.builder()
                .username(admin.getEmail())    
                .password(admin.getPassword())    
                .roles("ADMIN")                   
                .build();
    }
}