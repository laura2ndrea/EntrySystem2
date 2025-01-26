package campus.u2.entrysystem.accessnotes.domain;

import campus.u2.entrysystem.access.domain.Access;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "access_note")

public class AccessNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String note;
    
    @JsonBackReference("notes-access")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_access", nullable = false)
    private Access access;

    public AccessNote() {
    }

    public AccessNote(String note) {
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "AccessNote{" + "id=" + id
                + ", note='" + note + '\''
                + ", access=" + access.getIdAccess()
                + '}';
    }
}
