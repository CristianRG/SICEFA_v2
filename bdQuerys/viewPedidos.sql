create view view_pedidos as select c.idCompra, c.fechaHoraPedido, c.idEmpleado, s.idSucursal, s.nombre sucursal, c.estatus, dc.idProducto, p.nombre producto, dc.cantidad, dc.precioCompra, (dc.cantidad*dc.precioCompra) total
from compra as c inner join detallecompra as dc on c.idCompra = dc.idCompra inner join empleado as e on c.idEmpleado = e.idEmpleado
inner join sucursal as s on e.idSucursal = s.idSucursal inner join producto as p on dc.idProducto = p.idProducto order by dc.idCompra;

drop view view_pedidos;

select * from view_pedidos;