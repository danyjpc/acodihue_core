package com.peopleapps;

import com.peopleapps.security.RolesEnum;
import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures a JAX-RS endpoint. Delete this class, if you are not exposing
 * JAX-RS resources in your application.
 *
 * @author airhacks.com
 */
@ApplicationScoped
@ApplicationPath("rest")

@OpenAPIDefinition(
        info = @Info(
                title = "SISTEMA DE COOPERATIVA DE CREDITOS",
                version = "0.01",
                license = @License(
                        name = "Apache 2.0 license ",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"

                ),
                contact = @Contact(
                        name = "PeopleApps",
                        email = "info@mypeopleapps.com",
                        url = "www.peopleapps.dev"
                )
        ),
        servers = {
                @Server(url = "/acodihue-core", description = "localhost")
        }
)
@LoginConfig(authMethod = "MP-JWT")
@DeclareRoles({RolesEnum.Constants.COORDINADOR_VALUE, RolesEnum.Constants.OPERARIO_VALUE})
public class JAXRSConfiguration extends Application {

}
