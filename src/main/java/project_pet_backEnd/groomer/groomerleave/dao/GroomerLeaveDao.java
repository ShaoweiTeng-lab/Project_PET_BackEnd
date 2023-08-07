package project_pet_backEnd.groomer.groomerleave.dao;

import project_pet_backEnd.groomer.groomerleave.dto.GroomerLeaveQueryParameter;
import project_pet_backEnd.groomer.groomerleave.dto.PGLeaveSearchRes;
import project_pet_backEnd.groomer.groomerleave.vo.GroomerLeave;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;

import java.util.List;

public interface GroomerLeaveDao {

    public void insertNewGroomerLeave(GroomerLeave groomerLeave);

    public void updateGroomerLeaveByLeaveNo(GroomerLeave groomerLeave);

    public List<PGLeaveSearchRes> getAllGroomerLeave();

    public List<PGLeaveSearchRes> getAllGroomerLeaveWithSearch(GroomerLeaveQueryParameter groomerLeaveQueryParameter);

    public List<GroomerLeave> getGroomerLeaveByPgId(Integer PgId);

}
