(function (){
    

    //Abrir el inspector de archivos
    var addImage= document.getElementById('add-image');
    addImage.addEventListener('click', function(){
       document.getElementById('add-new-photo').click();
    }); 

     //Capturamos el evento change
     document.getElementById('add-new-photo').addEventListener('change', function(){
        console.log(this.files);
        var files = this.files;
        var element;
        var supportedImages=["image/jpeg", "image/png", "image/gif"];
        var seEncontraronElementoNoValidos =  false;
        var contador=0;
        for(var i = 0 ; i < files.length; i++) {
            element = files[i];
            console.log("Entro a este bucle");
            console.log(element);
            if (supportedImages.indexOf(element.type) != -1){
                createPreview(element, contador);
            }else{
                seEncontraronElementoNoValidos = true;
            }
            contador++;
        }

       /* if(seEncontraronElementoNoValidos){
            alert("Se encontraron archivos no validos.");
        }else{
            alert("Todos los archivos se subieron correctamente.");
        }*/
   });
    
   
 
})();
