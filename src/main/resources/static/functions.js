//Generar las previsualizaciones
function createPreview(file, cont) {
    console.log("Esta entrando a esta funcion");
    var imgCodified = URL.createObjectURL(file);

    /*const img= document.createElement('p');
    img.innerText="La concha de tu madre oldboys";
    document.getElementById('img-list').appendChild(img);*/
    
    var img = $('<div class="images-content"><img class="img-preview" src="' + imgCodified + '" alt=""><div class="delete"><span><i class="fa-solid fa-circle-xmark"></i></span></div></div>');
    if (cont == 0) {
        let divis = document.createElement('div');
        divis.classList.add("images-content");
        document.getElementById('img-list').appendChild(divis);
    }

    $(img).insertBefore(".images-content");

}