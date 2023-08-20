package project_pet_backEnd.socialMedia.post.post.service;


import org.springframework.web.multipart.MultipartFile;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.io.IOException;

public interface MediaService {


    // 上傳影片


    //上傳圖片
    ResultResponse<String> uploadImage(MultipartFile file) throws IOException;

    //上傳圖片-base64
//    ResultResponse<String> uploadBase64Image(MultipartFile file) throws IOException;

    //get 圖片


    //get 影片
}
