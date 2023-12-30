package com.EcSiteApplicationDemo.EcSiteApplicationDemo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Shop;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.User;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.service.EcSiteService;

@Controller
public class DemoController {
	
	//  EcSiteServiceインターフェイス型およびそのサブクラス型のオブジェクトをフィールドにセット
    private EcSiteService ecSiteService;
    
    // EcSiteServiceのコンストラクタ注入を作成
    @Autowired
    public DemoController(EcSiteService ecSiteService) {
    	
    	this.ecSiteService = ecSiteService;
    }
    
	
    /* '/top'エンドポイントに対するGETリクエストを処理
     * トップページに既存のショップ一覧を表示 */
    @GetMapping("/top")
    /* ModelインターフェイスとPrincipalインターフェイスを引数荷物
     * Modelインターフェイスを使用してビューにパラメータを渡す
     * Principalインターフェイスを使用して現在ログインしているユーザー情報の一部を取得 */
    public String top(Model theModel, Principal principal){
    	
    	/* DBからすべてのショップ情報を取得
    	 * ecSiteServiceインターフェイスのList<Shop>型のfindAllShopsメソッドを使用して既存のショップ情報をすべて取得 */
    	List<Shop> allShops = ecSiteService.findAllShops();
    	
    	// 現在ログインしているユーザー名を取得
    	String username = principal.getName();
    	        
    	// ユーザー名に一致するユーザー情報を検索
        User theUser = ecSiteService.findUserById(username); 

        System.out.println(theUser.getId() + "がトップページを表示しました。\n");	
        
    	// 取得したユーザーデータをtheModelに追加
    	theModel.addAttribute("allshops", allShops);
    	theModel.addAttribute("user", theUser);	
    	
        // top.htmlを返す
    	return "top";
    }

	
	
    /* '/user-info'エンドポイントに対するGETリクエストを処理
     * ユーザーの個人ページを表示し、オーナーとして所有しているショップ一覧を表示 */
    @GetMapping("/user-info")
    /* ビューから受け取ったuserIdパラメータとModelインターフェイスとPrincipalインターフェイスを引数荷物
     * userIdパラメータをString型javaオブジェクトのuserIdにバインド
     * Modelインターフェイスを使用してビューに渡すオブジェクトをセット
     * Principalインターフェイスを使用して現在ログインしているユーザー情報の一部を取得 */
    public String userInfo(Model theModel, Principal principal) {
    	
    	// ecSiteServiceクラスのfindUserByIdメソッドで渡されたユーザー名と一致するユーザーデータを取得
    	User theUser = ecSiteService.findUserById(principal.getName());
            	
    	// 取得したユーザーデータをtheModelに追加
    	theModel.addAttribute("user", theUser);
    	
    	System.out.println(theUser.getId() + "がユーザーページを表示しました。\n");
    	
    	// user-info.htmlを返します。
    	return "user-info";
    }


    /* '/access-denied'エンドポイントに対するGETリクエストを処理
     * アクセスが拒否されたときに表示するページを返す */
    @GetMapping("/access-denied")
    public String accessDenied() {
    	
    	System.out.println("アクセス権のないページにアクセスしました。\n");
    	
    	// access-denied.htmlを返す
    	return "access-denied";
    }
}













