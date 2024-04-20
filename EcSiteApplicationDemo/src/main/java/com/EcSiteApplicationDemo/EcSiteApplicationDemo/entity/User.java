package com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity  // DBテーブルとの接続情報を定義するクラス
@Table(name="users")  //　name=のDBテーブルと紐づけ
public class User {
	
	@NotBlank(message="・ユーザー名が入力されていません")
	@Size(min = 5, max = 50, message = "・5文字以上50文字以下で入力してください")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", 
    			message = "・ユーザー名は英数字で入力してください")
	@Id  // このカラムが主キー
	@Column(nullable = false, length = 50)  // idカラムと紐づけられたフィールド
	private String id;
	
	@NotBlank(message="・メールアドレスが入力されていません")
	@Size(min=1, max = 60, message="・メールアドレスは1文字以上60文字以下で入力してください")
	@Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
				message="・メールアドレスが無効です")
	@Column(nullable=false, unique=true, length=60)
	private String email;
	
	@NotBlank(message="・パスワードが入力されていません")
	@Size(min = 10, max = 60, message = "・10文字以上60文字以下で入力してください")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", 
    		 message = "・パスワードはそれぞれ一文字以上の小文字と大文字の英字と数字で作成してください")
	@Column(nullable=false, unique=true, length=68)
	private String password;
	
	@Column(nullable = false)
	private boolean active;	
	
	@Column(name = "joined_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp joinedDate;

	@Column(name = "updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedAt;

	@OneToMany(mappedBy="user",
 		   fetch = FetchType.LAZY,
 		   cascade= {CascadeType.ALL})
	@JsonManagedReference
	private List<Role> roles;
	
	// Shopクラスのuserフィールドの@JoinColumn(name="user_id")によってこのクラスと複数のShopsクラスが関連付けられる
    // ユーザーが削除されたらショップも削除されるが、ショップが削除されてもユーザーは削除されない
    @OneToMany(mappedBy="user",
    		   fetch = FetchType.LAZY,
    		   cascade= {CascadeType.ALL})
    //　UserクラスとShopクラス間で無限再帰が発生しないように配置
    @JsonManagedReference
    private List<Shop> shop;
    
    

	// 引数なしのコンストラクタ
	public User() {
		
	}

	// 引数を指定したコンストラクタ
	public User(String id, String email, String password, boolean active) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.active = active;
	}


	// joined_dateとupdated_atはシステムが記録をつけていくためsetterは省略
	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}	
	
	public boolean isActive() {
		return active;
	}

	public Timestamp getJoinedDate() {
		return joinedDate;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	
	
	public void setId(String id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	// データベースへの操作実行前に、ショップ作成日と更新日のフィールドに値を設定
    @PrePersist
    protected void onCreate() {
    	joinedDate = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

	

	@Override  // 登録したユーザーの情報。テストのためpasswordも出力
	public String toString() {
		return "ユーザー名=　" + id + 
				", メールアドレス=　" + email +
				", パスワード=　" + password +
				", 有効状態=　" + active +
				", 作成日=　" + joinedDate + 
				", 最終更新日=　" + updatedAt;
	}

	
	public List<Shop> getShop() {
		return shop;
	}
	
	public void setShops(List<Shop> shop) {
		this.shop = shop;
	}

}