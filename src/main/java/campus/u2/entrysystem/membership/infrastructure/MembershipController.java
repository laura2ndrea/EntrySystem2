package campus.u2.entrysystem.membership.infrastructure;

import campus.u2.entrysystem.membership.application.MembershipService;
import campus.u2.entrysystem.membership.domain.Membership;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {

    private final MembershipService membershipService;

    @Autowired
    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    // Obtener todos 
    @GetMapping
    public List<Membership> getAllMembership() {
        return membershipService.findAllMemberships();
    }

    // Obtener by Id 
    @GetMapping("/{idMembership}")
    public Membership findMembershipById(@PathVariable String idMembership) {
        return membershipService.findMembershipById(idMembership);
    }

    // Delete con el id 
    // Funciona 
    @DeleteMapping("/{idMembership}")
    public void deleteMembership(@PathVariable String idMembership) {
        membershipService.deleteMembership(idMembership);
    }

    // Crear un nuevo Membership
    @PostMapping
    public ResponseEntity<Membership> createMembership(@RequestBody Membership membership) {
        try {
            Membership savedMembership = membershipService.saveMembership(membership);
            return new ResponseEntity<>(savedMembership, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/duration/{duration}")
    public  List<Membership> findMembershipByDuration(@PathVariable Integer duration) {
       return membershipService.findMembershipsByDuration(duration);
    }
    
    @GetMapping("/price/{price}")
    public  List<Membership> findMembershipByPrice(@PathVariable Double price) {
       return membershipService.findMembershipsByPrice(price);
    }

    @GetMapping("/priceless/{price}")
    public  List<Membership> findMembershipsByPriceLessThan(@PathVariable Double price) {
       return membershipService.findMembershipsByPriceLessThan(price);
    }


 
}
