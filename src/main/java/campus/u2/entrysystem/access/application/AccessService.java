package campus.u2.entrysystem.access.application;

import campus.u2.entrysystem.Utilities.exceptions.InvalidInputException;
import campus.u2.entrysystem.Utilities.exceptions.NotFoundException;
import campus.u2.entrysystem.Utilities.exceptions.TypeMismatchException;
import campus.u2.entrysystem.access.domain.Access;
import campus.u2.entrysystem.accessnotes.application.AccessNoteRepository;
import campus.u2.entrysystem.accessnotes.domain.AccessNote;
import campus.u2.entrysystem.people.application.PeopleRepository;
import campus.u2.entrysystem.people.domain.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import campus.u2.entrysystem.porters.application.PortersRepository;
import campus.u2.entrysystem.porters.domain.Porters;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class AccessService {

    // Attributes 
    private final AccessRepository accessRepository;
    private final PortersRepository portersRepository;
    private final PeopleRepository peopleRepository;
    private final AccessNoteRepository noteRepository;

    // Constructor
    @Autowired
    public AccessService(AccessRepository accessRepository, PortersRepository portersRepository, PeopleRepository peopleRepository, AccessNoteRepository noteRepository) {
        this.accessRepository = accessRepository;
        this.portersRepository = portersRepository;
        this.peopleRepository = peopleRepository;
        this.noteRepository = noteRepository;
    }

    // Methods 
    // To save an access
    @Transactional
    public Access saveAccess(Access access) {
        if (access == null) {
            throw new InvalidInputException("Access object cannot be null");
        }
        return accessRepository.saveAccess(access);
    }

    // To create an access 
    @Transactional
    public Access createAccess(Date entryAccess, Date exitAccess, Boolean accessType) {
        if (entryAccess == null) {
            throw new InvalidInputException("Entry date cannot be null");
        }
        if (exitAccess == null) {
            throw new InvalidInputException("Exit date cannot be null");
        }
        if (accessType == null) {
            throw new InvalidInputException("Access type cannot be null");
        }
        Access access = new Access(entryAccess, exitAccess, accessType);
        return accessRepository.saveAccess(access);
    }

    // To delete and access 
    @Transactional
    public void deleteAccess(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidInputException("ID cannot be null or empty");
        }
        try {
            Long accessId = Long.parseLong(id);
            Access access = accessRepository.getAccessById(accessId)
                    .orElseThrow(() -> new NotFoundException("Access with ID " + id + " not found"));
            People people = peopleRepository.getPeopleById(access.getPeople().getId())
                    .orElseThrow(() -> new NotFoundException("Person associated with Access not found"));
            people.removeAccess(access);
            access.setPeople(null);
            accessRepository.deleteAccess(access);

        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("id", "Long", "invalid format: " + id);
        }
    }

    // To get all accesses
    @Transactional
    public List<Access> getAllAccesses() {
        return accessRepository.getAllAccesses();
    }

    // To get an access by id
    @Transactional
    public Access getAccessById(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidInputException("ID cannot be null or empty");
        }
        try {
            Long accessId = Long.parseLong(id);
            return accessRepository.getAccessById(accessId)
                    .orElseThrow(() -> new NotFoundException("Access with ID " + accessId + " not found"));
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("id", "Long", "invalid format: " + id);
        }
    }

    // To add a note to an access (with an object) 
    @Transactional
    public Access addAccessNoteToAccess(String idAccess, AccessNote accessNote) {
        if (accessNote == null) {
            throw new InvalidInputException("AccessNote object cannot be null");
        }
        if (idAccess == null || idAccess.isBlank()) {
            throw new InvalidInputException("ID cannot be null or empty");
        }
        try {
            Long accessId = Long.parseLong(idAccess);
            Access access = accessRepository.getAccessById(accessId)
                    .orElseThrow(() -> new NotFoundException("Access with ID " + accessId + " not found"));
            accessNote.setAccess(access);
            access.addAccessNotes(accessNote);
            //noteRepository.saveAccessNote(accessNote);
            return accessRepository.saveAccess(access);
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("id", "Long", "invalid format: " + idAccess);
        }
    }

    // To add a note to an access (create inside the accessNote)
    @Transactional
    public Access addAccessNoteToAccess(String idAccess, String note) {
        if (idAccess == null || note == null || note.isBlank() || idAccess.isBlank()) {
            throw new InvalidInputException("ID and note cannot be null or empty");
        }
        try {
            Long accessId = Long.parseLong(idAccess);
             Access access = accessRepository.getAccessById(accessId)
                .orElseThrow(() -> new NotFoundException("Access with ID " + accessId + " not found"));
        AccessNote accessNote = new AccessNote(note);
        access.addAccessNotes(accessNote);
        return accessRepository.saveAccess(access);
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("id", "Long", "invalid format: " + idAccess);
        }
    }

    // To remove a note from an access
     @Transactional
    public Access removeAccessNoteFromAccess(String idAccessNote) {
        if (idAccessNote == null || idAccessNote.isBlank()) {
            throw new InvalidInputException("ID cannot be null or empty");
        }
        try {
            Long idNote = Long.parseLong(idAccessNote);
            AccessNote note = noteRepository.findById(idNote)
                .orElseThrow(() -> new NotFoundException("AccessNote with ID " + idNote + " not found"));

            Access access = accessRepository.getAccessById(note.getAccess().getIdAccess())
                    .orElseThrow(() -> new NotFoundException("Access associated with the note not found"));

            note.setAccess(null);
            access.removeAccessNotes(note);
            noteRepository.deleteAccessNote(note);
            return accessRepository.saveAccess(access);
            
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("id", "Long", "invalid format: " + idAccessNote);
        }
    }

    // To add a porter to an access
    @Transactional
    public Access addPorterToAccess(String accessId, String porterId) {
        if (accessId == null || accessId.isBlank() || porterId == null || porterId.isBlank()) {
            throw new InvalidInputException("ID (access or porter) cannot be null or empty");
        }
        try {
            Long idAccess = Long.parseLong(accessId);
            Long idPorter = Long.parseLong(porterId); 
            Access access = accessRepository.getAccessById(idAccess)
                .orElseThrow(() -> new NotFoundException("Access with ID " + idAccess + " not found"));
            Porters porter = portersRepository.getPorterById(idPorter)
                    .orElseThrow(() -> new NotFoundException("Porter with ID " + idPorter + " not found"));
            if (access.getPorters().contains(porter)) {
            throw new InvalidInputException("Porter with ID " + idPorter + " is already associated with Access " + idAccess + ".");
            }
            access.getPorters().add(porter);
            porter.getAccesses().add(access);
            accessRepository.saveAccess(access);
            //portersRepository.savePorter(porter); 
            return access;
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("id (access or porter)", "Long", "invalid format");
        }
    }

    // To remove a porter from an access
    @Transactional
    public Access removePorterFromAccess(String accessId, String porterId) {
        if (accessId == null || accessId.isBlank() || porterId == null || porterId.isBlank()) {
            throw new InvalidInputException("ID (access or porter) cannot be null or empty");
        }
        try {
            Long idAccess = Long.parseLong(accessId);
            Long idPorter = Long.parseLong(porterId);
            Access access = accessRepository.getAccessById(idAccess)
                .orElseThrow(() -> new NotFoundException("Access with ID " + idAccess + " not found"));
            Porters porter = portersRepository.getPorterById(idPorter)
                    .orElseThrow(() -> new NotFoundException("Porter with ID " + idPorter + " not found"));
            access.getPorters().remove(porter);
            porter.getAccesses().remove(access);
            accessRepository.saveAccess(access);
            //portersRepository.savePorter(porter);
            return access;
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("id (access or porter)", "Long", "invalid format");
        }
    }

    // To find all the access between two dates
    @Transactional
    public List<Access> findAccessBetweenDates(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new InvalidInputException("Start and end dates cannot be null");
        }
        return accessRepository.findAccessBetweenDates(startDate, endDate);
    }

}
