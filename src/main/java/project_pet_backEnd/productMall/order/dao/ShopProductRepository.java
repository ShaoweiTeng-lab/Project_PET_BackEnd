package project_pet_backEnd.productMall.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.order.dto.ProductForCartDTO;
import project_pet_backEnd.productMall.productsmanage.vo.Product;

@Repository
public interface ShopProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT new project_pet_backEnd.productMall.order.dto.ProductForCartDTO( " +
            "p.pdName, p.pdPrice, pic.pdPic) " +
            "from Product p " +
            "JOIN project_pet_backEnd.productMall.productsmanage.vo.ProductPic pic ON p.pdNo = pic.pdNo " +
            "where pic.pdOrderList = 1 and p.pdNo = :pdNo")
    ProductForCartDTO findByPoNo(@Param("pdNo") Integer pdNo);
}
