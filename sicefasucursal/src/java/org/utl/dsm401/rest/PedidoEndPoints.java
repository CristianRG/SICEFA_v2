package org.utl.dsm401.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import org.utl.dsm.controller.EmpleadoController;
import org.utl.dsm.controller.PedidoController;
import org.utl.dsm401.model.Compra;

/**
 *
 * @author Pc
 */
@Path("compra")
public class PedidoEndPoints {
    
    @Path("getall")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        ArrayList<Compra> compras;
        Gson gson = new Gson();
        PedidoController pedidoController = new PedidoController();
        String out = "";
        try {
            compras = pedidoController.getAll();
            out = gson.toJson(compras);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"result": "Error. No se ha podido obtener la informacion"}
                  """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @Path("cancelar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmpleado(@QueryParam("id") String id){
        
        PedidoController pedidoController = new PedidoController();
        Boolean result = pedidoController.delete(Integer.parseInt(id));
        String out = "";
        
        if(result){
            out = """
                    {"result": "success", "message": "Compra cancelada exitosamente"}
                  """;
        }else{
            out = """
                    {"result": "Error al intentar cancelar..."}
                  """;
        }
        return Response.ok(out).build();
    }
    
    @Path("atender")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response reactivarEmpleado(@QueryParam("id") String id){
        
        PedidoController pedidoController = new PedidoController();
        Boolean result = pedidoController.reactivar(Integer.parseInt(id));
        String out = "";
        
        if(result){
            out = """
                    {"result": "success", "message": "Compra atendida exitosamente"}
                  """;
        }else{
            out = """
                    {"result": "Error al intentar atender..."}
                  """;
        }
        return Response.ok(out).build();
    }
}
