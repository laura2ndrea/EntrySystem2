package campus.u2.entrysystem.company.infrastructure;

import campus.u2.entrysystem.company.application.CompanyRepository;
import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.people.domain.People;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class CompanyAdapter implements CompanyRepository {
    // Attributtes 
    private final CompanyJpaRepository companyRepository; 
    
    // Constructor 
    public CompanyAdapter (CompanyJpaRepository companyRepository) {
        this.companyRepository = companyRepository; 
    }
    
    // Methods 
    
    // To save a company 
    @Override
    @Transactional
    public Company saveCompany(Company company) {
        return companyRepository.save(company); 
    }
    
    // To delete a company
    @Override
    @Transactional
    public void deleteCompany(Long id) {
        Optional<Company> companyOpt = companyRepository.findById(id); 
        if (companyOpt.isPresent()) {
            companyRepository.delete(companyOpt.get());
        }
    }
    
    // To get all the companies 
    @Override 
    @Transactional 
    public List<Company> listAllCompanies() {
        return companyRepository.findAll();
    }
    
    // To get a company by id 
    @Override
    @Transactional 
    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }
    
    // To get all the employees of a company
    @Override 
    @Transactional
    public List<People> getEmployeesByCompanyId(Long idCompany) {
        return companyRepository.findEmployeesByCompanyId(idCompany); 
    }
    
    // To get all the employees of a company
    @Override 
    @Transactional
    public List<People> getEmployeesByCompanyName(String name) {
        return companyRepository.findEmployeesByCompanyName(name); 
    }
    
    // To know if a name of a company exists
    @Override 
    @Transactional
    public Boolean existsByNameCompany(String name) {
        return companyRepository.existsByName(name); 
    }
}
