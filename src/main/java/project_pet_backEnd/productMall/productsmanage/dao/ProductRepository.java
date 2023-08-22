package project_pet_backEnd.productMall.productsmanage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project_pet_backEnd.productMall.productsmanage.vo.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    //新增商品資訊
    //查詢商品所有資訊
    //修改商品資訊
}
