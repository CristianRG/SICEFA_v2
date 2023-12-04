/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
//import org.utl.dsm401.sicefa.controller.ControllerEmpleado;
import org.utl.dsm.controller.ControllerSucursal;
//import org.utl.dsm401.model.Empleado;
//import org.utl.dsm401.model.Persona;
import org.utl.dsm401.model.Sucursal;
//import org.utl.dsm401.sicefa.model.Usuario;

/**
 *
 * @author 52479
 */
@Path("sucursal")
public class RESTSucursal {
    @Path("getAllSuc")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSuc(){
         
       String out="";
        try{
             ControllerSucursal objCE= new ControllerSucursal();
             List<Sucursal> listaSuc=objCE.getAllSucursal();
             Gson objGson=new Gson();
             out=objGson.toJson(listaSuc);
        }catch(SQLException ex){
            out="""
                 {"error":"No se pudo cargar la informacion, consulta el area del sistema" }
               
                """;
            
        }
        return Response.ok(out).build();
    }
    
    @Path("insertSuc")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertSuc(@FormParam("s") @DefaultValue("") String sucursal){
        Gson objGson = new Gson();
        //System.out.println("Impreso"+empleado);
        Sucursal s= objGson.fromJson(sucursal, Sucursal.class);
        String out="";
        
        ControllerSucursal objCS= new ControllerSucursal();
        try{
            int idSucursalGenerada =objCS.insertSuc(s);
            out="""
                 {"result":"Sucursal insertada exitosamente con id %s" }
               
                """;
            out= String.format(out, idSucursalGenerada);
        }catch(SQLException ex){
            System.out.println(s);
            ex.printStackTrace();
            out="""
                 {"error":"Error al insertar sucursal" }
               
                """;
        }
        return Response.ok(out).build();
    }
    
    @Path("deleteSuc")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSuc(@QueryParam("idS") @DefaultValue("0") String idS){
       String out="";
        try {
             ControllerSucursal objCS=new ControllerSucursal();
            objCS.deleteSuc(Integer.parseInt(idS));
            out="""
                {"result":"Sucursal eliminada exitosamente"}
                """;
        } catch (SQLException ex) {
            out="""
                {"error":"Hubo un error en la eliminacion"}
                """;
        }
        return Response.ok(out).build();
    }
    @Path("reactivarSuc")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response reactivarSuc(@QueryParam("idS") @DefaultValue("0") String idS){
       String out="";
        try {
             ControllerSucursal objCS=new ControllerSucursal();
            objCS.reactivarSuc(Integer.parseInt(idS));
            out="""
                {"result":"Sucursal reactivada exitosamente"}
                """;
        } catch (SQLException ex) {
            out="""
                {"error":"Hubo un error en la reativación"}
                """;
        }
        return Response.ok(out).build();
    }
    
    @Path("updateSuc")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSuc(@FormParam("s") @DefaultValue("") String sucursal){
        Gson objGson = new Gson();
        //System.out.println("Impreso"+empleado);
        Sucursal s= objGson.fromJson(sucursal, Sucursal.class);
        String out="";
        
        ControllerSucursal objCS= new ControllerSucursal();
        try{
            int idSucursalGenerada =objCS.updateSuc(s);
            out="""
                 {"result":"Sucursal modificada exitosamente con id %s" }
               
                """;
            out= String.format(out, idSucursalGenerada);
        }catch(SQLException ex){
            System.out.println(s);
            ex.printStackTrace();
            out="""
                 {"error":"Error al modificar sucursal" }
               
                """;
        }
        return Response.ok(out).build();
    }
}
