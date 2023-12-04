// variables de control para la paginación
let MAX = 0;
let MIN = 0;
let actual = 0;

// variable para guardar los elementos de la tabla
let compras = [];
// variable para guardar un elemento en especifico
let compras_element = null;


function consultarPedidos() {
    fetch(`${URL_BASE}/compra/getall`)
    .then(response => response.json())
    .then(data => {
        MAX = data.length;
        compras = data;
        
        cargarTablaConsulta(data, actual);
    });
}

function consultarCompra(idCompra){
    compras.forEach(compra => {
        if(compra.idCompra == idCompra){
            compras_element = compra;
        }
    });
}

function eliminarDuplicadosYSumarPropiedad(arr) {
    const resultado = arr.reduce((acumulador, objeto) => {
        const indiceExistente = acumulador.findIndex(item => item["idCompra"] === objeto["idCompra"]);

        if (indiceExistente !== -1) {
            // Sumar la propiedad 'cantidad' si el objeto ya existe
            acumulador[indiceExistente].total += objeto.total;
        } else {
            // Agregar el objeto al acumulador si no existe
            acumulador.push({ ...objeto });
        }

        return acumulador;
    }, []);

    return resultado;
}

function cargarTablaConsulta(listaElementos, pagina) {
    const tbody = document.getElementById('body-consulta');
    const pagination = pagina * 8;
    let contenido = '';
    // listaElementos = listaElementos.filter((valor, indice, self) => 
    //     self.findIndex(item => item["idCompra"] === valor["idCompra"]) === indice
    // );
    listaElementos = eliminarDuplicadosYSumarPropiedad(listaElementos);
    for (let i = pagination; i < pagination + 8; i++) {
        if(listaElementos[i]){
            let estatus = estatusPedido(listaElementos[i]);
            contenido += `
            <tr>
                <td id='idCompra'>${listaElementos[i].idCompra}</td>
                <td>${listaElementos[i].fechaPedido}</td>
                <td>${listaElementos[i].sucursal}</td>
                <td>${listaElementos[i].idEmpleado}</td>
                <td>${estatus}</td>
                <td>${Math.round(listaElementos[i].total)}</td>
            </tr>
            `
        }
    }
    tbody.innerHTML = contenido;
    addEventToRows();
}

function estatusPedido(compra){
    if(compra.estatus == 0){
        return "Cancelada";
    }
    else if(compra.estatus == 1){
        return "Pendiente";
    }else if(compra.estatus == 2){
        return "Atendida";
    }
}

function changePage(index){
    if(index == -1 && actual > 0){
        actual--;
        if(selectedActivos){
            mostrarActivos();
        }else{
            mostrarInactivos();
        }
        // cargarTablaConsulta(compras, actual);
    }
    else if(index == 1 && actual < parseInt(MAX/8)){
        actual++;
        if(selectedActivos){
            mostrarActivos();
        }else{
            mostrarInactivos();
        }
        //cargarTablaConsulta(compras, actual);
    }
    limpiarElemento();
}

function busqueda(){
    let busqueda = document.getElementById('search').value;
    let filtro = compras.filter(compra => {
        
        // variables para realizar la busqueda

        let nombre = compra.sucursal.toLowerCase();
        let producto = compra.producto.toLowerCase();
        let estatus = compra.estatus;

        return nombre.includes(busqueda) || producto.includes(busqueda);
    });
    actual = 0;
    cargarTablaConsulta(filtro, actual);
    limpiarElemento();
}

function mostrarInactivos(){
    let filtro = compras.filter(compra => compra.estatus == 0);
    actual = 0;
    cargarTablaConsulta(filtro, actual);
    limpiarElemento();
}

function mostrarActivos(){
    let filtro = compras.filter(compra => compra.estatus == 1);
    actual = 0;
    cargarTablaConsulta(filtro, actual);
    limpiarElemento();
}

function agregar(){
    // Y la de chambear no te la sabes? Pon tu codigo weon
}

function rellenarInputs(){
    // Escribe toda la lógica necesaria para hacer el llenado de los inputs
}

