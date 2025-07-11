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

import com.example.demo.form.ContactForm;
import com.example.demo.service.ContactService;

//httpを書くか書かないか、「状態を保持する必要があるかどうか」

@Controller
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping("/contact")
    public String contact(Model model) {
    	//多分、アプリケーション内のファイルだけで表示できるから、httpは書いていないのかも。
    	
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
    	//セッション自体を取得している
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
    
    
    
    //今度はDBから取ってきて、それを表示する。だからhttpは書かなくていいのかも？    
    @GetMapping("/admin/contacts")
    public String contacts(Model model) {
    	//毎回使われるこの引数の'model'は、contactFormのモデルではなく、spring bootがcontroller内の処理を"viewに渡すための箱"として機能している。
    	//だから、DBのmodelとは全く異なる。
    	
    	//一連の流れ//
    	//modelのデータの形をマッピングはする必要あるのかな？
    	//serviceで取ってきたデータをそこに当てて、"getAllContacts"はview側で使う変数っぽい？
    	model.addAttribute("getAllContacts", contactService.getAllContacts());
    	
    	
    	//その変数をviewに仮引数なりなんなりに詰めて送る

    	return "contacts";
    }
    
}







































