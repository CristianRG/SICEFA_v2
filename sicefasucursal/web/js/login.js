function login(){
    const user = document.getElementById('user').value;
    const password = document.getElementById('password').value;
    const params = {user:user,password:password};
        
        fetch(URL_BASE+"/login/sesion",{
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams(params)
        })
        .then(response => response.json())
        .then(response => {
            if(!response.result && response.nombreUsuario == user && response.contrasenia == password){
                localStorage.setItem("usuario",JSON.stringify(response));
                window.location.href = "./sistem.html";
            }
            else{
                Swal.fire({
                    position: 'middle',
                    text: 'Credenciales invalidas. Intentalo de nuevo',
                    showConfirmButton: false,
                    timer: 2000,
                    customClass: {
                        popup: 'small-popup'
                    }
                  });
            }
        });
}

document.getElementById("submit").addEventListener("click", function(event){
    event.preventDefault();
    login();
});

document.getElementById("cancelar").addEventListener("click", function(event){
    event.preventDefault();
    window.location.href = "../index.html";
});