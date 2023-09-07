package project_pet_backEnd.socialMedia.post.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.socialMedia.post.dto.req.DeleteTagReq;
import project_pet_backEnd.socialMedia.post.dto.req.PostTagReq;
import project_pet_backEnd.socialMedia.post.vo.POST;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class PostTagDaoImpl implements PostTagDao {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PostDao postDao;

    public static final String POST_TAG_KEY = "post:%d:tags";
    public static final String GET_POST_BY_TAGS_KEY = "tag:%s:posts";

    @Override
    public void createPostTag(int postId, PostTagReq postTagReq) {
        String[] tags = postTagReq.getTags();
        String postTags = String.format(POST_TAG_KEY, postId);

        //建立標籤有的貼文id
        for (String tag : tags) {
            String getPostByTagKey = String.format(GET_POST_BY_TAGS_KEY, tag);
            redisTemplate.opsForSet().add(getPostByTagKey, String.valueOf(postId));
        }
        //加入貼文標籤
        for (String tag : tags) {
            redisTemplate.opsForSet().add(postTags, tag);
        }

    }

    @Override
    public void deletePostTag(int postId, DeleteTagReq deleteTagReq) {
        //刪除貼文標籤，一次刪一個
        String postTags = String.format(POST_TAG_KEY, postId);
        //刪除tag有的貼文
        String getPostByTagKey = String.format(GET_POST_BY_TAGS_KEY, deleteTagReq.getTag());
        redisTemplate.opsForSet().remove(postTags, deleteTagReq.getTag());
        redisTemplate.opsForSet().remove(getPostByTagKey, String.valueOf(postId));
    }

    @Override
    public Set<String> queryAllTagsByPostId(int postId) {
        String postTags = String.format(POST_TAG_KEY, postId);
        return redisTemplate.opsForSet().members(postTags);
    }

    @Override
    public List<POST> queryAllPostByTag(String tag) {
        String getPostByTagKey = String.format(GET_POST_BY_TAGS_KEY, tag);
        Set<String> postIds;
        try {
            postIds = redisTemplate.opsForSet().members(getPostByTagKey);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到相關貼文");
        }
        List<Integer> queryIds = new ArrayList<>();
        for (String postId : postIds) {
            Integer integer = Integer.valueOf(postId);
            queryIds.add(integer);
        }
        List<POST> posts;

        try {
            posts = postDao.findAllById(queryIds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "發生錯誤");
        }

        return posts;
    }
}
