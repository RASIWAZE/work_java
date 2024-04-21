package com.EcSiteApplicationDemo.EcSiteApplicationDemo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EcSiteApplicationDemo.EcSiteApplicationDemo.dao.ProductRepository;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.dao.RoleRepository;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.dao.ShopRepository;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.dao.UserRepository;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Product;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Role;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Shop;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.User;


@Service
public class EcSiteServiceImpl implements EcSiteService {

	private UserRepository userRepository;
	private ShopRepository shopRepository;
	private ProductRepository productRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	public EcSiteServiceImpl(UserRepository userRepository, 
							 ShopRepository shopRepository, 
							 ProductRepository productRepository,
							 RoleRepository roleRepository) {
    	
		this.userRepository = userRepository;
    	this.shopRepository = shopRepository;
    	this.productRepository = productRepository;
    	this.roleRepository = roleRepository;
    }
	
	
	////////////////////////////////////////
	// ユーザーに対する操作
	////////////////////////////////////////
	
	// JPAリポジトリにはTransactionalの機能が備わっているので@Transactionalは記載不要
	
	@Override
	// すべてのユーザーのリストを返すメソッド(動作確認済み)
	public List<User> findAllUsers(){
		
		// userRepositoryのfindAllメソッドを使ってすべてのユーザーのリストを取得
		return userRepository.findAll();
	}
	
	@Override
	// ユーザーを指定したユーザーIDで検索するメソッド
	public User findUserById(String theId) {

	    // userRepositoryを使用して、指定されたIDを持つユーザーを検索
		// 検索したIDのユーザーが存在しない場合があるので、検索結果をOptionalオブジェクト（nullの可能性がある値をラップする）のresultUserに代入
	    Optional<User> resultUser = userRepository.findById(theId);

	    // ユーザーオブジェクトを初期化
	    User theUser = null;
	    
	    // resultUserに代入されたOptionalオブジェクトがnullでなかった場合
	    // (OptionalクラスのisPresentメソッドでOptionalオブジェクトに値が存在するか確認する)
	    if(resultUser.isPresent()) {

	        // resultUserに代入されたOptionalオブジェクトからユーザーオブジェクトを取得してtheUserに代入
	    	// (OptionalクラスのgetメソッドでOptionalオブジェクトの持つ値を取得する)
	        theUser = resultUser.get();
	    }
	    
	    // 指定したユーザーIDで見つかったユーザーデータを返す
	    return theUser;
	}
	
	
	@Override
	// 新しいユーザーの登録
	public void registerUser(User user) {
		
		System.out.println("保存を開始します");
		
        // ユーザー登録ページで入力された内容でユーザーをDBに登録
        userRepository.save(user);

        // 登録したユーザーに付与するロールのインスタンスを作成
        Role theRole = new Role();
        
        // 登録したユーザーのidを取得してロールインスタンスのuserIdプロパティにセット
        theRole.setUserId(user.getId());
        
        // 登録したユーザーのroleプロパティにユーザーとしてのロールをセット
        theRole.setRole("ROLE_USER");
        
        System.out.println("以下のユーザーを登録します");
		System.out.println(user);
        
        // 登録したユーザーに紐づいた新しいロールデータをDBに保存
        roleRepository.save(theRole);
	}


	@Override
	/* findUserByIdのようにIDがnullである可能性もあるが、戻り値がない（結果を返す必要がない）ので
	 * 値がnullであるかどうかに関わらず操作が実行できるためOptionalが必要ない */
	public void deleteUserById(String theId) {
		
		userRepository.deleteById(theId);
	}
	
	
	@Override
	public User findByEmail(String email) {
		
		User theUser = userRepository.findByEmail(email);
		
		return theUser;
	}
	
	
	////////////////////////////////////////
	// ロールに対する操作
	////////////////////////////////////////
	
	// ユーザーの持つロールを取得
	@Override
	public List<Role> getRolesByUserId(String userId) {
		
	    return roleRepository.findByUserId(userId);
	}
	

