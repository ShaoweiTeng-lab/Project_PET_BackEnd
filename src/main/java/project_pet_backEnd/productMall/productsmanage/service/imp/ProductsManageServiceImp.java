package project_pet_backEnd.productMall.productsmanage.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import project_pet_backEnd.productMall.productsmanage.dao.ProductsManageDao;
import project_pet_backEnd.productMall.productsmanage.dto.CreateProductRequest;
import project_pet_backEnd.productMall.productsmanage.service.ProductsManageService;

public class ProductsManageServiceImp implements ProductsManageService {

    @Autowired
    private ProductsManageDao productsManageDao;

    @Override
    public void insertProduct(CreateProductRequest createProductRequest) {


    }
}
