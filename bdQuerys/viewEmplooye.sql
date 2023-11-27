use sicefa;

CREATE VIEW view_empleados AS
    SELECT 
        e.idEmpleado,
        e.email,
        e.codigo,
        e.fechaIngreso,
        e.puesto,
        e.salarioBruto,
        e.activo,
        p.idPersona,
        p.nombre pNombre,
        p.apellidoPaterno,
        p.apellidoMaterno,
        p.genero,
        p.fechaNacimiento,
        p.rfc pRFC,
        p.curp,
        p.domicilio pDomicilio,
        p.codigoPostal pCodigoPostal,
        p.ciudad pCiudad,
        p.estado pEstado,
        p.telefono pTelefono,
        p.foto,
        u.idUsuario,
        u.nombreUsuario,
        u.contrasenia,
        u.rol,
        s.idSucursal,
        s.nombre,
        s.titular,
        s.rfc,
        s.domicilio,
        s.colonia,
        s.codigoPostal,
        s.ciudad,
        s.estado,
        s.telefono,
        s.latitud,
        s.longitud,
        s.estatus
    FROM
        empleado AS e
            INNER JOIN
        persona AS p ON e.idPersona = p.idPersona
            INNER JOIN
        usuario AS u ON e.idUsuario = u.idUsuario
            INNER JOIN
        sucursal AS s ON e.idSucursal = s.idSucursal;

SELECT 
    *
FROM
    view_empleados;
show columns from empleado;

# para tener permisos en las pc's de la uni
grant all privileges on *.* to 'root'@'localhost' identified by '' with grant option;
flush privileges;

update empleado set activo = 1;
select * from empleado;

 SELECT 
        e.idEmpleado,
        e.email,
        e.codigo,
        e.fechaIngreso,
        e.puesto,
        e.salarioBruto,
        e.activo,
        p.idPersona,
        p.nombre pNombre,
        p.apellidoPaterno,
        p.apellidoMaterno,
        p.genero,
        p.fechaNacimiento,
        p.rfc pRFC,
        p.curp,
        p.domicilio pDomicilio,
        p.codigoPostal pCodigoPostal,
        p.ciudad pCiudad,
        p.estado pEstado,
        p.telefono pTelefono,
        p.foto,
        u.idUsuario,
        u.nombreUsuario,
        u.contrasenia,
        u.rol,
        s.idSucursal,
        s.nombre,
        s.titular,
        s.rfc,
        s.domicilio,
        s.colonia,
        s.codigoPostal,
        s.ciudad,
        s.estado,
        s.telefono,
        s.latitud,
        s.longitud,
        s.estatus
    FROM
        empleado AS e
            INNER JOIN
        persona AS p ON e.idPersona = p.idPersona
            INNER JOIN
        usuario AS u ON e.idUsuario = u.idUsuario
            INNER JOIN
        sucursal AS s ON e.idSucursal = s.idSucursal
	WHERE
		p.nombre like 'A%';