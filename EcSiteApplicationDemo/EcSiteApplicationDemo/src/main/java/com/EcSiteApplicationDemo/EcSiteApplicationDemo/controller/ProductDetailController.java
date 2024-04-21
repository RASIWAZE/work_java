 package com.EcSiteApplicationDemo.EcSiteApplicationDemo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Product;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Shop;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.User;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.service.EcSiteService;

////////////////////////////////////////
//　商品詳細画面に関連するリクエストハンドリングメソッドのコントローラ
////////////////////////////////////////

@Controller
public class ProductDetailController {

	private EcSiteService ecSiteService;
	
	@Autowired
	public ProductDetailController(EcSiteService ecSiteService){
		this.ecSiteService = ecSiteService;
	}
	
		
	/* 商品詳細ページ呼び出しの処理メソッド */
	@GetMapping("/product")
	public String productList(@RequestParam("productId") int theId, Model theModel, Principal principal) {
		
		// 商品IDで商品情報を取得
		Product theProduct = ecSiteService.findProductById(theId);
		
		// 商品と紐づいているショップ情報を取得
		Shop theShop = theProduct.getShop();
		
		// ショップのオーナー情報を取得
		User theOwner = theShop.getUser();
		
		//　オーナーの名前を取得
		String ownerName = theOwner.getId();
		
		// ログインユーザー名を取得
		String userName = principal.getName();
		
		//　オーナー名とログインユーザー名が同じかの真偽をisOwnerに代入
		boolean isOwner = ownerName.equals(userName);
		
		// 取得したデータをそれぞれModelのアトリビュートに追加
		theModel.addAttribute("product", theProduct);
		theModel.addAttribute("shop", theShop);
		theModel.addAttribute("isOwner", isOwner);
		
		// 商品詳細ページを返す
		return "product";
	}
	
	// 商品詳細ページから渡されたパラメータの（購入情報）処理メソッド
	@PostMapping("/purchased")
	public String purchase(@RequestParam("productId") int theId, 
							@RequestParam("quantity") int quantity, 
							RedirectAttributes redirectAttributes) {
	    
		// 商品IDで商品情報を取得
	    Product theProduct = ecSiteService.findProductById(theId);
	    
	    // 注文後の在庫数（現在庫数 - 注文個数）をCurrentInventoryに代入
	    int CurrentInventory = theProduct.getInventory() - quantity;
	    
	    // 注文後の在庫数を商品情報のプロパティにセット
	    theProduct.setInventory(CurrentInventory);
	    
	    // 商品情報を更新
	    ecSiteService.saveProduct(theProduct);
	    
	    // メッセージをリダイレクト先のページに渡す
	    redirectAttributes.addAttribute("purchasedId", theId);
	    
	    // 二重購入防止のためにredirectプレフィックスを使用
	    return "redirect:/thankyou";
	}
	
	/* '/thankyou'エンドポイントに対するGETリクエストを処理
     * 商品購入御礼のメッセージページ */
    @GetMapping("/thankyou")
    public String thankyou(@RequestParam("purchasedId") int theId, Model theModel) {
    	
    	// 購入商品IDから商品を取得
    	Product theProduct = ecSiteService.findProductById(theId);
        
    	// 取得した商品情報を"product"としてModelのAttributeに追加
    	theModel.addAttribute("product", theProduct);
    	
    	// 商品購入御礼のメッセージページを返す
        return "purchased";
    }
}






