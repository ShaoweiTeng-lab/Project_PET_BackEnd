package project_pet_backEnd.productMall.productsmanage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;

@Repository
public interface CreateProductPicRepository extends JpaRepository<ProductPic, Integer> {

}
