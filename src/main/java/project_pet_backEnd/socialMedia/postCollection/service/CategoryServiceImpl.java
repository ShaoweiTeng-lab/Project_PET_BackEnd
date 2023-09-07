package project_pet_backEnd.socialMedia.postCollection.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.post.dao.MediaDao;
import project_pet_backEnd.socialMedia.post.vo.MediaData;
import project_pet_backEnd.socialMedia.postCollection.dao.CategoryDao;
import project_pet_backEnd.socialMedia.postCollection.dao.UserTagCategoryDao;
import project_pet_backEnd.socialMedia.postCollection.dto.req.CategoryReq;
import project_pet_backEnd.socialMedia.postCollection.dto.req.PostTagReq;
import project_pet_backEnd.socialMedia.postCollection.dto.res.ColCategoryRes;
import project_pet_backEnd.socialMedia.postCollection.dto.res.PostColRes;
import project_pet_backEnd.socialMedia.postCollection.dto.res.TagRes;
import project_pet_backEnd.socialMedia.postCollection.vo.ColCategory;
import project_pet_backEnd.socialMedia.postCollection.vo.TagJoinKey;
import project_pet_backEnd.socialMedia.postCollection.vo.UserTagCategory;
import project_pet_backEnd.socialMedia.util.DateUtils;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserTagCategoryDao userTagCategoryDao;

    @Autowired
    private MediaDao mediaDao;

    // 建立收藏分類標籤
    @Override
    public ResultResponse<String> createCategory(int userId, CategoryReq categoryReq) {
        ResultResponse<String> response = new ResultResponse<>();

        try {
            ColCategory colCategory = new ColCategory();
            colCategory.setUserId(userId);
            colCategory.setCategoryName(categoryReq.getTagName());
            colCategory.setDescription(categoryReq.getTagDisc());
            categoryDao.save(colCategory);
            response.setMessage("標籤建立成功");
        } catch (Exception e) {
            response.setMessage("標籤建立失敗");
        }
        return response;
    }


    //刪除收藏分類標籤
    @Override
    public ResultResponse<String> deleteCategory(int userId, int pctId) {
        ResultResponse<String> response = new ResultResponse<>();
        ColCategory colCategory = categoryDao.findById(pctId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "沒有此收藏分類"));
        if (colCategory.getUserId() != userId)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "你不能刪除此標籤");
        //查看是否使用者有預設標籤分類，沒有就建立並加入預設標籤分類
        List<ColCategory> category = categoryDao.findColCategoryByCategoryNameAndUserId("我的收藏", userId);
        if (category != null) {
            if (category.get(0).getPctId() == pctId) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "你不能刪除預設標籤");
            }
        }
        try {
            categoryDao.deleteById(pctId);
            response.setMessage("標籤刪除成功");
        } catch (Exception e) {
            response.setMessage("標籤刪除失敗");
        }
        return response;
    }

    //更新收藏標籤內容

    @Override
    public ResultResponse<ColCategoryRes> updateCategory(int userId, int pctId, CategoryReq categoryReq) {
        ResultResponse<ColCategoryRes> response = new ResultResponse<>();
        ColCategory colCategory = categoryDao.findById(pctId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "沒有此收藏分類"));
        colCategory.setCategoryName(categoryReq.getTagName());
        colCategory.setDescription(categoryReq.getTagDisc());

        try {
            ColCategory saveResult = categoryDao.save(colCategory);
            ColCategoryRes colCategoryRes = new ColCategoryRes();
            colCategoryRes.setUpdateTime(DateUtils.dateTimeSqlToStr(saveResult.getUpdateTime()));
            colCategoryRes.setCategoryName(saveResult.getCategoryName());
            colCategoryRes.setDescription(saveResult.getDescription());
            response.setMessage(colCategoryRes);
        } catch (Exception e) {
            response.setMessage(null);
        }
        return response;
    }

    // 將貼文收藏加入標籤分類

    @Override
    public ResultResponse<String> createPostTagCol(int pcId, int userId, PostTagReq postTagReq) {
        Integer[] pctIds = postTagReq.getPctId();
        if (pctIds.length == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "請加入標籤");

        for (Integer pctId : pctIds) {
            ColCategory colCategory = categoryDao.findById(pctId).get();
            if (colCategory.getUserId() != userId) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "你沒權限使用此標籤");
            }
        }
        ResultResponse<String> response = new ResultResponse<>();
        try {
            for (Integer pctId : pctIds) {
                //檢查pctId 擁有者，避免讓其他人可以使用自己的標籤


                TagJoinKey tagJoinKey = new TagJoinKey(pcId, pctId);
                boolean exists = userTagCategoryDao.existsById(tagJoinKey);
                if (exists) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "請勿重複加入");
                UserTagCategory userTagCategory = new UserTagCategory();
                userTagCategory.setPcId(pcId);
                userTagCategory.setUserId(userId);
                userTagCategory.setPctId(pctId);
                userTagCategoryDao.save(userTagCategory);


            }
            response.setMessage("加入成功");
        } catch (Exception e) {
            response.setMessage("加入失敗");
        }
        return response;
    }

    // 將貼文收藏從標籤分類中移除

    @Override
    public ResultResponse<String> removePostTagCol(int pcId, int userId, PostTagReq postTagReq) {
        ResultResponse<String> response = new ResultResponse<>();
        Integer[] pctIds = postTagReq.getPctId();
        if (pctIds.length == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "請加入標籤");

        try {
            for (Integer pctId : pctIds) {
                TagJoinKey tagJoinKey = new TagJoinKey(pcId, pctId);
                UserTagCategory userTagCategory = userTagCategoryDao.findById(tagJoinKey).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "收藏不存在此分類中"));
                if (userTagCategory.getUserId() != userId)
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "你沒有權限刪除此收藏");
                userTagCategoryDao.deleteById(tagJoinKey);
            }
            response.setMessage("移除成功");
        } catch (Exception e) {
            response.setMessage("移除失敗");
        }
        return response;
    }

    //列出所有收藏分類標籤
    @Override
    public ResultResponse<List<TagRes>> queryAllTags(int userId) {
        List<ColCategory> categories = categoryDao.findAllByUserId(userId);
        if (categories.size() == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "沒有此使用者");
        List<TagRes> tagResList = new ArrayList<>();
        for (ColCategory category : categories) {
            TagRes tagRes = new TagRes();
            tagRes.setPctId(category.getPctId());
            tagRes.setUserId(category.getUserId());
            tagRes.setCategoryName(category.getCategoryName());
            tagRes.setDescription(category.getDescription());
            tagRes.setUpdateTime(DateUtils.dateTimeSqlToStr(category.getUpdateTime()));

            tagResList.add(tagRes);
        }

        ResultResponse<List<TagRes>> response = new ResultResponse<>();
        response.setMessage(tagResList);
        return response;
    }


    //透過標籤查詢所有貼文收藏資訊
    @Override
    public ResultResponse<List<PostColRes>> queryPostCollByTagId(int userId, int pctId) {
        List<UserTagCategory> tagCategories = userTagCategoryDao.findAllByUserIdAndPctId(userId, pctId);
        List<PostColRes> postColResList = new ArrayList<>();


        for (UserTagCategory tagCategory : tagCategories) {
            Integer postId = tagCategory.getPostCol().getPost().getPostId();
            List<MediaData> mediaDataList = mediaDao.findAllByPostId(postId);
//            byte[] data = mediaDataList.get(0).getMediaData();

            PostColRes postColRes = new PostColRes();
            postColRes.setPcId(tagCategory.getPcId());
            //預設為0
            postColRes.setPostPic(null);
            postColRes.setPostPicNumber(mediaDataList.size());
            postColRes.setUserName(tagCategory.getUser().getUserName());
            postColRes.setPostContent(tagCategory.getPostCol().getPost().getPostContent());
            postColRes.setCreateTime(DateUtils.dateTimeSqlToStr(tagCategory.getPostCol().getCreateTime()));

            postColResList.add(postColRes);
        }

        ResultResponse<List<PostColRes>> response = new ResultResponse<>();
        response.setMessage(postColResList);
        return response;
    }


}
