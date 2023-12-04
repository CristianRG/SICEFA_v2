// variables de control para la paginación
let MAX = 0;
let MIN = 0;
let actual = 0;

// variable para guardar los elementos de la tabla
let sucursales = [];
// variable para guardar un elemento en especifico
let sucursales_element = null;


function consultarSucursales() {
    fetch(URL_BASE+'/sucursal/getAllSuc')
    .then(response => response.json())
    .then(data => {
        MAX = data.length;
        sucursales = data;
        cargarTablaConsulta(data, actual);
    });
}

function consultarSucursal(idSucursal){
    sucursales.forEach(sucursal => {
        if(sucursal.idSucursal == idSucursal){
            sucursales_element = sucursal;
        }
    });
}

function cargarTablaConsulta(listaElementos, pagina) {
    const tbody = document.getElementById('tbSucursales');
    const pagination = pagina * 8;
    let contenido = '';
    for (let i = pagination; i < pagination + 8; i++) {
        if(listaElementos[i]){
            contenido += `
                    <tr>
                        <td id='idSucursal'>${listaElementos[i].idSucursal}</td>
                        <td>${listaElementos[i].nombre}</td>
                        <td>${listaElementos[i].titular}</td>
                        <td>${listaElementos[i].rfc}</td>
                        <td>${listaElementos[i].domicilio}</td>
                        <td>${listaElementos[i].colonia}</td>
                        <td>${listaElementos[i].codigoPostal}</td>
                        <td>${listaElementos[i].telefono}</td>
                        <td>${listaElementos[i].ciudad}</td>
                        <td>${listaElementos[i].estado}</td>
                        <td>${listaElementos[i].latitud}</td>
                        <td>${listaElementos[i].longitud}</td>
                    </tr>
                    `;
        }
    }
    tbody.innerHTML = contenido;
    addEventToRows();
}

function changePage(index){
    if(index == -1 && actual > 0){
        actual--;
        cargarTablaConsulta(sucursales, actual);
    }
    else if(index == 1 && actual < parseInt(MAX/8)){
        actual++;
        cargarTablaConsulta(sucursales, actual);
    }
    limpiarElemento();
}

function busqueda(){
    let busqueda = document.getElementById('searchInput').value;
    let filtro = sucursales.filter(sucursal => {
        
        // variables para realizar la busqueda

        let nombre = sucursal.nombre.toLowerCase();
        let titular = sucursal.titular.toLowerCase();
        

        return nombre.includes(busqueda) || titular.includes(busqueda);
    });
    actual = 0;
    cargarTablaConsulta(filtro, actual);
    limpiarElemento();
}

function mostrarInactivos(){
    let filtro = sucursales.filter(sucursal => sucursal.estatus == 0);
    actual = 0;
    cargarTablaConsulta(filtro, actual);
    limpiarElemento();
}
function mostrarActivos(){
    let filtro = sucursales.filter(sucursal => sucursal.estatus == 1);
    actual = 0;
    cargarTablaConsulta(filtro, actual);
    limpiarElemento();
}
consultarSucursales();

function modificar(){
    //aqui va tu grandioso fetch, yo no lo hago porque ya lo tengo :b

    //igualamos el elemento a null para que se deshabilite el boton y llamamos a la funcion
    limpiarElemento();
}

//mostrar boton de modificar
function mostrarBotonModificar(){
    const button = document.getElementById('modificar');
    if(sucursales_element != null){
        button.disabled = false;
        button.onclick=function() {
            modificarSucursal();
        };

    }else {
        button.disabled = true;
    }
}

function mostrarBotonMapa(map){
    const button = document.getElementById('mapa');
    if(sucursales_element != null){
        button.disabled = false;
        

    }else {
        button.disabled = true;
    }
}

