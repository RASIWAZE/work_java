package com.EcSiteApplicationDemo.EcSiteApplicationDemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Product;

/* プロダクトエンティティに対するCRUD操作を行うためのJPAリポジトリ―を継承したインターフェイス
 * エンティティタイプとプライマリキーのデータ型を指定 */
public interface ProductRepository extends JpaRepository<Product, Integer>{

}

