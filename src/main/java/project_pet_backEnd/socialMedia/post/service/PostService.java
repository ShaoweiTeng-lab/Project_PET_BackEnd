package project_pet_backEnd.socialMedia.post.service;



import project_pet_backEnd.socialMedia.post.dto.req.UpPostRequest;
import project_pet_backEnd.socialMedia.post.vo.POST;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;


public interface PostService {

    ResultResponse<POST> create(POST post);

    ResultResponse<POST> update(int postId, UpPostRequest upPostRequest);

    ResultResponse<String> delete(int postId);

    ResultResponse<List<POST>> getAllPosts();

    ResultResponse<POST> getPostById(int postId);
}
