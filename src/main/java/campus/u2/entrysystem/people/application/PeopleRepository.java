
package campus.u2.entrysystem.people.application;


import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.registeredequipment.domain.RegisteredEquipment;
import java.util.List;
import java.util.Optional;


public interface PeopleRepository { 
    
    People savePeople(People people);
    People savePeople(String name, String cedula, String telefono, Boolean personType);
    People savePeople(String name, String cedula, String telefono, Boolean personType, Company company);
    People savePeopleEquipment(People people, List<RegisteredEquipment> equipments);
    People addEquipmentToPerson(Long personId, RegisteredEquipment equipment);
    People removeEquipmentFromPerson(Long personId, Long equipmentId);
    List<RegisteredEquipment> findEquipmentByPeopleId(Long peopleId);
//    People updatePeople(People peopleToUpdate);
    void deletePeople(Long id);
    List<People> listAllPeople();
    Optional<People>  getPeopleById(Long id);
//    People updatePeopleCompany(Long peopleId, Company newCompany);
    Optional<People>   getPeopleByCedula(String cedula);
    
    
    
    
    
    
    
    
    
}
