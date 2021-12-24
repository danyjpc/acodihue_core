package com.peopleapps.repository;


import com.peopleapps.model.AdmActivityAccount;
import com.peopleapps.util.CsnFunctions;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository(forEntity = AdmActivityAccount.class)
public abstract class AdmActivityAccountRepository extends AbstractEntityRepository<AdmActivityAccount, Long>
        implements CriteriaSupport<AdmActivityAccount> {

    public AdmActivityAccount createAccount() {
        AdmActivityAccount activityAccount = new AdmActivityAccount();
        activityAccount.setDateCreated(CsnFunctions.now());
        return this.save(activityAccount);
    }

}
