DELIMITER $$

CREATE PROCEDURE insertarSucursal(
    IN var_nombre          VARCHAR(49),
    IN var_titular         VARCHAR(49),
    IN var_rfc             VARCHAR(15),
    IN var_domicilio       VARCHAR(129),
    IN var_colonia         VARCHAR(65),
    IN var_codigoPostal    VARCHAR(11),
    IN var_ciudad          VARCHAR(65),
    IN var_estado          VARCHAR(49),
    IN var_telefono        VARCHAR(20),
    IN var_latitud         VARCHAR(65),
    IN var_longitud        VARCHAR(65),
    OUT var_idSucursal     INT,
    OUT var_idPersona      INT,
    OUT var_idUsuario      INT,
    OUT var_idEmpleado     INT,
    OUT var_codigoEmpleado VARCHAR(9),
    OUT var_nombreUsuario  VARCHAR(33),
    OUT var_contrasenia    VARCHAR(33)
)
BEGIN
    DECLARE idUsuarioMax INT;
    
    -- Insertar datos en la tabla sucursal
    INSERT INTO sucursal(nombre, titular, rfc, domicilio, colonia, codigoPostal,
                         ciudad, estado, telefono, latitud, longitud, estatus)
    VALUES(var_nombre, var_titular, var_rfc, var_domicilio, var_colonia, var_codigoPostal,
           var_ciudad, var_estado, var_telefono, var_latitud, var_longitud, 1);
    
    SET var_idSucursal = LAST_INSERT_ID();
    
    -- Generar el código del empleado
    CALL generarCodigoEmpleado(var_codigoEmpleado);
    
    -- Generar nombre de usuario y contraseña
    SET idUsuarioMax = 1 + (SELECT MAX(idUsuario) FROM usuario);
    SET var_nombreUsuario = CONCAT('Admins', idUsuarioMax);
    SET var_contrasenia = var_nombreUsuario;
    
    -- Insertar datos en la tabla usuario
    INSERT INTO usuario (nombreUsuario, contrasenia, rol)
    VALUES (var_nombreUsuario, var_contrasenia, 'ADMS');
    
    SET var_idUsuario = LAST_INSERT_ID();
    
    -- Insertar datos en la tabla persona
    INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, genero,fechaNacimiento, rfc, domicilio, codigoPostal,
                         ciudad, estado, telefono, foto)
    VALUES (CONCAT('Admins_', var_titular), '', '', '','1901-01-01', '', '',
            '', '', '', '', '');
    
    SET var_idPersona = LAST_INSERT_ID();
    
    -- Insertar datos en la tabla empleado
    INSERT INTO empleado(codigo, fechaIngreso, puesto, salarioBruto, activo,
                         idPersona, idUsuario, idSucursal)
    VALUES(var_codigoEmpleado, NOW(), '', 0.0, 1, var_idPersona, var_idUsuario, var_idSucursal);
    
    SET var_idEmpleado = LAST_INSERT_ID();
END$$

DELIMITER ;