package campus.u2.entrysystem.company.infrastructure;


import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.people.domain.People;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyJpaRepository extends JpaRepository<Company, Long> {

    @Query("SELECT p FROM People p WHERE p.company.id = :id_company")
    List<People> findEmployeesByCompanyId(@Param("id_company") Long companyId);
    
    @Query("SELECT p FROM People p WHERE p.company.name = :name")
    List<People> findEmployeesByCompanyName(@Param("name") String name);
    
    Boolean existsByName(String name);
   
}
