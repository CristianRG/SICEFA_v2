// variables de control para la paginación
let MAX = 0;
let MIN = 0;
let actual = 0;

// variable para guardar los elementos de la tabla
let clientes = [];
// variable para guardar un elemento en especifico
let clientes_element = null;
const URL_BASE = "http://localhost:8080/sicefasucursal/api";

//function prueba(){
//    alert("Hola")
//}


function consultarClientes() {
    fetch(`${URL_BASE}/cliente/getall`)
            .then(response => response.json())
            .then(data => {
                MAX = data.length;
                clientes = data;
                mostrarActivosCliente(0);
//            cargarTablaConsulta(data, actual);
            });
}

function consultarCliente(idCliente) {
    clientes.forEach(cliente => {
        if (cliente.idCliente == idCliente) {
            clientes_element = cliente;
        }
    });
}

function cargarTablaConsultaClientes(listaElementos, pagina) {
    const tbody = document.getElementById('body-consulta');
    const pagination = pagina * 8;
    let contenido = '';
    for (let i = pagination; i < pagination + 8; i++) {
        if (listaElementos[i]) {
            contenido += `
                    <tr>
                        <td id='idcliente'>${listaElementos[i].idCliente}</td>
                        <td>
                            Email: ${listaElementos[i].email} <br>
                            FechaRegistro: ${listaElementos[i].fechaRegistro} <br>
                            Estatus: ${listaElementos[i].estatus}<br>
                            Persona: ${listaElementos[i].persona.idPersona}
                        </td>
                        <td>
                            Nombre: ${listaElementos[i].persona.nombre} <br>
                            Genero: ${listaElementos[i].persona.genero} <br>
                            CURP: ${listaElementos[i].persona.curp} <br>
                            RFC: ${listaElementos[i].persona.rfc}
                        </td>
                    </tr>
                    `
        }
    }
    tbody.innerHTML = contenido;
    addEventToRowsCliente();
}

function changePageCliente(index) {
    if (index == -1 && actual > 0) {
        actual--;
        if (selectedActivosCliente()) {
            mostrarInactivosCliente(actual);

        } else {
            mostrarActivosCliente(actual);
        }
        //cargarTablaConsulta(empleados, actual);
    } else if (index == 1 && actual < parseInt(MAX / 8)) {
        actual++;
        if (selectedActivosCliente()) {
            mostrarInactivosCliente(actual);
        } else {
            mostrarActivosCliente(actual);

        }
        //cargarTablaConsulta(empleados, actual);
    }
    limpiarElemento();
}

function busquedaCliente() {
    let busqueda = document.getElementById('search').value;
        fetch("http://localhost:8080/sicefasucursal/api/cliente/buscar?parametro=" + busqueda)
            .then(response => response.json())
            .then(response => {
                filtro = response;
            });

    actual = 0;
    cargarTablaConsultaClientes(filtro, actual);
    limpiarElemento();
}

function mostrarInactivosCliente(posicion) {
    let filtro = clientes.filter(cliente => cliente.estatus == 0);
    actual = posicion;
    cargarTablaConsultaClientes(filtro, actual);
    limpiarElemento();
}

function mostrarActivosCliente(posicion) {
    let filtro = clientes.filter(cliente => cliente.estatus == 1);
    actual = posicion;
    console.log(actual)
    cargarTablaConsultaClientes(filtro, actual);
    limpiarElemento();
}


//mostrar boton de modificar
function mostrarBotonModificarCliente() {
    const button = document.getElementById('modificar');
    if (clientes_element != null) {
        button.disabled = false;
    } else {
        button.disabled = true;
    }
    comprobarGuardarCliente();
}

//mostrar boton activar/eliminar
function mostrarBotonEliminarCliente() {

    const buttonReactivar = document.getElementById('reactivar');
    const buttonEliminar = document.getElementById('eliminar');

    if (clientes_element != null && clientes_element.estatus == 0) {
        buttonReactivar.style.display = "block";
    } else if (clientes_element != null && clientes_element.estatus == 1) {
        buttonEliminar.style.display = "block";
    } else {
        buttonReactivar.style.display = "none";
        buttonEliminar.style.display = "none";
    }

}

function limpiarElemento() {
    clientes_element = null;
    mostrarBotonModificarCliente();
    mostrarBotonEliminarCliente();
}

function selectedActivosCliente() {
    const valueInput = document.getElementById('switchInactivos');
    if (valueInput.checked) {
        return true;
    }
    return false;
}

function comprobarGuardarCliente() {
    clearMessage();
    if (clientes_element) {
        const button = document.getElementById('guardar');
        button.onclick = function () {modificarCliente()
        }

        document.getElementById('staticBackdropLabel').innerHTML = "Modificar cliente";
    } else {
        const button = document.getElementById('guardar');
        button.onclick = function () {
            agregarCliente()
        }

        document.getElementById('staticBackdropLabel').innerHTML = "Agregar cliente";
    }
}

// escuchadores de eventos
document.getElementById('search').addEventListener('keyup', () => {
    busquedaCliente();
});

function getRol() {
    const puesto = document.getElementById("cmbPuesto").value;
    if (puesto == "Administrador") {
        document.getElementById("cmbRol").value = "ADMS";
    } else if (puesto == "Empleado") {
        document.getElementById("cmbRol").value = "EMPS";
    }
}

//document.getElementById("cmbPuesto").addEventListener('change', () => {
//    getRol();
//});

