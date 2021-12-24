package com.peopleapps.repository;

import com.peopleapps.dto.credit.AdmCreditDto;
import com.peopleapps.dto.credit.AdmCreditListDto;
import com.peopleapps.dto.credit.AdmCreditRequirementDto;
import com.peopleapps.dto.creditCalculator.AdmCreditCalculatorDto;
import com.peopleapps.dto.creditLine.AdmCreditLineDto;
import com.peopleapps.dto.document.AdmDocumentDto;
import com.peopleapps.dto.globalSearch.AdmGlobalSearchDto;
import com.peopleapps.dto.globalSearch.AdmGlobalSearchListDto;
import com.peopleapps.dto.guarantee.AdmGuaranteeDto;
import com.peopleapps.dto.multiAccount.AdmActivityAccountDto;
import com.peopleapps.dto.multiAccount.AdmAddressAccountDto;
import com.peopleapps.dto.multiAccount.AdmDocumentAccountDto;
import com.peopleapps.dto.multiAccount.AdmReferenceAccountDto;
import com.peopleapps.dto.partner.AdmPartnerListDto;
import com.peopleapps.dto.person.AdmPersonDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.model.*;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import com.peopleapps.util.StringMatcher;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository(forEntity = AdmCredit.class)
public abstract class AdmCreditRepository extends AbstractEntityRepository<AdmCredit, Long>
        implements CriteriaSupport<AdmCredit> {

    @Inject
    EntityManager em;

    @Inject
    AdmAddressAccountRepository admAddressAccountRepository;

    @Inject
    AdmReferenceAccountRepository admReferenceAccountRepository;

    @Inject
    AdmDocumentAccountRepository admDocumentAccountRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmCreditCalculatorRepository admCreditCalculatorRepository;

    @Inject
    AdmDocumentRepository admDocumentRepository;

    @Inject
    AdmGuaranteeRepository admGuaranteeRepository;

    @Inject
    Logger logger;

    public List<AdmCreditDto> getAllCredits(
            UUID creditKey,
            Boolean desc,
            UUID associateKey,
            String date_ini,
            String date_end,
            Long statusOperated,
            UUID agencyKey) {
        StringBuilder query = getOriginalQuery();

        query.append("WHERE 1 = 1");
        if (creditKey != null) {
            query.append("\nAND credit.credit_key = '").append(creditKey).append("'");
        }

        if (associateKey != null) {
            query.append("\nAND associate.person_key = '").append(associateKey).append("'");
        }

        if (date_ini != null && date_end != null) {
            date_ini += " 00:00:00";
            date_end += " 23:59:59";
            query.append("\nAND credit.date_created BETWEEN '")
                    .append(date_ini).append("' AND '")
                    .append(date_end).append("'");
        }
        if (statusOperated != null && statusOperated > 0) {
            query.append("\nAND credit.status_operated = ").append(statusOperated);
        }

        //search by agency
        if (agencyKey != null) {
            query.append("\nAND organization.organization_key = '").append(agencyKey).append("'");
        }
        if (desc != null && desc) {
            query.append("\nORDER BY credit.credit_id DESC");
        } else if (desc == null || !desc) {
            query.append("\nORDER BY credit.credit_id ASC");
        }


        Stream<AdmCreditListDto> q = em.createNativeQuery(query.toString(), "admCreditListDto").getResultStream();
        return q.map(item -> {
                    AdmCreditDto admCreditDto = new AdmCreditDto(
                            null,
                            item.getNoYear(),
                            item.getNoReference(),
                            null,
                            null,
                            null,
                            new AdmCreditDto.AdmPersonCreditDto(
                                    item.getCreatedByPersonKey(),
                                    item.getCreatedByEmail(),
                                    null,
                                    null,
                                    null
                            ),
                            item.getDateCreated(),
                            new AdmCreditDto.AdmTypologyCreditDto(
                                    item.getStatusId(),
                                    item.getStatusDescription()
                            ),
                            new AdmActivityAccountDto(
                                    item.getActivityAccount()
                            ),
                            item.getInternalCode(),
                            item.getEstateDate(),
                            new AdmCreditDto.AdmTypologyCreditDto(
                                    item.getStatusOperatedId(),
                                    item.getStatusOperatedDescription()
                            ),
                            new AdmCreditDto.AdmPersonCreditDto(
                                    item.getOperatedByPersonKey(),
                                    item.getOperatedByEmail(),
                                    null,
                                    null,
                                    null
                            ),
                            item.getAnnotation(),
                            new AdmCreditDto.AdmOrganizationCreditDto(
                                    item.getOrganizationKey(),
                                    item.getOrganizationName(),
                                    item.getOrganizationCommercial()
                            ),
                            /*
                            new AdmCreditDto.AdmOrganizationResponsibleCreditDto(
                                    item.getOrganizationResponsibleId(),
                                    new AdmCreditDto.AdmOrganizationCreditDto(
                                            item.getOrganizationKey(),
                                            item.getOrganizationName(),
                                            item.getOrganizationCommercial()
                                    ),
                                    new AdmCreditDto.AdmPersonCreditDto(
                                            item.getAssociatePersonKey(),
                                            null,
                                            item.getAssociateFirstName(),
                                            item.getAssociateLastName(),
                                            item.getAssociateNameComplete()
                                    )
                            ),

                             */
                            new AdmCreditDto.AdmTypologyCreditDto(
                                    item.getProfessionId(),
                                    item.getProfessionDescription()
                            ),
                            new AdmCreditDto.AdmTypologyCreditDto(
                                    item.getOccupationId(),
                                    item.getOccupationDescription()
                            ),
                            item.getOwnHouse(),
                            this.getCalculatorDto(item.getCalculatorId()),
                            item.getCreditKey()
                    );
                    return admCreditDto;
                }
        ).collect(Collectors.toList());

    }

    private AdmCreditCalculatorDto getCalculatorDto(Long calculatorId) {
        AdmCreditCalculator calculator = admCreditCalculatorRepository.findBy(calculatorId);
        AdmCreditCalculatorDto calculatorDto = new AdmCreditCalculatorDto(
                calculator.getCalculatorId(),
                new AdmPersonDto(
                        calculator.getPerson().getPersonKey(),
                        calculator.getPerson().getFirstName().toUpperCase(new Locale("es", "ES")),
                        calculator.getPerson().getMiddleName().toUpperCase(new Locale("es", "ES")),
                        calculator.getPerson().getLastName().toUpperCase(new Locale("es", "ES")),
                        null,
                        calculator.getPerson().getNameComplete().toUpperCase(new Locale("es", "ES")),
                        null
                ),
                calculator.getApplicationDate(),
                calculator.getNoPeriod(),
                calculator.getNoPayments(),
                CsnFunctions.redondearBigDecimal(new BigDecimal(calculator.getInterestRate().toString()), 2),
                CsnFunctions.redondearBigDecimal(new BigDecimal(calculator.getCredit().toString()), 2),
                calculator.getDateCreated(),
                null,
                null,
                CsnFunctions.redondearBigDecimal(new BigDecimal(calculator.getInterestFinal().toString()), 2),
                new AdmCreditLineDto(
                        calculator.getCreditLine().getCreditLineId(),
                        new AdmCreditLineDto.AdmCreditLineOrganizationDto(
                                calculator.getCreditLine().getOrganization().getOrganizationKey(),
                                calculator.getCreditLine().getOrganization().getOrganizationName(),
                                calculator.getCreditLine().getOrganization().getOrganizationCommercial()
                        ),
                        calculator.getCreditLine().getDescription(),
                        null,
                        null,
                        null
                )
        );
        return calculatorDto;
    }

    //find by credit key
    public AdmCredit findByKey(UUID key) {
        Criteria<AdmCredit, AdmCredit> query = criteria();
        query.eq(AdmCredit_.creditKey, key);
        List<AdmCredit> admCreditList = query.getResultList();
        return (admCreditList.size() > 0) ? admCreditList.get(0) : null;
    }

    //get last credit id for generating next credit internal code
    //index 0 max id, index 1  internal code for that max id
    public List<String> getLastCreditIdAndInternalCode() {
        ArrayList<String> respuesta = new ArrayList<>();

        StringBuilder query = new StringBuilder();
        query.append("SELECT MAX(credit_id) AS max_id, internal_code\n" +
                "FROM adm_credit\n" +
                "WHERE credit_id = (SELECT MAX(credit_id) FROM adm_credit)\n" +
                "GROUP BY internal_code");

        Query q = em.createNativeQuery(query.toString());
        List<Object[]> resultados = q.getResultList();
        if (resultados.size() > 0) {
            respuesta.add(resultados.get(0)[0].toString());
            respuesta.add(resultados.get(0)[1].toString());
        } else {
            logger.error("No credits found");
            respuesta = null;
        }

        return respuesta;
    }

    public AdmGlobalSearchDto globalSearch(String inputSearch) {
        StringBuilder query = getOriginalQuery();
        query.append("\nWHERE 1 = 1");

        Boolean filter = false;
        Boolean credit = false;

        if (StringMatcher.isCreditInternalCode(inputSearch)) {
            String[] tokens = inputSearch.split("-");
            //query.append("\nAND  (1 = 1  ");
            if (tokens.length == 2) {

                String agencyCode = tokens[0];
                Long creditNumber = Long.parseLong(tokens[1]);
                //prefixing zeroes to agency code
                String creditNumberWithZeroes = CsnFunctions.prefixZeroes(creditNumber, 4L);
                inputSearch = agencyCode + "-" + creditNumberWithZeroes;

                logger.error(inputSearch);
            }

            if (tokens.length == 3) {

                String agencyCode = tokens[0];
                Long creditNumber = Long.parseLong(tokens[1]);
                String year = tokens[2];
                //prefixing zeroes to agency code
                String creditNumberWithZeroes = CsnFunctions.prefixZeroes(creditNumber, 4L);
                inputSearch = agencyCode + "-" + creditNumberWithZeroes + "-" + year;

                logger.error(inputSearch);
            }

            filter = true;
            credit = true;

        }

        query.append("\nAND credit.internal_code ILIKE('%").append(inputSearch.toUpperCase()).append("%')");

        query.append("\nORDER BY credit.date_created DESC \n");

        if (!filter) {
            query.append("\nLIMIT 0");
        } else {
            query.append("\nLIMIT 100 \n ");
        }

        String searchType = credit ? "credit" : "to do";
        Stream<AdmCreditListDto> q = em.createNativeQuery(query.toString(), "admCreditListDto").getResultStream();
        List<AdmGlobalSearchDto.AdmGlobalSearchRecord> records = q.map(item -> {
            AdmGlobalSearchDto.AdmGlobalSearchRecord recs =
                    new AdmGlobalSearchDto.AdmGlobalSearchRecord(
                            new AdmCreditDto(
                                    null,
                                    item.getNoYear(),
                                    item.getNoReference(),
                                    null,
                                    null,
                                    null,
                                    new AdmCreditDto.AdmPersonCreditDto(
                                            item.getCreatedByPersonKey(),
                                            item.getCreatedByEmail(),
                                            null,
                                            null,
                                            null
                                    ),
                                    item.getDateCreated(),
                                    new AdmCreditDto.AdmTypologyCreditDto(
                                            item.getStatusId(),
                                            item.getStatusDescription()
                                    ),
                                    new AdmActivityAccountDto(
                                            item.getActivityAccount()
                                    ),
                                    item.getInternalCode(),
                                    item.getEstateDate(),
                                    new AdmCreditDto.AdmTypologyCreditDto(
                                            item.getStatusOperatedId(),
                                            item.getStatusOperatedDescription()
                                    ),
                                    new AdmCreditDto.AdmPersonCreditDto(
                                            item.getOperatedByPersonKey(),
                                            item.getOperatedByEmail(),
                                            null,
                                            null,
                                            null
                                    ),
                                    item.getAnnotation(),
                                    new AdmCreditDto.AdmOrganizationCreditDto(
                                            item.getOrganizationKey(),
                                            item.getOrganizationName(),
                                            item.getOrganizationCommercial()
                                    ),
                            /*
                            new AdmCreditDto.AdmOrganizationResponsibleCreditDto(
                                    item.getOrganizationResponsibleId(),
                                    new AdmCreditDto.AdmOrganizationCreditDto(
                                            item.getOrganizationKey(),
                                            item.getOrganizationName(),
                                            item.getOrganizationCommercial()
                                    ),
                                    new AdmCreditDto.AdmPersonCreditDto(
                                            item.getAssociatePersonKey(),
                                            null,
                                            item.getAssociateFirstName(),
                                            item.getAssociateLastName(),
                                            item.getAssociateNameComplete()
                                    )
                            ),

                             */
                                    new AdmCreditDto.AdmTypologyCreditDto(
                                            item.getProfessionId(),
                                            item.getProfessionDescription()
                                    ),
                                    new AdmCreditDto.AdmTypologyCreditDto(
                                            item.getOccupationId(),
                                            item.getOccupationDescription()
                                    ),
                                    item.getOwnHouse(),
                                    this.getCalculatorDto(item.getCalculatorId()),
                                    item.getCreditKey(),
                                    LocalDate.parse(CsnConstants.ONLY_DATE_EMPTY),
                                    CsnFunctions.redondearBigDecimal(new BigDecimal("0"), 2),
                                    CsnFunctions.redondearBigDecimal(new BigDecimal("0"), 2)
                            )
                    );

            return recs;
        }).collect(Collectors.toList());
        return new AdmGlobalSearchDto(searchType, records);
    }

    private StringBuilder getOriginalQuery() {
        StringBuilder query = new StringBuilder();

        query.append("SELECT\n" +
                "    credit.credit_id,\n" +
                "    credit.no_year,\n" +
                "    credit.no_reference,\n" +
                "    credit.address_account_id,\n" +
                "    credit.reference_account_id,\n" +
                "    credit.document_account_id,\n" +
                "    createdByPerson.email                             created_by_email,\n" +
                "    createdByPerson.person_key                        created_by_person_key,\n" +
                "    credit.date_created,\n" +
                "    statusTypo.typology_id                            status_id,\n" +
                "    statusTypo.description                            status_description,\n");
        //new properties
        query.append("    credit.activity_account_id,\n" +
                "    credit.internal_code,\n" +

                "    credit.estate_date,\n" +
                "    statusOperatedTypo.typology_id                    status_operated_id,\n" +
                "    statusOperatedTypo.description                    status_operated_description,\n" +
                "    operatedByPerson.email                            operated_by_email,\n" +
                "    operatedByPerson.person_key                       operated_by_person_key,\n" +
                "    credit.annotation,\n" +

                "    orgResponsible.organization_responsible_id        organization_responsible_id,\n" +
                "    organization.organization_key                     organization_key,\n" +
                "    organization.organization_name                    organization_name,\n" +
                "    organization.organization_commercial              organization_commercial,\n" +
                "    associate.person_key                              associate_person_key,\n" +
                "    associate.first_name                              associate_first_name,\n" +
                "    associate.last_name                               associate_last_name,\n" +
                "    associate.name_complete                           associate_name_complete,\n" +
                //new properties
                "    professionTypo.typology_id                        profession_id,\n" +
                "    professionTypo.description                        profession_description,\n" +
                "    occupationTypo.typology_id                        occupation_id,\n" +
                "    occupationTypo.description                        occupation_description,\n" +
                "    credit.own_house                                  own_house,\n" +
                "    calculator.calculator_id                          calculator_id,\n" +
                "    credit.credit_key\n" +

                "FROM adm_credit credit\n" +
                "INNER JOIN adm_user                     createdBy          ON credit.created_by                  = createdBy.user_id\n" +
                "INNER JOIN adm_person                   createdByPerson    ON createdBy.person_id                = createdByPerson.person_id\n" +
                "INNER JOIN adm_typology                 statusTypo         ON credit.status                      = statusTypo.typology_id\n" +

                "INNER JOIN adm_typology                 statusOperatedTypo ON credit.status_operated             = statusOperatedTypo.typology_id\n" +
                "INNER JOIN adm_user                     operatedByUser     ON credit.operated_by                 = operatedByUser.user_id\n" +
                "INNER JOIN adm_person                   operatedByPerson   ON operatedByUser.person_id           = operatedByPerson.person_id\n" +

                "INNER JOIN adm_organization_responsible orgResponsible     ON credit.organization_responsible_id = orgResponsible.organization_responsible_id\n" +
                "INNER JOIN adm_organization             organization       ON orgResponsible.organization_id     = organization.organization_id\n" +
                "INNER JOIN adm_person                   associate          ON orgResponsible.person_id           = associate.person_id\n" +

                "INNER JOIN adm_typology                 professionTypo     ON credit.profession_id               = professionTypo.typology_id\n" +
                "INNER JOIN adm_typology                 occupationTypo     ON credit.occupation_id               = occupationTypo.typology_id\n" +
                "INNER JOIN adm_calculator               calculator         ON credit.calculator_id               = calculator.calculator_id\n");
        return query;
    }

    //method that returns a list of completed requirements for a credit
    public List<AdmCreditRequirementDto> getCreditRequirementsList(AdmCredit credit) {
        try {
            //populating response list with inital values
            List<AdmCreditRequirementDto> requirementList = getInitialValues();
            //solving requirements

            //solving documents
            List<AdmDocumentDto> documentList = admDocumentRepository.getAllDocuments(0L, credit.getDocumentAccount().getDocumentAccountId(), false);
            if (documentList != null && documentList.size() > 0) {
                for (AdmDocumentDto documento : documentList) {
                    //logger.error("Doc id: "+documento.getDocumentCreditType().getTypologyId());
                    //logger.error("Doc desc: "+documento.getDocumentCreditType().getDescription());
                    //solving requirement 1.1
                    //Contrato mutuo
                    checkDocumentTypeId(documento, 160674L, 0, 0, requirementList);
                    //solving requirement 1.2
                    //Garantia del credito
                    checkDocumentTypeId(documento, 160675L, 0, 1, requirementList);

                    //solving requirement 2
                    //Cheque voucher
                    checkDocumentTypeId(documento, 160657L, 1, 0, requirementList);

                    //solving requirement 3
                    //Solicitud firmada por el socio
                    checkDocumentTypeId(documento, 160663L, 2, 0, requirementList);



                    //solving requirement 4
                    //dpi deudor
                    checkDocumentTypeId(documento, 160573L, 3, 0, requirementList);
                    //dpi fiador
                    checkDocumentTypeId(documento, 160656L, 3, 1, requirementList);

                     /* Removing Nit from requirements
                    //rtu deudor
                    checkDocumentTypeId(documento, 160576L, 3, 2, requirementList);
                    //rtu fiador
                    checkDocumentTypeId(documento, 160655L, 3, 3, requirementList);

                     **/

                    //solving requirement 5
                    //constancia residencia
                    checkDocumentTypeId(documento, 160575L, 4, 0, requirementList);
                    //constancia recibo luz
                    checkDocumentTypeId(documento, 160574L, 4, 1, requirementList);
                    //constancia recibo agua
                    checkDocumentTypeId(documento, 160658L, 4, 2, requirementList);
                    //Autorizacion COCODE
                    checkDocumentTypeId(documento, 160664L, 4, 3, requirementList);
                    //Autorizacion alcalde auxiliar
                    checkDocumentTypeId(documento, 160665L, 4, 4, requirementList);
                    //Autorizacion alcalde municipal
                    checkDocumentTypeId(documento, 160666L, 4, 5, requirementList);

                    //solving requirement 6
                    //Formularios ONU
                    checkDocumentTypeId(documento, 160667L, 5, 0, requirementList);
                    //Formularios OFAC
                    checkDocumentTypeId(documento, 160668L, 5, 1, requirementList);
                    //Formularios IVE
                    checkDocumentTypeId(documento, 160669L, 5, 2, requirementList);

                    //solving requirement 7
                    //Plan patrimonial
                    checkDocumentTypeId(documento, 160670L, 6, 0, requirementList);
                    //Estado patrimonial
                    checkDocumentTypeId(documento, 160671L, 6, 1, requirementList);

                    //solving requirement 8
                    //Resolucion comite de credito
                    checkDocumentTypeId(documento, 160672L, 7, 0, requirementList);

                    //solving requirement 9
                    //Resolucion consejo de administracion
                    checkDocumentTypeId(documento, 160673L, 8, 0, requirementList);


                    //solving requirement 10
                    //fotografia deudor
                    checkDocumentTypeId(documento, 160653L, 9, 0, requirementList);
                    //fotografia fiador
                    checkDocumentTypeId(documento, 160654L, 9, 1, requirementList);

                    //solving requirement 11
                    //Croquis ubicacion de la garantia
                    //TODO check for multiple guarantees
                    //checkDocumentTypeId(documento, 160659L, 10, 0, requirementList);

                    //solving requirement 13
                    //escritura original
                    checkDocumentTypeId(documento, 160660L, 12, 0, requirementList);
                    //constancia laboral
                    checkDocumentTypeId(documento, 160661L, 12, 1, requirementList);
                    //certificacion de ingresos
                    checkDocumentTypeId(documento, 160662L, 12, 2, requirementList);

                }

                //11 and 12
                List<AdmGuarantee> guaranteeList = admGuaranteeRepository.getAll(credit.getCreditId(), 0L);

                if (guaranteeList.size() > 0) {
                    //11 guarantee sketch
                    //TODO change requirement to dynamic according to number of guarantee records on table
                    double percentage = (100.0 / guaranteeList.size());

                    BigDecimal newPercentageRepresents = new BigDecimal(Double.toString(percentage));

                    //rounding number
                    newPercentageRepresents = CsnFunctions.redondearBigDecimal(newPercentageRepresents, 2);

                    //manually changing requirement 11 percentages
                    if (documentList.size() > 0) {
                        for (AdmDocumentDto documento : documentList) {
                            if (documento.getDocumentCreditType().getTypologyId() == 160659) {
                                BigDecimal currentComplete = requirementList.get(10).getPercentageComplete();
                                requirementList.get(10).setPercentageComplete(currentComplete.add(newPercentageRepresents));
                                //if > 99 set it to 100 (rounding problems)
                                if(requirementList.get(10).getPercentageComplete().compareTo(new BigDecimal("99")) >= 0){
                                    requirementList.get(10).setPercentageComplete(new BigDecimal("100"));
                                    //change to complete = true
                                    requirementList.get(10).getSubRequirements().get(0).setComplete(Boolean.TRUE);
                                }
                            }
                        }
                    }

                    //12 avaluo del credito
                    changeSubRequirementToComplete(11, 0, requirementList);
                }


            }
            return requirementList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //method that checks if the document type id is equal to the document passed as reference
    //if so, changes requirement to complete
    private void checkDocumentTypeId(AdmDocumentDto documento, Long documentId, Integer requirement, Integer sub, List<AdmCreditRequirementDto> requirementList) {
        if (documento.getDocumentCreditType().getTypologyId().equals(documentId)) {
            changeSubRequirementToComplete(requirement, sub, requirementList);
        }
    }

    //method that changes sub requirement to complete, requires requirement and sub requirement indexes
    private void changeSubRequirementToComplete(Integer requirement, Integer sub, List<AdmCreditRequirementDto> requirementList) {
        //apply logic only if FALSE to avoid double accumulation of same requirement on percentage
        if(requirementList.get(requirement).getSubRequirements().get(sub).getComplete() == Boolean.FALSE){
            requirementList.get(requirement).getSubRequirements().get(sub).setComplete(Boolean.TRUE);
            sumPercentageRepresents(requirement, sub, requirementList);
        }

    }

    //method that gets sub requirement percentage and adds it to the completed percentage
    private void sumPercentageRepresents(Integer requirement, Integer sub, List<AdmCreditRequirementDto> requirementList) {
        BigDecimal porcentajeParcial = requirementList.get(requirement).getSubRequirements().get(sub).getPercentageRepresented();
        BigDecimal currentTotalPercentage = requirementList.get(requirement).getPercentageComplete();
        //adding total percentage
        requirementList.get(requirement).setPercentageComplete(currentTotalPercentage.add(porcentajeParcial));
        //if percentage complete is > 99 then round it out to 100
        //Big decimal has to use compare to for evaluating value
        if(requirementList.get(requirement).getPercentageComplete().compareTo(new BigDecimal("99")) >= 0){
            requirementList.get(requirement).setPercentageComplete(new BigDecimal("100"));
        }
    }

    private List<AdmCreditRequirementDto> getInitialValues() {
        //image from https://dev.azure.com/People-Apps/Sistema%20Acodihue/_workitems/edit/1291/
        //13 current requirements
        List<AdmCreditRequirementDto> lista = new ArrayList<>();

        //first requirement begins----------------------------
        AdmCreditRequirementDto requerimiento = new AdmCreditRequirementDto();
        requerimiento.setNumber(1L);
        //sub requirement list
        List<AdmCreditRequirementDto.CreditSubRequirement> subRequirementList = new ArrayList<>();
        subRequirementList.add(generateSubRequirement("Contrato mutuo", new BigDecimal("50"), Boolean.FALSE));
        subRequirementList.add(generateSubRequirement("Garantia del credito", new BigDecimal("50"), Boolean.FALSE));
        requerimiento.setSubRequirements(subRequirementList);
        //For download
        requerimiento.setNameDocument("CONTRATO MUTUO");
        requerimiento.setDownload(Boolean.FALSE);
        requerimiento.setPathDownload("");
        //set percentage complete
        requerimiento.setPercentageComplete(new BigDecimal("0"));
        //add requirement to list
        lista.add(requerimiento);
        //first requirement ends -------------------------

        //second requirement begins----------------------------
        requerimiento = new AdmCreditRequirementDto();
        requerimiento.setNumber(2L);
        //sub requirement list
        subRequirementList = new ArrayList<>();
        subRequirementList.add(generateSubRequirement("Cheque Voucher", new BigDecimal("100"), Boolean.FALSE));
        requerimiento.setSubRequirements(subRequirementList);
        //For download
        requerimiento.setNameDocument("CHEQUE VOUCHER");
        requerimiento.setDownload(Boolean.FALSE);
        requerimiento.setPathDownload("cheque_voucher");
        //set percentage complete
        requerimiento.setPercentageComplete(new BigDecimal("0"));
        //add requirement to list
        lista.add(requerimiento);
        //second requirement ends -------------------------

        //third requirement begins----------------------------
        requerimiento = new AdmCreditRequirementDto();
        requerimiento.setNumber(3L);
        //sub requirement list
        subRequirementList = new ArrayList<>();
        subRequirementList.add(generateSubRequirement("Solicitud firmada por el socio", new BigDecimal("100"), Boolean.FALSE));
        requerimiento.setSubRequirements(subRequirementList);
        //For download
        requerimiento.setNameDocument("SOLICITUD DE CREDITO");
        requerimiento.setDownload(Boolean.TRUE);
        requerimiento.setPathDownload("solicitud_credito");
        //set percentage complete
        requerimiento.setPercentageComplete(new BigDecimal("0"));
        //add requirement to list
        lista.add(requerimiento);
        //third requirement ends -------------------------

        //fourth requirement begins----------------------------
        requerimiento = new AdmCreditRequirementDto();
        requerimiento.setNumber(4L);
        //sub requirement list
        subRequirementList = new ArrayList<>();
        subRequirementList.add(generateSubRequirement("Copia DPI deudor", new BigDecimal("50"), Boolean.FALSE));
        subRequirementList.add(generateSubRequirement("Copia DPI fiador", new BigDecimal("50"), Boolean.FALSE));
        //subRequirementList.add(generateSubRequirement("Copia RTU deudor", new BigDecimal("25"), Boolean.FALSE));
        //subRequirementList.add(generateSubRequirement("Copia RTU fiador", new BigDecimal("25"), Boolean.FALSE));
        requerimiento.setSubRequirements(subRequirementList);
        //For download
        requerimiento.setNameDocument("");
        requerimiento.setDownload(Boolean.FALSE);
        requerimiento.setPathDownload("");
        //set percentage complete
        requerimiento.setPercentageComplete(new BigDecimal("0"));
        //add requirement to list
        lista.add(requerimiento);
        //fourth requirement ends -------------------------

        //fifth requirement begins----------------------------
        requerimiento = new AdmCreditRequirementDto();
        requerimiento.setNumber(5L);
        //sub requirement list
        subRequirementList = new ArrayList<>();
        subRequirementList.add(generateSubRequirement("Constancia de residencia", new BigDecimal("16.66"), Boolean.FALSE));
        subRequirementList.add(generateSubRequirement("Copia recibo de luz", new BigDecimal("16.66"), Boolean.FALSE));
        subRequirementList.add(generateSubRequirement("Copia recibo de agua", new BigDecimal("16.66"), Boolean.FALSE));
        subRequirementList.add(generateSubRequirement("Autorizacion COCODE", new BigDecimal("16.66"), Boolean.FALSE));
        subRequirementList.add(generateSubRequirement("Autorizacion alcaldes auxiliares", new BigDecimal("16.66"), Boolean.FALSE));
        subRequirementList.add(generateSubRequirement("Autorizacion alcaldes municipales", new BigDecimal("16.66"), Boolean.FALSE));
        requerimiento.setSubRequirements(subRequirementList);
        //For download
        requerimiento.setNameDocument("");
        requerimiento.setDownload(Boolean.FALSE);
        requerimiento.setPathDownload("");
        //set percentage complete
        requerimiento.setPercentageComplete(new BigDecimal("0"));
        //add requirement to list
        lista.add(requerimiento);
        //fifth requirement ends -------------------------

        //sixth requirement begins----------------------------
        requerimiento = new AdmCreditRequirementDto();
        requerimiento.setNumber(6L);
        //sub requirement list
        subRequirementList = new ArrayList<>();
        subRequirementList.add(generateSubRequirement("Formularios ONU", new BigDecimal("33.33"), Boolean.FALSE));
        subRequirementList.add(generateSubRequirement("Formularios OFAC", new BigDecimal("33.33"), Boolean.FALSE));
        subRequirementList.add(generateSubRequirement("Formularios IVE", new BigDecimal("33.33"), Boolean.FALSE));
        requerimiento.setSubRequirements(subRequirementList);
        //For download
        requerimiento.setNameDocument("");
        requerimiento.setDownload(Boolean.FALSE);
        requerimiento.setPathDownload("");
        //set percentage complete
        requerimiento.setPercentageComplete(new BigDecimal("0"));
        //add requirement to list
        lista.add(requerimiento);
        //sixth requirement ends -------------------------

        //seventh requirement begins----------------------------
        requerimiento = new AdmCreditRequirementDto();
        requerimiento.setNumber(7L);
        //sub requirement list
        subRequirementList = new ArrayList<>();
        subRequirementList.add(generateSubRequirement("Plan patrimonial", new BigDecimal("50"), Boolean.FALSE));
        subRequirementList.add(generateSubRequirement("Estado patrimonial", new BigDecimal("50"), Boolean.FALSE));
        requerimiento.setSubRequirements(subRequirementList);
        //For download
        requerimiento.setNameDocument("ESTADO PATRIMONIAL");
        requerimiento.setDownload(Boolean.TRUE);
        requerimiento.setPathDownload("estado_patrimonial");
        //set percentage complete
        requerimiento.setPercentageComplete(new BigDecimal("0"));
        //add requirement to list
        lista.add(requerimiento);
        //seventh requirement ends -------------------------

        //eighth requirement begins----------------------------
        requerimiento = new AdmCreditRequirementDto();
        requerimiento.setNumber(8L);
        //sub requirement list
        subRequirementList = new ArrayList<>();
        subRequirementList.add(generateSubRequirement("Resolucion comite de credito", new BigDecimal("100"), Boolean.FALSE));
        requerimiento.setSubRequirements(subRequirementList);
        //For download
        requerimiento.setNameDocument("RESOLUCION COMITE DE CREDITO");
        requerimiento.setDownload(Boolean.TRUE);
        requerimiento.setPathDownload("resolucion_cc");
        //set percentage complete
        requerimiento.setPercentageComplete(new BigDecimal("0"));
        //add requirement to list
        lista.add(requerimiento);
        //eighth requirement ends -------------------------

        //ninth requirement begins----------------------------
        requerimiento = new AdmCreditRequirementDto();
        requerimiento.setNumber(9L);
        //sub requirement list
        subRequirementList = new ArrayList<>();
        subRequirementList.add(generateSubRequirement("Resolucion consejo de administracion", new BigDecimal("100"), Boolean.FALSE));
        requerimiento.setSubRequirements(subRequirementList);
        //For download
        requerimiento.setNameDocument("RESOLUCION CONSEJO DE ADMINISTRACION");
        requerimiento.setDownload(Boolean.TRUE);
        requerimiento.setPathDownload("resolucion_consejo_adm");
        //set percentage complete
        requerimiento.setPercentageComplete(new BigDecimal("0"));
        //add requirement to list
        lista.add(requerimiento);
        //ninth requirement ends -------------------------

        //tenth requirement begins----------------------------
        requerimiento = new AdmCreditRequirementDto();
        requerimiento.setNumber(10L);
        //sub requirement list
        subRequirementList = new ArrayList<>();
        subRequirementList.add(generateSubRequirement("Fotografia deudor", new BigDecimal("50"), Boolean.FALSE));
        subRequirementList.add(generateSubRequirement("Fotografia fiador", new BigDecimal("50"), Boolean.FALSE));
        requerimiento.setSubRequirements(subRequirementList);
        //For download
        requerimiento.setNameDocument("");
        requerimiento.setDownload(Boolean.FALSE);
        requerimiento.setPathDownload("");
        //set percentage complete
        requerimiento.setPercentageComplete(new BigDecimal("0"));
        //add requirement to list
        lista.add(requerimiento);
        //tenth requirement ends -------------------------

        //eleventh requirement begins----------------------------
        requerimiento = new AdmCreditRequirementDto();
        requerimiento.setNumber(11L);
        //sub requirement list
        subRequirementList = new ArrayList<>();
        subRequirementList.add(generateSubRequirement("Croquis ubicacion de la garantia", new BigDecimal("100"), Boolean.FALSE));
        requerimiento.setSubRequirements(subRequirementList);
        //For download
        requerimiento.setNameDocument("");
        requerimiento.setDownload(Boolean.FALSE);
        requerimiento.setPathDownload("");
        //set percentage complete
        requerimiento.setPercentageComplete(new BigDecimal("0"));
        //add requirement to list
        lista.add(requerimiento);
        //eleventh requirement ends -------------------------

        //twelfth requirement begins----------------------------
        requerimiento = new AdmCreditRequirementDto();
        requerimiento.setNumber(12L);
        //sub requirement list
        subRequirementList = new ArrayList<>();
        subRequirementList.add(generateSubRequirement("Avaluo del bien inmueble", new BigDecimal("100"), Boolean.FALSE));
        requerimiento.setSubRequirements(subRequirementList);
        //For download
        requerimiento.setNameDocument("AUTOAVALUO");
        requerimiento.setDownload(Boolean.TRUE);
        requerimiento.setPathDownload("autoavaluo");
        //set percentage complete
        requerimiento.setPercentageComplete(new BigDecimal("0"));
        //add requirement to list
        lista.add(requerimiento);
        //twelfth requirement ends -------------------------

        //thirteenth requirement begins----------------------------
        requerimiento = new AdmCreditRequirementDto();
        requerimiento.setNumber(13L);
        //sub requirement list
        subRequirementList = new ArrayList<>();
        subRequirementList.add(generateSubRequirement("Escritura original", new BigDecimal("33.33"), Boolean.FALSE));
        subRequirementList.add(generateSubRequirement("Constancia laboral", new BigDecimal("33.33"), Boolean.FALSE));
        subRequirementList.add(generateSubRequirement("Certificacion de ingresos", new BigDecimal("33.33"), Boolean.FALSE));
        requerimiento.setSubRequirements(subRequirementList);
        //For download
        requerimiento.setDownload(Boolean.FALSE);
        requerimiento.setPathDownload("");
        //set percentage complete
        requerimiento.setPercentageComplete(new BigDecimal("0"));
        //add requirement to list
        lista.add(requerimiento);
        //thirteenth requirement ends -------------------------
        return lista;
    }

    //returns subRequirement
    private AdmCreditRequirementDto.CreditSubRequirement generateSubRequirement(String description, BigDecimal percentageRepresented, Boolean complete) {
        AdmCreditRequirementDto.CreditSubRequirement subRequirement =
                new AdmCreditRequirementDto.CreditSubRequirement(description, percentageRepresented, complete);

        return subRequirement;
    }
}

