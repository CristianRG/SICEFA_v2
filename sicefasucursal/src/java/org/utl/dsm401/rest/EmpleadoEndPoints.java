package org.utl.dsm401.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import org.utl.dsm.controller.EmpleadoController;
import org.utl.dsm401.model.Empleado;

@Path("empleado")
public class EmpleadoEndPoints {
    
    @Path("getall")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmpleados(){
    
        EmpleadoController empleadoController = new EmpleadoController();
        List<Empleado> empleados = empleadoController.getAll();
        Gson gson = new Gson();
        String out = gson.toJson(empleados);
        
        return Response.ok(out).build();
    }
    
    @Path("getempleado")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmpleado(@QueryParam("id") String id, @QueryParam("user") String user) throws SQLException{
    
        EmpleadoController empleadoController = new EmpleadoController();
        Empleado empleado = empleadoController.getEmpleado(Integer.parseInt(id), Integer.parseInt(user));
        Gson gson = new Gson();
        String out = gson.toJson(empleado);
        
        return Response.ok(out).build();
    }
    
    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmpleado(@QueryParam("id") String id){
        
        EmpleadoController empleadoController = new EmpleadoController();
        Boolean result = empleadoController.delete(Integer.parseInt(id));
        String out = "";
        
        if(result){
            out = """
                    {"result": "success", "message": "Empleado eliminado exitosamente"}
                  """;
        }else{
            out = """
                    {"result": "Error al intentar eliminar..."}
                  """;
        }
        return Response.ok(out).build();
    }
    
    @Path("reactivar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response reactivarEmpleado(@QueryParam("id") String id){
        
        EmpleadoController empleadoController = new EmpleadoController();
        Boolean result = empleadoController.reactivar(Integer.parseInt(id));
        String out = "";
        
        if(result){
            out = """
                    {"result": "success", "message": "Empleado reactivado exitosamente"}
                  """;
        }else{
            out = """
                    {"result": "Error al intentar reactivar..."}
                  """;
        }
        return Response.ok(out).build();
    }
    
    @Path("insertar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertarEmpleado(@FormParam("e") @DefaultValue("") String empleado){
        
        Gson gson = new Gson();
        System.out.println(empleado);
        Empleado empleadoObject = gson.fromJson(empleado, Empleado.class);
        System.out.println(empleadoObject.toString());
        
        EmpleadoController empleadoController = new EmpleadoController();
        String out = "";
        
        try {
            int idEmpleado = empleadoController.insertar(empleadoObject);
            out = """
                    {"result": "success", "message": "Empleado insertado existosamente. ID: %s"}
                  """;
            System.out.println("All right");
            out = String.format(out, idEmpleado);
        } catch (SQLException e) {
            out = """
                    {"result": "Ha ocurrido un error al insertar un nuevo registro..."}
                  """;
            e.printStackTrace();
            System.out.println("Bad...");
        }
        return Response.ok(out).build();
    }
    
    @Path("modificar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarEmpleado(@FormParam("e") @DefaultValue("") String empleado){
        Gson gson = new Gson();
        Empleado empleadoObject = gson.fromJson(empleado, Empleado.class);
        EmpleadoController empleadoController = new EmpleadoController();
        String out = "";
        
        try {
            int idEmpleado = empleadoController.modificar(empleadoObject);
            out = """
                    {"result": "success","message": "Empleado modificado existosamente. ID: %s"}
                  """;
            out = String.format(out, idEmpleado);
        } catch (SQLException e) {
            out = """
                    {"result": "Ha ocurrido un error al modificar el registro..."}
                  """;
        }
        return Response.ok(out).build();
    }
    
    @Path("buscar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEmpleados(@QueryParam("nombre") String nombre){
    
        EmpleadoController empleadoController = new EmpleadoController();
        String out = "";
        try {
            List<Empleado> empleados = empleadoController.busqueda(nombre);
            Gson gson = new Gson();
            out = gson.toJson(empleados);
        } catch (SQLException e) {
            out = """
                  {"result":"No se ha encontrado"}
                  """;
        }
        return Response.ok(out).build();
    }
}
