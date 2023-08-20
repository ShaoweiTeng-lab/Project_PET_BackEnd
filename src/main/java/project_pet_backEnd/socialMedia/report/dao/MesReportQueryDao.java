package project_pet_backEnd.socialMedia.report.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import project_pet_backEnd.socialMedia.report.vo.MesReport;

public interface MesReportQueryDao extends PagingAndSortingRepository<MesReport, Integer> {
    Page<MesReport> findAll(Pageable pageable);

}
