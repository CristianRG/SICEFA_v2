-- -----------------------------------------------------
-- Artifact:    DDL_StoredProcedures_farmacia_v3.sql
-- Version:     3.0
-- Date:        2023-05-02 12:50:00
-- Author:      Miguel Angel Gil Rios
-- Email:       mgil@utleon.edu.mx
--              angel.grios@gmail.com
-- Comments:    Se agregaron procedimientos almacenados
--              para insertar sucursales y empleados.
-- -----------------------------------------------------
USE sicefa;

-- Procedimiento Almacenado para generar el codigo de un nuevo empleado.
DROP PROCEDURE IF EXISTS generarCodigoEmpleado;
DELIMITER $$
CREATE PROCEDURE generarCodigoEmpleado(OUT codigo VARCHAR(8))
	BEGIN
		DECLARE anio INT;
		DECLARE mes VARCHAR(2);
		DECLARE num VARCHAR(4);
		SET anio  = RIGHT(year(now()),2);
		SET mes   = LPAD(RIGHT(month(now()),2), 2, '0');
		SET num   = (SELECT LPAD(MAX(idUsuario) + 1, 4, '0') FROM usuario);
		SET codigo= CONCAT(anio,mes,num);
	END
$$
DELIMITER ;

-- Procedimiento almacenado para insertar un nuevo Empleado.
DROP PROCEDURE IF EXISTS insertarEmpleado;
DELIMITER $$
CREATE PROCEDURE insertarEmpleado(/* Datos Personales */
                                    IN	var_nombre          VARCHAR(64),    --  1
                                    IN	var_apellidoPaterno VARCHAR(64),    --  2
                                    IN	var_apellidoMaterno VARCHAR(64),    --  3
                                    IN  var_genero          VARCHAR(2),     --  4
                                    IN  var_fechaNacimiento VARCHAR(11),    --  5
                                    IN  var_rfc             VARCHAR(14),    --  6
                                    IN  var_curp            VARCHAR(19),    --  7
                                    IN	var_domicilio       VARCHAR(129),   --  8
                                    IN  var_cp              VARCHAR(11),    --  9
                                    IN  var_ciudad          VARCHAR(46),    -- 10
                                    IN  var_estado          VARCHAR(40),    -- 11
                                    IN	var_telefono        VARCHAR(20),    -- 12
                                    IN	var_foto            LONGTEXT,       -- 13
                                    
                                  /* Datos del la Sucursal */
                                    IN  var_idSucursal      INT,            -- 14
                                    
                                  /* Datos del Usuario    */
                                    IN  var_rol             VARCHAR(10),    -- 15
                                    
                                  /* Datos del Empleado */
                                    IN  var_email           VARCHAR(65),    -- 16
                                    IN  var_puesto          VARCHAR(25),    -- 17
                                    IN  var_salarioBruto    FLOAT,          -- 18
                                  
                                  /* Parametros de Salida */
                                    OUT var_idPersona       INT,            -- 19
                                    OUT var_idUsuario       INT,            -- 20
                                    OUT var_idEmpleado      INT,            -- 21
                                    OUT var_codigoEmpleado  VARCHAR(9)      -- 22
                                 )
    BEGIN
        -- Comenzamos insertando los datos de la Persona:
        INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, genero,
                             fechaNacimiento, rfc, curp, domicilio, codigoPostal, 
                             ciudad, estado, telefono, foto)
                    VALUES( var_nombre, var_apellidoPaterno, var_apellidoMaterno, 
                            var_genero, STR_TO_DATE(var_fechaNacimiento, '%d/%m/%Y'),
                            var_rfc, var_curp, var_domicilio, var_cp,
                            var_ciudad, var_estado, var_telefono, var_foto);
        
        -- Obtenemos el ID de Persona que se genero:
        SET var_idPersona = LAST_INSERT_ID(); 
        
        -- Generamos el Codigo del Empleado porque lo necesitamos
        -- para generar el usuario:
        CALL generarCodigoEmpleado(var_codigoEmpleado);
        
        -- Insertamos los datos del Usuario que tendra el Empleado:
        INSERT INTO usuario (nombreUsuario, contrasenia, rol)
                    VALUES (var_codigoEmpleado, var_codigoEmpleado, var_rol);
        -- Recuperamos el ID de Usuario generado:
        SET var_idUsuario = LAST_INSERT_ID(); 
        
        -- Insertamos los datos del Empleado:
        INSERT INTO empleado(email, codigo, fechaIngreso, puesto, salarioBruto, activo,
                             idPersona, idUsuario, idSucursal)
                    VALUES(var_email, var_codigoEmpleado, NOW(), var_puesto, var_salarioBruto,
                           1, var_idPersona, var_idUsuario, var_idSucursal);
		SET var_idEmpleado = LAST_INSERT_ID();
    END
$$
DELIMITER ;