function cargarMapa2() {
    //Paso 1: Hay que inicializar la comunicación con el API, mediante el objeto platform
    // Cada quien coloca su propia APIKEY 
    
    var platform = new H.service.Platform({
        apikey: 'gLKpleYmR7nM4mi6a3e2eioWIc-gLJuLXaI-DCaa5Uw'
    });
    var defaultLayers = platform.createDefaultLayers();

    //Paso 2: Se inicializa y crea el objeto map; el cual centras, en este caso sobre CDMX
    // y tiene un zoom de lo amplio que se quiera la vista del mapa
    var map = new H.Map(document.getElementById('mapa2'),
            defaultLayers.vector.normal.map, {
                center: {lat: sucursales_element.latitud, lng: sucursales_element.longitud},
                zoom: 4,
                pixelRatio: window.devicePixelRatio || 1
            });
    // Se agrega un escucha para permitir el cambio de tamaño del mapa
    window.addEventListener('resize', () => map.getViewPort().resize());

    //Paso 3: Se hace el mapa interactivo
    // Se habilitan los evento en el mapa >MapEvents 
    // El objeto Behavior implementa las interacciones por defecto (incluso los eventos en dispositivos moviles)
    var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

    // Crea un objeto de UI; 
    // Esta función crea la interfaz de usuario predeterminada 
    // que incluye el control de zoom, el control de configuración del mapa
    var ui = H.ui.UI.createDefault(map, defaultLayers);
    //Se llama a la función que añadirá los marcadores al mapa
    agregarMarcadores(map);
    
}

function agregarMarcadores(map ) {
    var lat1 = sucursales_element.latitud; var lng1 = sucursales_element.longitud;  //Leon
    
    
//    const coordenadas1 = {lat:21.116667,lng:-101.683334};
//    var marcador = new H.map.Marker(coordenadas1);
//    mapa.addObject(marcador);
    
    //Se crea el objeto del marcador, con los datos de las ubicaciones
    var marcador1 = new H.map.Marker({lat: lat1, lng: lng1});
    //Se añade el marcador al mapa
    map.addObject(marcador1);

}
//mostrar boton activar/eliminar
function mostrarBotonEliminar(){
    
    const button = document.getElementById('reactivar');
    if(sucursales_element!=null && sucursales_element.estatus == 0){
        button.innerHTML = "Reactivar";
        button.style.display = "block";
        button.onclick=function() {
            reactivarSucursal(sucursales_element.idSucursal);
        };
    }else if(sucursales_element!=null && sucursales_element.estatus == 1){
        button.onclick=function() {
            eliminarSucursal(sucursales_element.idSucursal);
        };
        button.innerHTML = "Eliminar";
        button.style.display = "block";
       
    }else{
        button.style.display = "none";
    }
    
}
function reactivarSucursal(sucursal) {
    let idSucursal = sucursal;
    fetch(URL_BASE+"/sucursal/reactivarSuc?idS=" + idSucursal)
            .then(response => response.json())
            .then(response => {
                if (response.result) {
                    Swal.fire(response.result, "Sucursal reactivada");
                    //alert("Empleado eliminado");
                } else {
                    Swal.fire(response.error, "Error de reactivacion");
                    //alert("Error al eliminar");
                }
              consultarSucursales();
                limpiarElemento();
            });
}
function limpiarElemento(){
    sucursales_element = null;
    mostrarBotonModificar();
    mostrarBotonEliminar();
    mostrarBotonMapa();
}

