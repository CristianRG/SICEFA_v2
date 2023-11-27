function formatDate(date){
    let parts = date.split('-');
    return `${parts[2]}/${parts[1]}/${parts[0]}`;
}

function clearInputs(HTMLElementId){
    document.querySelectorAll(`.${HTMLElementId} input`).forEach(element => {
        element.value = "";
    });
    limpiarElemento()
}

// function changeToPersonalData(){
//     const employeeData = document.getElementById("employee-data");
//     const personalDataForm = document.getElementById("personal-data");
//     if(!(employeeData.style.display == 'none')){
//         employeeData.style.display = "none";
//         personalDataForm.style.display = "block";
//     }
// }

// function changeToEmployeeData(){
//     const employeeData = document.getElementById("employee-data");
//     const personalDataForm = document.getElementById("personal-data");
//     if(!(personalDataForm.style.display == 'none')){
//         employeeData.style.display = "block";
//         personalDataForm.style.display = "none";
//     }
// }

// const buttonChangeToPersonalData = document.getElementById("change-personal").addEventListener('click', ()=>{
//     changeToPersonalData();
// });
// const buttonChangeToEmployeeData = document.getElementById("change-employee").addEventListener('click', ()=>{
//     changeToEmployeeData();
// });

function checkInputs(){
    let emptyInputs = 0;
    const message = document.getElementById("message");
    document.querySelectorAll(".inputs input").forEach(element => {
        if(element.value == ""){
            emptyInputs += 1;
        }
    });

    if(emptyInputs>0){
        if(message.classList.contains("right")){message.classList.remove("right");}
        message.classList.add("danger");
        message.innerHTML = `Existen ${emptyInputs} campos sin rellenar`;
        return false;
    }
    if(message.classList.contains == "danger"){message.classList.remove("danger");}
    message.classList.add("right");
    return true;
}

function clearMessage(){
    const message = document.getElementById("message");
    message.innerHTML = "";
}

// function changeButtonModifyToSave(){
//     const buttonSave = document.getElementById('send');
//     const buttonModify = document.getElementById('modify');
//     if(buttonSave.style.display == "none"){
//         buttonSave.style.display = "block";
//         buttonModify.style.display = "none";
//     }
// }

// function changeButtonSaveToModify(){
//     const buttonSave = document.getElementById('send');
//     const buttonModify = document.getElementById('modify');
//     if(!(buttonModify.style.display == "none")){
//         buttonModify.style.display = "block";
//         buttonSave.style.display = "none";
//     }
//     else if(buttonModify.style.display == "none"){
//         buttonModify.style.display = "block";
//         buttonSave.style.display = "none";
//     }
//     clearMessage();
// }