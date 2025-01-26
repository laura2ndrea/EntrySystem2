package campus.u2.entrysystem.accessnotes.application;

import campus.u2.entrysystem.accessnotes.domain.AccessNote;
import java.util.Optional;

public interface AccessNoteRepository {
    AccessNote saveAccessNote(AccessNote accessNote); 
    Optional <AccessNote> findById(Long id); 
    void deleteAccessNote(AccessNote accessNote); 
}