// escuchadores de eventos
document.getElementById('searchInput').addEventListener('keyup', ()=>{
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

const modal = document.getElementById('modifimodal');
const botonModificar = document.getElementById('modificar');

botonModificar.addEventListener('click', () => {
  modal.style.display="block";
});

// seleccionar elemento de la tabla
function addEventToRows(){
    document.querySelectorAll('#tbSucursales tr').forEach(row => {
        row.addEventListener('click', ()=>{
            document.querySelectorAll('#tbSucursales tr').forEach(row => {
                row.classList.remove('selected');
            });
            //obtener el elemento hijo td de la row
            let td = row.querySelector('td');
            let idVenta = td.innerHTML;
            row.classList.add('selected');
            consultarSucursal(idVenta);
            mostrarBotonModificar();
            mostrarBotonEliminar();
            mostrarBotonMapa();
        });
    });
}
function guardarSuc(){
    
    
    let nom= document.getElementById("txtNombre").value;
    let tit= document.getElementById("txtTitular").value;
    let rfc= document.getElementById("txtRfc").value;
    let dom= document.getElementById("txtDomicilio").value;
    let col= document.getElementById("txtColonia").value;
    let cp= document.getElementById("txtCp").value;
    let ciu= document.getElementById("txtCiudad").value;
    let est= document.getElementById("txtEstado").value;
    let tel= document.getElementById("txtTelefono").value;
    let lat= document.getElementById("txtLatitud").value;
    let lon= document.getElementById("txtlongitud").value;
    
    let sucursal={
        "nombre":nom,
        "titular":tit,
        "rfc":rfc,
        "domicilio":dom,
        "colonia":col,
        "codigoPostal":cp,
        "ciudad":ciu,
        "estado":est,
        "telefono":tel,
        "latitud":lat,
        "longitud":lon};
    
    let params = { s: JSON.stringify(sucursal) };
    let ruta = URL_BASE+"/sucursal/insertSuc"; // Cambiar la ruta según tu configuración

    fetch(ruta,
        {
            method: "POST",
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
            body: new URLSearchParams(params)
        })
        .then(response => response.json())
        .then(response => {
            if (response.result)
                Swal.fire("Inserccion correcta", response.result, "success");
            if (response.error)
                Swal.fire("Problemas al insertar", response.error, "error");
            consultarSucursales();
            console.log(response);
        });

    console.log(JSON.stringify(sucursal));
//    alert(JSON.stringify(sucursal));
}
function eliminarSucursal(sucursal) {
    let idSucursal = sucursal;
    fetch(URL_BASE+"/sucursal/deleteSuc?idS=" + idSucursal)
            .then(response => response.json())
            .then(response => {
                if (response.result) {
                    Swal.fire(response.result, "Sucursal eliminada");
            consultarSucursales();
                    //alert("Empleado eliminado");
                } else {
                    Swal.fire(response.error, "Error de eliminacion");
                    //alert("Error al eliminar");
                }
                limpiarElemento();
            });
}
function modificarSucursal() {
    // Datos persona
    document.getElementById("txtidSucursal2").value= sucursales_element.idSucursal;
    document.getElementById("txtNombre2").value = sucursales_element.nombre;
    document.getElementById("txtTitular2").value = sucursales_element.titular;
    document.getElementById("txtRfc2").value = sucursales_element.rfc;
    document.getElementById("txtDomicilio2").value = sucursales_element.domicilio;
    document.getElementById("txtColonia2").value = sucursales_element.colonia;
    document.getElementById("txtCp2").value = sucursales_element.codigoPostal;
    document.getElementById("txtCiudad2").value = sucursales_element.ciudad;
    document.getElementById("txtEstado2").value = sucursales_element.estado;
    document.getElementById("txtTelefono2").value = sucursales_element.telefono;
    document.getElementById("txtLatitud2").value = sucursales_element.latitud;
    document.getElementById("txtlongitud2").value = sucursales_element.longitud;
    
    
    
}
function guardarCambiosSuc(){
    
    let idS= document.getElementById("txtidSucursal2").value;
    let nom= document.getElementById("txtNombre2").value;
    let tit= document.getElementById("txtTitular2").value;
    let rfc= document.getElementById("txtRfc2").value;
    let dom= document.getElementById("txtDomicilio2").value;
    let col= document.getElementById("txtColonia2").value;
    let cp= document.getElementById("txtCp2").value;
    let ciu= document.getElementById("txtCiudad2").value;
    let est= document.getElementById("txtEstado2").value;
    let tel= document.getElementById("txtTelefono2").value;
    let lat= document.getElementById("txtLatitud2").value;
    let lon= document.getElementById("txtlongitud2").value;
    
    let sucursal= {"idSucursal":idS,
        "nombre":nom,
        "titular":tit,
        "rfc":rfc,
        "domicilio":dom,
        "colonia":col,
        "codigoPostal":cp,
        "ciudad":ciu,
        "estado":est,
        "telefono":tel,
        "latitud":lat,
        "longitud":lon};
    
    let params = { s: JSON.stringify(sucursal) };
    let ruta = URL_BASE+"/sucursal/updateSuc"; // Cambiar la ruta según tu configuración

    fetch(ruta,
        {
            method: "POST",
            headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
            body: new URLSearchParams(params)
        })
        .then(response => response.json())
        .then(response => {
            if (response.result)
                Swal.fire("Actualización correcta", response.result, "success");
            if (response.error)
                Swal.fire("Problemas al actualizar", response.error, "error");
            consultarSucursales();
            console.log(response);
        });

    console.log(JSON.stringify(sucursal));
//    alert(JSON.stringify(sucursal));
}
