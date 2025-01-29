package campus.u2.entrysystem.accessnotes.infrastructure;

import campus.u2.entrysystem.Utilities.exceptions.InvalidInputException;
import campus.u2.entrysystem.access.application.AccessService;
import campus.u2.entrysystem.accessnotes.domain.AccessNote;
import campus.u2.entrysystem.access.domain.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accessNote")
public class AccessNotesController {

    private final AccessService accessService;

    @Autowired
    public AccessNotesController(AccessService accessService) {
        this.accessService = accessService;
    }

    @GetMapping("/{idAccess}/")
    public ResponseEntity<List<AccessNote>> getAccessNotes(@PathVariable String idAccess) {
        Access access = accessService.getAccessById(idAccess);
        List<AccessNote> accessNotes = access.getAccessNotes();
        return ResponseEntity.ok(accessNotes);
    }

    @GetMapping("/{idAccessNote}")
    public ResponseEntity<AccessNote> getAccessNoteById(@PathVariable Long idAccessNote) {

        List<AccessNote> accessNotes = accessService.getAllAccesses()
                .stream()
                .flatMap(access -> access.getAccessNotes().stream())
                .toList();
        AccessNote accessNote = accessNotes.stream()
                .filter(note -> note.getId().equals(idAccessNote))
                .findFirst()
                .orElseThrow(() -> new InvalidInputException("AccessNote with id " + idAccessNote + " not found"));
        return ResponseEntity.ok(accessNote);
    }

 
}
