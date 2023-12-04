package org.utl.dsm.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.bd.MySQLConnection;
import org.utl.dsm401.model.Empleado;
import org.utl.dsm401.model.Persona;
import org.utl.dsm401.model.Sucursal;
import org.utl.dsm401.model.Usuario;

/**
 *
 * @author 52479
 */
public class ControllerSucursal {
    public List<Sucursal> getAllSucursal() throws SQLException{
        List<Sucursal> listaSucursales = new ArrayList<>();
        //1. Crear la sentencia SQL
        String query = "SELECT * FROM sucursal";
        //2. Se establece la conexion con la BD
        MySQLConnection connMySQL = new MySQLConnection();
        //3. Se abre la conexion
        Connection conn = connMySQL.open();
        //4. Se genera el statement para enviar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        //5. Se prepara un ResultSet para obtener la respuesta de la BD
        ResultSet rs = pstmt.executeQuery();
        //6. Recorrer el rs y extraer los datos
        while (rs.next())
        {
            Sucursal s = new Sucursal();
            s.setIdSucursal(rs.getInt("idSucursal"));
            s.setNombre(rs.getString("nombre"));
            s.setTitular(rs.getString("titular"));
            s.setCiudad(rs.getString("ciudad"));
            s.setCodigoPostal(rs.getString("codigoPostal"));
            s.setColonia(rs.getString("colonia"));
            s.setDomicilio(rs.getString("domicilio"));
            s.setEstado(rs.getString("estado"));
            s.setEstatus(rs.getInt("estatus"));
            s.setLatitud(rs.getString("latitud"));
            s.setLongitud(rs.getString("longitud"));
            s.setRfc(rs.getString("rfc"));
            s.setTelefono(rs.getString("telefono"));    
            
            listaSucursales.add(s);
        }
        //7. Cerrar todos los objetos
        rs.close();
        pstmt.close();
        conn.close();
        connMySQL.close();
        //8. Devolver la informacion
        return listaSucursales;
    }
    public int insertSuc(Sucursal s) throws SQLException {
        Empleado e = new Empleado();
        Usuario u = new Usuario();
        Persona p = new Persona();
        //1.- Generar la sentencia SQL
        String query = "{call insertarSucursal(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        //2.- Crear la conexion a la BD        1 2 3 4 5 6 7 8 9 101112131415161718
        MySQLConnection conMySQL = new MySQLConnection();
        //3.- Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement que llevara la consulta   prepareStatement, Statement y calleblestatement
        CallableStatement cstm = conn.prepareCall(query);
        //5.- Llenar todos los parametros de la llamada al procedure
        cstm.setString(1, s.getNombre());
        cstm.setString(2, s.getTitular());
        cstm.setString(3, s.getRfc());
        cstm.setString(4, s.getDomicilio());
        cstm.setString(5, s.getColonia()); 
        cstm.setString(6, s.getCodigoPostal());
        cstm.setString(7, s.getCiudad());
        cstm.setString(8, s.getEstado());
        cstm.setString(9, s.getTelefono());
        cstm.setString(10, s.getLatitud());
        cstm.setString(11, s.getLongitud());
        
        cstm.registerOutParameter(12, Types.INTEGER);
        cstm.registerOutParameter(13, Types.INTEGER);
        cstm.registerOutParameter(14, Types.INTEGER);
        cstm.registerOutParameter(15, Types.INTEGER);
        cstm.registerOutParameter(16, Types.VARCHAR);
        cstm.registerOutParameter(17, Types.VARCHAR);
        cstm.registerOutParameter(18, Types.VARCHAR);
        
        cstm.execute();
        
        s.setIdSucursal(cstm.getInt(12));
        p.setIdPersona(cstm.getInt(13));
        u.setIdUsuario(cstm.getInt(14));
        e.setIdEmpleado(cstm.getInt(15));
        e.setCodigo(cstm.getString(16));
        u.setNombreUsuario(cstm.getString(17));
        u.setContrasenia(cstm.getString(18));
        
        //8.- Cerrar los objetos
        cstm.close();
        conn.close();
        conMySQL.close();
        return s.getIdSucursal();
    }
    public void deleteSuc(int idSucursal) throws SQLException {
        //1.- Crear la sentencia SQL
        String query = "UPDATE sucursal SET estatus=0 WHERE idSucursal=" + idSucursal + ";";
        //2.- Crear un objeto para la conexion con mySql
        MySQLConnection conMySQL = new MySQLConnection();
        //3. Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement para enviar la query
        Statement stmt = conn.createStatement();
        //5.- Ejecutar la sentencia
        stmt.execute(query);
        //6.- Cerrar los objetos
        stmt.close();
        conn.close();
        conMySQL.close();
    }
    
    public void reactivarSuc(int idSucursal) throws SQLException {
        //1.- Crear la sentencia SQL
        String query = "UPDATE sucursal SET estatus=1 WHERE idSucursal=" + idSucursal + ";";
        //2.- Crear un objeto para la conexion con mySql
        MySQLConnection conMySQL = new MySQLConnection();
        //3. Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement para enviar la query
        Statement stmt = conn.createStatement();
        //5.- Ejecutar la sentencia
        stmt.execute(query);
        //6.- Cerrar los objetos
        stmt.close();
        conn.close();
        conMySQL.close();
    }
    
    public int updateSuc(Sucursal s) throws SQLException {
        
        //1.- Generar la sentencia SQL
        String query = "{call modificarSucursal(?,?,?,?,?,?,?,?,?,?,?,?)}";
        //2.- Crear la conexion a la BD        1 2 3 4 5 6 7 8 9 101112
        MySQLConnection conMySQL = new MySQLConnection();
        //3.- Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement que llevara la consulta   prepareStatement, Statement y calleblestatement
        CallableStatement cstm = conn.prepareCall(query);
        //5.- Llenar todos los parametros de la llamada al procedure
        cstm.setInt(1, s.getIdSucursal());
        cstm.setString(2, s.getNombre());
        cstm.setString(3, s.getTitular());
        cstm.setString(4, s.getRfc());
        cstm.setString(5, s.getDomicilio());
        cstm.setString(6, s.getColonia()); 
        cstm.setString(7, s.getCodigoPostal());
        cstm.setString(8, s.getCiudad());
        cstm.setString(9, s.getEstado());
        cstm.setString(10, s.getTelefono());
        cstm.setString(11, s.getLatitud());
        cstm.setString(12, s.getLongitud());
        
        
        cstm.execute();
        //8.- Cerrar los objetos
        cstm.close();
        conn.close();
        conMySQL.close();
        return s.getIdSucursal();
    }
}
