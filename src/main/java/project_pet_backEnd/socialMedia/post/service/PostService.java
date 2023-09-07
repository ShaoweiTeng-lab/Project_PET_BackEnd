package project_pet_backEnd.socialMedia.post.service;



import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import project_pet_backEnd.socialMedia.post.dto.req.DeleteTagReq;
import project_pet_backEnd.socialMedia.post.dto.req.PostReq;
import project_pet_backEnd.socialMedia.post.dto.req.PostTagReq;
import project_pet_backEnd.socialMedia.post.dto.res.PostRes;
import project_pet_backEnd.socialMedia.post.dto.res.VideoRes;
import project_pet_backEnd.socialMedia.post.vo.MediaData;
import project_pet_backEnd.socialMedia.post.vo.POST;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.io.IOException;
import java.util.List;
import java.util.Set;


public interface PostService {

    ResultResponse<String> create(Integer userId, PostReq postReq);

    ResultResponse<PostRes> update(int userId, int postId, PostReq postReq);

    ResultResponse<String> delete(int userId, int postId);

    ResultResponse<PageRes<PostRes>> getAllPosts(int page);

    ResultResponse<PostRes> getPostById(int postId);


    ResultResponse<String> uploadFiles(MultipartFile[] files,int postId);

    ResultResponse<List<MediaData>> getMediaDataByPostId(int postId);

    ResultResponse<byte[]> getImageDataById(int postMediaId);

    VideoRes getVideoStreamById(int postMediaId, String range);

    byte[] getVideoDataById(int postMediaId);

    ResultResponse<PostRes> convertToPostRes(POST post);

    ResultResponse<PageRes<PostRes>> convertToPagePostRes(Page<POST> postPage);

    //建立貼文標籤
    ResultResponse<String> createPostTag(int postId, PostTagReq postTagReq);

    //刪除貼文標籤
    ResultResponse<String> deletePostTag(int postId, DeleteTagReq deleteTagReq);

    //查詢貼文標籤
    ResultResponse<Set<String>> queryAllTagsByPostId(int postId);

    //用標籤列出相關貼文(透過redis快取增加速度)
    ResultResponse<List<PostRes>> queryAllPostByTag(String tag);


}
