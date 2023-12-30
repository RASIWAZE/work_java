package com.EcSiteApplicationDemo.EcSiteApplicationDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Product;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Shop;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.User;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.service.EcSiteService;

import jakarta.validation.Valid;


////////////////////
// オーナー権限下のページのリクエストハンドリング
////////////////////


@Controller // コントローラークラスであることを示すアノテーション
@RequestMapping("/owner") // このクラスのメソッドが処理するリクエストの共通のパスを指定するアノテーション
public class OwnerController {

	// サービス層のクラスをインジェクションするためのフィールド
	private EcSiteService ecSiteService;
	
	@Autowired // サービス層のクラスを自動的にインジェクションするためのアノテーション
	public OwnerController(EcSiteService ecSiteService){
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
	
	
	
	////////////////////////////////////////
	//　オーナ所有のショップページ関連
	////////////////////////////////////////
	
	//　オーナーが登録しているショップページの呼び出し
	@GetMapping("/shop") // "/shop"エンドポイントへのGETリクエストを処理するメソッド
	// リクエストパラメーターでショップIDを受け取り、Modelオブジェクトを引数
	// リクエストパラメーターでショップIDを受け取り、Modelオブジェクトを引数に持つメソッド
	public String shopList(@RequestParam("shopId") int theId, Model theModel) {
		
		/* 現在認証されているユーザーの情報を取得 
		 * SecurityContextHolderで現在認証されているユーザー名を取得
		 * Authenticationクラスのauthに代入　*/
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserId = auth.getName();
	    
	    // ユーザーIDからユーザーを特定して情報を取得
	    User currentUser = ecSiteService.findUserById(currentUserId);	    

		// ショップIDからショップ情報を取得
		Shop theShop = ecSiteService.findShopById(theId);
		
		// リクエストしたユーザーがショップページのオーナーではない場合アクセス拒否ページを表示
		if(!currentUser.equals(theShop.getUser())) {
			
			return "access-denied";
		}
	    
		
		// IDで指定されたショップに登録されたすべての商品情報を取得
		List<Product> allProducts = ecSiteService.findProductsForShop(theId);
		
		// 取得したデータをそれぞれModelのアトリビュートに追加
		theModel.addAttribute("shop", theShop); // ショップ情報を"shop"という名前でModelに追加
		theModel.addAttribute("allProducts", allProducts); // 商品情報のリストを"allProducts"という名前でModelに追加		
		
		// オーナーが所有しているうちの特定のショップのビューを返す
		return "owner/owner-shop";
	}
	
	
	//　ショップ編集ページの呼び出し
	@GetMapping("/edit-shop")
	public String editShop(@RequestParam("shopId") int theId, Model theModel) {
		
		// 現在認証されているユーザーの情報を取得
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserId = auth.getName();
	    
	    // ユーザーIDからユーザーを特定して情報を取得
	    User currentUser = ecSiteService.findUserById(currentUserId);	    

		// ショップIDからショップ情報を取得
		Shop theShop = ecSiteService.findShopById(theId);
		
		// リクエストしたユーザーがショップページのオーナーではない場合アクセス拒否ページを表示
		if(!currentUser.equals(theShop.getUser())) {
			
			return "access-denied";
		}
		
		// 取得したデータをそれぞれModelのアトリビュートに追加
		theModel.addAttribute("shop", theShop);
		
		// ショップ編集ページを返す
		return "owner/edit-shop";
	}
	
	// ショップ編集ページから渡されたデータでショップ情報を更新、その後オーナーショップページへ移動
	@PostMapping("/update-shop-complete")
	public String updateShopComplete(@ModelAttribute("shop") Shop theShop, @RequestParam("shopId") int theId) {
		
		// 更新前のショップ情報をショップIDからショップ情報を取得
		Shop tempShop = ecSiteService.findShopById(theId);
		
		System.out.println("更新前のショップ情報:\n" + tempShop + "\n");
		
		// ショップ編集ページから受け取ったtheShopのショップ名とショップ説明プロパティを、tempShopのプロパティにセット
		tempShop.setName(theShop.getName());
		tempShop.setDescription(theShop.getDescription());
		
		System.out.println("更新後のショップ情報:\n" + tempShop + "\n");
		
		// 更新内容をDBの既存データに上書き保存
		ecSiteService.saveShop(tempShop);
		
		// オーナーショップページを返す
		return "redirect:/owner/shop?shopId=" + tempShop.getId();
	}
	
	
	//　ショップ削除ページの呼び出し
	@GetMapping("/delete-shop")
	public String deleteShop(@RequestParam("shopId") int theId, Model theModel) {
		
		// 現在認証されているユーザーの情報を取得
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserId = auth.getName();
	    
	    // ユーザーIDからユーザーを特定して情報を取得
	    User currentUser = ecSiteService.findUserById(currentUserId);	    

		// ショップIDからショップ情報を取得
		Shop theShop = ecSiteService.findShopById(theId);
		
		// リクエストしたユーザーがショップページのオーナーではない場合アクセス拒否ページを表示
		if(!currentUser.equals(theShop.getUser())) {
			
			return "access-denied";
		}
		
		System.out.println("以下のショップを削除します。" + "\n" + theShop + "\n");
		
		// 取得したデータをそれぞれModelのアトリビュートに追加
		theModel.addAttribute("shop", theShop);
		
		return "owner/delete-shop";
	}
	
	// ショップ削除ページから渡されたデータでショップを削除、その後ユーザーページへ移動
	@PostMapping("/shop-delete-complete")
	public String shopDeleteComplete(@RequestParam("shopId") int shopId, Model theModel) {
		
		/* 削除後のページ移動用に、渡されたショップIDのショップを取得
		 * ショップオーナーのユーザーIDを取得*/
		Shop theShop = ecSiteService.findShopById(shopId);
		String theUserId = theShop.getUser().getId();
		
		//　渡されたショップIDのショップを削除
		ecSiteService.deleteShopById(shopId);
		
		// ユーザーページを返す
		return "redirect:/user-info?userId=" + theUserId;
	}
	

	////////////////////////////////////////
	//　オーナー所有の商品関連
	////////////////////////////////////////
	
	// オーナーがショップに登録している商品ページの呼び出し
	@GetMapping("/product")
	public String productList(@RequestParam("productId") int theId, Model theModel) {
		
		// 現在認証されているユーザーの情報を取得
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserId = auth.getName();
	    
	    // ユーザーIDからユーザーを特定して情報を取得
	    User currentUser = ecSiteService.findUserById(currentUserId);	    

	    // 商品IDで商品情報を取得
 		Product theProduct = ecSiteService.findProductById(theId);
 		
 		// 商品と紐づいているショップ情報を取得
 		Shop theShop = theProduct.getShop();
		
		// リクエストしたユーザーがショップページのオーナーではない場合アクセス拒否ページを表示
		if(!currentUser.equals(theShop.getUser())) {
			
			return "access-denied";
		}
		
		// 取得したデータをそれぞれModelのアトリビュートに追加
		theModel.addAttribute("product", theProduct);
		theModel.addAttribute("shop", theShop);
		
		// 商品ページを返す
		return "owner/owner-product";
	}

	
	// 新規商品登録ページの呼び出し
	@GetMapping("/create-product")
	public String createProduct(@RequestParam("shopId") int shopId, Model theModel){
		
		/* 現在認証されているユーザーの情報を取得
		 * SecurityContextHolder.getContext().getAuthentication()で現在認証されているユーザーの情報を取得
		 *　認証されているユーザー情報を保持するクラスであるAuthenticationクラスオブジェクトのauthに代入 */
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserId = auth.getName();
	    
	    // ユーザーIDからユーザーを特定して情報を取得
	    User currentUser = ecSiteService.findUserById(currentUserId);	    

		// ショップIDからショップ情報を取得
		Shop theShop = ecSiteService.findShopById(shopId);
		
		// リクエストしたユーザーがそのショップのオーナーではない場合アクセス拒否ページを表示
		if(!currentUser.equals(theShop.getUser())) {
			
			return "access-denied";
		}
		
		// 追加する商品のインスタンスを生成
		Product theProduct = new Product();
		
		System.out.println("指定されたショップID:" + theShop.getId() + " で " + theShop.getName() + " が取得されました。");
		
		// 追加商品のインスタンスに取得したショップを紐づけ
		theProduct.setShop(theShop);
		
		System.out.println(theShop.getName()+ " の商品登録ページに移行します。");
		
		// 追加商品インスタンスをtheModelのAttributeに"productとして追加
    	theModel.addAttribute("product", theProduct);
		
    	// 商品登録ページを返す
		return "owner/create-product";
	}
	
	/* "/register-theProduct"エンドポイントへのPOSTリクエストを処理するメソッド 
	 * 新規商品登録ページから渡されたパラメータをもとにエラー・もしくは追加処理*/
	@PostMapping("/register-theProduct")
	/* 
	 * 渡されたリクエストパラメータにバリデーションを行う
	 * ビューから渡されたproductパラメータをProductクラスのtheProductオブジェクトにバインド
	 * BindingResult theBindingResultにバリデーションの結果を格納する
	 * @RequestParam("shopId")でビューからショップIDを受け取りint型のshopIdオブジェクトにバインド
	 * ビューに渡すためのModelオブジェクトを引数に持つ */
	public String registerTheProduct(@Valid @ModelAttribute("product") Product theProduct,
										BindingResult theBindingResult,
										@RequestParam("shopId") int shopId,
										Model theModel) {
		
		// バリデーションにエラーがある場合、
		if(theBindingResult.hasErrors()) {
			
			// ショップIDからショップ情報を取得
			Shop theShop = ecSiteService.findShopById(shopId);
			
			// 商品にショップ情報をセット
			theProduct.setShop(theShop);
			
			// 商品情報を"product"という名前でModelに追加
	    	theModel.addAttribute("product", theProduct);
	    	
	    	// 商品作成ページを再表示
			return "owner/create-product"; 
		}
		
		// ショップIDからショップ情報を取得
		Shop theShop = ecSiteService.findShopById(shopId);
		
		// 商品にショップ情報をセット
		theProduct.setShop(theShop);
		
		// 商品情報を保存
		ecSiteService.saveProduct(theProduct);
		
		// ショップページにリダイレクト
		return "redirect:/owner/shop?shopId=" + shopId;
	}
	
	
	// 商品編集ページの呼び出し
	@GetMapping("/edit-product")
	public String editProduct(@RequestParam("productId") int theId, Model theModel) {
		
		// 現在認証されているユーザーの情報を取得
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserId = auth.getName();
	    
	    // ユーザーIDからユーザーを特定して情報を取得
	    User currentUser = ecSiteService.findUserById(currentUserId);	    

	    // 商品IDで商品情報を取得
 		Product theProduct = ecSiteService.findProductById(theId);
 		
 		// 商品と紐づいているショップ情報を取得
 		Shop theShop = theProduct.getShop();
		
		// リクエストしたユーザーがショップページのオーナーではない場合アクセス拒否ページを表示
		if(!currentUser.equals(theShop.getUser())) {
			
			return "access-denied";
		}
		
		// ModelのAttributeに取得した商品情報を追加、"product"としてビューに渡す
		theModel.addAttribute("product", theProduct);
		
		// 商品編集ページを返す
		return "owner/edit-product";
	}
	
	// 商品編集内容で更新処理
	@PostMapping("/update-product")
	public String updateProduct(@ModelAttribute("product") Product theProduct, @RequestParam("productId") int theId) {
		
		// 更新前の商品情報を商品IDから商品情報を取得
		Product tempProduct = ecSiteService.findProductById(theId);
			
		System.out.println("更新前の商品情報:\n" + tempProduct + "\n");
		
		// 商品編集ページから受け取ったtheProductの商品名と商品説明プロパティを、tempProductの商品にセット
		tempProduct.setName(theProduct.getName());
		tempProduct.setDescription(theProduct.getDescription());
		tempProduct.setPrice(theProduct.getPrice());
		tempProduct.setInventory(theProduct.getInventory());
		
		System.out.println("更新後の商品情報:\n"	+ tempProduct + "\n");
		
		// 更新内容をDBの既存データに上書き保存
		ecSiteService.saveProduct(tempProduct);
		
		System.out.println("商品情報を更新しました。\n");
		
		// オーナーの商品ページを返す
		return "redirect:/owner/product?productId=" + tempProduct.getId();
	}
	
	
	// 商品削除ページの呼び出し
	@GetMapping("/delete-product")
	public String deleteProduct(@RequestParam("productId") int theId, Model theModel) {
		
		// 現在認証されているユーザーの情報を取得
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String currentUserId = auth.getName();
	    
	    // ユーザーIDからユーザーを特定して情報を取得
	    User currentUser = ecSiteService.findUserById(currentUserId);	    

	    // 商品IDで商品情報を取得
 		Product theProduct = ecSiteService.findProductById(theId);
 		
 		// 商品と紐づいているショップ情報を取得
 		Shop theShop = theProduct.getShop();
		
		// リクエストしたユーザーがショップページのオーナーではない場合アクセス拒否ページを表示
		if(!currentUser.equals(theShop.getUser())) {
			
			return "access-denied";
		}
		
		System.out.println("以下の商品を削除します。\n" + theProduct + "\n");
		
		// 取得したデータをそれぞれModelのアトリビュートに追加
		theModel.addAttribute("product", theProduct);
		
		return "owner/delete-product";
	}
	
	// 商品削除処理
	@PostMapping("/deleted-product")
	public String deletedProduct(@RequestParam("productId") int theId, Model theModel) {
		
		// 削除する商品情報を商品Dから商品情報を取得
		Product theProduct = ecSiteService.findProductById(theId);
		
		// 取得した商品をDBから削除
		ecSiteService.deleteProductById(theId);
		
		System.out.println("商品を削除しました。\n");
		
		// オーナーショップページを返す
		return "redirect:/owner/shop?shopId=" + theProduct.getShop().getId();
	}
}


















