package com.EcSiteApplicationDemo.EcSiteApplicationDemo.service;

import java.util.List;

import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Product;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Role;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Shop;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.User;

public interface EcSiteService {

	////////////////////////////////////////
	// ユーザーに対する操作
	////////////////////////////////////////
	
	// 登録されているすべてのユーザーのリストを返す
	List<User> findAllUsers();
		
	//　ユーザー単体の登録
	void registerUser(User user);

    // ユーザーIDでユーザーを検索
    User findUserById(String theId);
    
    // ユーザーIDでユーザーを削除
    void deleteUserById(String theId);
    
    //　ユーザーのメールアドレスを検索
    User findByEmail(String email);
    
    
	////////////////////////////////////////
	// ロールに対する操作
	////////////////////////////////////////
    
    // ユーザーIDで取得したユーザーが特定のロールを持っているか確認
    boolean hasRole(String theId, String theRole);
    
    // ユーザーIDでユーザーの持つロールを検索
    List<Role> getRolesByUserId(String userId);
    
    // ロールを保存
    void saveRole(Role role);
    
	
	////////////////////////////////////////
	// ショップに対する操作
	////////////////////////////////////////
    
    List<Shop> findAllShops();

    void saveShop(Shop theShop);
    
    Shop findShopById(int theId);

    void deleteShopById(int theId);
    
    
	////////////////////////////////////////
	// 商品に対する操作
	////////////////////////////////////////
    
    List<Product> findAllProducts();
    
    void saveProduct(Product theProduct);
    
    Product findProductById(int theId);
    
    List<Product> findProductsForShop(int ShopId);

    void deleteProductById(int theId);
    
}
