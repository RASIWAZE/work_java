package com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "shops")
public class Shop {

	@Id  // このカラムが主キー
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //  AUTO_INCREMENTで設定されているカラム
	@Column(name = "id")  // idカラムと紐づけられたフィールド
    private int id;

	@NotBlank(message = "ショップ名が入力されていません")
    @Size(max = 50, message = "ショップ名は50文字以内で入力してください")
    @Column(name = "shop_name", nullable = false, length = 255)
    private String name;

	@Size(max = 400, message = "ショップ説明は400文字以内で入力してください")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdDate;

    @Column(name = "updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
    
    
    // Userクラスと、Shopテーブルのuser_idの値によって関連付けられる
    // ユーザーが削除されたらショップも削除されるが、ショップが削除されてもユーザーは削除されない
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH})
    //　UserクラスとShopクラス間で無限再帰が発生しないように配置
    @JsonBackReference
    @JoinColumn(name="user_id")
    private User user;
    
    // productクラスのShopフィールドの@JoinColumn(name="shop_id")によってこのクラスと複数のproductクラスのインスタンスが関連付けられる
    // ショップが削除されたら商品も削除されるが、商品が削除されてもショップは削除されない
    @OneToMany(mappedBy="shop",
    		   fetch = FetchType.LAZY,
    		   cascade= {CascadeType.ALL})
    //ShopクラスとProductクラス間で無限再帰が発生しないように配置
    @JsonManagedReference
    private List<Product> product;
    

   
    //　引数なしのコンストラクタ
	public Shop() {
	}

	// 引数を指定したコンストラクタ
	public Shop(String name, String description) {
		this.name = name;
		this.description = description;
	}

	
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	

	public User getUser() {
		return user;
	}
	
	public List<Product> getProduct() {
		return product;
	}
	

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	// データベースへの操作実行前に、ショップ作成日と更新日のフィールドに値を設定
    @PrePersist
    protected void onCreate() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
	
	

	@Override
	public String toString() {
		return "ショップ名= " + name + 
				", ショップID= " + id + 
				", ショップ説明= " + description +
				", オーナー情報= [" + user + "]" +
				", 作成日= " + createdDate + 
				", 最終更新日= " + updatedAt;
	}	
	
	// 関連づいたproductを追加するためのメソッド（ユーザーと紐づけられたショップ限定動作）
	public void addProduct(Product theProduct) {
		
		// productフィールドに値(関連付けられているproduct)がなかった場合、配列を作成
		if(product == null) {
			product = new ArrayList<>();
		}
		
		// Listのaddメソッドでproductフィールドに渡されたtheProductを追加
		product.add(theProduct);
		
		// theProductのShopプロパティに、このshopをセット
		theProduct.setShop(this);
	}
}
