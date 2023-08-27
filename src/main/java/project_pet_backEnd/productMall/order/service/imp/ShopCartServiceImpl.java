package project_pet_backEnd.productMall.order.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project_pet_backEnd.productMall.order.dao.ShopCartRepository;
import project_pet_backEnd.productMall.order.dto.CartItemDTO;
import project_pet_backEnd.productMall.order.service.ShopCartService;

import java.util.List;

@Service
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired
    ShopCartRepository shopCartRepository;
    @Override
    public void changCartAmount(Integer shoppingCart_userId, Integer pdNo, Integer quantity) {
        shopCartRepository.changCartAmount(shoppingCart_userId, pdNo, quantity);
    }

    @Override
    public List<CartItemDTO> getCart(Integer shoppingCart_userId) {

        return shopCartRepository.getCart(shoppingCart_userId);
    }

    @Override
    public void deleteCart(Integer shoppingCart_userId) {
        if(shoppingCart_userId != null){
            shopCartRepository.deleteCart(shoppingCart_userId);
        }
    }

}