-- Procedimiento almacenado para insertar una nueva sucursal.
--      Esta operacion implica que, al agregar una nueva sucursal,
--      de forma automatica se agregara un usuario administrador,
--      lo cual implica, la insercion de un empleado y una persona.
DROP PROCEDURE IF EXISTS insertarSucursal;
DELIMITER $$
CREATE PROCEDURE insertarSucursal(/* Datos Sucursal */
                                    IN	var_nombre          VARCHAR(49),    --  1
                                    IN	var_titular         VARCHAR(49),    --  2
                                    IN  var_rfc             VARCHAR(15),    --  3                                    
                                    IN	var_domicilio       VARCHAR(129),   --  4
                                    IN  var_colonia         VARCHAR(65),    --  5
                                    IN  var_codigoPostal    VARCHAR(11),    --  6
                                    IN  var_ciudad          VARCHAR(65),    --  7
                                    IN  var_estado          VARCHAR(49),    --  8                                    
                                    IN	var_telefono        VARCHAR(20),    --  9
                                    IN	var_latitud         VARCHAR(65),    -- 10
                                    IN	var_longitud        VARCHAR(65),    -- 11
                                    
                                  /* Parametros de Salida */
                                    OUT  var_idSucursal     INT,            -- 12
                                    OUT  var_idPersona      INT,            -- 13
                                    OUT  var_idUsuario      INT,            -- 14
                                    OUT  var_idEmpleado     INT,            -- 15
                                    OUT  var_codigoEmpleado VARCHAR( 9),    -- 17
                                    OUT  var_nombreUsuario  VARCHAR(33),    -- 17
                                    OUT  var_contrasenia    VARCHAR(33)     -- 18
                                 )
    BEGIN
        DECLARE idUsuarioMax INT;
    
        -- Comenzamos insertando los datos de la Sucursal:
        INSERT INTO sucursal(nombre, titular, rfc, domicilio, colonia, codigoPostal,
                             ciudad, estado, telefono, latitud, longitud, estatus)
                    VALUES(var_nombre, var_titular, var_rfc, var_domicilio, var_colonia, var_codigoPostal,
                           var_ciudad, var_estado, var_telefono, var_latitud, var_longitud, 1);
        
        -- Recuperamos el ID de la Sucursal que se genero:
        SET var_idSucursal = LAST_INSERT_ID();
                
        -- Generamos el Codigo del Empleado porque lo necesitamos
        -- para generar el usuario:
        CALL generarCodigoEmpleado(var_codigoEmpleado);
        
        -- Generamos el nombre del Usuario Administrador que por default tendra la Sucursal:
        SET idUsuarioMax      = 1 + (SELECT MAX(idUsuario) FROM usuario);
        SET var_nombreUsuario = CONCAT('Admins', idUsuarioMax);
        SET var_contrasenia   = var_nombreUsuario;
        
        -- Insertamos los datos del Usuario que tendra el Empleado:
        INSERT INTO usuario (nombre, contrasenia, rol)
                    VALUES (var_nombreUsuario, var_contrasenia, 'ADMS');
        
        -- Recuperamos el ID de Usuario generado:
        SET var_idUsuario = LAST_INSERT_ID();
        
        -- Insertamos los datos personales:
        INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, genero,
                             fechaNacimiento, rfc, curp, domicilio, codigoPostal, 
                             ciudad, estado, telefono, foto)
                    VALUES( CONCAT('Admins_', var_titular), '', '', 
                            'O', STR_TO_DATE('01/01/1901', '%d/%m/%Y'),
                            '', '', '', '',
                            '', '', '', '');
        
        -- Recuperamos el ID de la Persona que se genero:
        SET var_idPersona = LAST_INSERT_ID();
        
        -- Insertamos los datos del Empleado:
        INSERT INTO empleado(codigo, fechaIngreso, puesto, salarioBruto, activo,
                             idPersona, idUsuario, idSucursal)
                    VALUES(var_codigoEmpleado, NOW(), var_puesto, var_salarioBruto,
                           1, var_idPersona, var_idUsuario, var_idSucursal);
    END
$$
DELIMITER ;

use sicefa;





select v.idVenta, pr.nombre, d.cantidad, d.precioVenta, p.nombre as EM, p.nombre as CL, v.fechaHora, v.estatus 
from venta as v, detalleVenta as d, producto as pr, persona as p, empleado as em, cliente as cl
where pr.idProducto = d.idProducto and p.idPersona = em.idPersona;
select * from inventario;

DELIMITER $$
create procedure insertarVenta(in var_idProducto int, in idEmpleado int, in idCliente int, in idSucursal int, in cantidad int, out estatus_existencias int, out var_idVenta int)
	begin
    declare precioVentaP decimal(5,2);
		call comprobarExistencias();
		set estatus_existencias = estatus;
        if estatus_existencias = 1 then
			select  precioVenta into precioVentaP from producto where idProducto = var_idProducto;
			update inventario set existencias = (existencias - cantidad);
            insert venta values (0, now(), 1, idCliente, idEmpleado);
            set var_idVenta = last_insert_id();
            insert detalleventa values (idProducto, var_idVenta, cantidad, precioVentaP);
		end if;
	end
$$
DELIMITER ;

DELIMITER $$
create procedure comprobarExistencias(in var_idProducto int, in var_idSucursal int, in cantidad int, out estatus int)
	begin
		if cantidad > (select existencias from inventario where idSucursal = var_idSucursal and idProducto = var_idProducto) then
			set estatus = 0;
		else
			set estatus = 1;
		end if;
    end
$$
DELIMITER ;
select * from producto limit 10 offset 20;