package campus.u2.entrysystem.porters.infrastructure;

import campus.u2.entrysystem.porters.application.PortersRepository;
import campus.u2.entrysystem.porters.domain.Porters;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;


@Component
public class PortersAdapter implements PortersRepository {

    // Attributes 
    private final PortersJpaRepository portersRepository;

    // Constructor
    public PortersAdapter(PortersJpaRepository portersRepository) {
        this.portersRepository = portersRepository;
    }

    // Methods 
    // To save a porter 
    @Override
    @Transactional
    public Porters savePorter(Porters porter) {
        return portersRepository.save(porter);
    }

    // To delete a porter 
    @Override
    @Transactional
    public void deletePorter(Long id) {
        Optional<Porters> porterOpt = portersRepository.findById(id);
        if (porterOpt.isPresent()) {
            portersRepository.delete(porterOpt.get());
        }
    }

    // To show all the porters
    @Override
    @Transactional
    public List<Porters> listAllPorters() {
        return portersRepository.findAll();
    }

    // To get a porter for the id 
    @Override
    @Transactional
    public Optional<Porters> getPorterById(Long id) {
        return portersRepository.findById(id);
    }
    
    // To get porters for the position 
    @Override
    @Transactional
    public List<Porters> getPortersByPosition(Boolean position) {
        return portersRepository.findByPosition(position);
    }

    @Override
    public Porters findByCedula(String cedula) {
        return portersRepository.findByCedula(cedula);
    }

}
