package campus.u2.entrysystem.accessnotes.application;

import campus.u2.entrysystem.accessnotes.domain.AccessNote;

public interface AccessNoteRepository {
    AccessNote saveAccessNote(AccessNote accessNote); 
}
