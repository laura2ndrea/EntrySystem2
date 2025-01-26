package campus.u2.entrysystem.accessnotes.infrastructure;

import campus.u2.entrysystem.accessnotes.application.AccessNoteRepository;
import campus.u2.entrysystem.accessnotes.domain.AccessNote;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class AccessNoteAdapter implements AccessNoteRepository {
    
    // Attributes 
    private final AccessNoteJpaRepository accessNoteRepository; 
    
    // Constructor 
    public AccessNoteAdapter(AccessNoteJpaRepository accessRepository) {
        this.accessNoteRepository = accessRepository; 
    }
    
    // Methods 
    
    // To save an access note 
    @Override
    @Transactional
    public AccessNote saveAccessNote(AccessNote accessNote) {
        return accessNoteRepository.save(accessNote);
    }
}