	// ユーザーが特定のロールを持っているか真偽を返す
	@Override
	public boolean hasRole(String userId, String theRole){

		// 渡されたユーザーIDからロールを取得
		List<Role> roles = getRolesByUserId(userId);		
		
		//　取得したロールの中に指定のロールがないか確認し、あればtrueを返す
		for (Role tempRole : roles) {
            if (tempRole.getRole().equals(theRole)) {
                return true;
            }
        }		
        // 指定されたロールが見つからなかった場合はfalseを返す
        return false;		
	}
	
	// ロールを保存
	@Override
	public void saveRole(Role role) {
		
		roleRepository.save(role);
	}
	
	
	////////////////////////////////////////
	// ショップに対する操作
	////////////////////////////////////////
	
	@Override
	public List<Shop> findAllShops(){
		
		return shopRepository.findAll();
	};
	
	@Override
	public void saveShop(Shop theShop) {
		
		shopRepository.save(theShop);
	}

	@Override
	public Shop findShopById(int theId) {
	    // shopRepositoryを使用して、指定されたIDを持つショップを検索
		// 検索したIDのショップが存在しない場合があるので、検索結果をOptionalオブジェクト（nullの可能性がある値をラップする）のresultShopに代入
	    Optional<Shop> resultShop = shopRepository.findById(theId);

	    // ユーザーオブジェクトを初期化
	    Shop theShop = null;
	    
	    // resultShopに代入されたOptionalオブジェクトがnullでなかった場合
	    // (OptionalクラスのisPresentメソッドでOptionalオブジェクトに値が存在するか確認する)
	    if(resultShop.isPresent()) {

	        // resultShopに代入されたOptionalオブジェクトからユーザーオブジェクトを取得してtheShopに代入
	    	// (OptionalクラスのgetメソッドでOptionalオブジェクトの持つ値を取得する)
	        theShop = resultShop.get();
	    } 
	    // resultShopに代入されたOptionalオブジェクトがnullだった(検索したIDのショップが存在しなかった)場合
	    else {
	        
	    	// ショップが見つからなかったエラー文言を表示
	        throw new RuntimeException("お探しのショップは見つかりませんでした。");
	    }
	    
	    // 指定したユーザーIDで見つかったショップデータを返す
	    return theShop;
	}


	@Override
	public void deleteShopById(int theId) {
		
		shopRepository.deleteById(theId);
	}
	
	
	
	////////////////////////////////////////
	// 商品に対する操作
	////////////////////////////////////////
	
	@Override
	public List<Product> findAllProducts(){
		
		return productRepository.findAll();
	};
	
	// ショップIDから商品を検索
	@Override
	public List<Product> findProductsForShop(int ShopId){
		
		Shop theShop = findShopById(ShopId);
		
		List<Product> products = theShop.getProduct();
		
		return products;
	}
	
	@Override
	public void saveProduct(Product theProduct) {
		
		productRepository.save(theProduct);
	}

	@Override
	public Product findProductById(int theId) {

	    // productRepositoryを使用して、指定されたIDを持つ商品を検索
		// 検索したIDの商品が存在しない場合があるので、検索結果をOptionalオブジェクト（nullの可能性がある値をラップする）のresultProductに代入
	    Optional<Product> resultProduct = productRepository.findById(theId);

	    // Productオブジェクトを初期化
	    Product theProduct = null;
	    
	    // resultProductに代入されたOptionalオブジェクトがnullでなかった場合
	    // (OptionalクラスのisPresentメソッドでOptionalオブジェクトに値が存在するか確認する)
	    if(resultProduct.isPresent()) {

	        // resultProductに代入されたOptionalオブジェクトから商品オブジェクトを取得してtheProductに代入
	    	// (OptionalクラスのgetメソッドでOptionalオブジェクトの持つ値を取得する)
	        theProduct = resultProduct.get();
	    } 
	    // resultProductに代入されたOptionalオブジェクトがnullだった(検索したIDの商品が存在しなかった)場合
	    else {
	        
	    	// 商品が見つからなかったエラー文言を表示
	        throw new RuntimeException("お探しの商品は見つかりませんでした。");
	    }
	    
	    // 指定した商品IDで見つかった商品データを返す
	    return theProduct;
	}


	@Override
	public void deleteProductById(int theId) {
		
		productRepository.deleteById(theId);
	}
	
	

}

