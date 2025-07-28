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

    
    // すでに組み込まれているこのメソッドを使用することで、SecurityFilterChainにこのメソッドのFilterが組み込まれる。
    //UserDetailService: 
    // NOTE: https://spring-boot.jp/security/overview/61 
    // NOTE: loadByUserNameを使えば実装できるが、引数はusernameしか受け取らない。パスワード認証ができない。(https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/core/userdetails/UserDetailsService.html#loadUserByUsername(java.lang.String))
    // UserDetailService: インターフェース。
    // AbstractUserDetailsAuthenticationProvider..
    
    //NOTE: "UserDetailsServiceとはまさに「usernameの情報だけに基づいて検索」するための機構です。"(https://ja.stackoverflow.com/questions/67596/userdetailsservice%E3%81%AEloaduserbyusername%E3%81%AE%E5%AD%98%E5%9C%A8%E6%84%8F%E7%BE%A9%E3%81%8C%E3%82%88%E3%81%8F%E3%82%8F%E3%81%8B%E3%82%89%E3%81%AA%E3%81%84%E3%81%A7%E3%81%99)
    @Override // 本来、Overrideは親のクラスを継承して使うアノテーションだが、FilterChain側でUserDetailServiceは暗に定義されているので、使う必要がある。
    //@Bean: Spring Security側で探してくるものだから、ここではわざわざ使わない。 
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        // データベースからメールアドレスに基づいてAdminユーザーを検索します
//        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
    	
    	// emailを持ってくるのではなく、emailを基準に取ってくる。
    	// Optional書くと、例外処理書かなくていいから便利。
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);

 // ユーザーが見つからない場合は例外(上でoptional書いてるからいいのかな？)
//        if (adminRepository.isEmpty()) {
//            throw new UsernameNotFoundException("ユーザーが見つかりませんでした: " + email);
//        }

        // entityにあるAdminがあるかどうか。無かったら例外処理を返す。(reoはインスタンスではないから、そっからは取ってこれない)       
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