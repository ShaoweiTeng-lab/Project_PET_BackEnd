package project_pet_backEnd.socialMedia.post.dto.res;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class VideoRes {
    //header
    //CONTENT_TYPE + video/+fileType
    String fileType;
    //CONTENT_LENGTH (fileSize or end range)
    String contentLength;
    String fileSize;
    String rangeStart;
    String rangeEnd;
    HttpStatus httpStatus;
    //CONTENT_RANGE  BYTES + " " + rangeStart + "-" + rangeEnd + "/" + fileSize
    String contentRange = " " + rangeStart + "-" + rangeEnd + "/" + fileSize;
    //data readByteRange(fileKey, rangeStart, rangeEnd)
    byte[] data;
}
