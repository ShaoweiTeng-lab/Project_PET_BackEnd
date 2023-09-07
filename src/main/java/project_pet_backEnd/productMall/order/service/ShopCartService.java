package project_pet_backEnd.productMall.order.service;


import project_pet_backEnd.productMall.order.dto.CartItemDTO;

import java.util.List;

public interface ShopCartService {

    public abstract void addProduct(Integer shoppingCart_userId, Integer pdNo);

    public abstract void changCartAmount(Integer shoppingCart_userId, Integer pdNo, Integer quantity);

    public abstract List<CartItemDTO> getCart(Integer shoppingCart_userId);

    public abstract void deleteCart(Integer shoppingCart_userId);

    public abstract void deleteProduct(Integer shoppingCart_userId, Integer pdNO);

}
