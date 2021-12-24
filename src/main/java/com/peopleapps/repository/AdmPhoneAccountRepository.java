package com.peopleapps.repository;

import com.peopleapps.model.AdmAddressAccount;
import com.peopleapps.model.AdmPhoneAccount;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository(forEntity = AdmPhoneAccount.class)
public abstract class AdmPhoneAccountRepository extends AbstractEntityRepository<AdmPhoneAccount, Long>
        implements CriteriaSupport<AdmPhoneAccount> {

    public AdmPhoneAccount createAccount() {
        AdmPhoneAccount admPhoneAccount = new AdmPhoneAccount();
        admPhoneAccount.setDateCreated(CsnFunctions.now());
        return this.save(admPhoneAccount);
    }
}
