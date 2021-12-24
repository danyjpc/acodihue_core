package com.peopleapps.repository;

import com.peopleapps.model.AdmAddressAccount;
import com.peopleapps.model.AdmPhrase;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository(forEntity = AdmAddressAccount.class)
public abstract class AdmAddressAccountRepository extends AbstractEntityRepository<AdmAddressAccount, Long>
        implements CriteriaSupport<AdmAddressAccount> {

    public AdmAddressAccount createAccount() {
        AdmAddressAccount admAddressAccount = new AdmAddressAccount();
        admAddressAccount.setDateCreated(CsnFunctions.now());
        return this.save(admAddressAccount);
    }
}
