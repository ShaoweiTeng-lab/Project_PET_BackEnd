package project_pet_backEnd.socialMedia.post.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.post.dao.MediaDao;
import project_pet_backEnd.socialMedia.post.dao.PostDao;
import project_pet_backEnd.socialMedia.post.dto.req.PostReq;
import project_pet_backEnd.socialMedia.post.dto.res.PostRes;
import project_pet_backEnd.socialMedia.post.vo.MediaData;
import project_pet_backEnd.socialMedia.post.vo.POST;
import project_pet_backEnd.socialMedia.util.DateUtils;
import project_pet_backEnd.socialMedia.util.ImageUtils;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private MediaDao mediaDao;

    // ==================== user發布貼文 ====================//
    @Override
    public ResultResponse<String> create(Integer userId, PostReq postReq) {
        POST post = new POST();
        post.setUserId(userId);
        post.setPostContent(postReq.getContent());
        //預設狀態為0
        post.setPostStatus(0);
        POST savePost = null;
        ResultResponse<String> resultResponse = new ResultResponse<>();
        try {
            savePost = postDao.save(post);
            resultResponse.setMessage("發布成功");
        } catch (Exception e) {
            resultResponse.setMessage("發布失敗");
        }
        return resultResponse;
    }

    // ==================== user上傳貼文圖片 ====================//


    //上傳圖片
    public ResultResponse<String> uploadImage(MultipartFile file) throws IOException {
        MediaData mediaData = mediaDao.save(MediaData.builder()
                .mediaName(file.getOriginalFilename())
                .mediaType(file.getContentType())
                .mediaData(ImageUtils.compressImage(file.getBytes()))
                .build());
        ResultResponse<String> resultResponse = new ResultResponse<>();
        if (mediaData != null) {
            resultResponse.setMessage("file uploaded successfully!");
            return resultResponse;

        } else {
            resultResponse.setMessage("file uploaded fail");
            return resultResponse;
        }
    }


    //上傳圖片 base64

    //顯示圖片


    // ==================== user上傳貼文影片 ====================//


    // ==================== user修改貼文內容 ====================//
    @Override
    public ResultResponse<PostRes> update(int userId, int postId, PostReq postReq) {
        POST post = postDao.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此貼文"));
        if (post.getUserId() != userId) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "你沒有權限修改此貼文");
        post.setPostContent(postReq.getContent());
        POST savePost = postDao.save(post);
        ResultResponse<PostRes> response = convertToPostRes(savePost);
        return response;
    }


    // ====================  user刪除貼文 ====================//
    @Override
    public ResultResponse<String> delete(int userId, int postId) {
        POST post = postDao.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此貼文"));
        if (post.getUserId() != userId) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "你沒有權限刪除此貼文");
        ResultResponse<String> resultResponse = new ResultResponse<>();
        try {
            postDao.deleteById(postId);
        } catch (Exception e) {
            resultResponse.setMessage("刪除失敗: " + e.getMessage());
        }
        resultResponse.setMessage("刪除成功");
        return resultResponse;
    }


    // ====================  user查詢所有貼文 ====================//
    @Override
    public ResultResponse<PageRes<PostRes>> getAllPosts(int page) {
        Page<POST> postPage = postDao.findAllByPostStatus(PageRequest.of(page, 10, Sort.by("updateTime").descending()), 0);
        ResultResponse<PageRes<PostRes>> response = convertToPagePostRes(postPage);
        return response;
    }


    // ====================  user查詢單一貼文 ====================//
    @Override
    public ResultResponse<PostRes> getPostById(int postId) {
        POST post = postDao.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此貼文"));
        ResultResponse<PostRes> response = convertToPostRes(post);
        return response;
    }

    // ====================  貼文res轉換 ====================//

    public ResultResponse<PostRes> convertToPostRes(POST post) {
        PostRes postRes = new PostRes();
        postRes.setPostId(post.getPostId());
        postRes.setUserId(post.getUserId());
        postRes.setPostContent(post.getPostContent());
        postRes.setUserName(post.getUser().getUserName());
        postRes.setPostStatus(post.getPostStatus());
        postRes.setUserPic(ImageUtils.base64Encode(post.getUser().getUserPic()));
        postRes.setCreateTime(DateUtils.dateTimeSqlToStr(post.getCreateTime()));
        postRes.setUpdateTime(DateUtils.dateTimeSqlToStr(post.getUpdateTime()));
        ResultResponse<PostRes> resultResponse = new ResultResponse<>();
        resultResponse.setMessage(postRes);
        return resultResponse;
    }

    public ResultResponse<PageRes<PostRes>> convertToPagePostRes(Page<POST> postPage) {
        List<POST> postList = new ArrayList<>();
        List<PostRes> postResResultList = new ArrayList<>();

        if (postPage != null && postPage.hasContent()) {
            postList = postPage.getContent();
        }

        for (POST post : postList) {
            PostRes postRes = new PostRes();
            postRes.setPostId(post.getPostId());
            postRes.setUserId(post.getUserId());
            postRes.setPostContent(post.getPostContent());
            postRes.setUserName(post.getUser().getUserName());
            postRes.setPostStatus(post.getPostStatus());
            postRes.setUserPic(ImageUtils.base64Encode(post.getUser().getUserPic()));
            postRes.setCreateTime(DateUtils.dateTimeSqlToStr(post.getCreateTime()));
            postRes.setUpdateTime(DateUtils.dateTimeSqlToStr(post.getUpdateTime()));
            postResResultList.add(postRes);
        }

        PageRes pageRes = new PageRes();
        pageRes.setResList(postResResultList);
        pageRes.setCurrentPageNumber(postPage.getNumber());
        pageRes.setPageSize(postPage.getSize());
        pageRes.setTotalPage(postPage.getTotalPages());
        pageRes.setCurrentPageDataSize(postPage.getNumberOfElements());

        ResultResponse<PageRes<PostRes>> response = new ResultResponse<>();
        response.setMessage(pageRes);
        return response;
    }
}

