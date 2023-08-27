package project_pet_backEnd.productMall.order.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.productMall.order.dto.CartItemDTO;
import project_pet_backEnd.productMall.order.dto.ProductForCartDTO;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ShopCartRepository {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ShopProductRepository shopProductRepository;

    private HashOperations<String, String, String> hashOperations;
    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public String getCartKey(Integer shoppingCart_userId){
        return "cart:" + shoppingCart_userId;
    }

    public void changCartAmount(Integer shoppingCart_userId, Integer pdNo, Integer quantity) {
        String redisKey = getCartKey(shoppingCart_userId);
        String field = String.valueOf(pdNo);

        // 從 Redis 中獲取購物車內容
        String existingQuantity = hashOperations.get(redisKey, field);

        if (quantity < 0) {
            // 如果數量為負數，表示要從購物車扣除該數量
            if (existingQuantity == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "此商品不在購物車中，無法扣除數量");
            }

            Integer currentQuantity = Integer.parseInt(existingQuantity);
            Integer newQuantity = currentQuantity + quantity;

            if (newQuantity <= 0) {
                // 如果扣除後數量小於 0，則從購物車中刪除該商品
                hashOperations.delete(redisKey, field);
            } else {
                hashOperations.put(redisKey, field, String.valueOf(newQuantity));
            }
        } else {
            // 如果數量大於等於 0，則執行類似原本的操作
            if (existingQuantity == null) {
                if(shopProductRepository.findByPoNo(pdNo) == null){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "本商城無此商品");
                }
                hashOperations.put(redisKey, field, String.valueOf(quantity));
            } else {
                Integer newQuantity = Integer.parseInt(existingQuantity) + quantity;

                if (newQuantity <= 0) {
                    hashOperations.delete(redisKey, field);
                } else {
                    hashOperations.put(redisKey, field, String.valueOf(newQuantity));
                }
            }
        }

    }

    public List<CartItemDTO> getCart(Integer shoppingCart_userId){
        String redisKey = getCartKey(shoppingCart_userId);
        Map<String, String> cartData = hashOperations.entries(redisKey);
        List<CartItemDTO> cartItems = new ArrayList<>();

        for (Map.Entry<String, String> entry : cartData.entrySet()) {
            Integer pdNo = Integer.parseInt(entry.getKey());
            Integer quantity = Integer.parseInt(entry.getValue());

            // Fetch product details based on productId
            ProductForCartDTO productForCartDTO = shopProductRepository.findByPoNo(pdNo);
            // Create CartItemDTO and add it to cartItems list
            if(productForCartDTO != null){
                CartItemDTO cartItem = new CartItemDTO();
                cartItem.setPdNo(pdNo);
                cartItem.setProductName(productForCartDTO.getPdName());
                cartItem.setProductPrice(productForCartDTO.getPdPrice());
                cartItem.setProductImage(productForCartDTO.getPics());
                cartItem.setQuantity(quantity);
                cartItems.add(cartItem);
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "查無此商品");
            }
        }
        return cartItems;
    }


    public void deleteCart(Integer shoppingCart_userId){
        String redisKey = getCartKey(shoppingCart_userId);
        System.out.println(redisKey);
        Long size = hashOperations.size(redisKey);
        if (size != null && size > 0) {
            hashOperations.delete(redisKey);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "購物車無清單");
        }
    }

}
