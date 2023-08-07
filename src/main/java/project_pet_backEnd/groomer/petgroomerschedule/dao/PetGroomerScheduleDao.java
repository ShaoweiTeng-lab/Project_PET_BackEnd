package project_pet_backEnd.groomer.petgroomerschedule.dao;

import project_pet_backEnd.groomer.petgroomerschedule.dto.PGScheduleQueryParameter;
import project_pet_backEnd.groomer.petgroomerschedule.vo.PetGroomerSchedule;

import java.util.List;

public interface PetGroomerScheduleDao {

    public void insertNewPgSchedule (PetGroomerSchedule petGroomerSchedule);

    public void updatePgScheduleByPgsId(PetGroomerSchedule petGroomerSchedule);

    public List<PetGroomerSchedule> getAllPgSchedule();

    public List<PetGroomerSchedule> getAllPgScheduleWithSearch(PGScheduleQueryParameter pgScheduleQueryParameter);

    public List<PetGroomerSchedule> getPgScheduleByPgId(Integer PgId);

}
