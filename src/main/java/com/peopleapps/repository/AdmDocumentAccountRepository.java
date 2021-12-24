package com.peopleapps.repository;

import com.peopleapps.model.AdmDocumentAccount;
import com.peopleapps.model.AdmPhoneAccount;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository(forEntity = AdmDocumentAccount.class)
public abstract class AdmDocumentAccountRepository extends AbstractEntityRepository<AdmDocumentAccount, Long>
        implements CriteriaSupport<AdmDocumentAccount> {

    public AdmDocumentAccount createAccount() {
        AdmDocumentAccount admDocumentAccount = new AdmDocumentAccount();
        admDocumentAccount.setDateCreated(CsnFunctions.now());
        return this.save(admDocumentAccount);
    }
}
