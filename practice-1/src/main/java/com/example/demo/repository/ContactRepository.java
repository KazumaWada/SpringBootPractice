package com.example.demo.repository;

import java.util.List;
//これを押したらこれが取れるという自動販売機のようなファイル//
//Serviceで、実際にボタンを押して取ってくる//

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Contact;


//DBへ接続した後のCRUD操作、検索とかを行うファイル. クラスではなく、インターフェースを使って行う。//
//ここでJapRepositoryが使えるからfindAllを使ってやるのかな？//

public interface ContactRepository extends JpaRepository<Contact, Long>{
	//jdbc(mysqlと接続した時のコマンド)をDBデータ取得の時にでもここで使えるのかも。
	//ログを出して、本当に出力できたか確かめる。
	
	
	List<Contact> findAll();//ModelのContact.
}