//mostrar inactivos
document.getElementById('switchInactivos').addEventListener('change', () => {
    const valueInput = document.getElementById('switchInactivos');
    if (valueInput.checked) {
        mostrarInactivosCliente(0);
    } else {
        mostrarActivosCliente(0);
    }
});

// seleccionar elemento de la tabla
function addEventToRowsCliente() {
    document.querySelectorAll('#body-consulta tr').forEach(row => {
        row.addEventListener('click', () => {
            document.querySelectorAll('#body-consulta tr').forEach(row => {
                row.classList.remove('selected');
            });
            //obtener el elemento hijo td de la row
            let td = row.querySelector('td');
            let idCliente = td.innerHTML;
            row.classList.add('selected');
            consultarCliente(idCliente);
            mostrarBotonModificarCliente();
            mostrarBotonEliminarCliente();
        });
    });
}

document.addEventListener('DOMContentLoaded', () => {
    consultarClientes();
    mostrarActivosCliente();
});

// constantes

const INSERTAR = "insertar";
const MODIFICAR = "modificar";

// funciones largas

function enviarDataCliente(cliente, action) {

    let params = {c: JSON.stringify(cliente)}
//    alert(params);
    fetch(`${URL_BASE}/cliente/${action}?`, {
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
                    consultarClientes();
                } else {
                    Swal.fire("Error al guardar", data.result, "error");
                }
            })
            .catch(error => {
                Swal.fire({icon: "error", title: "Error", text: error});
            });
}

function agregarCliente() {


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
    const txtFechaRegistro = document.getElementById('txtFechaRegistro').value;
    const cmbEstatus = document.getElementById('cmbEstatus').value;


    let person = new persona(1, txtNombre, txtApellidoPaterno, txtApellidoMaterno, cmbGenero, formatDate(txtFechaNacimiento),
            txtRfc, txtCurp, txtDomicilio, txtCp, txtCiudad, txtEstado, txtTelefono, txtCodigoImagen);
    let client = new cliente(1, txtEmail, formatDate(txtFechaRegistro), 1, person);

    if (checkInputs()) {
        enviarDataCliente(client, INSERTAR);
        clearInputs('inputs');
        clearMessage();
    }

}

function rellenarInputsCliente() {
    // Escribe toda la lógica necesaria para hacer el llenado de los inputs

    document.getElementById('txtNombre').value = clientes_element.persona.nombre;
    document.getElementById('txtApellidoPaterno').value = clientes_element.persona.apellidoPaterno;
    document.getElementById('txtApellidoMaterno').value = clientes_element.persona.apellidoMaterno;
    document.getElementById('cmbGenero').value;
    document.getElementById('txtFechaNacimiento').value = clientes_element.persona.fechaNacimiento;
    document.getElementById('txtRfc').value = clientes_element.persona.rfc;
    document.getElementById('txtCurp').value = clientes_element.persona.curp;
    document.getElementById('txtDomicilio').value = clientes_element.persona.domicilio;
    document.getElementById('txtCp').value = clientes_element.persona.codigoPostal;
    document.getElementById('txtCiudad').value = clientes_element.persona.ciudad;
    document.getElementById('txtEstado').value = clientes_element.persona.estado;
    document.getElementById('txtTelefono').value = clientes_element.persona.telefono;
    document.getElementById('txtEmail').value = clientes_element.email;
    document.getElementById('txtFechaRegistro').value = clientes_element.fechaRegistro;
    document.getElementById('cmbEstatus').value = clientes_element.activo;

}

function modificarCliente() {

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

    const txtFechaRegistro = document.getElementById('txtFechaRegistro').value;

    const cmbEstatus = document.getElementById('cmbEstatus').value;


    //aqui va tu grandioso fetch, yo no lo hago porque ya lo tengo :b
    let person = new persona(clientes_element.persona.idPersona, txtNombre, txtApellidoPaterno, txtApellidoMaterno, cmbGenero, txtFechaNacimiento,
            txtRfc, txtCurp, txtDomicilio, txtCp, txtCiudad, txtEstado, txtTelefono, txtCodigoImagen);
    let client = new cliente(clientes_element.idCliente, txtEmail, txtFechaRegistro, 1, person);


    enviarDataCliente(client, MODIFICAR);
    clearInputs('inputs');

    //igualamos el elemento a null para que se deshabilite el boton y llamamos a la funcion
    limpiarElemento();
}

function eliminarCliente() {
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
            fetch(`${URL_BASE}/cliente/delete?id=${clientes_element.idCliente}`)
                    .then(response => response.json())
                    .then(data => {
                        if (data.result == "success") {
                            Swal.fire("¡Eliminado!", data.message, "success");
                            consultarClientes();
                            limpiarElemento();
                        } else {
                            Swal.fire("Error al Eliminar", data.result, "error");
                        }
                    });
        }
    });
}

async function reactivarCliente() {
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

            fetch(`${URL_BASE}/cliente/reactivar?id=${clientes_element.idCliente}`)
                    .then(response => response.json())
                    .then(data => {
                        if (data.result == "success") {
                            Swal.fire("¡Reactivado!", data.message, "success");
                            consultarClientes();
                        } else {
                            Swal.fire("Error al reactivar", data.result, "error");
                        }
                    }
                    );
            setTimeout(() => { mostrarInactivosCliente() }, 100)
        }
    });
}
