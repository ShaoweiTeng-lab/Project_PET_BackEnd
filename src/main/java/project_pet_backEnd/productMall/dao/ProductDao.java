package project_pet_backEnd.productMall.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import project_pet_backEnd.productMall.vo.Product;

import java.net.CacheRequest;


public interface ProductDao {
    void insertProduct(Product product);
}
