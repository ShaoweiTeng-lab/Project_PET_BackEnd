package project_pet_backEnd.productMall.productsmanage.dao.imp;

import org.springframework.data.jpa.repository.JpaRepository;
import project_pet_backEnd.productMall.productsmanage.vo.Product;

public interface ProductsManageRepository extends JpaRepository<Product, Integer> {

}
