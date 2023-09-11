package project_pet_backEnd.productMall.productsmanage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;

import java.util.List;

@Repository
public interface ProductPicRepository extends JpaRepository<ProductPic, Integer> {

    List<ProductPic> findByPdNo(Integer pdNo);

//    List<ProductPic> findByPdPicNo(Integer pdPicNo);

    List<ProductPic> deleteByPdPicNo(Integer pdPicNo);
}
