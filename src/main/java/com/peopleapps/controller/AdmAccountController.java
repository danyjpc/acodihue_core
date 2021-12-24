package com.peopleapps.controller;


import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.dto.account.AdmAccountDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceInfoDto;
import com.peopleapps.dto.typology.AdmTypologyDto;
import com.peopleapps.model.*;
import com.peopleapps.repository.*;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnConstants;
import com.peopleapps.util.CsnFunctions;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.DoubleStream;

@Path("/associate/v1")
@Tag(name = "accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmAccountController {

    @Inject
    AdmAccountRepository admAccountRepository;

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmAccountBalanceRepository admAccountBalanceRepository;

    @Inject
    AdmOrganizationResponsibleRepository admOrganizationResponsibleRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    Logger logger;


    @Inject
    ResponseJson responseJson;

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/account/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAll(
            @PathParam("key") UUID associateKey,
            @QueryParam("status") @DefaultValue("0") Long statusId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {
        try {
            List<AdmAccountDto> admAccounts = admAccountRepository.getAllAccounts(
                    associateKey,
                    null,
                    desc
            );
            for (AdmAccountDto accountDto : admAccounts) {
                List<AdmAccountBalanceInfoDto> movements = admAccountBalanceRepository.getMovementsByAccount(
                        associateKey,
                        accountDto.getPersonAccount().getAccount_id(),
                        statusId,
                        desc,
                        0L,
                        0L,
                        //TODO hacer cortes de saldo
                        "1900-01-01",
                        "9999-01-01"
                );
                Double balance = 0.00;
                if (movements.size() > 0) {
                    balance = movements.get(movements.size() - 1).getBalance();
                }
                accountDto.setBalance(balance);

            }

            return Response.status(Response.Status.OK).entity(admAccounts).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }

    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/account/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAccountById(
            @PathParam("key") UUID associateKey,
            @PathParam("id") @DefaultValue("0") Long accountId,
            @QueryParam("status") @DefaultValue("0") Long statusId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {

        try {
            if (associateKey == null)
                return Response.status(Response.Status.BAD_REQUEST).build();

            List<AdmAccountDto> admAccounts = admAccountRepository.getAllAccounts(
                    associateKey, accountId, desc
            );
            for (AdmAccountDto accountDto : admAccounts) {
                List<AdmAccountBalanceInfoDto> movements = admAccountBalanceRepository.getMovementsByAccount(
                        associateKey,
                        accountDto.getPersonAccount().getAccount_id(),
                        statusId,
                        desc,
                        0L,
                        0L,
                        //TODO hacer cortes de saldo
                        "1900-01-01",
                        "9999-01-01"
                );
                Double balance = 0.00;
                if (movements.size() > 0) {
                    balance = movements.get(movements.size() - 1).getBalance();
                }
                accountDto.setBalance(balance);
            }

            if (admAccounts.size() == 0)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.status(Response.Status.OK).entity(admAccounts.get(0)).build();
        } catch (Exception ex) {
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }


    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/account/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response createAccount(AdmAccountDto admAccountDto,
                                  @PathParam("key") UUID associateKey) {
        try {
            AdmPerson associate = this.admPersonRepository.findByKey(associateKey);
            if (associate == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Associate not found");
            }
            if (admAccountDto.getPersonAccount().getCreatedBy() == null || admAccountDto.getPersonAccount().getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User is mandatory");
            }
            if (admAccountDto.getPersonAccount().getAccountType() == null || admAccountDto.getPersonAccount().getAccountType().getTypologyId() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Account Type is mandatory");
            }

            AdmTypology accountType = this.admTypologyRepository.findBy(admAccountDto.getPersonAccount().getAccountType().getTypologyId());
            if (accountType == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incorrect Account Type");
            }
            //check if valu1 is correct
            if (accountType.getValue1().equals("")) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incorrect Account Type");
            }

            if (admAccountDto.getPersonAccount().getOrganizationResponsible() == null
                    || admAccountDto.getPersonAccount().getOrganizationResponsible().getOrganizationResponsibleId() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Organization Responsible is mandatory");
            }
            AdmOrganizationResponsible organizationResponsible = this.admOrganizationResponsibleRepository.findBy(admAccountDto.getPersonAccount().getOrganizationResponsible().getOrganizationResponsibleId());
            if (organizationResponsible == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Organization Responsible is mandatory");
            }

            AdmPerson createdByPerson = admPersonRepository.findByKey(admAccountDto.getPersonAccount().getCreatedBy().getPersonKey());
            if (createdByPerson == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found Error");
            }

            AdmUser createdByUser = admUserRepository.findByKey(createdByPerson.getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found Error");
            }

            //checking if account number and order are unique
            Boolean existNumberOrder = admAccountRepository.findByAccountNumberOrder(associate.getMembershipNumber(), Long.parseLong(accountType.getValue1()));
            if (existNumberOrder) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Account exists");
            }

            AdmTypology status = null;
            if (admAccountDto.getPersonAccount().getStatus() != null) {
                status = this.admTypologyRepository.findBy(admAccountDto.getPersonAccount().getStatus().getTypologyId());
            }
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }


            AdmAccount account = new AdmAccount(
                    CsnConstants.ZERO,
                    organizationResponsible,
                    accountType,
                    associate.getMembershipNumber(),
                    //11 savings, 10 contribution account
                    Long.parseLong(accountType.getValue1()),
                    createdByUser,
                    CsnFunctions.now(),
                    status
            );

            admAccountRepository.save(account);
            return CsnFunctions.setResponse(account.getAccountId(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @PUT
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/account/{id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response edit(@PathParam("id") Long id,
                         AdmAccountDto admAccountDto) {
        if (!id.equals(admAccountDto.getPersonAccount().getAccount_id())) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        AdmAccount currentAccount = this.admAccountRepository.findBy(id);
        if (currentAccount == null) {
            return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Account not found");
        }
        AdmTypology status = null;
        if (admAccountDto.getPersonAccount().getAccountType() != null
                && admAccountDto.getPersonAccount().getStatus().getTypologyId() != null) {
            status = admTypologyRepository.findBy(admAccountDto.getPersonAccount().getStatus().getTypologyId());
            currentAccount.setStatus(status);

        }
        admAccountRepository.saveEdit(currentAccount);
        return Response.status(Response.Status.OK).build();
    }


    @GET
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/account/{id:[0-9][0-9]*}/movements")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAccountMovemets(
            @PathParam("key") UUID associateKey,
            @PathParam("id") @DefaultValue("0") Long accountId,
            @QueryParam("status") @DefaultValue("0") Long statusId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc,
            @QueryParam("año") @DefaultValue("0") Long year,
            @QueryParam("mes") @DefaultValue("0") Long month,
            @QueryParam("startDate") @DefaultValue("") String startDate,
            @QueryParam("endDate") @DefaultValue("") String endDate,
            @QueryParam("type") @DefaultValue("json") String responseType,
            @QueryParam("file_name") @DefaultValue("movimientos") String fileName
    ) {
        try {
            List<AdmAccountBalanceInfoDto> admAccountBalanceInfoDtos = admAccountBalanceRepository.getMovementsByAccount(
                    associateKey,
                    accountId,
                    statusId,
                    desc,
                    year,
                    month,
                    startDate,
                    endDate
            );


            if (responseType.equals("json")) {
                //creating object for transactionType
                //iterating response so we can set the transaction type property
                for(AdmAccountBalanceInfoDto single: admAccountBalanceInfoDtos ){
                    AdmTypologyDto transactionType = new AdmTypologyDto(
                            single.getTransaction_type_id(),
                            single.getTransaction_type_description()
                    );
                    //set properties to null so they are not shown
                    single.setTransaction_type_id(null);
                    single.setTransaction_type_description(null);
                    //setting property value to the object created
                    single.setTransactionType(transactionType);
                }

                return Response.status(Response.Status.OK).entity(admAccountBalanceInfoDtos).build();

            } else if (responseType.equals("xlsx")) {
                Field[] fields = AdmAccountBalanceInfoDto.class.getDeclaredFields();


                // account for array resizing changes property index
                //removing property transactionType from field list
                fields = ArrayUtils.remove(fields, (fields.length - 1) );

                //removing account_balance_id
                fields = ArrayUtils.remove(fields, 0 );

                //removing account_id
                fields = ArrayUtils.remove(fields, 0 );

                //removing transaction_type_id
                fields = ArrayUtils.remove(fields, 6 );


                //convert dto list to List<Object[]>
                List<Object[]> resultObjects = new ArrayList<>();
                //from dto to Object[]
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(CsnConstants.DATE_FORMAT);
                for (AdmAccountBalanceInfoDto dto : admAccountBalanceInfoDtos) {
                    Object[] objectArray = new Object[fields.length];

                    // account for array resizing changes property index
                    //objectArray[0] = dto.getAccount_balance_id();
                    //objectArray[1] = dto.getAccount_id();
                    objectArray[0] = dto.getTransaction_no();
                    //Date with format
                    objectArray[1] = dto.getDate_created().format(dtf);
                    objectArray[2] = dto.getDebit();
                    objectArray[3] = dto.getCredit();
                    objectArray[4] = dto.getBalance();
                    objectArray[5] = dto.getAnnotation();
                    //objectArray[8] = dto.getTransaction_type_id();
                    objectArray[6] = dto.getTransaction_type_description();

                    resultObjects.add(objectArray);
                }


                return createReporteExcel(fields, resultObjects, fileName).build();
            } else {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Incorrect response type");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }

    //Persist para account balance
    @POST
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/account/{id:[0-9][0-9]*}/movements")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response persistMovement(
            @PathParam("key") UUID associateKey,
            @PathParam("id") @DefaultValue("0") Long accountId,
            AdmAccountBalanceDto admAccountBalanceDto) {
        try {

            AdmAccount account = this.admAccountRepository.findBy(accountId);
            if (account == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Account not found");
            }
            if (admAccountBalanceDto.getCreatedBy() == null || admAccountBalanceDto.getCreatedBy().getPersonKey() == null) {
                return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User is mandatory");
            }

            AdmUser createdByUser = this.admUserRepository.findByKey(admAccountBalanceDto.getCreatedBy().getPersonKey());
            if (createdByUser == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "User not found");
            }

            AdmTypology transactionType = admTypologyRepository.findBy(admAccountBalanceDto.getTransactionType().getTypologyId());
            if (transactionType == null) {

                transactionType = new AdmTypology(CsnConstants.REGULAR_SAVINGS_ACCOUNT);
            }

            AdmTypology status = admTypologyRepository.findBy(admAccountBalanceDto.getStatus().getTypologyId());
            if (status == null) {
                status = new AdmTypology(CsnConstants.STATUS_ACTIVE);
            }


            //TODO generate transaction number
            AdmAccountBalance newAccountBalance = new AdmAccountBalance(
                    0L,
                    account,
                    //fixme transaction number formula
                    CsnFunctions.now().toEpochSecond(ZoneOffset.UTC) + new Random().nextInt(1000),
                    admAccountBalanceDto.getAmount(),
                    transactionType,
                    createdByUser,
                    status,
                    CsnFunctions.now(),
                    admAccountBalanceDto.getAnnotation()
            );

            admAccountBalanceRepository.save(newAccountBalance);
            return CsnFunctions.setResponse(newAccountBalance.getAccountBalanceId(),
                    Response.Status.CREATED, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }


    @PUT
    @Path("{key:[A-Za-z0-9-][A-Za-z0-9-]*}/account/{id:[0-9][0-9]*}/movements/{balance_id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response edit(
            @PathParam("key") UUID associateKey,
            @PathParam("id") @DefaultValue("0") Long accountId,
            @PathParam("balance_id") @DefaultValue("0") Long balanceId,
            AdmAccountBalanceDto admAccountBalanceDto) {

        try {
            if (!admAccountBalanceDto.getAccount_balance_id().equals(balanceId)) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "ID not found");
            }

            AdmAccountBalance currentAccountBalance = admAccountBalanceRepository.findBy(admAccountBalanceDto.getAccount_balance_id());
            if (currentAccountBalance == null) {
                return CsnFunctions.setResponse(0L, Response.Status.NOT_FOUND, "Balance not found");
            }
            /*
            if (admAccountBalanceDto.getAccount_id() != null) {
                AdmAccount account = admAccountRepository.findBy(admAccountBalanceDto.getAccount_id());
                if (account != null) {
                    currentAccountBalance.setAccount(account);
                }
            }

            if (admAccountBalanceDto.getAmount() != null) {
                currentAccountBalance.setAmount(admAccountBalanceDto.getAmount());
            }

            if (admAccountBalanceDto.getTransactionType() != null) {
                AdmTypology transactionType = admTypologyRepository.findBy(admAccountBalanceDto.getTransactionType().getTypologyId());
                if (transactionType != null) {
                    currentAccountBalance.setTransactionType(transactionType);
                }
            }

             */

            if (admAccountBalanceDto.getStatus() != null) {
                AdmTypology status = admTypologyRepository.findBy(admAccountBalanceDto.getStatus().getTypologyId());
                if (status != null) {
                    currentAccountBalance.setStatus(status);
                }
            }

            admAccountBalanceRepository.save(currentAccountBalance);
            return CsnFunctions.setResponse(currentAccountBalance.getAccountBalanceId(),
                    Response.Status.OK, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }


    private Response.ResponseBuilder createReporteExcel(Field[] fields,
                                                        List<Object[]> resultObjects,
                                                        String fileName) throws Exception {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(CsnConstants.SHEET_NAME);
        Row header = sheet.createRow(0);

        //creating headers
        for (int i = 0; i < fields.length; i++) {
            sheet.setColumnWidth(i, 4000);
            header.createCell(i).setCellValue(fields[i].getName());
        }

        //row count
        int rowCount = 0;
        for (Object[] item : resultObjects) {
            Row row = sheet.createRow(++rowCount);
            int columnCount = 0;
            for (Object field : item) {
                row.createCell(columnCount++).setCellValue(String.valueOf(field));
            }
        }

        //Creating file
        File currDir = new File(Paths.get("").toAbsolutePath().toString() + CsnConstants.ROOT_DIRECTORY);
        String path = currDir.getAbsolutePath();
        String fileLocation = path + "/" + fileName + ".xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();

        File file = new File(Paths.get("").toAbsolutePath().toString() + CsnConstants.ROOT_DIRECTORY + "/" + fileName + ".xlsx");
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        return response;


    }


}
