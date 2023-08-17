package project_pet_backEnd.productMall.productsmanage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project_pet_backEnd.productMall.productsmanage.dto.CreateProductInfoRequest;
import project_pet_backEnd.productMall.productsmanage.vo.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
