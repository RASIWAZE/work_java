package com.EcSiteApplicationDemo.EcSiteApplicationDemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.User;

/* ユーザーエンティティに対するCRUD操作を行うためのJPAリポジトリ―を継承したインターフェイス
 * エンティティタイプとプライマリキーのデータ型を指定 */
public interface UserRepository extends JpaRepository<User, String>{

	User findByEmail(String email);

}
