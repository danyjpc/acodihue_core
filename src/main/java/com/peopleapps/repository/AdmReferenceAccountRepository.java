package com.peopleapps.repository;


import com.peopleapps.model.AdmReferenceAccount;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository(forEntity = AdmReferenceAccount.class)
public abstract class AdmReferenceAccountRepository extends AbstractEntityRepository<AdmReferenceAccount, Long>
        implements CriteriaSupport<AdmReferenceAccount> {

    public AdmReferenceAccount createAccount() {
        AdmReferenceAccount admReferenceAccount = new AdmReferenceAccount();
        admReferenceAccount.setDateCreated(CsnFunctions.now());
        return this.save(admReferenceAccount);
    }
}
