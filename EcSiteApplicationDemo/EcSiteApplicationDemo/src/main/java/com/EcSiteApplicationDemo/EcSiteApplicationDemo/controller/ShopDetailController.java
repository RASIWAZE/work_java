package com.EcSiteApplicationDemo.EcSiteApplicationDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Product;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Shop;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.service.EcSiteService;

@Controller
public class ShopDetailController {

	private EcSiteService ecSiteService;
	
	@Autowired
	public ShopDetailController(EcSiteService ecSiteService){
		this.ecSiteService = ecSiteService;
	}
	
	// "/shop/ショップID"のエンドポイントにGetリクエストがあった時、そのショップIDを持つショップのページを表示する
	@GetMapping("/shop")
	public String shopList(@RequestParam("shopId") int theId, Model theModel) {
		
		// ショップIDからショップ情報を取得
		Shop theShop = ecSiteService.findShopById(theId);
		
		// IDで指定されたショップに登録されたすべての商品情報を取得
		List<Product> allProducts = ecSiteService.findProductsForShop(theId);
		
		// 取得したデータをそれぞれModelのアトリビュートに追加
		theModel.addAttribute("shop", theShop);
		theModel.addAttribute("allProducts", allProducts);		
		
		//　ショップ詳細ページを返す
		return "shop";
	}
}