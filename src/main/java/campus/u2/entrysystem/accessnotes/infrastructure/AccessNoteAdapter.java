package campus.u2.entrysystem.accessnotes.infrastructure;

import campus.u2.entrysystem.accessnotes.application.AccessNoteRepository;
import campus.u2.entrysystem.accessnotes.domain.AccessNote;
import jakarta.transaction.Transactional;
import java.util.Optional;
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
    
    // To find an access note for the id 
    @Override
    @Transactional
    public Optional<AccessNote> findById(Long id) {
        return accessNoteRepository.findById(id); 
    }
    
    // To delete a note
    @Override
    @Transactional
    public void deleteAccessNote(AccessNote note) {
        accessNoteRepository.delete(note); 
    }
}
