package com.EcSiteApplicationDemo.EcSiteApplicationDemo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Role;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Shop;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.User;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.service.EcSiteService;

import jakarta.validation.Valid;


////////////////////////////////////////
//商品詳細画面に関連するリクエストハンドリングメソッドのコントローラ
////////////////////////////////////////

@Controller
public class RegisterShopController {

	private EcSiteService ecSiteService;
	
	@Autowired
	public RegisterShopController(EcSiteService ecSiteService){
		this.ecSiteService = ecSiteService;
	}
	
	@InitBinder
	// ビューから渡されたパラメータをjavaオブジェクトにバインドするクラスであるdataBinderに格納
	public void initBinder(DataBinder dataBinder) {
		
		// 渡された文字列パラメータの前後の空白を除去するためのStringTrimmerEditorクラスをインスタンス化
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
	
		// StringクラスのパラメータにStringTrimmerEditorクラスを実行して空白を除去する
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	
	// 新規ショップ作成ページを表示
	@GetMapping("/create-shop")
	public String registerShop(Model theModel, Principal principal) {
		
		// ショップクラスインスタンスを作成
		Shop theShop = new Shop();
		
		// ログインユーザー名を取得
		String userName = principal.getName(); 
        
		// ユーザー名に一致するユーザーを検索
        User user = ecSiteService.findUserById(userName);
        
		// 新規ショップ登録ページから受け取ったショップデータにオーナーのユーザーIDを紐づけ
		theShop.setUser(user);
    	
		//　ビューに上記で作成したショップクラスインスタンスをModelインターフェイスに追加
    	theModel.addAttribute("shop", theShop);
		
    	// 新規ショップ作成ページを返す
		return "create-shop";
	}
	
	
	// 新規ショップ作成ページからのデータを検証して、DBに登録
	@PostMapping("/register-theShop")
	public String registerTheShop(@Valid @ModelAttribute("shop") Shop theShop,
									BindingResult theBindingResult,
									Model theModel,
									Principal principal) {
		
		// 検証の結果エラーがあった場合、新規ショップ作成ページに戻す
		if(theBindingResult.hasErrors()) {
			
			// ログインユーザー名を取得
			String userName = principal.getName(); 
	        
			// ユーザー名に一致するユーザーを検索
	        User user = ecSiteService.findUserById(userName);
	        
			// 新規ショップ登録ページから受け取ったショップデータにオーナーのユーザーIDを紐づけ
			theShop.setUser(user);
	    	
			//　上記で作成したショップインスタンスをModelインターフェイスに追加
	    	theModel.addAttribute("shop", theShop);
			
	    	// 新規ショップ作成ページを返す
			return "create-shop";
		}
		
		// ログインユーザー名を取得
		String userName = principal.getName(); 
        
		// ユーザー名に一致するユーザーを検索
        User user = ecSiteService.findUserById(userName);
        
		// 新規ショップ登録ページから受け取ったショップデータにオーナーのユーザーIDを紐づけ
		theShop.setUser(user);
		
		System.out.println(ecSiteService.hasRole(userName, "ROLE_OWNER"));
		
		// ユーザーにオーナーロールが付与されていない場合、付与
		if(!ecSiteService.hasRole(userName, "ROLE_OWNER")) {
			
			System.out.println("ユーザーにオーナー権限を付与します。");
			
			//　ユーザーネームとオーナー権限を登録したロールインスタンスを作成
			Role role = new Role(userName, "ROLE_OWNER");
			
			ecSiteService.saveRole(role);
			
			// 上記で設定したショップをDBに保存する
			ecSiteService.saveShop(theShop);
			
			// オーナー権限が初めて付与された場合、オーナーとして再度ログイン
			return "redirect:my-login";
		}
		
		// 上記で設定したショップをDBに保存する
		ecSiteService.saveShop(theShop);
		
		// ユーザーページへリダイレクト
		return "redirect:user-info";
	}

}