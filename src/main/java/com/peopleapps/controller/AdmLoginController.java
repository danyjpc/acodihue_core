package com.peopleapps.controller;

import com.peopleapps.controller.util.ParamsPaginated;
import com.peopleapps.controller.util.ResponseJson;
import com.peopleapps.dto.auth.AdmCredentials;
import com.peopleapps.dto.auth.AdmPasswordRecoveryDto;
import com.peopleapps.dto.auth.AdmTokenDto;
import com.peopleapps.model.AdmPerson;
import com.peopleapps.model.AdmUser;
import com.peopleapps.repository.AdmPersonRepository;
import com.peopleapps.repository.AdmPhraseRepository;
import com.peopleapps.repository.AdmUserRepository;
import com.peopleapps.security.MicroProfileService;
import com.peopleapps.security.RolesEnum;
import com.peopleapps.util.CsnFunctions;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Singleton
@Path("/login")
@Tag(name = "login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdmLoginController {

    @Inject
    AdmUserRepository admUserRepository;

    @Inject
    AdmPersonRepository admPersonRepository;

    @Inject
    MicroProfileService microProfileService;

    private PrivateKey key;

    @Inject
    Logger logger;

    @PostConstruct
    public void init() {
        try {
            key = microProfileService.readPrivateKey();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @POST
    public Response validate(@Valid AdmCredentials admCredentials) {
        List<String> target = new ArrayList<>();
        try {

            List<AdmUser> admUserList = admUserRepository.getAllUsers(null, admCredentials.getPwd(), admCredentials.getEmail(), null);

            if (admUserList.size() > 0) {

                target.add(admUserList.get(0).getRole().getDescription());

                AdmTokenDto tokenDto = microProfileService.generateJWT(key,
                        admCredentials.getEmail(),
                        target,
                        admUserList.get(0).getPerson().getPersonKey().toString(),
                        admUserList.get(0).getPerson().getEmail(),
                        admUserList.get(0).getRole().getTypologyId(),
                        admUserList.get(0).getRole().getDescription());

                tokenDto.setPersonKey(admUserList.get(0).getPerson().getPersonKey().toString());



                logger.error("ok");
                return Response.status(Response.Status.OK)
                        .header(AUTHORIZATION, "Bearer ".concat(tokenDto.getToken()))
                        .entity(tokenDto)
                        .build();

            } else {
                logger.error("error");
                return Response.status(Response.Status.BAD_REQUEST).build();
            }


        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/recover/")
    @RolesAllowed({RolesEnum.Constants.IT_VALUE, RolesEnum.Constants.OPERARIO_VALUE, RolesEnum.Constants.ADMINISTRADOR_VALUE})
    public Response findPersonByEmail(
            AdmPasswordRecoveryDto admPasswordRecoveryDto
    ){
        AdmUser user = this.admUserRepository.findByEmail(admPasswordRecoveryDto.getEmail());
        AdmPerson person = this.admPersonRepository.findByEmail(admPasswordRecoveryDto.getEmail());

        if(person == null){
            CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "Person not found");
        }
        if(user == null){
            return CsnFunctions.setResponse(0L, Response.Status.BAD_REQUEST, "User not found");
        }
        return CsnFunctions.setResponse(user.getPerson().getPersonKey(), Response.Status.OK, "User found");
    }

}
