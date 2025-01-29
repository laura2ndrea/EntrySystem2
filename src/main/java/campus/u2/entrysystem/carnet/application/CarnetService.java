package campus.u2.entrysystem.carnet.application;


import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import campus.u2.entrysystem.Utilities.exceptions.InvalidInputException;
import campus.u2.entrysystem.Utilities.exceptions.NotFoundException;
import campus.u2.entrysystem.Utilities.exceptions.TypeMismatchException;
import campus.u2.entrysystem.carnet.domain.Carnet;
import campus.u2.entrysystem.people.domain.People;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarnetService {

    private final CarnetRepository carnetRepository;
    
    @Autowired
    public CarnetService(CarnetRepository carnetRepository) {
        this.carnetRepository = carnetRepository;
    }

    //To save a carnet 
    @Transactional
    public Carnet saveCarnet(Carnet carnet) {
        if (carnet == null) {
            throw new InvalidInputException("Carnet object cannot be null");
        }
        return carnetRepository.saveCarnet(carnet);
    }
    
    // To create a new carnet 
    @Transactional
    public Carnet createCarnet() {
        Carnet carnet = new Carnet();
        return carnetRepository.saveCarnet(carnet);
    }

    // To save a carnet for a person
    @Transactional
    public Carnet saveCarnetForPerson(People people, Carnet carnet) {
        if (people == null) {
            throw new InvalidInputException("People object cannot be null");
        }
        if (carnet == null) {
            throw new InvalidInputException("Carnet object cannot be null");
        }
        if (people.getCarnet() == null || !people.getCarnet().equals(carnet)) {
            carnet.setPeople(people);
            people.setCarnet(carnet);
        }
        return carnetRepository.saveCarnet(carnet);
    }

    // To show all the carnets 
    @Transactional
    public List<Carnet> getAllCarnets() {
        return carnetRepository.getAllCarnets();
    }

    // To find a carnet for the id
    @Transactional
    public Carnet getCarnetById(Long id) {
        if (id == null) {
            throw new GlobalException("ID cannot be empty");
        }
        try {
            return carnetRepository.getCarnetById(id)
                    .orElseThrow(() -> new GlobalException("Not found ID"));
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("id", "Long", "invalid format: " + id);
        }
    }
    
    // To find a carnet by the people 
    @Transactional
    public Carnet findCarnetByPeople(People people) {
        if (people == null) {
            throw new InvalidInputException("People object cannot be null");
        }
        return carnetRepository.findCarnetByPeople(people)
                .orElseThrow(() -> new NotFoundException("No carnet found for the given person"));
    }
}
