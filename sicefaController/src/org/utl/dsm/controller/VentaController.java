package org.utl.dsm.controller;

import com.google.gson.Gson;
import java.util.ArrayList;
import org.utl.dsm.bd.MySQLConnection;
import org.utl.dsm401.model.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VentaController {
    MySQLConnection mySQLConnection = new MySQLConnection();
    
    public ArrayList<Venta> listarVentas(ResultSet rs) throws SQLException{
        ArrayList<Venta> ventas = new ArrayList<>();
        String json = "";
        while(rs.next()){
            Venta venta = new Venta(rs.getInt("idVenta"),rs.getString("nombre"),
                    rs.getInt("cantidad"),rs.getInt("precioVenta"),rs.getString("EM"),
                    rs.getString("CL"),rs.getString("fechaHora"),rs.getInt("estatus"),rs.getInt("total"));
            ventas.add(venta);
        }
        return ventas;
    }
    
    public ArrayList<Venta> getAll() throws SQLException{
        String query = "SELECT * FROM view_ventas;";
        Connection connection = mySQLConnection.open();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Venta> ventas = listarVentas(rs);
        return ventas;
    }
}
