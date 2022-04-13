
console.log("Entroooo");
const foto = document.getElementById('foto');
const portada = document.getElementById('cambiarPortada');

portada.addEventListener('click', function () {

    document.getElementById('inputP').click();

    document.getElementById('inputP').addEventListener('change', function () {
        let file = this.files;
        var element = file[0];
        var supportedImages = ["image/jpeg", "image/png"];

        if (supportedImages.indexOf(element.type) != -1) {
            document.getElementById('cambiarP').click();
        } else {
            alert("El tipo de archivo no es valido");
        }
    });
});


foto.addEventListener('click', function () {
    document.getElementById('inputF').click();

    document.getElementById('inputF').addEventListener('change', function () {
        let file = this.files;
        var supportedImages = ["image/jpeg", "image/png"];
        var element = file[0];
        if (supportedImages.indexOf(element.type) != -1) {
            console.log("Entro a esta condicion");
            document.getElementById('cambiarF').click();
        } else {
            alert("El tipo de archivo no es valido");
        }
    });

});





/*const inputM = ()=>{
    inputkey();
}*/









