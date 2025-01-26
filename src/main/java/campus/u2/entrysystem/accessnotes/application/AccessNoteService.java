package campus.u2.entrysystem.accessnotes.application;

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
        return accessNoteRepository.saveAccessNote(accessNote); 
    }
    
}