function mostrarConsulta(){
    //aqui va tu grandioso fetch, yo no lo hago porque ya lo tengo :b
    const tbody = document.getElementById('body-consulta-pedido');
    // const pagination = pagina * 8;
    let contenido = '';

    compras.forEach(compra => {
        if(compra.idCompra == compras_element.idCompra){
            contenido += `
                    <tr>
                        <td id='idCompra'>${compra.idProducto}</td>
                        <td>${compra.producto}</td>
                        <td>${compra.sucursal}</td>
                        <td>${compra.cantidad}</td>
                        <td>${compra.precioCompra}</td>
                        <td>${Math.round(compra.total)}</td>
                    </tr>
                    `
        }
    });
    
    tbody.innerHTML = contenido;
    //igualamos el elemento a null para que se deshabilite el boton y llamamos a la funcion
    limpiarElemento();
}

function eliminar() {
    // comprobación
    Swal.fire({
        title: "¿Desea cancelar este registro?",
        text: "Al cancelar el estatus de la compra pasara a ser Cancelada",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Cancelar"
    }).then((result) => {
        if (result.isConfirmed) {
            console.log(compras_element.idCompra)
            fetch(`${URL_BASE}/compra/cancelar?id=${compras_element.idCompra}`)
                .then(response => response.json())
                .then(data => {
                    if (data.result == "success") {
                        Swal.fire("¡Cancelada!", data.message, "success");
                        consultarPedidos();
                        limpiarElemento();
                    } else {
                        Swal.fire("Error al cancelar", data.result, "error");
                    }
                });
        }
    });
}

async function reactivar() {
    // comprobación
    Swal.fire({
        title: "¿Desea atender este registro?",
        text: "Al atender el estatus de la compra pasara a ser Atendida",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Atender"
    }).then((result) => {
        if (result.isConfirmed) {

            fetch(`${URL_BASE}/compra/atender?id=${compras_element.idCompra}`)
                .then(response => response.json())
                .then(data => {
                    console.log(data)
                    if (data.result == "success") {
                        Swal.fire("¡Atendida!", data.message, "success");
                        consultarPedidos();
                    } else {
                        Swal.fire("Error al atender", data.result, "error");
                    }
                }
            );
            setTimeout(()=>{mostrarInactivos()},100)
        }
    });
}


//mostrar boton de modificar
function mostrarBotonModificar(){
    const button = document.getElementById('modificar');
    if(compras_element != null){
        button.disabled = false;
    }else {
        button.disabled = true;
    }
}

//mostrar boton activar/eliminar
function mostrarBotonEliminar(){
    
    const buttonReactivar = document.getElementById('reactivar');
    const buttonEliminar = document.getElementById('eliminar');

    if (compras_element != null && compras_element.estatus == 0) {
        buttonReactivar.style.display = "block";
    } else if (compras_element != null && compras_element.estatus == 1) {
        buttonEliminar.style.display = "block";
    } else {
        buttonReactivar.style.display = "none";
        buttonEliminar.style.display = "none";
    }
}

function limpiarElemento(){
    compras_element = null;
    mostrarBotonModificar();
    mostrarBotonEliminar();
}

function selectedActivos(){
    const valueInput = document.getElementById('switchInactivos');
    if(valueInput.checked){
        return true;
    }
    return false;
}

// escuchadores de eventos
document.getElementById('search').addEventListener('keyup', ()=>{
    busqueda();
});

//mostrar inactivos
document.getElementById('switchInactivos').addEventListener('change', ()=>{
    const valueInput = document.getElementById('switchInactivos');
    if(valueInput.checked){
        mostrarInactivos();
    }else{
        mostrarActivos();
    }
});

// seleccionar elemento de la tabla
function addEventToRows(){
    document.querySelectorAll('#body-consulta tr').forEach(row => {
        row.addEventListener('click', ()=>{
            document.querySelectorAll('#body-consulta tr').forEach(row => {
                row.classList.remove('selected');
            });
            //obtener el elemento hijo td de la row
            let td = row.querySelector('td');
            let idCompra = td.innerHTML;
            row.classList.add('selected');
            consultarCompra(idCompra);
            mostrarBotonModificar();
            mostrarBotonEliminar();
        });
    });
}

document.addEventListener('DOMContentLoaded',()=>{
    consultarPedidos() 
    mostrarActivos();
});