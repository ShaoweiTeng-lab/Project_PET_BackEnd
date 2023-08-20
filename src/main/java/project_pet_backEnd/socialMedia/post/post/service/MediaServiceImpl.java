package project_pet_backEnd.socialMedia.post.post.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project_pet_backEnd.socialMedia.post.post.dao.MediaDao;
import project_pet_backEnd.socialMedia.post.post.utils.ImageUtils;
import project_pet_backEnd.socialMedia.post.post.vo.MediaData;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.io.IOException;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaDao mediaDao;

    // 上傳影片


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


    //獲取影片
}
