package project_pet_backEnd.groomer.petgroomer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.groomer.petgroomercollection.vo.Portfolio;
import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,Integer> {

    List<Portfolio> findByPgId(Integer pgId);
}
