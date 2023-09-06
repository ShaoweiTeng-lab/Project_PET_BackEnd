package project_pet_backEnd.groomer.groomerleave.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.groomer.groomerleave.vo.GroomerLeave;

@Repository
public interface GroomerLeaveRepository extends JpaRepository<GroomerLeave,Integer> {

    GroomerLeave findByLeaveNo(Integer leaveNo);

}
