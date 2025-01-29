package campus.u2.entrysystem.people.infrastructure;

import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.people.application.PeopleRepository;
import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.registeredequipment.domain.RegisteredEquipment;
import campus.u2.entrysystem.registeredequipment.infrastructure.RegisteredEquipmentJpaRepositpry;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PeopleAdapter implements PeopleRepository {

    private final PeopleJpaRepository peopleJpaRepository;
    
    private final RegisteredEquipmentJpaRepositpry registeredEquipmentJpaRepositpry;

    @Autowired
    public PeopleAdapter(PeopleJpaRepository peopleJpaRepository, RegisteredEquipmentJpaRepositpry registeredEquipmentJpaRepositpry) {
        this.peopleJpaRepository = peopleJpaRepository;
        this.registeredEquipmentJpaRepositpry =registeredEquipmentJpaRepositpry;
    }

    @Transactional
    @Override
    public People savePeople(People people) {
        return peopleJpaRepository.save(people);

    }

    @Transactional
    @Override
    public People savePeople(String name, String cedula, String telefono, Boolean personType, Company company) {
        People people = new People(personType, company, name, cedula, telefono);
        return peopleJpaRepository.save(people);
    }

   
    
    @Transactional
    @Override
    public People savePeopleEquipment(People people, List<RegisteredEquipment> equipments
    ) {
        for (RegisteredEquipment equipment : equipments) {
            RegisteredEquipment eq = new RegisteredEquipment();
            eq.setSerial(equipment.getSerial());
            eq.setRegistrationDate(equipment.getRegistrationDate());
            eq.setDescription(equipment.getDescription());
            eq.setPeople(people);
            people.addEquipments(eq);
        }
        return peopleJpaRepository.save(people);
    }

    
   
    
    
    @Transactional
    @Override
   public People addEquipmentToPerson(Long personId, RegisteredEquipment equipment) {
        Optional<People> peopleOpt = peopleJpaRepository.findById(personId);
        if (peopleOpt.isPresent()) {
            peopleOpt.get().addEquipments(equipment);
            return peopleJpaRepository.save(peopleOpt.get());
        }
        return null;
       
    }
   
   
    @Transactional
    @Override

    public People removeEquipmentFromPerson(Long personId, Long equipmentId) {
        Optional<People> personOpt = peopleJpaRepository.findById(personId);
        if (personOpt.isPresent()) {
            People person = personOpt.get();
            RegisteredEquipment equipmentToRemove = person.getEquipments().stream()
                    .filter(equipment -> equipment.getId().equals(equipmentId))
                    .findFirst()
                    .orElse(null);
            if (equipmentToRemove != null) {
                person.removeEquiments(equipmentToRemove);
                return peopleJpaRepository.save(person);
            }
        }
        return null;
    }
    
    
    
    @Override
      public List<RegisteredEquipment> findEquipmentByPeopleId(Long peopleId) {
       
        return peopleJpaRepository.findEquipmentByPeopleId(peopleId);
    }

      
        
      
    @Override
     @Transactional
    public void deletePeople(Long id) {
        Optional<People> existingPeopleOpt = peopleJpaRepository.findById(id);
        if (existingPeopleOpt.isPresent()) {
            peopleJpaRepository.delete(existingPeopleOpt.get());
        }  
        
    }
    
   
    
    
    
    @Override
    public List<People> listAllPeople() {
        
        return peopleJpaRepository.findAll();
        
    }

    
    
    
 
    
    @Override
    public Optional<People> getPeopleById(Long id) {
        return peopleJpaRepository.findById(id);
    }

    
    
    
  
    
    
    
    
    
    @Override
    public Optional<People> getPeopleByCedula(String cedula) {
        return peopleJpaRepository.findByCedula(cedula);
    }

    
    
    
    
  
    
    @Transactional
    @Override
    public People savePeople(String name, String cedula, String telefono, Boolean personType) {
        People people = new People(personType, name, cedula, telefono);
        return peopleJpaRepository.save(people);
    }

    @Override
    public RegisteredEquipment saveRegisteredEquipment(RegisteredEquipment registeredEquipment) {

        return registeredEquipmentJpaRepositpry.save(registeredEquipment);
        
        
    }

    @Override
    public Optional<RegisteredEquipment> getRegisteredEquipmentById(Long id) {
        return registeredEquipmentJpaRepositpry.findById(id);
    }

    
    
}
