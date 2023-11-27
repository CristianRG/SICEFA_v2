# inserts para persona / cliente

INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, genero, fechaNacimiento, rfc, curp, domicilio, codigoPostal, ciudad, estado, telefono, foto)
VALUES ('Venta al publico', '', '', '', '', '', '', '', '', '', '', '', '');

INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, genero, fechaNacimiento, rfc, curp, domicilio, codigoPostal, ciudad, estado, telefono, foto)
VALUES ('María', 'Hernández', 'García', 'M', '1985-08-22', 'HERG850822', 'HEMG850822MDFRMR07', 'Av. Principal 456, Colonia Nueva', '54321', 'Guadalajara', 'Jalisco', '555-987-6543', 'url_de_la_foto.jpg');

INSERT INTO persona (nombre, apellidoPaterno, apellidoMaterno, genero, fechaNacimiento, rfc, curp, domicilio, codigoPostal, ciudad, estado, telefono, foto)
VALUES ('Alex', 'Pérez', 'Martínez', 'O', '1995-02-10', 'PEMA950210', 'PEMA950210HDFRLX03', 'Calle 789, Colonia Moderna', '67890', 'Monterrey', 'Nuevo León', '555-555-5555', 'url_de_la_foto.jpg');

select * from persona;

INSERT INTO cliente (email, fechaRegistro, estatus, idPersona)
VALUES ('', '2023-01-01', 1, 25);

INSERT INTO cliente (email, fechaRegistro, estatus, idPersona)
VALUES ('maria.hernandez@example.com', '2023-01-02', 1, 26);

INSERT INTO cliente (email, fechaRegistro, estatus, idPersona)
VALUES ('alex.perez@example.com', '2023-01-03', 1, 27);


# inserts de venta / detalle venta

INSERT INTO venta (fechaHora, estatus, idCliente, idEmpleado) VALUES 
('2023-01-01 10:00:00', 1, 1, 1),
('2023-01-02 11:30:00', 2, 1, 1),
('2023-01-03 12:45:00', 1, 1, 1),
('2023-01-04 09:15:00', 2, 2, 1),
('2023-01-05 14:20:00', 1, 2, 1);

select * from venta;

delete from detalleventa;
-- Inserts para la tabla 'detalleVenta'
INSERT INTO detalleVenta (idProducto, idVenta, cantidad, precioVenta) VALUES 
(1, 6, 5, 547),
(2, 7, 3, 210),
(3, 8, 2, 254),
(1, 9, 4, 547),
(2, 10, 1, 210),
(3, 9, 3, 254),
(1, 10, 2, 547),
(2, 6, 2, 210),
(3, 10, 5, 254),
(1, 6, 1, 547);