package campus.u2.entrysystem.access.infrastructure;

import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import campus.u2.entrysystem.access.application.AccessRepository;
import campus.u2.entrysystem.access.domain.Access;
import java.util.Date;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class AccessAdapter implements AccessRepository {

    //Atributtes 
    private final AccessJpaRepository accessRepository;

    // Constructor 
    public AccessAdapter(AccessJpaRepository accessRepository) {
        this.accessRepository = accessRepository;
    }

    //Methods 
    // To save an access
    @Override
    @Transactional
    public Access saveAccess(Access access) {
        return accessRepository.save(access);
    }

    // To delete an access
    @Override
    @Transactional
    public void deleteAccess(Long id) {
        Optional <Access> access = accessRepository.findById(id); 
        accessRepository.delete(access.get());
    }
//    public void deleteAccess(Long id) {
//        if (id == null) {
//            throw new GlobalException("Id not valid, please try again");
//        }
//
//        // Verifica si el registro existe antes de intentar eliminarlo
//        if (accessRepository.existsById(id)) {
//            accessRepository.deleteById(id); // Elimina el acceso usando el repositorio JPA
//        } else {
//            throw new GlobalException("Access not found for id " + id);
//        }
//    }

    // To get all accesses
    @Override
    @Transactional
    public List<Access> getAllAccesses() {
        return accessRepository.findAll();
    }

    // To get an access by id
    @Override
    @Transactional
    public Optional<Access> getAccessById(Long id) {
        return accessRepository.findById(id);
    }

    // To find all the access between two dates
    @Override
    @Transactional
    public List<Access> findAccessBetweenDates(Date startDate, Date endDate) {
        return accessRepository.findByEntryAccessBetween(startDate, endDate);
    }
}
