package org.utl.dsm.controller;

import com.mysql.cj.jdbc.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Types;

import org.utl.dsm.bd.MySQLConnection;
import org.utl.dsm401.model.Empleado;
import org.utl.dsm401.model.Persona;
import org.utl.dsm401.model.Sucursal;
import org.utl.dsm401.model.Usuario;

public class EmpleadoController {
    MySQLConnection mySQLConnection = new MySQLConnection();

    public ArrayList<Empleado> listEmpleados(ResultSet rs) throws SQLException{
        ArrayList<Empleado> empleados = new ArrayList<>();
        while (rs.next()) {
            Usuario usuario = new Usuario(rs.getInt("idUsuario"),rs.getString("nombreUsuario"),rs.getString("contrasenia"),rs.getString("rol"));
            Persona persona = new Persona(rs.getInt("idPersona"),rs.getString("pNombre"),rs.getString("apellidoPaterno"),rs.getString("apellidoMaterno"),rs.getString("genero"),
                    rs.getString("fechaNacimiento"),rs.getString("pRFC"),rs.getString("curp"),rs.getString("domicilio"),rs.getString("pCodigoPostal"), rs.getString("pCiudad"),
                    rs.getString("pEstado"), rs.getString("pTelefono"),rs.getString("foto"));
            Sucursal sucursal = new Sucursal(rs.getInt("idSucursal"),rs.getString("nombre"),rs.getString("titular"),rs.getString("rfc"),rs.getString("domicilio"),rs.getString("codigoPostal"),
                    rs.getString("ciudad"), rs.getString("estado"),rs.getString("telefono"),rs.getString("latitud"),rs.getString("longitud"),rs.getInt("estatus"));
            Empleado empleado = new Empleado(rs.getInt("idEmpleado"),rs.getString("email"),rs.getString("codigo"),rs.getString("fechaIngreso"),rs.getString("puesto"),rs.getFloat("salarioBruto"),rs.getInt("activo"), persona, usuario, sucursal);
            empleados.add(empleado);
        }
        return  empleados;
    }
    
    public ArrayList<Empleado> getAll(){
        try {
            String quey = "SELECT * FROM view_empleados";
            Connection connection = mySQLConnection.open();
            PreparedStatement prepareStatement = connection.prepareStatement(quey);
            ResultSet resultSet = prepareStatement.executeQuery();
            ArrayList<Empleado> empleados = listEmpleados(resultSet);
            resultSet.close();
            prepareStatement.close();
            connection.close();
            return empleados;
        } catch (SQLException e) {
            //System.out.println("Ha ocurrido un error al intentar obtener los datos de la base de datos...");
        }
        return null;
        //return null;
    }
    
    public Empleado construirEmpleado(ResultSet rs) throws SQLException{
        Empleado empleado;
        rs.next();
        Usuario usuario = new Usuario(rs.getInt("idUsuario"),rs.getString("nombreUsuario"),rs.getString("contrasenia"),rs.getString("rol"));
        Persona persona = new Persona(rs.getInt("idPersona"),rs.getString("pNombre"),rs.getString("apellidoPaterno"),rs.getString("apellidoMaterno"),rs.getString("genero"),
             rs.getString("fechaNacimiento"),rs.getString("pRFC"),rs.getString("curp"),rs.getString("domicilio"),rs.getString("pCodigoPostal"), rs.getString("pCiudad"),
                    rs.getString("pEstado"), rs.getString("pTelefono"),rs.getString("foto"));
        Sucursal sucursal = new Sucursal(rs.getInt("idSucursal"),rs.getString("nombre"),rs.getString("titular"),rs.getString("rfc"),rs.getString("domicilio"),rs.getString("codigoPostal"),
                    rs.getString("ciudad"), rs.getString("estado"),rs.getString("telefono"),rs.getString("latitud"),rs.getString("longitud"),rs.getInt("estatus"));
        empleado = new Empleado(rs.getInt("idEmpleado"),rs.getString("email"),rs.getString("codigo"),rs.getString("fechaIngreso"),rs.getString("puesto"),rs.getFloat("salarioBruto"),rs.getInt("activo"), persona, usuario, sucursal);
        
        return empleado;
    }
    
