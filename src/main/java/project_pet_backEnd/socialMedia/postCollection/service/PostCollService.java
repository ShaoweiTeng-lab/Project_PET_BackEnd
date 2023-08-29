package project_pet_backEnd.socialMedia.postCollection.service;


import project_pet_backEnd.socialMedia.postCollection.dto.res.PostColRes;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

public interface PostCollService {

    ResultResponse<String> create(int postId, int userId);

    ResultResponse<String> delete(int pcId, int userId);

    ResultResponse<PageRes<PostColRes>> getPostCol(int userId);


}
