package project_pet_backEnd.groomer.PetGroomer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.groomer.PetGroomer.service.PetGroomerService;

@RestController
@Validated

public class GroomerController {

    @Autowired
    PetGroomerService petGroomerService;


}
