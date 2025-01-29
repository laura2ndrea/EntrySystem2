package campus.u2.entrysystem.people.application;

import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.Utilities.exceptions.InvalidInputException;
import campus.u2.entrysystem.Utilities.exceptions.NotFoundException;
import campus.u2.entrysystem.Utilities.exceptions.TypeMismatchException;
import campus.u2.entrysystem.carnet.application.CarnetRepository;
import campus.u2.entrysystem.carnet.domain.Carnet;
import campus.u2.entrysystem.company.application.CompanyRepository;
import campus.u2.entrysystem.registeredequipment.domain.RegisteredEquipment;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final CarnetRepository carnetRepository;
    private final CompanyRepository companyrepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, CarnetRepository carnetRepository, CompanyRepository companyrepository) {
        this.peopleRepository = peopleRepository;
        this.carnetRepository = carnetRepository; 
        this.companyrepository = companyrepository;
    }

    // To Save People 
    @Transactional
    public People savePeople(People people) {
        if (people == null) {
            throw new InvalidInputException("People object cannot be null");
        }
        return peopleRepository.savePeople(people);
    }
    
    @Transactional
    public RegisteredEquipment saveRegisteredEquipment(RegisteredEquipment registeredEquipment) {
        if (registeredEquipment == null) {
            throw new InvalidInputException("RegisteredEquipment object cannot be null");
        }
        return peopleRepository.saveRegisteredEquipment(registeredEquipment);
    }
    
    @Transactional
    public People savePeople(String name, String cedula, String telefono, Boolean personType, Company company) {
        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("Name cannot be empty");
        }
        if (cedula == null || cedula.isEmpty()) {
            throw new InvalidInputException("Cedula cannot be empty");
        }
        if (company == null) {
            throw new InvalidInputException("Company cannot be null");
        }
        People people = new People(personType, company, name, cedula, telefono);
        if (Boolean.TRUE.equals(personType)) {
            Carnet carnet = new Carnet();
            carnet.setPeople(people);
            people.setCarnet(carnet);
        }
        return peopleRepository.savePeople(people);
    }
    
    @Transactional
    public People savePeopleEquipment(People people, List<RegisteredEquipment> equipments) {
        if (people == null) {
            throw new InvalidInputException("People object cannot be null");
        }
        if (equipments == null || equipments.isEmpty()) {
            throw new InvalidInputException("Equipment list cannot be empty");
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

    @Transactional 
    public People addEquipmentToPerson(String personId, RegisteredEquipment equipment) {
        if (personId == null || personId.isEmpty()) {
            throw new InvalidInputException("Id cannot be empty");
        }
        try {
            Long personIdLong = Long.parseLong(personId);  
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("personId", "Long", "Invalid ID format: " + personId);
        }
        if (equipment == null) {
            throw new InvalidInputException("Equipment cannot be empty");
        }
        Long personIdLong = Long.parseLong(personId);  
        Optional<People> peopleOpt = peopleRepository.getPeopleById(personIdLong);
        if (peopleOpt.isPresent()) {
            peopleOpt.get().addEquipments(equipment);
            return peopleRepository.savePeople(peopleOpt.get());
        }
        throw new NotFoundException("Person with ID " + personId + " not found");
    }

    @Transactional
    public People removeEquipmentFromPerson(String personId, String equipmentId) {
        if (personId == null || personId.isEmpty()) {
            throw new InvalidInputException("Person ID cannot be empty");
        }
        if (equipmentId == null || equipmentId.isEmpty()) {
            throw new InvalidInputException("Equipment ID cannot be empty");
        }
        Long personIdLong;
        try {
            personIdLong = Long.parseLong(personId);
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("personId", "Long", "Invalid Person ID format: " + personId);
        }
        Long equipmentIdLong;
        try {
            equipmentIdLong = Long.parseLong(equipmentId);
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("equipmentId", "Long", "Invalid Equipment ID format: " + equipmentId);
        }
        Optional<People> personOpt = peopleRepository.getPeopleById(personIdLong);
        if (!personOpt.isPresent()) {
            throw new EntityNotFoundException("Person with ID " + personId + " not found");
        }
        People person = personOpt.get();
        RegisteredEquipment equipmentToRemove = person.getEquipments().stream()
                .filter(equipment -> equipment.getId().equals(equipmentIdLong))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Equipment with ID " + equipmentId + " not found"));
        person.removeEquiments(equipmentToRemove);
        return peopleRepository.savePeople(person);
    }

    public List<RegisteredEquipment> findEquipmentByPeopleId(String peopleId) {
        if (peopleId == null || peopleId.isEmpty()) {
            throw new InvalidInputException("Person ID cannot be empty");
        }
        Long peopleIdLong;
        try {
            peopleIdLong = Long.parseLong(peopleId);
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("peopleId", "Long", "Invalid Person ID format: " + peopleId);
        }
        List<RegisteredEquipment> equipmentList = peopleRepository.findEquipmentByPeopleId(peopleIdLong);
        if (equipmentList == null || equipmentList.isEmpty()) {
            throw new EntityNotFoundException("No equipment found for person with ID " + peopleId);
        }
        return equipmentList;
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
        if (cedula == null || cedula.isEmpty()) {
            throw new InvalidInputException("Incorrect cedula, please try again");
        }
        Optional<People> existingPeopleOpt = peopleRepository.getPeopleByCedula(cedula);
        if (existingPeopleOpt.isPresent()) {
            peopleRepository.deletePeople(existingPeopleOpt.get().getId());
        } else {
            throw new EntityNotFoundException("Person with cedula " + cedula + " not found");
        }
    }

    @Transactional
    public void deletePeopleId(String id) {
        if (id == null || id.isEmpty()) {
            throw new InvalidInputException("ID cannot be empty");
        }
        Long personId;
        try {
            personId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("id", "Long", "Invalid ID format: " + id);
        }
        Optional<People> optionalPeople = peopleRepository.getPeopleById(personId);
        if (optionalPeople.isPresent()) {
            People people = optionalPeople.get();
            Company company = people.getCompany();
            company.getPeopleList().remove(people);
            people.setCompany(null);
            peopleRepository.deletePeople(people.getId());
            companyrepository.saveCompany(company);
        } else {
            throw new NotFoundException("Person with ID " + personId + " not found");
        }
    }

    @Transactional 
    public List<People> listAllPeople() {
        return peopleRepository.listAllPeople();
    }

    // To get a People by ID
    @Transactional 
    public People getPeopleById(String id) {
        if (id == null || id.isEmpty()) {
            throw new InvalidInputException("ID cannot be empty");
        }
        Long personId;
        try {
            personId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("id", "Long", "Invalid ID format: " + id);
        }
        return peopleRepository.getPeopleById(personId)
                .orElseThrow(() -> new NotFoundException("Person with ID " + id + " not found"));
    }
    
    @Transactional 
    public People getPeopleByCedula(String cedula) {
        if (cedula == null || cedula.isEmpty()) {
            throw new InvalidInputException("Cedula cannot be empty");
        }

        return peopleRepository.getPeopleByCedula(cedula)
                .orElseThrow(() -> new NotFoundException("Person with cedula " + cedula + " not found"));
    }

    public Optional<RegisteredEquipment> getRegisteredEquipmentByid(String id) {
        if (id == null || id.isEmpty()) {
            throw new InvalidInputException("ID cannot be empty");
        }

        Long equipmentId;
        try {
            equipmentId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("id", "Long", "Invalid ID format: " + id);
        }

        return peopleRepository.getRegisteredEquipmentById(equipmentId);
    }
    
}
