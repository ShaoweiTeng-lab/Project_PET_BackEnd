package project_pet_backEnd.groomer.appointment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project_pet_backEnd.groomer.appointment.vo.PetGroomerAppointment;

@Repository
public interface AppointmentRepository extends JpaRepository<PetGroomerAppointment,Integer> {

    PetGroomerAppointment findFirstByOrderByPgaNoDesc();
}
