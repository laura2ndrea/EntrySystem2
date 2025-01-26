package campus.u2.entrysystem.porters.application;

import campus.u2.entrysystem.porters.domain.Porters;
import java.util.List;
import java.util.Optional;

public interface PortersRepository {

    Porters savePorter(Porters porter);

    void deletePorter(Long id);

    List<Porters> listAllPorters();

    Optional<Porters> getPorterById(Long id);
    
    List<Porters> getPortersByPosition(Boolean position);

}
