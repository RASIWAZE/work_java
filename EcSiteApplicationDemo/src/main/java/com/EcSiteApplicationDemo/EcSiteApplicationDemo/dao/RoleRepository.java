package com.EcSiteApplicationDemo.EcSiteApplicationDemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Role;
import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.RoleId;

/* ロールエンティティに対するCRUD操作を行うためのJPAリポジトリ―を継承したインターフェイス
 * エンティティタイプとプライマリキーのデータ型を指定 */
public interface RoleRepository extends JpaRepository<Role, RoleId> {
	
	List<Role> findByUserId(String userId);
}

