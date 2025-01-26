package campus.u2.entrysystem.people.infrastructure;


import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.registeredequipment.domain.RegisteredEquipment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PeopleJpaRepository extends JpaRepository<People, Long> {

    @Query("SELECT r FROM RegisteredEquipment r JOIN r.people p WHERE p.id = :id_people")
    List<RegisteredEquipment> findEquipmentByPeopleId(@Param("id_people") Long peopleId);

    Optional<People> findByCedula(String cedula);
};
