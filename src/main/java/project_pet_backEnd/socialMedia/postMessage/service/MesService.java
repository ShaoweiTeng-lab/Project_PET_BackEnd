package project_pet_backEnd.socialMedia.postMessage.service;



import project_pet_backEnd.socialMedia.postMessage.dto.req.MesReq;
import project_pet_backEnd.socialMedia.postMessage.dto.res.MesRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.List;


public interface MesService {


    ResultResponse<String> create(Integer userId, Integer postId, MesReq mesReq);

    ResultResponse<MesRes> update(Integer userId, Integer mesId, MesReq mesReq);

    ResultResponse<String> delete(Integer userId, int messageId);

    ResultResponse<List<MesRes>> findMessageByPostId(int postId);

    ResultResponse<MesRes> getMesById(int messageId);


}
