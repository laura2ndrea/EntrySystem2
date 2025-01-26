package campus.u2.entrysystem.access.application;

import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import campus.u2.entrysystem.access.domain.Access;
import campus.u2.entrysystem.accessnotes.domain.AccessNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import campus.u2.entrysystem.porters.application.PortersRepository;
import campus.u2.entrysystem.porters.domain.Porters;
import java.util.List;
import java.util.Optional;

@Service
public class AccessService {

    // Attributes 
    private final AccessRepository accessRepository;
    private final PortersRepository portersRepository;

    // Constructor
    @Autowired
    public AccessService(AccessRepository accessRepository, PortersRepository portersRepository) {
        this.accessRepository = accessRepository;
        this.portersRepository = portersRepository;
    }

    // Methods 
    // To save an access
    public Access saveAccess(Access access) {
        if (access == null) {
            throw new GlobalException("Empty object, please try again ");
        }
        return accessRepository.saveAccess(access);
    }

    // To create an access 
    public Access createAccess(Date entryAccess, Date exitAccess, Boolean accessType) {
        if (entryAccess == null) {
            throw new GlobalException("Entry date cannot be empty");
        }
        if (exitAccess == null) {
            throw new GlobalException("Exist date cannot be empty");
        }
        if (accessType == null) {
            throw new GlobalException("Access type cannot be empty");
        }
        Access access = new Access(entryAccess, exitAccess, accessType);
        return accessRepository.saveAccess(access);
    }

    // To delete and access 
    public void deleteAccess(Long id) {
        if (id == null) {
            throw new GlobalException("Id not valid please try again");
        }
        Optional<Access> accessOpt = accessRepository.getAccessById(id);
        if (accessOpt.isPresent()) {
            accessRepository.deleteAccess(accessOpt.get().getIdAccess());
        } else {
            throw new GlobalException("Unexpected error, please try again");
        }
    }

    // To get all accesses
    public List<Access> getAllAccesses() {
        return accessRepository.getAllAccesses();
    }

    // To get an access by id
    public Access getAccessById(Long id) {
        if (id == null) {
            throw new GlobalException("Id not valid please try again");
        }
        Optional<Access> accessOpt = accessRepository.getAccessById(id);
        if (accessOpt.isPresent()) {
            return accessOpt.get();
        } else {
            throw new GlobalException("Id is not valid please try again ");
        }
    }

    // To add a note to an access (with an object) 
    public Access addAccessNoteToAccess(Long idAccess, AccessNote accessNote) {
        if (idAccess == null || accessNote == null || accessNote.getNote() == null || accessNote.getNote().isEmpty()) {
            throw new GlobalException("Error with the inputs, please try again");
        }
        Optional<Access> accessOpt = accessRepository.getAccessById(idAccess);
        if (accessOpt.isPresent()) {
            Access access = accessOpt.get();
            access.addAccessNotes(accessNote);
            return accessRepository.saveAccess(access);
        } else {
            throw new GlobalException("Access with id " + idAccess + " not found");
        }
    }

    // To add a note to an access (create inside the accessNote) 
    public Access addAccessNoteToAccess(Long idAccess, String note) {
        if (idAccess == null || note == null || note.isEmpty()) {
            throw new GlobalException("Error with the inputs, please try again");
        }
        Optional<Access> accessOpt = accessRepository.getAccessById(idAccess);
        if (accessOpt.isPresent()) {
            Access access = accessOpt.get();
            AccessNote accessNote = new AccessNote(note);
            access.addAccessNotes(accessNote);
            return accessRepository.saveAccess(access);
        } else {
            throw new GlobalException("Access with id " + idAccess + " not found");
        }
    }

    // To remove a note from an access
    public Access removeAccessNoteFromAccess(Long idAccess, Long idAccessNote) {
        if (idAccess == null || idAccessNote == null) {
            throw new GlobalException("Error with the inputs, please try again");
        }
        Optional<Access> accessOpt = accessRepository.getAccessById(idAccess);
        if (accessOpt.isPresent()) {
            Access access = accessOpt.get();
            Optional<AccessNote> accessNoteOpt = access.getAccessNotes().stream()
                    .filter(note -> note.getId().equals(idAccessNote))
                    .findFirst();
            if (accessNoteOpt.isPresent()) {
                AccessNote accessNote = accessNoteOpt.get();
                access.removeAccessNotes(accessNote);
                return accessRepository.saveAccess(access);
            } else {
                throw new GlobalException("AccessNote with id " + idAccessNote + " not found");
            }
        } else {
            throw new GlobalException("Access with id " + idAccess + "not found");
        }
    }

    // To add a porter to an access
    public Access addPorterToAccess(Long accessId, Long porterId) {
        Access access = accessRepository.getAccessById(accessId)
                .orElseThrow(() -> new RuntimeException("Access with ID " + accessId + " not found."));
        Porters porter = portersRepository.getPorterById(porterId)
                .orElseThrow(() -> new RuntimeException("Porter with ID " + porterId + " not found."));
        access.getPorters().add(porter);
        porter.getAccesses().add(access);
        accessRepository.saveAccess(access);
        portersRepository.savePorter(porter);
        return access;
    }

    // To remove a porter from an access
    public Access removePorterFromAccess(Long accessId, Long porterId) {
        Optional<Access> accessOpt = accessRepository.getAccessById(accessId);
        if (accessOpt.isPresent()) {
            Access access = accessOpt.get();
            Optional<Porters> porterOpt = portersRepository.getPorterById(porterId);
            if (porterOpt.isPresent()) {
                Porters porter = porterOpt.get();
                access.getPorters().remove(porter);  // Delete the porter in the access
                porter.getAccesses().remove(access);  // Delete the access in the porter
                accessRepository.saveAccess(access);  // Save the update access
                portersRepository.savePorter(porter);  // Save the update porter
                return access;
            }
        }
        return null;  // If the porter or the access is not found
    }

    // To find all the access between two dates
    public List<Access> findAccessBetweenDates(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new GlobalException("Date is not valid");
        }
        return accessRepository.findAccessBetweenDates(startDate, endDate);
    }
    
  

}
