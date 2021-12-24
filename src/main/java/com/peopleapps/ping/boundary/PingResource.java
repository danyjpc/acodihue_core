package com.peopleapps.ping.boundary;


import com.peopleapps.repository.AdmGuaranteeRepository;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("test")
public class PingResource {

    @GET
    public String test() {
        return "Welcome to Jakarta EE 8 + Microprofile 3.1 ";
    }

}