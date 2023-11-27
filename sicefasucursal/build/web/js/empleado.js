// variables de control para la paginación
let MAX = 0;
let MIN = 0;
let actual = 0;

// variable para guardar los elementos de la tabla
let empleados = [];
// variable para guardar un elemento en especifico
let empleados_element = null;


function consultarEmpleados() {
    fetch(`${URL_BASE}/empleado/getall`)
        .then(response => response.json())
        .then(data => {
            MAX = data.length;
            empleados = data;
            mostrarActivos(0);
            //cargarTablaConsulta(data, actual);
        });
}

function consultarEmpleado(idEmpleado) {
    empleados.forEach(empleado => {
        if (empleado.idEmpleado == idEmpleado) {
            empleados_element = empleado;
        }
    });
}

function cargarTablaConsulta(listaElementos, pagina) {
    const tbody = document.getElementById('body-consulta');
    const pagination = pagina * 8;
    let contenido = '';
    for (let i = pagination; i < pagination + 8; i++) {
        if (listaElementos[i]) {
            contenido += `
                    <tr>
                        <td id='idempleado'>${listaElementos[i].idEmpleado}</td>
                        <td>
                            Codigo: ${listaElementos[i].codigo} <br>
                            Email: ${listaElementos[i].email} <br>
                            Puesto: ${listaElementos[i].puesto} <br>
                            Estatus: ${listaElementos[i].activo}
                        </td>
                        <td>
                            Nombre: ${listaElementos[i].persona.nombre} <br>
                            Genero: ${listaElementos[i].persona.genero} <br>
                            CURP: ${listaElementos[i].persona.curp} <br>
                            RFC: ${listaElementos[i].persona.rfc}
                        </td>
                        <td>
                            Usuario: ${listaElementos[i].usuario.nombreUsuario} <br>
                            Rol: ${listaElementos[i].usuario.rol}
                        </td>
                        <td>
                            ID sucursal: ${listaElementos[i].sucursal.idSucursal} <br>
                            Nombre sucursal: ${listaElementos[i].sucursal.nombre} <br>
                            Titular: ${listaElementos[i].sucursal.titular} <br>
                            Telefono: ${listaElementos[i].sucursal.telefono} <br>
                        </td>
                    </tr>
                    `
        }
    }
    tbody.innerHTML = contenido;
    addEventToRows();
}

function changePage(index) {
    if (index == -1 && actual > 0) {
        actual--;
        if (selectedActivos()) {
            mostrarInactivos(actual);
            
        } else {
            mostrarActivos(actual);
        }
        //cargarTablaConsulta(empleados, actual);
    }
    else if (index == 1 && actual < parseInt(MAX / 8)) {
        actual++;
        if (selectedActivos()) {
            mostrarInactivos(actual);
        } else {
            mostrarActivos(actual);
            
        }
        //cargarTablaConsulta(empleados, actual);
    }
    limpiarElemento();
}

function busqueda() {
    let busqueda = document.getElementById('search').value;
    let filtro = empleados.filter(empleado => {

        // variables para realizar la busqueda

        let nombre = empleado.persona.nombre.toLowerCase();
        let apellidoP = empleado.persona.apellidoPaterno.toLowerCase();
        let apellidoM = empleado.persona.apellidoMaterno.toLowerCase();

        return nombre.includes(busqueda) || apellidoP.includes(busqueda) || apellidoM.includes(busqueda);
    });
    actual = 0;
    cargarTablaConsulta(filtro, actual);
    limpiarElemento();
}

function mostrarInactivos(posicion) {
    let filtro = empleados.filter(empleado => empleado.activo == 0);
    actual = posicion;
    cargarTablaConsulta(filtro, actual);
    limpiarElemento();
}

function mostrarActivos(posicion) {
    let filtro = empleados.filter(empleado => empleado.activo == 1);
    actual = posicion;
    console.log(actual)
    cargarTablaConsulta(filtro, actual);
    limpiarElemento();
}


//mostrar boton de modificar
function mostrarBotonModificar() {
    const button = document.getElementById('modificar');
    if (empleados_element != null) {
        button.disabled = false;
    } else {
        button.disabled = true;
    }
    comprobarGuardar();
}

//mostrar boton activar/eliminar
function mostrarBotonEliminar() {

    const buttonReactivar = document.getElementById('reactivar');
    const buttonEliminar = document.getElementById('eliminar');

    if (empleados_element != null && empleados_element.activo == 0) {
        buttonReactivar.style.display = "block";
    } else if (empleados_element != null && empleados_element.activo == 1) {
        buttonEliminar.style.display = "block";
    } else {
        buttonReactivar.style.display = "none";
        buttonEliminar.style.display = "none";
    }

}

function limpiarElemento() {
    empleados_element = null;
    mostrarBotonModificar();
    mostrarBotonEliminar();
}

function selectedActivos() {
    const valueInput = document.getElementById('switchInactivos');
    if (valueInput.checked) {
        return true;
    }
    return false;
}

