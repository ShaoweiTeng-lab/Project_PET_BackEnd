package project_pet_backEnd.socialMedia.post.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post_media")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaData {
    /*
     * POST_MEDIA_ID int AI PK
     * POST_ID int
     * POST_MEDIA longblob
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_MEDIA_ID")
    Integer postMediaId;
    @Column(name = "POST_ID")
    Integer postId;
    @Column(name = "MEDIA_NAME")
    private String mediaName;
    @Column(name = "MEDIA_TYPE")
    private String mediaType;
    @Column(name = "POST_MEDIA")
    @Lob
    private byte[] mediaData;

}
