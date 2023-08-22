package project_pet_backEnd.socialMedia.report.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import project_pet_backEnd.socialMedia.report.vo.MesReport;

import java.util.List;

public interface MesReportQueryDao extends PagingAndSortingRepository<MesReport, Integer> {
    //列出所有留言檢舉清單
    Page<MesReport> findAllByMessageReportStatus(Pageable pageable, Integer messageReportStatus);

    //找出message的檢舉清單
    List<MesReport> findByMessageId(Integer messageId);

    //計算留言檢舉次數
    Integer countByMessageId(Integer messageId);

}