function comprobarGuardar(){
    clearMessage();
    if(empleados_element){
        const button = document.getElementById('guardar');
        button.onclick = function() {modificar()}

        document.getElementById('staticBackdropLabel').innerHTML = "Modificar empleado";
    }else{
        const button = document.getElementById('guardar');
        button.onclick = function() {agregar()}

        document.getElementById('staticBackdropLabel').innerHTML = "Agregar empleado";
    }
}

// escuchadores de eventos
document.getElementById('search').addEventListener('keyup', () => {
    busqueda();
});

function getRol(){
    const puesto = document.getElementById("cmbPuesto").value;
    if(puesto == "Administrador"){
        document.getElementById("cmbRol").value = "ADMS";
    }else if(puesto == "Empleado"){
        document.getElementById("cmbRol").value = "EMPS";
    }
}

document.getElementById("cmbPuesto").addEventListener('change', ()=>{
    getRol();
});

//mostrar inactivos
document.getElementById('switchInactivos').addEventListener('change', () => {
    const valueInput = document.getElementById('switchInactivos');
    if (valueInput.checked) {
        mostrarInactivos(0);
    } else {
        mostrarActivos(0);
    }
});

// seleccionar elemento de la tabla
function addEventToRows() {
    document.querySelectorAll('#body-consulta tr').forEach(row => {
        row.addEventListener('click', () => {
            document.querySelectorAll('#body-consulta tr').forEach(row => {
                row.classList.remove('selected');
            });
            //obtener el elemento hijo td de la row
            let td = row.querySelector('td');
            let idEmpleado = td.innerHTML;
            row.classList.add('selected');
            consultarEmpleado(idEmpleado);
            mostrarBotonModificar();
            mostrarBotonEliminar();
        });
    });
}

document.addEventListener('DOMContentLoaded', () => {
    consultarEmpleados()
    mostrarActivos();
});

// constantes

const INSERTAR = "insertar";
const MODIFICAR = "modificar";

// funciones largas

function enviarDataEmpleado(empleado, action) {

    let params = { e: JSON.stringify(empleado) }
    fetch(`${URL_BASE}/empleado/${action}?`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body:
            new URLSearchParams(params)
    })
        .then(response => response.json())
        .then(data => {

            if (data.result == "success") {
                Swal.fire("¡Guardado!", data.message, "success");
                consultarEmpleados();
            } else {
                Swal.fire("Error al guardar", data.result, "error");
            }
        })
        .catch(error => {
            Swal.fire({ icon: "error", title: "Error", text: error });
        });
}

function agregar() {
    

    const txtCodigoImagen = document.getElementById('txtaCodigoImagen').value;
    const txtNombre = document.getElementById('txtNombre').value;
    const txtApellidoPaterno = document.getElementById('txtApellidoPaterno').value;
    const txtApellidoMaterno = document.getElementById('txtApellidoMaterno').value;
    const cmbGenero = document.getElementById('cmbGenero').value;
    const txtFechaNacimiento = document.getElementById('txtFechaNacimiento').value;
    const txtRfc = document.getElementById('txtRfc').value;
    const txtCurp = document.getElementById('txtCurp').value;
    const txtDomicilio = document.getElementById('txtDomicilio').value;
    const txtCp = document.getElementById('txtCp').value;
    const txtCiudad = document.getElementById('txtCiudad').value;
    const txtEstado = document.getElementById('txtEstado').value;
    const txtTelefono = document.getElementById('txtTelefono').value;
    const txtEmail = document.getElementById('txtEmail').value;
    const cmbIdSucursal = document.getElementById('cmbIdSucursal').value;
    const txtFechaIngreso = document.getElementById('txtFechaIngreso').value;
    const cmbPuesto = document.getElementById('cmbPuesto').value;
    const txtSalarioBruto = document.getElementById('txtSalarioBruto').value;
    const cmbEstatus = document.getElementById('cmbEstatus').value;
    const cmbRol = document.getElementById('cmbRol').value;

    let person = new persona(1, txtNombre, txtApellidoPaterno, txtApellidoMaterno, cmbGenero, formatDate(txtFechaNacimiento),
        txtRfc, txtCurp, txtDomicilio, txtCp, txtCiudad, txtEstado, txtTelefono, txtCodigoImagen);
    let user = new usuario(1, "", "", cmbRol);
    let sucur = new sucursal(cmbIdSucursal, "", "", "", "", "", "", "", "", "", "", 1);
    let employee = new empleado(1, txtEmail, "", formatDate(txtFechaIngreso), cmbPuesto, txtSalarioBruto, 1, person, user, sucur);

    if (checkInputs()) {
        enviarDataEmpleado(employee, INSERTAR);
        clearInputs('inputs');
        clearMessage();
    }
    
}

