package project_pet_backEnd.socialMedia.postCollection.service;

import project_pet_backEnd.socialMedia.postCollection.dto.req.CategoryReq;
import project_pet_backEnd.socialMedia.postCollection.dto.req.PostTagReq;
import project_pet_backEnd.socialMedia.postCollection.dto.res.ColCategoryRes;
import project_pet_backEnd.socialMedia.postCollection.dto.res.PostColRes;
import project_pet_backEnd.socialMedia.postCollection.dto.res.TagRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;

public interface CategoryService {

    // =================== 標籤分類 ====================//
    ResultResponse<String> createCategory(int userId, CategoryReq categoryReq);

    ResultResponse<String> deleteCategory(int userId, int pctId);

    ResultResponse<ColCategoryRes> updateCategory(int userId, int pctId, CategoryReq categoryReq);


    // =================== 標籤收藏 ====================//

    ResultResponse<String> createPostTagCol(int pcId, int userId, PostTagReq postTagReq);

    ResultResponse<String> removePostTagCol(int pcId, int userId, PostTagReq postTagReq);

    ResultResponse<List<TagRes>> queryAllTags(int userId);

    ResultResponse<List<PostColRes>> queryPostCollByTagId(int userId, int pctId);


}
