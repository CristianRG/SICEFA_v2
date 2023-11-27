package org.utl.dsm401.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import org.utl.dsm.controller.LoginController;
import org.utl.dsm401.model.Usuario;

/**
 *
 * @author Pc
 */
@Path("login")
public class loginEndPoints {
    
    @Path("sesion")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("user") String user, @FormParam("password") String password){
        Gson gson = new Gson();
        LoginController loginController = new LoginController();
        Usuario usuario;
        String out;
        
        try {
            usuario = loginController.login(user, password);
            if(!(usuario == null)){out = gson.toJson(usuario);}
            else{
                out = """
                      {"result": "Error. Credenciales invalidas"}
                       """;
                }
        } catch (SQLException | NullPointerException e) {
            out = """
                  {"result": "Error. Credenciales invalidas"}
                  """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
