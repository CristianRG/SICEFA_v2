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
import org.utl.dsm.controller.ClienteController;

import org.utl.dsm401.model.Cliente;

/**
 *
 * @author johan
 */
@Path("cliente")
public class ClienteEndPoints {

    @Path("getall")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientes() {

        ClienteController clienteController = new ClienteController();
        List<Cliente> clientes = clienteController.getAll();
        Gson gson = new Gson();
        String out = gson.toJson(clientes);

        return Response.ok(out).build();
    }

    @Path("getCliente")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCliente(@QueryParam("id") String id) throws SQLException {

        ClienteController clienteController = new ClienteController();
        Cliente cliente = clienteController.getCliente(Integer.parseInt(id));
        Gson gson = new Gson();
        String out = gson.toJson(cliente);

        return Response.ok(out).build();
    }

    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCliente(@QueryParam("id") String id) {

        ClienteController clienteController = new ClienteController();
        Boolean result = clienteController.delete(Integer.parseInt(id));
        String out = "";

        if (result) {
            out = """
                    {"result": "success", "message": "Cliente eliminado exitosamente"}
                  """;
        } else {
            out = """
                    {"result": "Error al intentar eliminar..."}
                  """;
        }
        return Response.ok(out).build();
    }

    @Path("reactivar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response reactivarCliente(@QueryParam("id") String id) {

        ClienteController clienteController = new ClienteController();
        Boolean result = clienteController.reactivar(Integer.parseInt(id));
        String out = "";

        if (result) {
            out = """
                    {"result": "success", "message": "Cliente reactivado exitosamente"}
                  """;
        } else {
            out = """
                    {"result": "Error al intentar reactivar..."}
                  """;
        }
        return Response.ok(out).build();
    }

    @Path("insertar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertarCliente(@FormParam("c") @DefaultValue("") String cliente) {

        Gson gson = new Gson();
        System.out.println(cliente);
        Cliente clienteObject = gson.fromJson(cliente, Cliente.class);
        System.out.println(clienteObject.toString());

        ClienteController clienteController = new ClienteController();
        String out = "";

        try {
            int idCliente = clienteController.insertar(clienteObject);
            out = """
                    {"result": "success", "message": "Cliente insertado existosamente. ID: %s"}
                  """;
            System.out.println("All right");
            out = String.format(out, idCliente);
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
    public Response modificarCLiente(@FormParam("c") @DefaultValue("") String cliente) {
        Gson gson = new Gson();
        Cliente clienteObject = gson.fromJson(cliente, Cliente.class);
        System.out.println(clienteObject.toString());
        ClienteController clienteController = new ClienteController();
        String out = "";

        try {
            int idCliente = clienteController.modificar(clienteObject);
            out = """
                    {"result": "success","message": "Cliente modificado existosamente. ID: %s"}
                  """;
            out = String.format(out, idCliente);
        } catch (SQLException e) {
            out = """
                    {"result": "Ha ocurrido un error al modificar el registro..."}
                  """;
        }
        return Response.ok(out).build();
    }

    @GET
    @Path("buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBusqueda(@QueryParam("parametro") @DefaultValue("") String busqueda) throws SQLException {
        ClienteController clienteController = new ClienteController();

        List<Cliente> clientes = clienteController.buscar(busqueda);
        Gson objGS = new Gson();
        objGS.toJson(clientes);
        String out = objGS.toJson(clientes);
        return Response.ok(out).build();
    }
}
