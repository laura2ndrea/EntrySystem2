package campus.u2.entrysystem.company.infrastructure;

import campus.u2.entrysystem.company.application.CompanyService;
import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.people.application.PeopleService;
import campus.u2.entrysystem.people.domain.People;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Company addEmployeeToCompany(@PathVariable String idCompany, @PathVariable Long idEmployee) {
        Company company = companyService.getCompanyById(idCompany);
        People employee = peopleService.getPeopleById(idEmployee);
        return companyService.addEmployeeToCompany(company, employee);
    }

    @PostMapping("/{idCompany}")
    public ResponseEntity<People> savePeopleCompany(@PathVariable String idCompany, @RequestBody People people) {
        Company company = companyService.getCompanyById(idCompany);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        people.setCompany(company);
        People savedPeople = peopleService.savePeople(people);
        return ResponseEntity.ok(savedPeople);
    }

    @DeleteMapping("/{idCompany}/{idpeople}")
    public ResponseEntity<String> deletePeopleCompany(@PathVariable String idCompany, @PathVariable Long idpeople) {
        Company company = companyService.getCompanyById(idCompany);
        People people = peopleService.getPeopleById(idpeople);
        if (!company.getPeopleList().contains(people)) {
            return new ResponseEntity<>("Person is not associated with the company", HttpStatus.BAD_REQUEST);
        }
        company.getPeopleList().remove(people);
        companyService.saveCompany(company);
        return new ResponseEntity<>("Person deleted successfully", HttpStatus.OK);
    }

    // To delete a company 
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable String id) {
        companyService.deleteCompany(id);
    }

    // To get all the companies 
    @GetMapping
    public List<Company> listAllCompanies() {
        return companyService.listAllCompanies();
    }

    // To get a company for the id 
    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable String id) {
        return companyService.getCompanyById(id);
    }

    // To get all the employees by the company id 
    @GetMapping("/{id}/employees")
    public List<People> getEmployeesByCompanyId(@PathVariable String id) {
        return companyService.getEmployeesByCompanyId(id);
    }

    // To get all the employees by the company name 
    @GetMapping("/employees")
    public List<People> getEmployeesByCompanyName(@RequestParam String name) {
        return companyService.getEmployeesByCompanyName(name);
    }

    // To update the name of a company 
    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable String id, @RequestBody Company newCompany) {
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

    @PutMapping("/{idCompany}/{identifier}")
    public ResponseEntity<People> updatePeopleFromCompany(@PathVariable String idCompany, @PathVariable String identifier, @RequestBody People people) {
        Company company = companyService.getCompanyById(idCompany);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        People peopleToUpdate;
        try {
            Long id = Long.valueOf(identifier);
            peopleToUpdate = peopleService.getPeopleById(id);
        } catch (NumberFormatException e) {
            peopleToUpdate = peopleService.getPeopleByCedula(identifier);
        }
        if (peopleToUpdate == null || !peopleToUpdate.getCompany().getId_company().equals(idCompany)) {
            return ResponseEntity.notFound().build();
        }
        if (people.getName() != null) {
            peopleToUpdate.setName(people.getName());
        }
        if (people.getCedula() != null) {
            peopleToUpdate.setCedula(people.getCedula());
        }
        if (people.getPersonType() != null) {
            peopleToUpdate.setPersonType(people.getPersonType());
        }
        if (people.getTelefono() != null) {
            peopleToUpdate.setTelefono(people.getTelefono());
        }
        peopleService.savePeople(peopleToUpdate);
        return ResponseEntity.ok(peopleToUpdate);
    }
}
