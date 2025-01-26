package campus.u2.entrysystem.company.infrastructure;

import campus.u2.entrysystem.company.application.CompanyService;
import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.people.application.PeopleService;
import campus.u2.entrysystem.people.domain.People;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    // Attributes 
    private final CompanyService companyService;
    private final PeopleService peopleService;

    // Constructor 
    @Autowired
    public CompanyController(CompanyService companyService, PeopleService peopleService) {
        this.companyService = companyService;
        this.peopleService = peopleService;
    }

    // Methods 
    // To save an company 
    @PostMapping
    public Company saveCompany(@RequestBody Company company) {
        return companyService.saveCompany(company);
    }

    // To add a employee to a company 
    @PostMapping("/{idCompany}/employee/{idEmployee}")
    public Company addEmployeeToCompany(@PathVariable Long idCompany, @PathVariable Long idEmployee) {
        Company company = companyService.getCompanyById(idCompany);
        People employee = peopleService.getPeopleById(idEmployee);
        return companyService.addEmployeeToCompany(company, employee);
    }

    // To delete a company 
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }

    // To get all the companies 
    @GetMapping
    public List<Company> listAllCompanies() {
        return companyService.listAllCompanies();
    }

    // To get a company for the id 
    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    // To get all the employees by the company id 
    @GetMapping("/{id}/employees")
    public List<People> getEmployeesByCompanyId(@PathVariable Long id) {
        return companyService.getEmployeesByCompanyId(id);
    }

    // To get all the employees by the company name 
    @GetMapping("/employees")
    public List<People> getEmployeesByCompanyName(@RequestParam String name) {
        return companyService.getEmployeesByCompanyName(name);
    }

    // To update the name of a company 
    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company newCompany) {
        Company company = companyService.getCompanyById(id);
        if (company != null) {
            if (newCompany.getName() != null) {
                company.setName(newCompany.getName());
            }
            return companyService.saveCompany(company);
        } else {
            return companyService.saveCompany(newCompany);
        }
    }
}
