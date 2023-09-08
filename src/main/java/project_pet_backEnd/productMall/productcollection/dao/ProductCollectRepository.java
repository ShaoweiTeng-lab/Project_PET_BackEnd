package project_pet_backEnd.productMall.productcollection.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.productMall.productcollection.vo.ProductCollect;
import project_pet_backEnd.productMall.productcollection.vo.ProductCollectPk;

import java.util.Optional;

@Repository
public interface ProductCollectRepository extends JpaRepository<ProductCollect, Integer> {

    Optional<ProductCollect> findById(ProductCollectPk primaryKey);
}
