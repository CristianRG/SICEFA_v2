package org.utl.dsm.controller;

import com.mysql.cj.jdbc.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.bd.MySQLConnection;
import org.utl.dsm401.model.Cliente;

import org.utl.dsm401.model.Persona;

public class ClienteController {

    MySQLConnection mySQLConnection = new MySQLConnection();

    public ArrayList<Cliente> listClientes(ResultSet rs) throws SQLException {
        ArrayList<Cliente> clientes = new ArrayList<>();
        while (rs.next()) {
            Persona persona = new Persona(rs.getInt("idPersona"), rs.getString("Nombre"), rs.getString("apellidoPaterno"), rs.getString("apellidoMaterno"), rs.getString("genero"),
                    rs.getString("fechaNacimiento"), rs.getString("rfc"), rs.getString("curp"), rs.getString("domicilio"), rs.getString("codigoPostal"), rs.getString("ciudad"),
                    rs.getString("estado"), rs.getString("telefono"), rs.getString("foto"));
            Cliente cliente = new Cliente(rs.getInt("idCliente"), rs.getString("email"), rs.getString("fechaRegistro"), rs.getInt("estatus"), persona);
            clientes.add(cliente);
        }
        return clientes;
    }

    public ArrayList<Cliente> getAll() {
        try {
            String quey = "SELECT * FROM view_clientes";
            Connection connection = mySQLConnection.open();
            PreparedStatement prepareStatement = connection.prepareStatement(quey);
            ResultSet resultSet = prepareStatement.executeQuery();
            ArrayList<Cliente> clientes = listClientes(resultSet);
            resultSet.close();
            prepareStatement.close();
            connection.close();
            return clientes;
        } catch (SQLException e) {
            //System.out.println("Ha ocurrido un error al intentar obtener los datos de la base de datos...");
        }
        return null;
        //return null;
    }

    public Cliente construirCliente(ResultSet rs) throws SQLException {
        Cliente cliente;
        rs.next();
        Persona persona = new Persona(rs.getInt("idPersona"), rs.getString("Nombre"), rs.getString("apellidoPaterno"), rs.getString("apellidoMaterno"), rs.getString("genero"),
                rs.getString("fechaNacimiento"), rs.getString("rfc"), rs.getString("curp"), rs.getString("domicilio"), rs.getString("codigoPostal"), rs.getString("ciudad"),
                rs.getString("estado"), rs.getString("telefono"), rs.getString("foto"));
        cliente = new Cliente(rs.getInt("idCliente"), rs.getString("email"), rs.getString("fechaRegistro"), rs.getInt("estatus"), persona);

        return cliente;
    }

    public Cliente getCliente(int id) throws SQLException {
        Cliente cliente;
        String query = "SELECT * FROM view_clientes WHERE idCliente=" + id + ";";
        Connection connection = mySQLConnection.open();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        cliente = construirCliente(resultSet);
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return cliente;
    }

    public ArrayList<Cliente> buscar(String parametro) throws SQLException {

        ArrayList<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM view_clientes WHERE "
                + "idCliente LIKE ? OR "
                + "email LIKE ? OR "
                + "fechaRegistro LIKE ? OR "
                + "estatus LIKE ? OR "
                + "idPersona LIKE ? OR "
                + "nombre LIKE ? OR "
                + "apellidoPaterno LIKE ? OR "
                + "apellidoMaterno LIKE ? OR "
                + "genero LIKE ? OR "
                + "fechaNacimiento LIKE ? OR "
                + "rfc LIKE ? OR "
                + "curp LIKE ? OR "
                + "domicilio LIKE ? OR "
                + "codigoPostal LIKE ? OR "
                + "ciudad LIKE ? OR "
                + "estado LIKE ? OR "
                + "telefono LIKE ?;";

        Connection connection = mySQLConnection.open();
        PreparedStatement pstmt = connection.prepareStatement(query);
        for (int i = 1; i <= 17; i++) {
            pstmt.setString(i, "%" + parametro + "%");
        }
        ResultSet rs = pstmt.executeQuery();
        clientes = listClientes(rs);
        return clientes;
    }

    public boolean delete(int id) {
        try {
            String query = "UPDATE cliente SET estatus = 0 WHERE idCliente = " + id + ";";
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

    public boolean reactivar(int id) {
        try {
            String query = "UPDATE cliente set estatus = 1 where idCliente = " + id + ";";
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

    public void insertarDatosCliente(CallableStatement cstm, Cliente c) throws SQLException {
        cstm.setString(1, c.getPersona().getNombre());
        cstm.setString(2, c.getPersona().getApellidoPaterno());
        cstm.setString(3, c.getPersona().getApellidoMaterno());
        cstm.setString(4, c.getPersona().getGenero());
        cstm.setString(5, c.getPersona().getFechaNacimiento());
        cstm.setString(6, c.getPersona().getRfc());
        cstm.setString(7, c.getPersona().getCurp());
        cstm.setString(8, c.getPersona().getDomicilio());
        cstm.setString(9, c.getPersona().getCodigoPostal());
        cstm.setString(10, c.getPersona().getCiudad());
        cstm.setString(11, c.getPersona().getEstado());
        cstm.setString(12, c.getPersona().getTelefono());
        cstm.setString(13, c.getPersona().getFoto());

        cstm.setString(14, c.getEmail());

        cstm.registerOutParameter(15, Types.INTEGER);
        cstm.registerOutParameter(16, Types.INTEGER);

    }

    public int insertar(Cliente c) throws SQLException {
        String query = "{call insertarCliente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        Connection connection = mySQLConnection.open();
        CallableStatement cstm = (CallableStatement) connection.prepareCall(query);
        insertarDatosCliente(cstm, c);
        cstm.execute();
        c.getPersona().setIdPersona(cstm.getInt(15));
        c.setIdCliente(cstm.getInt(16));

        cstm.close();
        connection.close();

        return c.getIdCliente();
    }

    public void executeQuery(Connection connection, String query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            statement.close();
        }
    }

    public int modificar(Cliente c) throws SQLException {
        ArrayList<String> querys = new ArrayList<>();
        System.out.println(c.toString());
        String query =  """
                       UPDATE cliente set email = '%s', fechaRegistro = '%s',  estatus = 1, idPersona = %s where idCliente = %s;
                       """;
        query = String.format(query, c.getEmail(), c.getFechaRegistro(),c.getPersona().getIdPersona(), c.getIdCliente());
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
        query = String.format(query, c.getPersona().getNombre(), c.getPersona().getApellidoPaterno(), c.getPersona().getApellidoMaterno(),
                c.getPersona().getGenero(), c.getPersona().getFechaNacimiento(), c.getPersona().getRfc(), c.getPersona().getCurp(), c.getPersona().getDomicilio(),
                c.getPersona().getCodigoPostal(), c.getPersona().getCiudad(), c.getPersona().getEstado(), c.getPersona().getTelefono(), c.getPersona().getFoto(), c.getPersona().getIdPersona());
        querys.add(query);

        Connection connection = mySQLConnection.open();
        querys.forEach(e ->{
            System.out.println(e);
                    });
        

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
        return c.getIdCliente();
    }
    
    

}
