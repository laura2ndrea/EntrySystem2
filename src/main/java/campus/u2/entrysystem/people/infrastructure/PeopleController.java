package campus.u2.entrysystem.people.infrastructure;

import campus.u2.entrysystem.people.application.PeopleService;
import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.registeredequipment.domain.RegisteredEquipment;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import campus.u2.entrysystem.carnet.application.CarnetService;
import campus.u2.entrysystem.carnet.domain.Carnet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final CarnetService carnetService;

    @Autowired
    public PeopleController(PeopleService peopleService, CarnetService carnetService) {
        this.peopleService = peopleService;
        this.carnetService = carnetService;
    }

    @PostMapping
    public ResponseEntity<People> savePeople(@RequestBody People people) {
        People savedPeople = peopleService.savePeople(people);
        return ResponseEntity.ok(savedPeople);
    }



    // AGREGAR El Equipo y asignarselo a una perosna existente 
//    
//    			{
//				"serial": "12345ArtgBC",
//				"registrationDate": "2025-01-24",
//				"description": "Laptop Dell Insgregergegrpiron"
//			}
    // Body para crear el equipo  el id ya debe estar creado 
    @PostMapping("/{personId}/equipment")
    public ResponseEntity<People> addEquipmentToPerson(@PathVariable Long personId, @RequestBody RegisteredEquipment equipment) {
        People peopleToUpdate = peopleService.getPeopleById(personId);
        if (peopleToUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        peopleToUpdate.addEquipments(equipment);
        equipment.setPeople(peopleToUpdate);

        peopleService.saveRegisteredEquipment(equipment);
        peopleService.savePeople(peopleToUpdate);

        return ResponseEntity.ok(peopleToUpdate);
    }

    @DeleteMapping("/{personId}/removeEquipment/{equipmentId}")
    public ResponseEntity<People> removeEquipmentFromPerson(@PathVariable Long personId,
            @PathVariable Long equipmentId) {

        People updatedPeople = peopleService.removeEquipmentFromPerson(personId, equipmentId);
        return ResponseEntity.ok(updatedPeople);
    }

    @GetMapping("/{peopleId}/equipment")
    public ResponseEntity<List<RegisteredEquipment>> findEquipmentByPeopleId(@PathVariable Long peopleId) {
        List<RegisteredEquipment> equipmentList = peopleService.findEquipmentByPeopleId(peopleId);
        return ResponseEntity.ok(equipmentList);

    }

    @GetMapping
    public ResponseEntity<List<People>> listAllPeople() {
        List<People> peopleList = peopleService.listAllPeople();
        return ResponseEntity.ok(peopleList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<People> getPeopleById(@PathVariable Long id) {

        People people = peopleService.getPeopleById(id);
        return ResponseEntity.ok(people);

    }

    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<People> getPeopleByCedula(@PathVariable String cedula) {
        People people = peopleService.getPeopleByCedula(cedula);
        return ResponseEntity.ok(people);

    }

    @DeleteMapping("/{cedula}")
    public ResponseEntity<Void> deletePeople(@PathVariable String cedula) {
        peopleService.deletePeople(cedula);
        return ResponseEntity.ok().build();
    }

    // CUERPO PARA UPDATE 
    //	{
//		"id": 24,
//		"name": "BBBB",
//		"cedula": "1111111111",
//		"telefono": "2222222",
//		"personType": true
//	} 
    @PutMapping
    public ResponseEntity<People> updatePeople(@RequestBody People people) {
        People peopleToUpdate = peopleService.getPeopleById(people.getId());
        if (peopleToUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        peopleToUpdate.setName(people.getName());
        peopleToUpdate.setCedula(people.getCedula());
        peopleToUpdate.setPersonType(people.getPersonType());
        peopleToUpdate.setTelefono(people.getTelefono());
        return ResponseEntity.ok(peopleToUpdate);
    }
    
    
    // ya deb estar la perosna crea pero solo necesita el person id para asignarle 
    @PutMapping("/carnettoupdate/{idpeople}")
    public ResponseEntity<People>  updateCarnetPeople(@PathVariable Long idpeople) {
        
        Carnet carnetAsignacion =  carnetService.createCarnet();
        People PeopleUpDate =  peopleService.getPeopleById(idpeople);
        PeopleUpDate.setCarnet(carnetAsignacion);
        carnetAsignacion.setPeople(PeopleUpDate);
        peopleService.savePeople(PeopleUpDate);
        carnetService.saveCarnet(carnetAsignacion);
        return ResponseEntity.ok(PeopleUpDate);
    }

    
    
    @PutMapping("/equipment")
    public ResponseEntity<RegisteredEquipment> updateEquipment(@RequestBody RegisteredEquipment equipment) {
        Optional<RegisteredEquipment> equipmentToUpdateOpt = peopleService.getRegisteredEquipmentByid(equipment.getId());

        if (!equipmentToUpdateOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        RegisteredEquipment equipmentToUpdate = equipmentToUpdateOpt.get();

        equipmentToUpdate.setSerial(equipment.getSerial());
        equipmentToUpdate.setRegistrationDate(equipment.getRegistrationDate());
        equipmentToUpdate.setDescription(equipment.getDescription());
        peopleService.saveRegisteredEquipment(equipmentToUpdate);
        return ResponseEntity.ok(equipmentToUpdate);
    }

}
