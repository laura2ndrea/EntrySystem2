package campus.u2.entrysystem.people.application;

import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import campus.u2.entrysystem.carnet.application.CarnetRepository;
import campus.u2.entrysystem.carnet.domain.Carnet;
import campus.u2.entrysystem.registeredequipment.domain.RegisteredEquipment;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleService  {

    private final PeopleRepository peopleRepository;
    private final CarnetRepository carnetRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, CarnetRepository carnetRepository) {
        this.peopleRepository = peopleRepository;
        this.carnetRepository = carnetRepository; 
    }

// To Save People 
  
    public People savePeople(People people) {
        if (people == null){
            throw new GlobalException("Empty object, please try again ");
        }
        return peopleRepository.savePeople(people);
    }

    public People savePeople(String name, String cedula, String telefono, Boolean personType, Company company) {

        if (name == null || name.isEmpty()) {
            throw new GlobalException("Name cannot be empty");
        }
        if (cedula == null || cedula.isEmpty()) {
            throw new GlobalException("Cedula cannot be empty");
        }
        if (company == null) {
            throw new GlobalException("Company cannot be empty");
        }
        People people = new People(personType, company, name, cedula, telefono);
        if (Boolean.TRUE.equals(personType)) {
            Carnet carnet = new Carnet(); 
            carnet.setPeople(people);
            people.setCarnet(carnet);
        }
        return peopleRepository.savePeople(people);

    }

    public People savePeopleEquipment(People people, List<RegisteredEquipment> equipments
    ) {
        if (people == null) {
            throw new GlobalException("Person cannot be empty ");
        }

        if (equipments == null || equipments.isEmpty()) {
            throw new GlobalException("Equipment list cannot be empty");
        }

        for (RegisteredEquipment equipment : equipments) {
            RegisteredEquipment eq = new RegisteredEquipment();
            eq.setSerial(equipment.getSerial());
            eq.setRegistrationDate(equipment.getRegistrationDate());
            eq.setDescription(equipment.getDescription());
            eq.setPeople(people);
            people.addEquipments(eq);
        }
        return peopleRepository.savePeople(people);
    }


    public People addEquipmentToPerson(Long personId, RegisteredEquipment equipment) {

        if (personId == null) {
            throw new GlobalException("Id cannot be empty");
        }
        if (equipment == null) {
            throw new GlobalException("Equipment cannot be empty");
        }
        Optional<People> peopleOpt = peopleRepository.getPeopleById(personId);
        if (peopleOpt.isPresent()) {
            peopleOpt.get().addEquipments(equipment);
            return peopleRepository.savePeople(peopleOpt.get());
        }
        throw new GlobalException("Person with ID " + personId + " not found");
    }

    @Transactional
   public People removeEquipmentFromPerson(Long personId, Long equipmentId) {
        if (personId == null) {
            throw new GlobalException("Id cannot be empty");
        }
        if (equipmentId == null) {
            throw new GlobalException("Equipment cannot be empty");
        }

        Optional<People> personOpt = peopleRepository.getPeopleById(personId);
        if (personOpt.isPresent()) {
            People person = personOpt.get();
            RegisteredEquipment equipmentToRemove = person.getEquipments().stream()
                    .filter(equipment -> equipment.getId().equals(equipmentId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Equipment with ID " + equipmentId + " not found."));
            person.removeEquiments(equipmentToRemove);
            return peopleRepository.savePeople(person);
        } else {
            throw new RuntimeException("Person with ID " + personId + " not found.");
        }
    }

    public List<RegisteredEquipment> findEquipmentByPeopleId(Long peopleId) {

        if (peopleId == null) {
            throw new GlobalException("The entered ID is incorrect, please try again");
        }
        return peopleRepository.findEquipmentByPeopleId(peopleId);
    }

    //este metodo se hace desde el controlador 
//// to update people
//    @Transactional
//    public People updatePeople(People peopleToUpdate) {
//        Optional<People> existingPeopleOpt = peopleRepository.getPeopleById(peopleToUpdate.getId());
//        if (existingPeopleOpt.isPresent()) {
//            People existinPeople = existingPeopleOpt.get();
//            existinPeople.setName(peopleToUpdate.getName());
//            existinPeople.setCedula(peopleToUpdate.getCedula());
//            existinPeople.setPersonType(peopleToUpdate.getPersonType());
//            return peopleRepository.savePeople(existinPeople);
//
//        } else {
//            throw new RuntimeException("People with ID " + peopleToUpdate.getId() + " not found.");
//
//        }
//
//    }
    // To delete a  People
    @Transactional
    public void deletePeople(String cedula) {

        if (cedula == null) {
            throw new GlobalException("Incorrect cedula, please try again");

        }

        Optional<People> existingPeopleOpt = peopleRepository.getPeopleByCedula(cedula);
        if (existingPeopleOpt.isPresent()) {
            peopleRepository.deletePeople(existingPeopleOpt.get().getId());
        } else {
            throw new GlobalException("Unexpected error, please try again");
        }

    }

    // To list all porters
    public List<People> listAllPeople() {
        return peopleRepository.listAllPeople();
    }

    // To get a People by ID
    public People getPeopleById(Long id) {
        if (id == null) {
            throw new GlobalException("ID cannot be empty");
        }
        return peopleRepository.getPeopleById(id)
                .orElseThrow(() -> new GlobalException("Id not found "));
    }

    public People getPeopleByCedula(String cedula) {
        if (cedula == null) {
            throw new GlobalException("ID cannot be empty");
        }
        return peopleRepository.getPeopleByCedula(cedula)
                .orElseThrow(() -> new RuntimeException("People with ID " + cedula + " not found."));
    }

}
