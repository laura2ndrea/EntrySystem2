package campus.u2.entrysystem.company.application;

import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.people.domain.People;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // To save a company 
    public Company saveCompany(Company company) {
        if (company == null) {
            throw new GlobalException("Company object is null");
        }
        return companyRepository.saveCompany(company);
    }

    // To create a new company
    public Company createCompany(String name) {
        if (name == null || name.isEmpty()) {
            throw new GlobalException("Name cannot be empty");
        }
        if (companyRepository.existsByNameCompany(name)) {
            throw new GlobalException("A company with this name already exists.");
        }
        Company company = new Company(name);
        return companyRepository.saveCompany(company);
    }

    // To add an employee or empleoyees in a company
    public Company addEmployeesToCompany(Company company, List<People> employees) {
        if (company == null || employees == null || employees.isEmpty()) {
            throw new GlobalException("Company or employee list is null or empty");
        }

        for (People employee : employees) {
            if (employee == null) {
                throw new GlobalException("Employee is null");
            }
            employee.setCompany(company);
            company.addPeople(employee);
        }
        return companyRepository.saveCompany(company);
    }

    // To add a single employee to a company
    public Company addEmployeeToCompany(Company company, People employee) {
        if (company == null) {
            throw new GlobalException("Company cannot be null");
        }
        if (employee == null) {
            throw new GlobalException("Employee cannot be null");
        }
        employee.setCompany(company);
        company.addPeople(employee);
        return companyRepository.saveCompany(company);
    }

    // To delete a company 
    public void deleteCompany(Long id) {
        Optional<Company> companyOpt = companyRepository.getCompanyById(id);
        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();
            companyRepository.deleteCompany(company.getId_company());
        } else {
            throw new RuntimeException("Company with id " + id + " not found");
        }
    }

    // To list all companies 
    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }

    // To get a company by id 
    public Company getCompanyById(Long id) {
        return companyRepository.getCompanyById(id)
                .orElseThrow(() -> new RuntimeException("Company with id " + id + " not found"));
    }

    // To get employees by the company id
    public List<People> getEmployeesByCompanyId(Long idCompany) {
        if (idCompany == null) {
            throw new GlobalException("Company id cannot be null");
        }
        List<People> employees = companyRepository.getEmployeesByCompanyId(idCompany);
        if (employees == null || employees.isEmpty()) {
            throw new GlobalException("No employees found for the company with id " + idCompany);
        }
        return employees;
    }

    // To get employees by the company name
    public List<People> getEmployeesByCompanyName(String name) {
        if (name == null) {
            throw new GlobalException("Company name cannot be null");
        }
        List<People> employees = companyRepository.getEmployeesByCompanyName(name);
        if (employees == null || employees.isEmpty()) {
            throw new GlobalException("No employees found for the company with name " + name);
        }
        return employees;
    }

}
