package project_pet_backEnd.groomer.petgroomerschedule.dao;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.groomer.petgroomerschedule.dto.PgScheduleSearchList;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;

import java.util.List;

@Repository
public interface PetScheduleRepository extends JpaRepository<PetGroomerSchedule,Integer> {
    //查詢班表。
    @Query(value = "SELECT p.pgsId, g.pgName, p.pgId, p.pgsDate, p.pgsState FROM PetGroomerSchedule p JOIN PetGroomer g ON p.pgId = g.pgId WHERE YEAR(p.pgsDate) = :year AND MONTH(p.pgsDate) = :month AND p.pgId = :pgId ORDER BY p.pgsDate ASC", nativeQuery = true)
    List<PgScheduleSearchList> findByYearMonthAndPgIdOrderByDateAsc(@Param("year") int year, @Param("month") int month, @Param("pgId") int pgId);
}
