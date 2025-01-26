package campus.u2.entrysystem.accessnotes.application;

import campus.u2.entrysystem.accessnotes.domain.AccessNote;
import jakarta.transaction.Transactional;
import java.util.Optional;
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
    
    // To find an access note for the id 
    @Transactional 
    public Optional<AccessNote> findById(Long id){
        return accessNoteRepository.findById(id); 
    }
    
    // To delete a note 
    @Transactional 
    public void deleteAccessNote(AccessNote note) {
        accessNoteRepository.deleteAccessNote(note);
    }
    
}
