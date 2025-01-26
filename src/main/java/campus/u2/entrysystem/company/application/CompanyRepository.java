package campus.u2.entrysystem.company.application;

import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.people.domain.People;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository {

    Company saveCompany(Company company);
    void deleteCompany(Long id);
    List<Company> listAllCompanies();
    Optional<Company> getCompanyById(Long id);
    List<People> getEmployeesByCompanyId(Long idCompany);
    List<People> getEmployeesByCompanyName(String name);
    Boolean existsByNameCompany(String name);

}
