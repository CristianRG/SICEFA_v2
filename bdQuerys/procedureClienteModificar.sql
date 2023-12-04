DELIMITER //

CREATE PROCEDURE modificarSucursal(
    IN p_idSucursal INT,
    IN p_nombre VARCHAR(49),
    IN p_titular VARCHAR(49),
    IN p_rfc VARCHAR(15),
    IN p_domicilio VARCHAR(129),
    IN p_colonia VARCHAR(65),
    IN p_codigoPostal VARCHAR(11),
    IN p_ciudad VARCHAR(65),
    IN p_estado VARCHAR(49),
    IN p_telefono VARCHAR(20),
    IN p_latitud VARCHAR(65),
    IN p_longitud VARCHAR(65)
)
BEGIN
    UPDATE sucursal
    SET nombre = p_nombre,
        titular = p_titular,
        rfc = p_rfc,
        domicilio = p_domicilio,
        colonia = p_colonia,
        codigoPostal = p_codigoPostal,
        ciudad = p_ciudad,
        estado = p_estado,
        telefono = p_telefono,
        latitud = p_latitud,
        longitud = p_longitud
    WHERE idSucursal = p_idSucursal;
END //

DELIMITER ;