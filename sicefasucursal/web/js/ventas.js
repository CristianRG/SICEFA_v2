// variables de control para la paginación
let MAX = 0;
let MIN = 0;
let actual = 0;

// variable para guardar los elementos de la tabla
let ventas = [];
// variable para guardar un elemento en especifico
let ventas_element = null;


function consultarVentas() {
    fetch(`${URL_BASE}/venta/getall`)
    .then(response => response.json())
    .then(data => {
        MAX = data.length;
        ventas = data;
        mostrarActivos();
        // cargarTablaConsulta(data, actual);
    });
}

function consultarVenta(idVenta){
    ventas.forEach(venta => {
        if(venta.idVenta == idVenta){
            ventas_element = venta;
        }
    });
}

function cargarTablaConsulta(listaElementos, pagina) {
    const tbody = document.getElementById('body-consulta');
    const pagination = pagina * 8;
    let contenido = '';
    for (let i = pagination; i < pagination + 8; i++) {
        if(listaElementos[i]){
            contenido += `
                    <tr>
                        <td id='idVenta'>${listaElementos[i].idVenta}</td>
                        <td>${listaElementos[i].nombre}</td>
                        <td>${listaElementos[i].cantidad}</td>
                        <td>${listaElementos[i].precio}</td>
                        <td>${listaElementos[i].empleado}</td>
                        <td>${listaElementos[i].cliente}</td>
                        <td>${listaElementos[i].fecha}</td>
                        <td>${listaElementos[i].status}</td>
                        <td>$${listaElementos[i].total}</td>
                        
                    </tr>
                    `
        }
    }
    tbody.innerHTML = contenido;
    addEventToRows();
}

function changePage(index){
    if(index == -1 && actual > 0){
        actual--;
        if(selectedActivos){
            mostrarActivos();
        }else{
            mostrarInactivos();
        }
        // cargarTablaConsulta(ventas, actual);
    }
    else if(index == 1 && actual < parseInt(MAX/8)){
        actual++;
        if(selectedActivos){
            mostrarActivos();
        }else{
            mostrarInactivos();
        }
        //cargarTablaConsulta(ventas, actual);
    }
    limpiarElemento();
}

function busqueda(){
    let busqueda = document.getElementById('search').value;
    let filtro = ventas.filter(venta => {
        
        // variables para realizar la busqueda

        let nombre = venta.nombre.toLowerCase();
        let empleado = venta.empleado.toLowerCase();
        let cliente = venta.cliente.toLowerCase();

        return nombre.includes(busqueda) || empleado.includes(busqueda) || cliente.includes(busqueda);
    });
    actual = 0;
    cargarTablaConsulta(filtro, actual);
    limpiarElemento();
}

function mostrarInactivos(){
    let filtro = ventas.filter(venta => venta.status == 0);
    actual = 0;
    cargarTablaConsulta(filtro, actual);
    limpiarElemento();
}

function mostrarActivos(){
    let filtro = ventas.filter(venta => venta.status == 1);
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

function modificar(){
    //aqui va tu grandioso fetch, yo no lo hago porque ya lo tengo :b

    //igualamos el elemento a null para que se deshabilite el boton y llamamos a la funcion
    limpiarElemento();
}

//mostrar boton de modificar
function mostrarBotonModificar(){
    const button = document.getElementById('modificar');
    if(ventas_element != null){
        button.disabled = false;
    }else {
        button.disabled = true;
    }
}

//mostrar boton activar/eliminar
function mostrarBotonEliminar(){
    
    const button = document.getElementById('reactivar');
    if(ventas_element!=null && ventas_element.status == 0){
        button.innerHTML = "Reactivar";
        button.style.display = "block";
    }else if(ventas_element!=null && ventas_element.status == 1){
        button.innerHTML = "Eliminar";
        button.style.display = "block";
    }else{
        button.style.display = "none";
    }
    
}

function limpiarElemento(){
    ventas_element = null;
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
            let idVenta = td.innerHTML;
            row.classList.add('selected');
            consultarVenta(idVenta);
            mostrarBotonModificar();
            mostrarBotonEliminar();
        });
    });
}

document.addEventListener('DOMContentLoaded',()=>{
    consultarVentas() 
    mostrarActivos();
});