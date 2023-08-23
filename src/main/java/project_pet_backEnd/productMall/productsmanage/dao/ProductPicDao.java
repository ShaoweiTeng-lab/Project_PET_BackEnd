package project_pet_backEnd.productMall.productsmanage.dao;

import project_pet_backEnd.productMall.productsmanage.vo.ProductPic;

import java.util.List;


public interface ProductPicDao {

    /*
     * 批次新增商品圖片
     * @param List<ProductPic> 要新增的商品圖片們
     */
    void batchinsertProductPic(List<ProductPic> pics);

    /*
     *編輯商品顯示(獲取)商品圖片(pic)
     */
    public List<ProductPic> getAllProductPic(Integer PdNo);

    /*
     *修改商品圖片
     */
    public void batchupdateproductPicByPdNo(List<ProductPic> pics);
}
