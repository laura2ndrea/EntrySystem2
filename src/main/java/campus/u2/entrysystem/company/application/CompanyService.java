package campus.u2.entrysystem.company.application;

import campus.u2.entrysystem.Utilities.exceptions.InvalidInputException;
import campus.u2.entrysystem.Utilities.exceptions.NotFoundException;
import campus.u2.entrysystem.Utilities.exceptions.TypeMismatchException;
import campus.u2.entrysystem.Utilities.exceptions.UniqueViolationException;
import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.people.application.PeopleRepository;
import campus.u2.entrysystem.people.domain.People;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final PeopleRepository peopleRepository; 

    @Autowired
    public CompanyService(CompanyRepository companyRepository, PeopleRepository peopleRepository) {
        this.companyRepository = companyRepository;
        this.peopleRepository = peopleRepository; 
    }

    // To save a company 
    @Transactional
    public Company saveCompany(Company company) {
        if (company == null) {
            throw new InvalidInputException("Company object cannot be null");
        }
        return companyRepository.saveCompany(company);
    }

    // To create a new company
    @Transactional
      public Company createCompany(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Company name cannot be null or empty");
        }
        if (companyRepository.existsByNameCompany(name)) {
            throw new UniqueViolationException("A company with this name already exists.");
        }
        Company company = new Company(name);
        return companyRepository.saveCompany(company);
    }

    // To add an employee or empleoyees in a company
    @Transactional
    public Company addEmployeesToCompany(Company company, List<People> employees) {
    if (company == null) {
        throw new IllegalArgumentException("Company cannot be null");
    }
    Optional<Company> existingCompany = companyRepository.getCompanyById(company.getId_company());
    if (!existingCompany.isPresent()) {
        throw new NotFoundException("Company with id " + company.getId_company() + " does not exist");
    }
    if (employees == null || employees.isEmpty()) {
        throw new IllegalArgumentException("Employee list cannot be null or empty");
    }
    for (People employee : employees) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        employee.setCompany(company);
        company.addPeople(employee);
    }
    return companyRepository.saveCompany(company);
}

    // To add a single employee to a company
    @Transactional 
    public Company addEmployeeToCompany(Company company, People employee) {
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null");
        }
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        Optional<Company> existingCompany = companyRepository.getCompanyById(company.getId_company());
        if (!existingCompany.isPresent()) {
            throw new NotFoundException("Company with id " + company.getId_company() + " does not exist");
        }
        Optional<People> existingEmployee = peopleRepository.getPeopleById(employee.getId());
        if (!existingEmployee.isPresent()) {
            throw new NotFoundException("Employee with id " + employee.getId() + " does not exist");
        }
        employee.setCompany(company);
        company.addPeople(employee);
        return companyRepository.saveCompany(company);
    }


    // To delete a company 
    @Transactional
    public void deleteCompany(String id) {
        if (id == null || id.isEmpty()) {
            throw new InvalidInputException("Company ID cannot be null or empty");
        }
        try {
            Long companyId = Long.parseLong(id);
            Optional<Company> companyOpt = companyRepository.getCompanyById(companyId);
            if (companyOpt.isPresent()) {
                Company company = companyOpt.get();
                companyRepository.deleteCompany(company.getId_company());
            } else {
            throw new NotFoundException("Company with id " + companyId + " not found");
        }
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("id", "Long", "Invalid ID format: " + id);
        }
    }

    // To list all companies 
    @Transactional 
    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }

    // To get a company by id 
    @Transactional 
    public Company getCompanyById(String id) {
    if (id == null || id.isEmpty()) {
        throw new InvalidInputException("Company ID cannot be null or empty");
    }
    try {
        Long companyId = Long.parseLong(id);
        return companyRepository.getCompanyById(companyId)
            .orElseThrow(() -> new NotFoundException("Company with id " + companyId + " not found"));
    } catch (NumberFormatException e) {
        throw new TypeMismatchException("id", "Long", "Invalid ID format: " + id);
    }
}

    // To get employees by the company id
    @Transactional 
    public List<People> getEmployeesByCompanyId(String idCompany) {
        if (idCompany == null || idCompany.isEmpty()) {
            throw new InvalidInputException("Company ID cannot be null or empty");
        }
        try {
            Long companyId = Long.parseLong(idCompany);
            List<People> employees = companyRepository.getEmployeesByCompanyId(companyId);
            if (employees == null || employees.isEmpty()) {
                throw new NotFoundException("No employees found for the company with id " + companyId);
            }
            return employees;
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("id", "Long", "Invalid ID format: " + idCompany);
        }
    }

    // To get employees by the company name
    @Transactional 
    public List<People> getEmployeesByCompanyName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Company name cannot be null or empty");
        }
        List<People> employees = companyRepository.getEmployeesByCompanyName(name);
        if (employees == null || employees.isEmpty()) {
            throw new NotFoundException("No employees found for the company with name " + name);
        }
        return employees;
    }
    
}
