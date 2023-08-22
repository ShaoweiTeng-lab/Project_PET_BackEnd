package project_pet_backEnd.socialMedia.report.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import project_pet_backEnd.socialMedia.report.vo.PostReport;

import java.util.List;

public interface PostReportQueryDao extends PagingAndSortingRepository<PostReport, Integer> {
    Page<PostReport> findAllByPostRepostStatus(Pageable pageable, Integer postRepostStatus);

    List<PostReport> findByPostId(Integer postId);

    //計算貼文檢舉次數
    Integer countByPostId(Integer postId);

}
