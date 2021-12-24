package com.peopleapps.repository;

import com.peopleapps.dto.accountBalance.AdmAccountBalanceDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceInfoDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceListDto;
import com.peopleapps.dto.accountTypeMovementAllowed.AdmAccountTypeMovementDto;
import com.peopleapps.dto.accountTypeMovementAllowed.AdmAccountTypeMovementDto.AdmMovements;
import com.peopleapps.dto.accountTypeMovementAllowed.AdmAccountTypeMovementListDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.dto.typology.AdmTypologyListDto;
import com.peopleapps.dto.user.AdmUserDto;
import com.peopleapps.dto.user.AdmUserInfoDto;
import com.peopleapps.model.*;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmAccountTypeMovement.class)
public abstract class AdmAccountTypeMovementRepository extends AbstractEntityRepository<AdmAccountTypeMovement, Long>
        implements CriteriaSupport<AdmAccountTypeMovement> {

    @Inject
    EntityManager em;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    Logger logger;

    public List<AdmTypologyDto> getAllAccountTypeMovements(
            Long accountTypeId,
            Long transactionTypeId,
            Long statusId,
            Boolean desc
    ) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n" +
                "       operationTypo.typology_id,\n" +
                "       operationTypo.description,\n" +
                "       operationTypo.value_1,\n" +
                "       operationTypo.value_2,\n" +
                "       operationTypo.editable,\n" +
                "       parentTypo.typology_id        parent_typology_id,\n" +
                "       parentTypo.description        parent_typology_description\n" +
                "FROM adm_account_allowed_movements accountMovements\n" +
                "JOIN adm_typology operationTypo\n" +
                "ON accountMovements.operation       = operationTypo.typology_id\n" +
                "JOIN adm_typology parentTypo\n" +
                "ON operationTypo.parent_typology_id = parentTypo.typology_id" +
                "\nWHERE 1 = 1 ");
        if (accountTypeId > 0) {
            query.append("\nAND accountMovements.account_type = ").append(accountTypeId);
        }
        if (transactionTypeId > 0) {
            query.append("\nAND accountMovements.operation_type = ").append(transactionTypeId);
        }

        if (statusId > 0) {
            query.append("\nAND accountMovements.status = ").append(statusId);
        } else if (statusId.equals(CsnConstants.STATUS_ACTIVE) || statusId.equals(CsnConstants.ZERO)) {
            query.append("\nAND accountMovements.status = ").append(CsnConstants.STATUS_ACTIVE);
        }

        if (desc != null && desc) {
            query.append("\nORDER BY account_type DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY account_type ASC");
        }

        //logger.error(query.toString());

        Stream<AdmTypologyListDto> q = em.createNativeQuery(query.toString(), "admTypologyListDto").getResultStream();
        List<AdmTypologyDto> result = q.map(item -> {
            AdmTypologyDto admTypologyDto = new AdmTypologyDto(
                    item.getTypologyId(),
                    item.getDescription(),
                    item.getValue1(),
                    item.getValue2(),
                    item.getAvailable(),
                    item.getEditable(),
                    new AdmTypologyDto(
                            item.getParentTypologyId(),
                            item.getParentTypologyDescription()
                    )
            );
            return admTypologyDto;
        }).collect(Collectors.toList());

        return result;
    }

    //method that gets movements given an account type
    public List<AdmAccountTypeMovement> getAccountTypeMovements(Long accountTypeId) {
        //Apply logic only if property is TRUE

        Criteria<AdmAccountTypeMovement, AdmAccountTypeMovement> query = criteria();
        query.join(AdmAccountTypeMovement_.accountType,
                where(AdmTypology.class)
                        .eq(AdmTypology_.typologyId, accountTypeId))
                .join(AdmAccountTypeMovement_.status,
                        where(AdmTypology.class)
                                .eq(AdmTypology_.typologyId, CsnConstants.STATUS_ACTIVE));

        List<AdmAccountTypeMovement> admAccountTypeMovements = query.getResultList();
        return (admAccountTypeMovements.size() > 0) ? admAccountTypeMovements : null;
    }

    public void persistMovements(AdmTypology accountType, AdmTypology transactionType, List<AdmTypologyDto> admMovementsDto) {
        //get status objects to avoid constant calls
        AdmTypology activeStatus = admTypologyRepository.findBy(CsnConstants.STATUS_ACTIVE);
        AdmTypology inactiveStatus = admTypologyRepository.findBy(CsnConstants.STATUS_INACTIVE);

        //first we set the current movements to inactive
        //a request is sent from the front sending an empty list if no values are selected, it must be ignored
        if (admMovementsDto.size() > 0) {

            List<AdmAccountTypeMovement> currentMovements = this.getAccountTypeMovements(accountType.getTypologyId());
            if (currentMovements != null) {
                for (AdmAccountTypeMovement movement : currentMovements) {
                    //update only the movements according to transaction type and account type
                    if(movement.getOperationType().getTypologyId().equals(transactionType.getTypologyId())
                    && movement.getAccountType().getTypologyId().equals(accountType.getTypologyId())){
                        movement.setStatus(inactiveStatus);
                        this.save(movement);
                    }

                }
            }
        }

        for (AdmTypologyDto movement : admMovementsDto) {
            AdmAccountTypeMovement admAccountTypeMovement = new AdmAccountTypeMovement(
                    0L,
                    accountType,
                    //operation
                    admTypologyRepository.findBy(movement.getTypologyId()),
                    //operation type
                    transactionType,
                    admUserRepository.findBy(CsnConstants.DEFAULT_USER_ID),
                    CsnFunctions.now(),
                    activeStatus
            );
            this.save(admAccountTypeMovement);
        }
    }
}
