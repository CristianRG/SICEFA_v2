package org.utl.dsm401.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import org.utl.dsm.controller.VentaController;
import org.utl.dsm401.model.Venta;

/**
 *
 * @author Pc
 */
@Path("venta")
public class VentaEndPoints {
    
    @Path("getall")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        ArrayList<Venta> ventas;
        Gson gson = new Gson();
        VentaController ventaController = new VentaController();
        String out = "";
        try {
            ventas = ventaController.getAll();
            out = gson.toJson(ventas);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"result": "Error. No se ha podido obtener la informacion"}
                  """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
