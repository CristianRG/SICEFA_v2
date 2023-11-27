create view view_ventas as select
    v.idVenta,
    pr.nombre,
    d.cantidad,
    d.precioVenta,
    emp_persona.nombre as EM, -- Nombre del empleado
    cl_persona.nombre as CL, -- Nombre del cliente
    v.fechaHora,
    v.estatus,
    (d.cantidad*d.precioVenta) as total
FROM
    venta AS v
    JOIN detalleVenta AS d ON v.idVenta = d.idVenta
    JOIN producto AS pr ON d.idProducto = pr.idProducto
    LEFT JOIN empleado AS emp ON v.idEmpleado = emp.idEmpleado
    LEFT JOIN cliente AS cl ON v.idCliente = cl.idCliente
    LEFT JOIN persona AS emp_persona ON emp.idPersona = emp_persona.idPersona
    LEFT JOIN persona AS cl_persona ON cl.idPersona = cl_persona.idPersona;

drop view view_ventas;
select * from view_ventas;