/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.utl.dsm.bd.MySQLConnection;
import org.utl.dsm401.model.Compra;
import org.utl.dsm401.model.Empleado;
import org.utl.dsm401.model.Producto;
import org.utl.dsm401.model.Sucursal;
import org.utl.dsm401.model.Venta;

/**
 *
 * @author Pc
 */
public class PedidoController {
    MySQLConnection mySQLConnection = new MySQLConnection();
    
    public ArrayList<Compra> listarCompras(ResultSet rs) throws SQLException{
        ArrayList<Compra> compras = new ArrayList<>();
        while(rs.next()){
            Compra compra = new Compra(rs.getInt("idCompra"), rs.getString("fechaHoraPedido"), rs.getInt("idEmpleado"), 
                    rs.getInt("idSucursal"), rs.getString("sucursal"), rs.getInt("estatus"), rs.getInt("idProducto"), 
                    rs.getString("producto"), rs.getInt("cantidad"), rs.getDouble("precioCompra"), rs.getDouble("total"));
            compras.add(compra);
        }
        return compras;
    }
    
    public ArrayList<Compra> getAll() throws SQLException{
        String query = "SELECT * FROM view_pedidos;";
        Connection connection = mySQLConnection.open();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Compra> compras = listarCompras(rs);
        return compras;
    }
    
    public boolean delete(int id){
        try {
            String query = "UPDATE compra set estatus = 0 where idCompra = " + id + ";";
            Connection connection = mySQLConnection.open();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            //System.out.println("Error al eliminar...");
        }
        return false;
    }
    
        public boolean reactivar(int id){
        try {
            String query = "UPDATE compra set estatus = 2 where idCompra = " + id + ";";
            Connection connection = mySQLConnection.open();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            
        }
        return false;
    }
}
