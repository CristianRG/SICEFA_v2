delete from detalleCompra;

use sicefa;
select * from compra;

-- Inserciones en la tabla compra
INSERT INTO compra (fechaHoraPedido, estatus, idEmpleado) VALUES
('2023-11-29 10:00:00', 1, 1),
('2023-11-29 11:30:00', 1, 2),
('2023-11-29 12:45:00', 1, 3),
('2023-11-29 14:15:00', 1, 1),
('2023-11-29 15:30:00', 1, 2);

-- Inserciones en la tabla detalleCompra
INSERT INTO detalleCompra (idCompra, idProducto, cantidad, precioCompra) VALUES
(6, 1, 5, 10.99),
(6, 2, 3, 15.50),
(7, 3, 2, 8.75),
(8, 1, 7, 12.25),
(7, 2, 3, 15.50),
(7, 3, 4, 8.75),
(9, 2, 4, 15.50),
(9, 1, 6, 10.99);

