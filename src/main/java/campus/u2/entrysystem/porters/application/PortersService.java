package campus.u2.entrysystem.porters.application;


import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import campus.u2.entrysystem.porters.domain.Porters;
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
    public Porters savePorter(Porters porter) {
        if (porter == null) {
            throw new GlobalException("Porter not valid");
        }
        return portersRepository.savePorter(porter);
    }

    public Porters savePorter(String name, String cedula, String telefono, Date employmentDate, Boolean position, Porters jefe) {
        Porters porter = new Porters(employmentDate, position, jefe, name, cedula, telefono);
        return portersRepository.savePorter(porter);
    }

    public Porters savePorter(String name, String cedula, String telefono, Date employmentDate, Boolean position) {
        Porters porter = new Porters(employmentDate, position, name, cedula, telefono);
        return portersRepository.savePorter(porter);
    }

    // To add a boss to a porter
    public Porters addBossToPorter(Long idPorter, Long idBoss) {
        Optional<Porters> porterOpt = portersRepository.getPorterById(idPorter);
        Optional<Porters> bossOpt = portersRepository.getPorterById(idBoss);
        if (porterOpt.isPresent() || bossOpt.isPresent()) {
            Porters porterObjt = porterOpt.get();
            Porters bossObjt = bossOpt.get();
            porterObjt.setId_jefe(bossObjt);
            return portersRepository.savePorter(porterObjt);
        } else {
            throw new GlobalException("Id is not valid please try again");
        }
    }

    // To delete a porter
    public void deletePorter(Long id) {
        Optional<Porters> portersOpt = portersRepository.getPorterById(id);
        if (portersOpt.isPresent()) {
            Porters portersToEliminate = portersOpt.get();
            portersRepository.deletePorter(portersToEliminate.getId());
        } else {
            throw new GlobalException("Id is not valid please try again");
        }
    }

    // To list all porters
    public List<Porters> listAllPorters() {
        return portersRepository.listAllPorters();
    }

    // To get a porter by ID
    public Porters getPorterById(Long id) {
        if (id == null) {
            throw new GlobalException("Id is not Valid please try again");
        }
        return portersRepository.getPorterById(id)
                .orElseThrow(() -> new GlobalException("Id no valido"));

    }

    //To get porters depending on the position
    public List<Porters> getPortersByPosition(Boolean position) {
        return portersRepository.getPortersByPosition(position);
    }

}
