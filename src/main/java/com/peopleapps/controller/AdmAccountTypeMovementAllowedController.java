package com.peopleapps.controller;


import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.dto.account.AdmAccountDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceDto;
import com.peopleapps.dto.accountBalance.AdmAccountBalanceInfoDto;
import com.peopleapps.dto.accountTypeMovementAllowed.AdmAccountTypeMovementDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Path("/products_accounts/v1")
@Tag(name = "accountAllowedMovements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdmAccountTypeMovementAllowedController {

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmTypologyRepository admTypologyRepository;

    @Inject
    AdmAccountTypeMovementRepository admAccountTypeMovementRepository;

    @Inject
    Logger logger;

    @Inject
    ResponseJson responseJson;

    @GET
    @Path("{account_type_id:[0-9][0-9]*}/transaction/type/{transaction_type_id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response getAllAccountTypeAllowedMovements(
            @PathParam("account_type_id") @DefaultValue("0") Long accountTypeId,
            @PathParam("transaction_type_id") @DefaultValue("0") Long transactionTypeId,
            @QueryParam("status") @DefaultValue("0") Long statusId,
            @QueryParam("descending") @DefaultValue("true") Boolean desc
    ) {
        try {
            List<AdmTypologyDto> admAccountTypeMovementDtos = admAccountTypeMovementRepository.getAllAccountTypeMovements(
                    accountTypeId,
                    transactionTypeId,
                    statusId,
                    desc
            );

            return Response.status(Response.Status.OK).entity(admAccountTypeMovementDtos).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            responseJson.setCode("FAIL");
            responseJson.setCode("Error en la consulta");
        }

        return Response.status(Response.Status.OK).entity(responseJson).build();
    }

    @POST
    @Path("{account_type_id:[0-9][0-9]*}/transaction/type/{transaction_type_id:[0-9][0-9]*}")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response create(
            @PathParam("account_type_id") @DefaultValue("0") Long accountTypeId,
            @PathParam("transaction_type_id") @DefaultValue("0") Long transactionTypeId,
            List<AdmTypologyDto> admMovementsDto) {
        try {
            AdmTypology accountType = admTypologyRepository.findBy(accountTypeId);
            if(accountType == null){
                return CsnFunctions.setResponse(0L,
                        Response.Status.BAD_REQUEST, "Incorrect account type");
            }
            AdmTypology transactionType = admTypologyRepository.findBy(transactionTypeId);
            if(transactionType == null){
                return CsnFunctions.setResponse(0L,
                        Response.Status.BAD_REQUEST, "Incorrect transaction type");
            }
            admAccountTypeMovementRepository.persistMovements(accountType, transactionType, admMovementsDto);
            return CsnFunctions.setResponse(0L,
                    Response.Status.CREATED, "Created");
        } catch (Exception ex) {
            ex.printStackTrace();
            return CsnFunctions.setResponse(0L,
                    Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

}
