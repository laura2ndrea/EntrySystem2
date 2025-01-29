package campus.u2.entrysystem.accessnotes.application;

import campus.u2.entrysystem.Utilities.exceptions.InvalidInputException;
import campus.u2.entrysystem.Utilities.exceptions.NotFoundException;
import campus.u2.entrysystem.Utilities.exceptions.TypeMismatchException;
import campus.u2.entrysystem.accessnotes.domain.AccessNote;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service 
public class AccessNoteService {
    
    // Attributes 
    private final AccessNoteRepository accessNoteRepository; 
    
    // Constructor 
    public AccessNoteService(AccessNoteRepository accessNoteRepository) {
        this.accessNoteRepository = accessNoteRepository; 
    }
    
    // Methods 
    
    // To save a note
    @Transactional 
    public AccessNote saveAccessNote(AccessNote accessNote) {
        if (accessNote == null) {
            throw new InvalidInputException("AccessNote object cannot be null");
        }
        return accessNoteRepository.saveAccessNote(accessNote);
    }
    
    // To find an access note for the id 
    @Transactional
    public AccessNote findById(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidInputException("ID cannot be null or empty");
        }
        try {
            Long noteId = Long.parseLong(id);
            return accessNoteRepository.findById(noteId)
                    .orElseThrow(() -> new NotFoundException("AccessNote with ID " + noteId + " not found"));
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("id", "Long", "Invalid format: " + id);
        }
    }
    
    // To delete a note 
    @Transactional
    public void deleteAccessNote(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidInputException("ID cannot be null or empty");
        }
        try {
            Long noteId = Long.parseLong(id);
            AccessNote note = accessNoteRepository.findById(noteId)
                    .orElseThrow(() -> new NotFoundException("AccessNote with ID " + noteId + " not found"));
            
            accessNoteRepository.deleteAccessNote(note);
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("id", "Long", "Invalid format: " + id);
        }
    }
    
}
