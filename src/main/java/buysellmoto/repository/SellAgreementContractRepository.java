package buysellmoto.repository;

import buysellmoto.model.entity.RejectRequestEntity;
import buysellmoto.model.entity.SellAgreementContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellAgreementContractRepository extends JpaRepository<SellAgreementContractEntity, Long> {

}