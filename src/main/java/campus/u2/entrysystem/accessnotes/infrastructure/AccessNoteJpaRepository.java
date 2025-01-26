package campus.u2.entrysystem.accessnotes.infrastructure;

import campus.u2.entrysystem.accessnotes.domain.AccessNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessNoteJpaRepository extends JpaRepository<AccessNote, Long>{
    
}
