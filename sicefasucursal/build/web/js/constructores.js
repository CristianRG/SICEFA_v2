function empleado(idEmpleado, email, codigo, fechaIngreso, puesto, salarioBruto, activo, persona, usuario, sucursal) {
    this.idEmpleado = idEmpleado;
    this.email = email;
    this.codigo = codigo;
    this.fechaIngreso = fechaIngreso;
    this.puesto = puesto;
    this.salarioBruto = salarioBruto;
    this.activo = activo;
    this.persona = persona;
    this.usuario = usuario;
    this.sucursal = sucursal;

    function getEmpleado() {
        return { idEmpleado:idEmpleado, email: email, codigo:codigo, fechaIngreso: fechaIngreso, puesto: puesto, salarioBruto: salarioBruto, activo: activo, persona:persona, usuario:usuario, sucursal:sucursal };
    }

    return getEmpleado();
}

function persona(idPersona, nombre, apellidoPaterno, apellidoMaterno, genero, fechaNacimiento, rfc, curp, domicilio, codigoPostal, ciudad, estado, telefono, foto) {
    this.idPersona = idPersona;
    this.nombre = nombre;
    this.apellidoPaterno = apellidoPaterno;
    this.apellidoMaterno = apellidoMaterno;
    this.genero = genero;
    this.fechaNacimiento = fechaNacimiento;
    this.rfc = rfc;
    this.curp = curp;
    this.domicilio = domicilio;
    this.codigoPostal = codigoPostal;
    this.ciudad = ciudad;
    this.estado = estado;
    this.telefono = telefono;
    this.foto = foto;

    function getPersona() {
        return {
            idPersona:idPersona, nombre: nombre, apellidoPaterno: apellidoPaterno, apellidoMaterno: apellidoMaterno, genero: genero, fechaNacimiento: fechaNacimiento, rfc: rfc, curp: curp, domicilio: domicilio, codigoPostal: codigoPostal,
            ciudad: ciudad, estado: estado, telefono: telefono, foto: foto
        };
    }
    return getPersona();
}

function usuario(idUsuario, nombre, contrasenia, rol) {
    this.idUsuario = idUsuario;
    this.nombre = nombre;
    this.contrasenia = contrasenia;
    this.rol = rol;

    function getUsuario() {
        return { idUsuario:idUsuario, nombre: nombre, contrasenia: contrasenia, rol: rol }
    }

    return getUsuario();
}

function sucursal(idSucursal, nombre, titular, rfc, domicilio, codigoPostal, ciudad, estado, telefono, latitud, longitud, estatus) {
    this.idSucursal = idSucursal;
    this.nombre = nombre;
    this.titular = titular;
    this.rfc = rfc;
    this.domicilio = domicilio;
    this.codigoPostal = codigoPostal;
    this.ciudad = ciudad;
    this.estado = estado;
    this.telefono = telefono;
    this.latitud = latitud;
    this.longitud = longitud;
    this.estatus = estatus;

    function getSucusal(){
        return { idSucursal:idSucursal, nombre:nombre, titular:titular, rfc:rfc, domicilio:domicilio, codigoPostal:codigoPostal, ciudad:ciudad, estado:estado,
                telefono:telefono, latitud:latitud, longitud:longitud, estatus:estatus }
    }
    
    return getSucusal();
}