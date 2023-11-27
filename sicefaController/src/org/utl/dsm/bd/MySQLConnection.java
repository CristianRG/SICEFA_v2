package org.utl.dsm.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    String user = "root";
    String password = "";
    String url = "jdbc:mysql://127.0.0.1:3306/sicefa";
    Connection connection;

    public Connection open(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            return connection;
            }
        catch (ClassNotFoundException | SQLException e){
                System.out.println("Error al intentar conectar con la base de datos...");
                e.printStackTrace();
            }
        return null;
    }

    public void close(){
        if (connection != null){
            try{
                connection.close();
            }
            catch (SQLException e){
                System.out.println("No se pudo cerrar la conexion a la base de datos...");
            }
        }
    }
}
