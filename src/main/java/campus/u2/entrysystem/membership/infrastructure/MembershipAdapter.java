
package campus.u2.entrysystem.membership.infrastructure;

import campus.u2.entrysystem.membership.application.MembershipRepository;
import campus.u2.entrysystem.membership.domain.Membership;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MembershipAdapter implements MembershipRepository{
    
    
    private final MembershipJpaRepository membershipJpaRepository;
    
    @Autowired
    public MembershipAdapter(MembershipJpaRepository membershipJpaRepository) {
        this.membershipJpaRepository = membershipJpaRepository;
    }

    @Override
    @Transactional
    public Membership save(Membership membership) {
        return membershipJpaRepository.save(membership);
       
    }

    @Override
    public Optional<Membership> findById(Long idMembership) { 
        return membershipJpaRepository.findById(idMembership);
    }

    
    
    @Override
    public List<Membership> findAll() {
      
        return membershipJpaRepository.findAll();        
    }

    @Override
    @Transactional
    public void delete(Membership membership) {
      
        Optional<Membership> optionalmembership = membershipJpaRepository.findById(    membership.getIdMembership());
        if(optionalmembership.isPresent()){
            membershipJpaRepository.delete(optionalmembership.get());
        }
        
        
    }

    
    
    @Override
    public List<Membership> findByDuration(Integer duration) {
        
        return membershipJpaRepository.findByDuration(duration);
    }

    @Override
    public List<Membership> findByPrice(Double price) {
       return membershipJpaRepository.findByPrice(price);
    }

    @Override
    public List<Membership> findByPriceLessThan(Double price) {
        return membershipJpaRepository.findByPriceLessThan(price);
    }
    
    
    
    
    
    
    
    
}
