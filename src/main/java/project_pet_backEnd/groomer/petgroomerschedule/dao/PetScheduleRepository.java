package project_pet_backEnd.groomer.petgroomerschedule.dao;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;
import java.util.List;

@Repository
public interface PetScheduleRepository extends JpaRepository<PetGroomerSchedule,Integer> {
    //查詢班表。
    @Query(value = "SELECT p.PGS_ID, g.PG_NAME, p.PG_ID, p.PGS_DATE, p.PGS_STATE FROM pet_groomer_schedule p JOIN pet_groomer g ON p.PG_ID = g.PG_ID WHERE YEAR(p.PGS_DATE) = :year AND MONTH(p.PGS_DATE) = :month AND p.PG_ID = :pgId ORDER BY p.PGS_DATE ASC", nativeQuery = true)
    List<String> findDataByYearMonthAndPgId(@Param("year") int year, @Param("month") int month, @Param("pgId") int pgId);

}
