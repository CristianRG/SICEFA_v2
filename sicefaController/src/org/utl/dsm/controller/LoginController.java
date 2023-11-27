
package org.utl.dsm.controller;

import org.utl.dsm.bd.MySQLConnection;
import org.utl.dsm401.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    MySQLConnection mySQLConnection = new MySQLConnection();
    
    public Usuario login(String user, String password) throws SQLException{
        String query = "SELECT * FROM usuario where nombreUsuario = '%s' and contrasenia = '%s';";
        query = String.format(query, user, password);
        
        Connection connection = mySQLConnection.open();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        Usuario usuario = new Usuario();
        if(rs.next()){
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNombreUsuario(rs.getString("nombreUsuario"));
            usuario.setContrasenia(rs.getString("contrasenia"));
            usuario.setRol(rs.getString("rol"));
        }
        else{
            return null;
        }
        return usuario;  
    }
}