    public Empleado getEmpleado(int id, int idUsuario) throws SQLException{
        Empleado empleado;
        String query = "SELECT e.idEmpleado,e.email,e.codigo,e.fechaIngreso,e.puesto,e.salarioBruto,e.activo,p.idPersona,p.nombre pNombre,p.apellidoPaterno,p.apellidoMaterno,p.genero,p.fechaNacimiento,p.rfc pRFC,p.curp,p.domicilio pDomicilio,p.codigoPostal pCodigoPostal,p.ciudad pCiudad,p.estado pEstado,p.telefono pTelefono,p.foto,u.idUsuario,u.nombreUsuario,u.contrasenia,u.rol,s.idSucursal,s.nombre,s.titular,s.rfc,s.domicilio,s.colonia,s.codigoPostal,s.ciudad,s.estado,s.telefono,s.latitud,s.longitud,s.estatus FROM empleado AS e INNER JOIN persona AS p ON e.idPersona = p.idPersona INNER JOIN usuario AS u ON e.idUsuario = u.idUsuario INNER JOIN sucursal AS s ON e.idSucursal = s.idSucursal " + 
                " WHERE e.idEmpleado = " + id + " or e.idUsuario = " + idUsuario + ";";
        Connection connection = mySQLConnection.open();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        empleado = construirEmpleado(resultSet);
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return empleado;
    }
    
    public ArrayList<Empleado> busqueda(String campo) throws SQLException{
        String query = "SELECT " +
"        e.idEmpleado," +
"        e.email," +
"        e.codigo," +
"        e.fechaIngreso," +
"        e.puesto," +
"        e.salarioBruto," +
"        e.activo," +
"        p.idPersona," +
"        p.nombre pNombre," +
"        p.apellidoPaterno," +
"        p.apellidoMaterno," +
"        p.genero," +
"        p.fechaNacimiento," +
"        p.rfc pRFC," +
"        p.curp," +
"        p.domicilio pDomicilio," +
"        p.codigoPostal pCodigoPostal," +
"        p.ciudad pCiudad," +
"        p.estado pEstado," +
"        p.telefono pTelefono," +
"        p.foto," +
"        u.idUsuario," +
"        u.nombreUsuario," +
"        u.contrasenia," +
"        u.rol," +
"        s.idSucursal," +
"        s.nombre," +
"        s.titular," +
"        s.rfc," +
"        s.domicilio," +
"        s.colonia," +
"        s.codigoPostal," +
"        s.ciudad," +
"        s.estado," +
"        s.telefono," +
"        s.latitud," +
"        s.longitud," +
"        s.estatus" +
"    FROM" +
"        empleado AS e" +
"            INNER JOIN" +
"        persona AS p ON e.idPersona = p.idPersona" +
"            INNER JOIN" +
"        usuario AS u ON e.idUsuario = u.idUsuario" +
"            INNER JOIN" +
"        sucursal AS s ON e.idSucursal = s.idSucursal" +
"	WHERE" +
"		p.nombre like '" + campo + "%';";
        Connection connection = mySQLConnection.open();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Empleado> empleados = listEmpleados(rs);
        return empleados;
    }
    
