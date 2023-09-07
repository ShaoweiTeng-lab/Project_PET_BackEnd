package project_pet_backEnd.socialMedia.postCollection.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.post.dao.MediaDao;
import project_pet_backEnd.socialMedia.post.dao.PostDao;
import project_pet_backEnd.socialMedia.post.vo.MediaData;
import project_pet_backEnd.socialMedia.post.vo.POST;
import project_pet_backEnd.socialMedia.postCollection.dao.CategoryDao;
import project_pet_backEnd.socialMedia.postCollection.dao.PostCollectionDao;
import project_pet_backEnd.socialMedia.postCollection.dao.UserTagCategoryDao;
import project_pet_backEnd.socialMedia.postCollection.dto.res.PostColRes;
import project_pet_backEnd.socialMedia.postCollection.vo.ColCategory;
import project_pet_backEnd.socialMedia.postCollection.vo.PostCol;
import project_pet_backEnd.socialMedia.postCollection.vo.UserTagCategory;
import project_pet_backEnd.socialMedia.util.DateUtils;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostCollServiceImpl implements PostCollService {
    @Autowired
    private PostDao postDao;


    @Autowired
    private PostCollectionDao postCollectionDao;

    @Autowired
    private MediaDao mediaDao;


    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserTagCategoryDao userTagCategoryDao;

    // ================= 建立貼文收藏 ================= //

    @Override
    public ResultResponse<String> create(int postId, int userId) {
        //確認貼文是否存在
        POST post = postDao.findById(postId).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "此貼文不存在");
        });
        //確認是否是自己的貼文
        if (userId == post.getPostId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "你不能收藏自己的貼文");

        //查看是否使用者有預設標籤分類，沒有就建立並加入預設標籤分類
        List<ColCategory> category = categoryDao.findColCategoryByCategoryNameAndUserId("我的收藏", userId);


        //收藏標籤
        ColCategory saveColCategory = null;
        //分類標籤
        Integer pctId = null;

        //集合為空
        if (category.size() == 0) {
            ColCategory newColCategory = new ColCategory();
            newColCategory.setCategoryName("我的收藏");
            newColCategory.setDescription("這是預設資料夾");
            newColCategory.setUserId(userId);
            try {
                saveColCategory = categoryDao.save(newColCategory);
                pctId = saveColCategory.getPctId();
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "無法建立我的收藏");
            }
        } else {
            //獲得list第一個元素
            saveColCategory = category.get(0);
            pctId = category.get(0).getPctId();
        }

        //貼文只能收藏一次，但可以放入不同標籤分類中，預設放入我的藏標籤分類中
        List<PostCol> existPostCol = postCollectionDao.findByUserIdAndPostId(userId, postId);
        if (existPostCol.size() > 0) throw new ResponseStatusException(HttpStatus.CONFLICT, "你已經收藏過此貼文");

        //建立貼文收藏
        PostCol postCol = new PostCol();
        postCol.setPostId(postId);
        postCol.setUserId(userId);
        Integer saveColId;
        try {
            PostCol save = postCollectionDao.save(postCol);
            saveColId = save.getPcId();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "收藏失敗");
        }
        //將貼文收藏加入預設標籤
        UserTagCategory userTagCategory = new UserTagCategory();
        userTagCategory.setPcId(saveColId);
        userTagCategory.setUserId(userId);
        userTagCategory.setPctId(pctId);

        ResultResponse<String> response = new ResultResponse<>();
        try {

            userTagCategoryDao.save(userTagCategory);
            response.setMessage("收藏成功");
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("標籤加入失敗!");
        }
        return response;
    }

    // ================= 刪除貼文收藏 ================= //
    @Override
    public ResultResponse<String> delete(int pcId, int userId) {
        ResultResponse<String> response = new ResultResponse<>();
        PostCol postCol = postCollectionDao.findById(pcId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此筆貼文收藏"));
        if (postCol.getUserId() != userId)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "你沒有權限刪除此貼文收藏");

        try {
            postCollectionDao.deleteById(pcId);
            response.setMessage("刪除成功");
        } catch (Exception e) {
            response.setMessage("刪除失敗");
            e.printStackTrace();
        }
        return response;
    }

    // ================= 查詢所有貼文收藏清單，使用者點擊會跳回社群貼文頁面 ================= //
    @Override
    public ResultResponse<PageRes<PostColRes>> getPostCol(int userId) {
        ResultResponse<PageRes<PostColRes>> response = new ResultResponse<>();

        Page<PostCol> postColPage = postCollectionDao.findAllByUserId(userId, PageRequest.of(0, 10, Sort.Direction.ASC, "pcId"));
        List<PostColRes> postColResList = new ArrayList<>();
        List<PostCol> postCols = new ArrayList<>();


        if (postColPage != null && postColPage.hasContent()) {
            postCols = postColPage.getContent();
        }

        for (PostCol postCol : postCols) {
            Integer postId = postCol.getPostId();
            List<MediaData> mediaDataList = mediaDao.findAllByPostId(postId);

//            byte[] data = mediaDataList.get(0).getMediaData();

            //根據 media type 隨機選一張放封面 預設是一
            PostColRes postColRes = new PostColRes();
            postColRes.setPcId(postCol.getPcId());
            //會再加上圖片..
            postColRes.setPostPic(null);
            postColRes.setPostContent(postCol.getPost().getPostContent());
            postColRes.setPostPicNumber(mediaDataList.size());
            postColRes.setUserName(postCol.getUser().getUserName());
            postColRes.setCreateTime(DateUtils.dateTimeSqlToStr(postCol.getCreateTime()));

            postColResList.add(postColRes);
        }

        PageRes pageRes = new PageRes();
        pageRes.setResList(postColResList);
        pageRes.setCurrentPageNumber(postColPage.getNumber());
        pageRes.setPageSize(postColPage.getSize());
        pageRes.setTotalPage(postColPage.getTotalPages());
        pageRes.setCurrentPageDataSize(postColPage.getNumberOfElements());

        response.setMessage(pageRes);
        return response;
    }
}
