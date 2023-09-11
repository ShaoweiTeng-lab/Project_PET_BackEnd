package project_pet_backEnd.productMall.productsmanage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.productsmanage.dto.ProductListResponse;
import project_pet_backEnd.productMall.productsmanage.vo.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    //新增商品資訊
    //查詢商品所有資訊
    //修改商品資訊
    boolean existsByPdName(String pdName);

    //列出單一商品的所有資料。
    Product findByPdNo (Integer pdNo);

}
