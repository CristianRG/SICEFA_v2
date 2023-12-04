USE sicefa;
DROP VIEW view_clientes;
CREATE VIEW  view_clientes AS
    SELECT 
        c.idCliente,
        c.email,
        c.fechaRegistro,
        c.estatus,
        p.idPersona,
        p.nombre,
        p.apellidoPaterno,
        p.apellidoMaterno,
        p.genero,
        p.fechaNacimiento,
        p.rfc,
        p.curp,
        p.domicilio,
        p.codigoPostal ,
        p.ciudad,
        p.estado,
        p.telefono,
        p.foto
    FROM
        cliente AS c
            INNER JOIN
        persona AS p ON c.idPersona = p.idPersona;
        
SELECT * FROM view_clientes;
SELECT * FROM persona;
SELECT * FROM cliente;

  UPDATE cliente set idPersona=3 where idCliente=2;

/*Persona y cliente 1*/
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, 
                     fechaNacimiento, rfc, curp, domicilio, codigoPostal, ciudad, estado, telefono, foto)
            VALUES  (1, 'Johan', 'Lino', 'Moreno', 'O', STR_TO_DATE('01/01/1901', '%d/%m/%Y'), '', '', '', '', '', '', '', '');
INSERT INTO cliente (idCliente,email,fechaRegistro,estatus,idPersona) VALUES (1,'johan.antonio25@gmail.com',STR_TO_DATE('01/01/1901', '%d/%m/%Y'),1,1);

/*Persona y cliente 2*/
INSERT INTO persona (idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, 
                     fechaNacimiento, rfc, curp, domicilio, codigoPostal, ciudad, estado, telefono, foto)
            VALUES  (2, 'Ref', 'Gonzales', 'Aguilera', 'O', STR_TO_DATE('01/01/1901', '%d/%m/%Y'), '', '', '', '', '', '', '', '');
            
INSERT INTO cliente (idCliente,email,fechaRegistro,estatus,idPersona) VALUES (2,'ref.fer@gmail.com',STR_TO_DATE('01/01/1901', '%d/%m/%Y'),1,2);


SELECT  c.idCliente,c.email, c.fechaRegistro,c.estatus,p.idPersona, p.nombre,p.apellidoPaterno,p.apellidoMaterno, p.genero, p.fechaNacimiento, p.rfc, p.curp, p.domicilio, p.codigoPostal , p.ciudad, p.estado, p.telefono, p.foto FROM cliente c INNER JOIN persona p ON c.idPersona = p.idPersona WHERE idCliente=2;

/*Seleccionar cliente*/
SELECT * FROM view_clientes WHERE idCliente=2;
/*Buscar cliente*/
SELECT * FROM view_clientes WHERE idCliente LIKE 1 OR email LIKE 'johan.antonio25@gmail.com'OR fechaRegistro LIKE '01/01/1901' 
OR estatus LIKE 1 OR idPersona LIKE 1 OR nombre LIKE '' OR apellidoPaterno LIKE '' OR apellidoMaterno LIKE''OR genero LIKE '' 
OR fechaNacimiento LIKE '01/01/1901' OR rfc LIKE'' OR  curp LIKE '' OR domicilio LIKE '' OR codigoPostal LIKE '' OR ciudad LIKE '' OR
estado LIKE '' OR telefono LIKE '';


/*Insertar Cliente*/
DROP PROCEDURE insertarCliente;
DELIMITER $$
CREATE PROCEDURE insertarCliente(/* Datos Personales */
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
                                    
                                    /*Datos cliente*/
                                    IN var_email VARCHAR(45),				-- 14	
                                  
                                  /* Parametros de Salida */
                                    OUT var_idPersona       INT,            -- 15
                                    OUT var_idCliente      INT            	-- 16
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
        
        -- Insertamos los datos del Cliente:
        INSERT INTO cliente (email,fechaRegistro,estatus,
                             idPersona)
                    VALUES(var_email, NOW(), 
                           1, var_idPersona);
		SET var_idCliente = LAST_INSERT_ID();
    END
$$
DELIMITER ;

CALL insertarCliente('Jose', 'Ramon', 'Gomez', 'M', '01/01/1990', 'ABC12345', 'CURP123456ABCXYZ789', 'Direccion 123', '12345', 'Ciudad', 'Estado', '1234567890', 'URL_FOTO','juan@example.com', @idPersona, @idCliente);
/*
17:21:05	CALL insertarCliente('Juan', 'Perez', 'Gomez', 'M', '01/01/1990', 'ABC12345', 'CURP123456ABCXYZ789', 'Direccion 123', '12345', 'Ciudad', 'Estado', '1234567890', 'URL_FOTO','juan@example.com', 1, @idPersona, @idCliente)	Error Code: 1054. Unknown column 'fechaRegistro' in 'field list'	0.000 sec

*/
SELECT * FROM usuario;
SELECT * FROM empleado;
