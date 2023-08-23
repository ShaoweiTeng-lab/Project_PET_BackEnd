package project_pet_backEnd.socialMedia.post.service;



import org.springframework.web.multipart.MultipartFile;
import project_pet_backEnd.socialMedia.post.dto.req.PostReq;
import project_pet_backEnd.socialMedia.post.dto.res.PostRes;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.io.IOException;


public interface PostService {

    ResultResponse<String> create(Integer userId, PostReq postReq);

    ResultResponse<PostRes> update(int userId, int postId, PostReq postReq);

    ResultResponse<String> delete(int userId, int postId);

    ResultResponse<PageRes<PostRes>> getAllPosts(int page);

    ResultResponse<PostRes> getPostById(int postId);

    // 上傳影片


    //上傳圖片
    ResultResponse<String> uploadImage(MultipartFile file) throws IOException;

    //上傳圖片-base64
//    ResultResponse<String> uploadBase64Image(MultipartFile file) throws IOException;

    //get 圖片


    //get 影片
}
