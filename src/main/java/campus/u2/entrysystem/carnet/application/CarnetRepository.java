package campus.u2.entrysystem.carnet.application;

import campus.u2.entrysystem.carnet.domain.Carnet;
import campus.u2.entrysystem.people.domain.People;
import java.util.List;
import java.util.Optional;

public interface CarnetRepository {

    Carnet saveCarnet(Carnet carnet);
    List<Carnet> getAllCarnets();
    Optional<Carnet> getCarnetById(Long id);
    Optional<Carnet> findCarnetByPeople(People people); 

}
