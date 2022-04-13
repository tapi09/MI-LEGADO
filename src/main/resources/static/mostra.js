function mostrarse() {
    const mostrar = document.getElementById('mostrar');
    const ocultar = document.getElementById('ocultar')
    mostrar.addEventListener('click', function () {

        let boxComents = document.getElementById('box-coments');
        boxComents.style.height = "auto";
        boxComents.style.transition = "all 0.5s linear"
    });

    ocultar.addEventListener('click', function () {

        let boxComents = document.getElementById('box-coments');
        boxComents.style.height = "0";
    });
}


const mostrarC = ()=>{
    mostrarse();
}