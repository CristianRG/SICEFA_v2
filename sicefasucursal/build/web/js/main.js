function getDatosUsuario(user){
    fetch(`${URL_BASE}/empleado/getempleado?id=${user.idUsuario}&user=${user.idUsuario}`)
    .then(response => response.json())
    .then(data => {
        let usuario = {
            userInfo: user,
            empleadoInfo: data
        };
        cargarDatosUsuario(usuario);
    });
}

function cargarDatosUsuario(infoUser){
    const divUsuario = document.getElementById('info-user');
    const nombreEmpleado = document.getElementById('username-sidebar');
    const info = `
                Puesto: ${infoUser.empleadoInfo.puesto} <br>
                Sucursal: ${infoUser.empleadoInfo.sucursal.nombre} <br>
                ID sucursal: ${infoUser.empleadoInfo.sucursal.idSucursal}
                `;
    console.log(infoUser);
    divUsuario.innerHTML = info;
    nombreEmpleado.innerHTML = `${infoUser.empleadoInfo.persona.nombre} ${infoUser.empleadoInfo.persona.apellidoPaterno} ${infoUser.empleadoInfo.persona.apellidoMaterno}`;  
}

function validar(){
    if(localStorage.getItem("usuario")){
        const usuario = JSON.parse(localStorage.getItem("usuario"));
        const params = {user:usuario.nombreUsuario,password:usuario.contrasenia};
        
        fetch(URL_BASE+"/login/sesion",{
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams(params)
        })
        .then(response => response.json())
        .then(response => {
            if(!response.result && response.nombreUsuario == usuario.nombreUsuario && response.contrasenia == usuario.contrasenia){
                //Swal.fire(`¡Bienvenido ${response.nombreUsuario}`, "", "success");
                getDatosUsuario(response);
            }
            else{
                window.location.href = "./login.html";
                // console.log(response)
            }
        });
    }else{
        window.location.href = "./login.html";
    }
    
}

function signOut(){
    
    Swal.fire({
        title: "¿Desea salir del sistema?",
        
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Salir"
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire({
            title: "Saliendo del sistema...",
            text: "Cerrando sesión, por favor no cierre la ventana...",
            showConfirmButton: false,
            timer: 2000,
            
          });
          setTimeout(()=>{
            localStorage.removeItem("usuario");
            validar();
          },2000);
        }
      });
}

window.addEventListener('DOMContentLoaded',()=>{validar();});

//sidebar

function observerSideBar(width){
    const sidebarUserName = document.getElementById('user');
    const infoUser = document.getElementById('info-user');
    if(width == 176){
        document.getElementById('username-sidebar').addEventListener('click', ()=>{      
            if(sidebarUserName.clientHeight == 48){
                sidebarUserName.style.height = "10rem";
                infoUser.style.display = "flex";        
            }
            else{
                sidebarUserName.style.height = "3rem";
                infoUser.style.display = "none";        
            }
            //console.log(sidebarUserName.clientHeight)
        });
    }else{
        sidebarUserName.style.height = "3rem";
        infoUser.style.display = "none";
    }
}


const observer = new ResizeObserver(function(entries) {
    entries.forEach(function(entry){
        //console.log(entry.target.clientWidth);
        observerSideBar(entry.target.clientWidth);
    });
});

const elementoObservable = document.getElementById('side-bar');
observer.observe(elementoObservable);