function rellenarInputs() {
    // Escribe toda la lógica necesaria para hacer el llenado de los inputs

    document.getElementById('txtNombre').value = empleados_element.persona.nombre;
    document.getElementById('txtApellidoPaterno').value = empleados_element.persona.apellidoPaterno;
    document.getElementById('txtApellidoMaterno').value = empleados_element.persona.apellidoMaterno;
    document.getElementById('cmbGenero').value;
    document.getElementById('txtFechaNacimiento').value = empleados_element.persona.fechaNacimiento;
    document.getElementById('txtRfc').value = empleados_element.persona.rfc;
    document.getElementById('txtCurp').value = empleados_element.persona.curp;
    document.getElementById('txtDomicilio').value = empleados_element.persona.domicilio;
    document.getElementById('txtCp').value = empleados_element.persona.codigoPostal;
    document.getElementById('txtCiudad').value = empleados_element.persona.ciudad;
    document.getElementById('txtEstado').value = empleados_element.persona.estado;
    document.getElementById('txtTelefono').value = empleados_element.persona.telefono;
    document.getElementById('txtEmail').value = empleados_element.email;
    document.getElementById('cmbIdSucursal').value = empleados_element.sucursal.idSucursal;
    document.getElementById('txtFechaIngreso').value = empleados_element.fechaIngreso;
    document.getElementById('cmbPuesto').value = empleados_element.puesto;
    document.getElementById('txtSalarioBruto').value = empleados_element.salarioBruto;
    document.getElementById('cmbEstatus').value = empleados_element.activo;
    document.getElementById('cmbRol').value = empleados_element.usuario.rol;
}

function modificar() {

    const txtCodigoImagen = document.getElementById('txtaCodigoImagen').value;
    const txtNombre = document.getElementById('txtNombre').value;
    const txtApellidoPaterno = document.getElementById('txtApellidoPaterno').value;
    const txtApellidoMaterno = document.getElementById('txtApellidoMaterno').value;
    const cmbGenero = document.getElementById('cmbGenero').value;
    const txtFechaNacimiento = document.getElementById('txtFechaNacimiento').value;
    const txtRfc = document.getElementById('txtRfc').value;
    const txtCurp = document.getElementById('txtCurp').value;
    const txtDomicilio = document.getElementById('txtDomicilio').value;
    const txtCp = document.getElementById('txtCp').value;
    const txtCiudad = document.getElementById('txtCiudad').value;
    const txtEstado = document.getElementById('txtEstado').value;
    const txtTelefono = document.getElementById('txtTelefono').value;
    const txtEmail = document.getElementById('txtEmail').value;
    const cmbIdSucursal = document.getElementById('cmbIdSucursal').value;
    const txtFechaIngreso = document.getElementById('txtFechaIngreso').value;
    const cmbPuesto = document.getElementById('cmbPuesto').value;
    const txtSalarioBruto = document.getElementById('txtSalarioBruto').value;
    const cmbEstatus = document.getElementById('cmbEstatus').value;
    const cmbRol = document.getElementById('cmbRol').value;

    //aqui va tu grandioso fetch, yo no lo hago porque ya lo tengo :b
    let person = new persona(empleados_element.persona.idPersona, txtNombre, txtApellidoPaterno, txtApellidoMaterno, cmbGenero, txtFechaNacimiento,
        txtRfc, txtCurp, txtDomicilio, txtCp, txtCiudad, txtEstado, txtTelefono, txtCodigoImagen);
    let user = new usuario(empleados_element.usuario.idUsuario,empleados_element.usuario.nombreUsuario,empleados_element.usuario.contrasenia, cmbRol);
    let sucur = new sucursal(cmbIdSucursal, "", "", "", "", "", "", "", "", "", "", 1);
    let employee = new empleado(empleados_element.idEmpleado, txtEmail,empleados_element.codigo, txtFechaIngreso, cmbPuesto, txtSalarioBruto, 1, person, user, sucur);

    enviarDataEmpleado(employee, MODIFICAR);
    clearInputs('inputs');

    //igualamos el elemento a null para que se deshabilite el boton y llamamos a la funcion
    limpiarElemento();
}

function eliminar() {
    // comprobación
    Swal.fire({
        title: "¿Desea eliminar este registro?",
        text: "Al eliminar el estatus del empleado pasara a ser inactivo",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Eliminar"
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`${URL_BASE}/empleado/delete?id=${empleados_element.idEmpleado}`)
                .then(response => response.json())
                .then(data => {
                    if (data.result == "success") {
                        Swal.fire("¡Eliminado!", data.message, "success");
                        consultarEmpleados();
                        limpiarElemento();
                    } else {
                        Swal.fire("Error al Eliminar", data.result, "error");
                    }
                });
        }
    });
}

async function reactivar() {
    // comprobación
    Swal.fire({
        title: "¿Desea reactivar este registro?",
        text: "Al reactivar el estatus del empleado pasara a ser activo",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Reactivar"
    }).then((result) => {
        if (result.isConfirmed) {

            fetch(`${URL_BASE}/empleado/reactivar?id=${empleados_element.idEmpleado}`)
                .then(response => response.json())
                .then(data => {
                    if (data.result == "success") {
                        Swal.fire("¡Reactivado!", data.message, "success");
                        consultarEmpleados();
                    } else {
                        Swal.fire("Error al reactivar", data.result, "error");
                    }
                }
            );
            setTimeout(()=>{mostrarInactivos()},100)
        }
    });
}