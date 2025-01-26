package campus.u2.entrysystem.membership.application;

import campus.u2.entrysystem.membership.domain.Membership;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
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
            throw new GlobalException("Membership cannot be null.");
        }
        return membershipRepository.save(membership);
    }

    @Transactional
    public Optional<Membership> findMembershipById(Long idMembership) {
        if (idMembership == null) {
            throw new GlobalException("Membership ID cannot be null.");
        }
        return membershipRepository.findById(idMembership);
                
    }

    @Transactional
    public List<Membership> findAllMemberships() {
        return membershipRepository.findAll();
    }

    @Transactional
    public Membership updateMembership(Long idMembership, Membership updatedMembership) {
        if (idMembership == null) {
            throw new GlobalException("Membership ID cannot be null.");
        }
        if (updatedMembership == null) {
            throw new GlobalException("Updated membership cannot be null.");
        }
        return membershipRepository.findById(idMembership)
                .map(existingMembership -> {
                    existingMembership.setDuration(updatedMembership.getDuration());
                    existingMembership.setPrice(updatedMembership.getPrice());
                    existingMembership.setVehicleType(updatedMembership.getVehicleType());
                    return membershipRepository.save(existingMembership);
                }).orElseThrow(() -> new GlobalException("Membership with ID " + idMembership + " not found."));
    }

    @Transactional
    public void deleteMembership(Long idMembership) {
        if (idMembership == null) {
            throw new GlobalException("Membership ID cannot be null.");
        }
        Membership membership = membershipRepository.findById(idMembership)
                .orElseThrow(() -> new GlobalException("Membership with ID " + idMembership + " not found."));
        membershipRepository.delete(membership);
    }

    public List<Membership> findMembershipsByDuration(Integer duration) {
        if (duration == null) {
            throw new GlobalException("Duration cannot be null.");
        }
        return membershipRepository.findByDuration(duration);
    }

    public List<Membership> findMembershipsByPrice(Double price) {
        if (price == null) {
            throw new GlobalException("Price cannot be null.");
        }
        return membershipRepository.findByPrice(price);
    }

    public List<Membership> findMembershipsByPriceLessThan(Double price) {
        if (price == null) {
            throw new GlobalException("Price cannot be null.");
        }
        return membershipRepository.findByPriceLessThan(price);
    }
}
