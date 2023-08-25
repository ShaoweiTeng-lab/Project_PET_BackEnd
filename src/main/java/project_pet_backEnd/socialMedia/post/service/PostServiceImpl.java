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
import project_pet_backEnd.socialMedia.post.dao.PostTagDao;
import project_pet_backEnd.socialMedia.post.dto.req.DeleteTagReq;
import project_pet_backEnd.socialMedia.post.dto.req.PostReq;
import project_pet_backEnd.socialMedia.post.dto.req.PostTagReq;
import project_pet_backEnd.socialMedia.post.dto.res.PostRes;
import project_pet_backEnd.socialMedia.post.dto.res.VideoRes;
import project_pet_backEnd.socialMedia.post.vo.MediaData;
import project_pet_backEnd.socialMedia.post.vo.POST;
import project_pet_backEnd.socialMedia.util.DateUtils;
import project_pet_backEnd.socialMedia.util.ImageUtils;
import project_pet_backEnd.socialMedia.util.PageRes;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private MediaDao mediaDao;

    @Autowired
    private PostTagDao postTagDao;

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


    // ==================== user上傳貼文檔案 ====================//
    @Override
    public ResultResponse<String> uploadFiles(MultipartFile[] files, int postId) {
        ResultResponse<String> resultResponse = new ResultResponse<>();
        if (files.length > 5) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "請不要上傳超過五個檔案");
        try {
            for (MultipartFile file : files) {
                mediaDao.save(MediaData.builder()
                        .postId(postId)
                        .mediaName(file.getOriginalFilename())
                        .mediaType(file.getContentType())
                        .mediaData(file.getBytes())
                        .build());
            }

            resultResponse.setMessage("檔案上傳成功");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "檔案上傳失敗");
        }
        return resultResponse;
    }

    // ==================== user查詢貼文檔案 ====================//
    @Override
    public ResultResponse<List<MediaData>> getMediaDataByPostId(int postId) {
        ResultResponse<List<MediaData>> response = new ResultResponse<>();
        List<MediaData> mediaDataList;
        try {
            mediaDataList = mediaDao.findAllByPostId(postId);
            for (MediaData mediaData : mediaDataList) {
                mediaData.setMediaData(mediaData.getMediaData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "解壓縮失敗");
        }
        response.setMessage(mediaDataList);
        return response;
    }

    // ==================== user查詢單一圖片 ====================//
    @Override
    public ResultResponse<byte[]> getImageDataById(int postMediaId) {
        ResultResponse<byte[]> response = new ResultResponse<>();
        MediaData mediaData = mediaDao.findById(postMediaId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "沒有此檔案"));
        byte[] file = mediaData.getMediaData();
        response.setMessage(file);
        return response;
    }


    // ============================= user查詢單一影片range串流 =============================//

    public static final int CHUNK_SIZE = 314700;

    @Override
    public VideoRes getVideoStreamById(int postMediaId, String range) {
        MediaData mediaData = mediaDao.findById(postMediaId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "沒有此檔案"));
        //回傳資料格式
        VideoRes videoRes = new VideoRes();
        //解壓縮
        try {
            mediaData.setMediaData(mediaData.getMediaData());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "解壓縮失敗");
        }
        //共同資料格式
        videoRes.setFileType(mediaData.getMediaType());

        //檔案所有bytes
        byte[] mediaBytes = mediaData.getMediaData();
        //檔案size
        long fileSize = (long) mediaData.getMediaData().length;
        //byte起始點
        long rangeStart = 0;
        //byte終點
        long rangeEnd;


        //這邊判斷range
        if (range == null) {
            rangeEnd = fileSize;
            videoRes.setRangeStart(String.valueOf(rangeStart));
            videoRes.setRangeEnd(String.valueOf(rangeEnd));
            videoRes.setContentLength(String.valueOf(fileSize));
            videoRes.setData(readByteRange(mediaBytes, rangeStart, rangeEnd));
            HttpStatus httpStatus = HttpStatus.OK;
            videoRes.setHttpStatus(httpStatus);
        } else {

            //如果有傳入range 用-分隔，這邊目的是要拿到使用者的開始範圍
            // Range: bytes=200-1000, 2000-6576, 19000- substring 從第6個字元開始擷取後面的字串
            //將資料分割 bytes=200,1000
            String[] ranges = range.split("-");
            //取得起始元素200
            rangeStart = Long.parseLong(ranges[0].substring(6));
            videoRes.setRangeStart(String.valueOf(rangeStart));
            if (ranges.length > 1) {
                //1000
                rangeEnd = Long.parseLong(ranges[1]);
                videoRes.setRangeStart(String.valueOf(rangeEnd));
            } else {
                rangeEnd = rangeStart + CHUNK_SIZE;
                videoRes.setRangeStart(String.valueOf(rangeEnd));
            }
            //回傳最小值
            rangeEnd = Math.min(rangeEnd, fileSize - 1);
            final byte[] data = readByteRange(mediaBytes, rangeStart, rangeEnd);
            videoRes.setData(data);
            final String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
            videoRes.setContentLength(contentLength);
            HttpStatus httpStatus = HttpStatus.PARTIAL_CONTENT;
            videoRes.setHttpStatus(httpStatus);
            if (rangeEnd >= fileSize) {
                httpStatus = HttpStatus.OK;
                videoRes.setHttpStatus(httpStatus);
            }

        }
        return videoRes;
    }


    //使用者能讀取的byte[] 範圍
    public byte[] readByteRange(byte[] data, long start, long end) {
        byte[] result;
        try {
            //使用者想要要拿到的資源
            result = new byte[(int) (end - start) + 1];
            //透過arraycopy複製資源並回傳，如果不複製，會參照到原本的obj
            System.arraycopy(data, (int) start, result, 0, (int) (end - start) + 1);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "傳輸錯誤");
        }

        return result;
    }

    //這個影片無法移動影片播放位置
    @Override
    public byte[] getVideoDataById(int postMediaId) {
        MediaData mediaData = mediaDao.findById(postMediaId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "沒有此檔案"));
        mediaData.setMediaData(mediaData.getMediaData());
        return mediaData.getMediaData();
    }

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
    @Override
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

    @Override
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

    @Override
    public ResultResponse<String> createPostTag(int postId, PostTagReq postTagReq) {
        ResultResponse<String> response = new ResultResponse<>();
        try {
            postTagDao.createPostTag(postId, postTagReq);
            response.setMessage("新增成功");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "加入失敗");
        }
        return response;
    }

    @Override
    public ResultResponse<String> deletePostTag(int postId, DeleteTagReq deleteTagReq) {
        ResultResponse<String> response = new ResultResponse<>();
        try {
            postTagDao.deletePostTag(postId, deleteTagReq);
            response.setMessage("刪除成功");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "刪除失敗");
        }
        return response;
    }

    @Override
    public ResultResponse<Set<String>> queryAllTagsByPostId(int postId) {
        ResultResponse<Set<String>> response = new ResultResponse<>();
        Set<String> tags;
        try {
            tags = postTagDao.queryAllTagsByPostId(postId);
            response.setMessage(tags);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "標籤搜尋失敗");
        }
        return response;
    }

    @Override
    public ResultResponse<List<PostRes>> queryAllPostByTag(String tag) {
        ResultResponse<List<PostRes>> response = new ResultResponse<>();
        List<PostRes> postResList = new ArrayList<>();
        List<POST> postList = new ArrayList<>();
        try {
            postList = postTagDao.queryAllPostByTag(tag);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "貼文搜尋失敗");
        }
        for (POST post : postList) {
            ResultResponse<PostRes> resultResponse = convertToPostRes(post);
            PostRes postRes = new PostRes();
            postRes.setPostId(post.getPostId());
            postRes.setUserId(post.getUserId());
            postRes.setUserPic(ImageUtils.base64Encode(post.getUser().getUserPic()));
            postRes.setPostStatus(post.getPostStatus());
            postRes.setCreateTime(DateUtils.dateTimeSqlToStr(post.getCreateTime()));
            postRes.setUpdateTime(DateUtils.dateTimeSqlToStr(post.getUpdateTime()));
            postRes.setPostContent(post.getPostContent());
            postRes.setUserName(post.getUser().getUserName());

            postResList.add(postRes);
        }
        response.setMessage(postResList);
        return response;
    }
}

