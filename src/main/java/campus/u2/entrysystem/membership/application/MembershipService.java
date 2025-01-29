package campus.u2.entrysystem.membership.application;

import campus.u2.entrysystem.membership.domain.Membership;
import campus.u2.entrysystem.Utilities.exceptions.InvalidInputException;
import campus.u2.entrysystem.Utilities.exceptions.NotFoundException;
import campus.u2.entrysystem.Utilities.exceptions.TypeMismatchException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;

    @Autowired
    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Transactional
    public Membership saveMembership(Membership membership) {
        if (membership == null) {
            throw new InvalidInputException("Membership cannot be null.");
        }
        return membershipRepository.save(membership);
    }


    @Transactional
    public Membership findMembershipById(String idMembership) {
        if (idMembership == null || idMembership.isBlank()) {
            throw new InvalidInputException("Membership ID cannot be null or empty.");
        }
        try {
            Long id = Long.parseLong(idMembership);
            return membershipRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Membership with ID " + id + " not found."));
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("idMembership", "Long", "invalid format: " + idMembership);
        }
    }

    @Transactional
    public List<Membership> findAllMemberships() {
        return membershipRepository.findAll();
    }

    @Transactional
    public Membership updateMembership(String idMembership, Membership updatedMembership) {
        if (idMembership == null || idMembership.isBlank()) {
            throw new InvalidInputException("Membership ID cannot be null or empty.");
        }
        if (updatedMembership == null) {
            throw new InvalidInputException("Updated membership cannot be null.");
        }
        try {
            Long id = Long.parseLong(idMembership);
            return membershipRepository.findById(id)
                    .map(existingMembership -> {
                        existingMembership.setDuration(updatedMembership.getDuration());
                        existingMembership.setPrice(updatedMembership.getPrice());
                        existingMembership.setVehicleType(updatedMembership.getVehicleType());
                        return membershipRepository.save(existingMembership);
                    }).orElseThrow(() -> new NotFoundException("Membership with ID " + id + " not found."));
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("idMembership", "Long", "invalid format: " + idMembership);
        }
    }

    @Transactional
    public void deleteMembership(String idMembership) {
        if (idMembership == null || idMembership.isBlank()) {
            throw new InvalidInputException("Membership ID cannot be null or empty.");
        }
        try {
            Long id = Long.parseLong(idMembership);
            Membership membership = membershipRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Membership with ID " + id + " not found."));
            membershipRepository.delete(membership);
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("idMembership", "Long", "invalid format: " + idMembership);
        }
    }

    @Transactional 
    public List<Membership> findMembershipsByDuration(Integer duration) {
        if (duration == null) {
            throw new InvalidInputException("Duration cannot be null.");
        }
        if (duration <= 0) {
            throw new InvalidInputException("Duration must be a positive number.");
        }
        List<Membership> memberships = membershipRepository.findByDuration(duration);
        if (memberships.isEmpty()) {
            throw new NotFoundException("No memberships found with duration: " + duration);
        }
        return memberships;
    }
    
    @Transactional 
    public List<Membership> findMembershipsByPrice(Double price) {
        if (price == null) {
            throw new InvalidInputException("Price cannot be null.");
        }
        if (price < 0) {
            throw new InvalidInputException("Price cannot be negative.");
        }
        List<Membership> memberships = membershipRepository.findByPrice(price);
        if (memberships == null || memberships.isEmpty()) {
            throw new NotFoundException("No memberships found with the price " + price);
        }
        return memberships;
    }

    @Transactional 
    public List<Membership> findMembershipsByPriceLessThan(Double price) {
        if (price == null) {
            throw new InvalidInputException("Price cannot be null.");
        }
        if (price < 0) {
            throw new InvalidInputException("Price cannot be negative.");
        }
        List<Membership> memberships = membershipRepository.findByPriceLessThan(price);
        if (memberships == null || memberships.isEmpty()) {
            throw new NotFoundException("No memberships found with price less than " + price);
        }
        return memberships;
    }
}
