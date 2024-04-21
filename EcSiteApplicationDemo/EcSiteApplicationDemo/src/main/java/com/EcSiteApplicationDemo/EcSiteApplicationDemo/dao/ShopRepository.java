package com.EcSiteApplicationDemo.EcSiteApplicationDemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EcSiteApplicationDemo.EcSiteApplicationDemo.entity.Shop;

/* ショップエンティティに対するCRUD操作を行うためのJPAリポジトリ―を継承したインターフェイス
 * エンティティタイプとプライマリキーのデータ型を指定 */
public interface ShopRepository extends JpaRepository<Shop, Integer>{

}
