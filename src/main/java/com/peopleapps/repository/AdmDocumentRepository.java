package com.peopleapps.repository;

import com.peopleapps.dto.document.AdmDocumentDto;
import com.peopleapps.dto.document.AdmDocumentListDto;
import com.peopleapps.model.*;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmDocument.class)
public abstract class AdmDocumentRepository extends AbstractEntityRepository<AdmDocument, Long>
        implements CriteriaSupport<AdmDocument> {

    @Inject
    EntityManager em;

    @Inject
    Logger logger;

    public List<AdmDocumentDto> getAllDocuments(
            Long documentId,
            Long document_account_id,
            Boolean desc
    ) throws Exception {

        StringBuilder query = getBaseQuery();

        query.append("\nWHERE 1 = 1\n");

        if (document_account_id != null && document_account_id > 0)
            query.append(" AND document.document_account_id = ").append(document_account_id);

        if (documentId != null && documentId > 0)
            query.append(" AND document.document_id = ").append(documentId);

        if (desc != null && desc) {
            query.append("\nORDER BY document.document_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY document.document_id ASC");
        }


        Stream<AdmDocumentListDto> q = em.createNativeQuery(query.toString(), "admDocumentListDto").getResultStream();

        return q.map(item -> {
            AdmDocumentDto admDocumentDto = new AdmDocumentDto(
                    item.getDocumentId(),
                    new AdmDocumentDto.AdmDocDocumentAccountDto(
                            item.getDocumentAccountId()
                    ),
                    item.getPath(),
                    new AdmDocumentDto.AdmDocumentPersonDto(
                            item.getCreatedByPersonKey(),
                            null,
                            null,
                            null,
                            item.getCreatedByEmail(),
                            null

                    ),
                    item.getDateCreated(),
                    new AdmDocumentDto.AdmDocumentTypologyDto(
                            item.getStatusId(),
                            item.getStatusDesc()
                    ),
                    item.getLeader(),
                    new AdmDocumentDto.AdmDocumentTypologyDto(
                            item.getDocumentCreditTypeId(),
                            item.getDocumentCreditTypeDesc()
                    ),
                    item.getDocumentName()
            );

            return admDocumentDto;
        }).collect(Collectors.toList());
    }

    public AdmDocument createDocument(AdmDocument admDocument) {


        if (admDocument.getCreatedBy() == null) {
            admDocument.setCreatedBy(new AdmUser(CsnConstants.DEFAULT_USER_ID));
        }

        admDocument.setDateCreated(CsnFunctions.now());
        admDocument.setStatus(new AdmTypology(CsnConstants.STATUS_ACTIVE));
        if (admDocument.getLeader() == null) {
            admDocument.setLeader(Boolean.FALSE);
        }
        //TODO check default credit Type Typology
        if (admDocument.getDocumentCreditType() == null) {
            admDocument.setDocumentCreditType(new AdmTypology(CsnConstants.DOCUMENT_TYPE));
        }
        return this.save(admDocument);
    }


    public void saveEdit(AdmDocument newDocument) {
        AdmDocument currentDocument = this.findBy(newDocument.getDocumentId());

        newDocument.setDocumentId(currentDocument.getDocumentId());
        newDocument.setCreatedBy(currentDocument.getCreatedBy());
        newDocument.setDateCreated(currentDocument.getDateCreated());

        if (newDocument.getDocumentAccount() == null) {
            newDocument.setDocumentAccount(currentDocument.getDocumentAccount());
        }
        if (newDocument.getPath() == null) {
            newDocument.setPath(currentDocument.getPath());
        }
        if (newDocument.getStatus() == null) {
            newDocument.setStatus(currentDocument.getStatus());
        }
        if (newDocument.getLeader() == null) {
            newDocument.setLeader(currentDocument.getLeader());
        }
        if (newDocument.getDocumentCreditType() == null) {
            newDocument.setDocumentCreditType(currentDocument.getDocumentCreditType());
        }
        if (newDocument.getDocumentName() == null) {
            newDocument.setDocumentName(currentDocument.getDocumentName());
        }

        this.save(newDocument);
    }


    private StringBuilder getBaseQuery(){
        StringBuilder query = new StringBuilder();

        query.append("SELECT\n" +
                "    document.document_id,\n" +
                "    document.document_account_id,\n" +
                "    document.path,\n" +
                "    createdByPerson.email                created_by_email,\n" +
                "    createdByPerson.person_key           created_by_person_key,\n" +
                "    document.date_created,\n" +
                "    statusTypo.typology_id               status_id,\n" +
                "    statusTypo.description               status_desc,\n" +
                "    document.leader,\n" +
                "    docCreditTypeTypo.typology_id        document_credit_type_id,\n" +
                "    docCreditTypeTypo.description        document_credit_type_desc,\n" +
                "    document.document_name               document_name\n" +
                "FROM adm_document document\n" +
                "INNER JOIN adm_user createdBy ON document.created_by = createdBy.user_id\n" +
                "INNER JOIN adm_person createdByPerson ON createdBy.person_id = createdByPerson.person_id\n" +
                "INNER JOIN adm_typology statusTypo ON statusTypo.typology_id = document.status\n" +
                "INNER JOIN adm_typology docCreditTypeTypo ON docCreditTypeTypo.typology_id = document.document_credit_type\n");

        return query;
    }

}