package com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import validation.DescriptionCount;
import validation.NameCount;


@Entity
@Table(name = "products")
@JsonPropertyOrder({"商品名", "商品説明", "価格", "在庫数"})
public class Product {
	
	@Id  // このカラムが主キー
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //  AUTO_INCREMENTで設定されているカラム
	@Column(name = "id")  // idカラムと紐づけられたフィールド
	@JsonIgnore
    private int id;


	@Pattern(regexp =  "^[a-zA-Z0-9\\u30A1-\\u30FF\\u3041-\\u3096\\u4E00-\\u9FFF]+$", 
			message = "・特殊文字・スペースを含まない日英数字で入力してください")
	@NameCount
    @Column(name = "product_name", nullable = false, length = 255)
	@JsonProperty("商品名")
    private String name;

	@Pattern(regexp =  "^[a-zA-Z0-9\\s\\u30A1-\\u30FF\\u3041-\\u3096\\u4E00-\\u9FFF\\u3000-\\u303F\\uFF01-\\uFF60\\u3001-\\u303F.,!]+$", 
				message = "・日英数字で入力してください")
	@DescriptionCount
    @Column(name = "description", columnDefinition = "TEXT")
	@JsonProperty("商品説明")
    private String description;
    
	// priceをString型に変更してperseIntでメソッド内バリデーションを作成
	@NotNull(message = "・価格を入力してください")
	@Min(value= 0, message = "・0以上で入力してください")
	@Max(value = 99999999, message = "・99999999以下で入力してください")
    @Column(name = "price", nullable = false)
	@JsonProperty("価格")
    private int price;
	
	@NotNull(message = "・在庫数を入力してください")
	@Min(value= 0, message = "・0以上で入力してください")
	@Max(value = 99999999, message = "・99999999以下で入力してください")
    @Column(name = "inventory", nullable = false)
	@JsonProperty("在庫数")
    private int inventory;

    @Column(name = "created_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonIgnore
    private Timestamp createdDate;

    @Column(name = "updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @JsonIgnore
    private Timestamp updatedAt;
    
    
    // shopクラスと、productテーブルのShop_idの値によって関連付けられる
    // ショップが削除されたら商品も削除されるが、商品が削除されてもショップは削除されない
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH})
    //ShopクラスとProductクラス間で無限再帰が発生しないように配置
    @JsonBackReference
    @JoinColumn(name="shop_id")
    @JsonIgnore
    private Shop shop;

    
    //　引数なしのコンストラクタ
	public Product() {
	}

	// 引数を指定したコンストラクタ
	public Product(String name, String description, int inventory, int price) {
		this.name = name;
		this.description = description;
		this.inventory = inventory;
		this.price = price;
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

	public int getInventory() {
		return inventory;
	}

	public int getPrice() {
		return price;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public Shop getShop() {
		return shop;
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

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
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
		return "商品名= " + name + 
				", 商品ID= " + id + 
				", 商品説明= " + description + 
				", 在庫数= " + inventory + 
				", 登録ショップ情報= [" + shop + "]" + 
				", 作成日= " + createdDate + 
				", 最終更新日= " + updatedAt;		
	}	
}
