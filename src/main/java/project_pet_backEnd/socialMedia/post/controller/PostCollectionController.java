package project_pet_backEnd.socialMedia.post.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.socialMedia.post.vo.PostCol;

@RestController
@RequestMapping("user/social/collection")
public class PostCollectionController {

    /**
     * 收藏貼文
     */
    @PostMapping("/post")
    public PostCol create() {

        return null;
    }


    /**
     * 刪除收藏的貼文
     */


    /**
     * 查詢 user 收藏的貼文
     */




}
