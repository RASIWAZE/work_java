 package com.EcSiteApplicationDemo.EcSiteApplicationDemo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Product;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Shop;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.User;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.service.EcSiteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

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
    
    
    /*　csv出力ボタンが押されたときの処理メソッド 
     * producesパラメータは、このメソッドが生成するレスポンスのメディアタイプと文字エンコーディングを指定
     * 出力形式をUTF-8に指定
     * Content-Disposition: attachmentでレスポンスをダウンロードファイルに指定　*/
    @GetMapping(value="/csv-export.cvs",
    			produces=MediaType.APPLICATION_OCTET_STREAM_VALUE
    						+ "; charset=UTF-8; Content-Disposition: attachment")
    // CSVデータを含むメソッドの戻り値をレスポンスボディに直接書き込む
    @ResponseBody
    /* CSVデータを生成し、それをレスポンスボディとして返すメソッド
     * JsonProcessingExceptionをスローする可能性がある */
    public Object getCsv(@ModelAttribute("product") Product theProduct) throws JsonProcessingException {
    	
        CsvMapper mapper = new CsvMapper(); // CSVデータのマッピングに使用するCsvMapperオブジェクトを作成
        CsvSchema schema = mapper.schemaFor(Product.class).withHeader(); // Productクラスのスキーマを取得し、ヘッダーを含めるように設定

        return mapper.writer(schema).writeValueAsString(theProduct); // スキーマを使用してCSVデータを生成し、レスポンスボディとしてクライアントに送信
    }
}







