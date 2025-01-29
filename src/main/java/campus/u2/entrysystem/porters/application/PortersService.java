package campus.u2.entrysystem.porters.application;

import campus.u2.entrysystem.Utilities.exceptions.InvalidInputException;
import campus.u2.entrysystem.Utilities.exceptions.NotFoundException;
import campus.u2.entrysystem.Utilities.exceptions.TypeMismatchException;
import campus.u2.entrysystem.porters.domain.Porters;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PortersService {

    private final PortersRepository portersRepository;

    @Autowired
    public PortersService(PortersRepository portersRepository) {
        this.portersRepository = portersRepository;
    }

    // To save a porter
    @Transactional
    public Porters savePorter(Porters porter) {
        if (porter == null) {
            throw new InvalidInputException("Porter object cannot be null");
        }
        return portersRepository.savePorter(porter);
    }
    
    @Transactional 
    public Porters savePorter(String name, String cedula, String telefono, Date employmentDate, Boolean position, Porters jefe) {
        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("Name cannot be empty");
        }
        if (cedula == null || cedula.isEmpty()) {
            throw new InvalidInputException("Cedula cannot be empty");
        }
        if (employmentDate == null) {
            throw new InvalidInputException("Employment date cannot be null");
        }
        if (jefe != null) {
            Optional<Porters> existingBoss = portersRepository.getPorterById(jefe.getId());
            if (!existingBoss.isPresent()) {
                throw new NotFoundException("Boss with ID " + jefe.getId() + " not found");
            }
        }
        Porters porter = new Porters(employmentDate, position, jefe, name, cedula, telefono);
        return portersRepository.savePorter(porter);
    }
    
    public Porters savePorter(String name, String cedula, String telefono, Date employmentDate, Boolean position) {
        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("Name cannot be empty");
        }
        if (cedula == null || cedula.isEmpty()) {
            throw new InvalidInputException("Cedula cannot be empty");
        }
        if (employmentDate == null) {
            throw new InvalidInputException("Employment date cannot be null");
        }
        Porters porter = new Porters(employmentDate, position, name, cedula, telefono);
        return portersRepository.savePorter(porter);
    }

    // To add a boss to a porter
     public Porters addBossToPorter(String idPorter, String idBoss) {
        if (idPorter == null || idPorter.isEmpty() || idBoss == null || idBoss.isEmpty()) {
            throw new InvalidInputException("Porter ID and Boss ID cannot be empty");
        }
        Long porterIdLong, bossIdLong;
        try {
            porterIdLong = Long.parseLong(idPorter);
            bossIdLong = Long.parseLong(idBoss);
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("idPorter or idBoss", "Long", "Invalid ID format: " + idPorter + " or " + idBoss);
        }
        Optional<Porters> porterOpt = portersRepository.getPorterById(porterIdLong);
        Optional<Porters> bossOpt = portersRepository.getPorterById(bossIdLong);
        if (!porterOpt.isPresent()) {
            throw new NotFoundException("Porter with ID " + idPorter + " not found");
        }
        if (!bossOpt.isPresent()) {
            throw new NotFoundException("Boss with ID " + idBoss + " not found");
        }
        Porters porterObjt = porterOpt.get();
        Porters bossObjt = bossOpt.get();
        porterObjt.setId_jefe(bossObjt);
        return portersRepository.savePorter(porterObjt);
    }

    // To delete a porter
    public void deletePorter(String id) {
        if (id == null || id.isEmpty()) {
            throw new InvalidInputException("ID cannot be empty");
        }

        Long idPorter;
        try {
            idPorter = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("id", "Long", "Invalid ID format: " + id);
        }

        Optional<Porters> portersOpt = portersRepository.getPorterById(idPorter);
        if (portersOpt.isPresent()) {
            portersRepository.deletePorter(idPorter);
        } else {
            throw new NotFoundException("Porter with ID " + id + " not found");
        }
    }

    // To list all porters
    public List<Porters> listAllPorters() {
        return portersRepository.listAllPorters();
    }
    
    // To get a porter by ID
    public Porters getPorterById(String id) {
        if (id == null || id.isEmpty()) {
            throw new InvalidInputException("ID cannot be empty");
        }

        Long idPorter;
        try {
            idPorter = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("id", "Long", "Invalid ID format: " + id);
        }

        return portersRepository.getPorterById(idPorter)
                .orElseThrow(() -> new NotFoundException("Porter with ID " + id + " not found"));
    }

    //To get porters depending on the position
    public List<Porters> getPortersByPosition(Boolean position) {
        if (position == null) {
            throw new InvalidInputException("Position cannot be null");
        }
        return portersRepository.getPortersByPosition(position);
    }

}
