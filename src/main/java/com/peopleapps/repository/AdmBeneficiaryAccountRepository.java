package com.peopleapps.repository;

import com.peopleapps.model.AdmBeneficiaryAccount;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository(forEntity = AdmBeneficiaryAccount.class)
public abstract class AdmBeneficiaryAccountRepository extends AbstractEntityRepository<AdmBeneficiaryAccount, Long>
        implements CriteriaSupport<AdmBeneficiaryAccount> {

    public AdmBeneficiaryAccount createAccount() {
        AdmBeneficiaryAccount admBeneficiaryAccount = new AdmBeneficiaryAccount();
        admBeneficiaryAccount.setDateCreated(CsnFunctions.now());
        return this.save(admBeneficiaryAccount);
    }
}