    public boolean delete(int id){
        try {
            String query = "UPDATE empleado set activo = 0 where idEmpleado = " + id + ";";
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
    
    public void insertarDatosEmpleado(CallableStatement cstm, Empleado e) throws SQLException{
        cstm.setString(1, e.getPersona().getNombre());
        cstm.setString(2, e.getPersona().getApellidoPaterno());
        cstm.setString(3, e.getPersona().getApellidoMaterno());
        cstm.setString(4, e.getPersona().getGenero());
        cstm.setString(5, e.getPersona().getFechaNacimiento());
        cstm.setString(6, e.getPersona().getRfc());
        cstm.setString(7, e.getPersona().getCurp());
        cstm.setString(8, e.getPersona().getDomicilio());
        cstm.setString(9, e.getPersona().getCodigoPostal());
        cstm.setString(10, e.getPersona().getCiudad());
        cstm.setString(11, e.getPersona().getEstado());
        cstm.setString(12, e.getPersona().getTelefono());
        cstm.setString(13, e.getPersona().getFoto());

        cstm.setInt(14, e.getSucursal().getIdSucursal());
        cstm.setString(15, e.getUsuario().getRol());
        cstm.setString(16, e.getEmail());
        cstm.setString(17, e.getPuesto());
        cstm.setFloat(18, e.getSalarioBruto());
        
        cstm.registerOutParameter(19, Types.INTEGER);
        cstm.registerOutParameter(20, Types.INTEGER);
        cstm.registerOutParameter(21, Types.INTEGER);
        cstm.registerOutParameter(22, Types.VARCHAR);
    }
    
    public int insertar(Empleado e) throws SQLException{
        String query = "{call insertarEmpleado(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        Connection connection = mySQLConnection.open();
        CallableStatement cstm = (CallableStatement) connection.prepareCall(query);
        insertarDatosEmpleado(cstm, e);
        cstm.execute();
        e.getPersona().setIdPersona(cstm.getInt(19));
        e.getUsuario().setIdUsuario(cstm.getInt(20));
        e.setIdEmpleado(cstm.getInt(21));
        e.setCodigo(cstm.getString(22));
            
        cstm.close();
        connection.close();
        
        return e.getIdEmpleado();
    }
    
    public int modificar(Empleado e) throws SQLException{
        ArrayList<String> querys = new ArrayList<>();
        
        
        System.out.println(e.toString());
        String query = """
                       UPDATE empleado set email = '%s', fechaIngreso = '%s', puesto = '%s',
                       salarioBruto = %s, activo = 1, idPersona = %s, idUsuario = %s, idSucursal = %s where idEmpleado = %s;
                       """;
        query = String.format(query, e.getEmail(), e.getFechaIngreso(), e.getPuesto(), e.getSalarioBruto(),
                e.getPersona().getIdPersona(), e.getUsuario().getIdUsuario(), e.getSucursal().getIdSucursal(), e.getIdEmpleado());
        querys.add(query);
        query = "UPDATE persona "
        + "SET "
        + "nombre = '%s', "
        + "apellidoPaterno = '%s', "
        + "apellidoMaterno = '%s', "
        + "genero = '%s', "
        + "fechaNacimiento = '%s', "
        + "rfc = '%s', "
        + "curp = '%s', "
        + "domicilio = '%s', "
        + "codigoPostal = '%s', "
        + "ciudad = '%s', "
        + "estado = '%s', "
        + "telefono = '%s', "
        + "foto = '%s' "
        + "WHERE idPersona = %s";
        query = String.format(query, e.getPersona().getNombre(), e.getPersona().getApellidoPaterno(), e.getPersona().getApellidoMaterno(),
                e.getPersona().getGenero(), e.getPersona().getFechaNacimiento(), e.getPersona().getRfc(), e.getPersona().getCurp(), e.getPersona().getDomicilio(),
                e.getPersona().getCodigoPostal(),e.getPersona().getCiudad(),e.getPersona().getEstado(),e.getPersona().getTelefono(),e.getPersona().getFoto(),e.getPersona().getIdPersona());
        querys.add(query);
        
        query = "UPDATE usuario "
            + "SET "
            + "nombreUsuario = '%s', "
            + "contrasenia = '%s', "
            + "rol = '%s' "
            + "WHERE idUsuario = %s";
        query = String.format(query, e.getUsuario().getNombreUsuario(),e.getUsuario().getContrasenia(),e.getUsuario().getRol(),e.getUsuario().getIdUsuario());
        querys.add(query);
        
        Connection connection = mySQLConnection.open();
        
        for (String q : querys) {
            try {
                executeQuery(connection, q);
            } catch (SQLException ex) {
                //Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                return 0;
            }
        }
        
        connection.close();
        return e.getIdEmpleado();
    }
    
    public void executeQuery(Connection connection, String query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            statement.close();
        }
    }
    
    public boolean reactivar(int id){
        try {
            String query = "UPDATE empleado set activo = 1 where idEmpleado = " + id + ";";
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
