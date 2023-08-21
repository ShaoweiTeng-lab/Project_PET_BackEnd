package project_pet_backEnd.socialMedia.report.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import project_pet_backEnd.socialMedia.report.vo.PostReport;

public interface PostReportQueryDao extends PagingAndSortingRepository<PostReport, Integer> {
    Page<PostReport> findAll(Pageable pageable);

}
