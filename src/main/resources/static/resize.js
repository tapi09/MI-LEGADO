
function resizE() {
    console.log("Entro a resize");
    const textarea = document.getElementById("textareaP");

    textarea.addEventListener("keyup", e => {
	    console.log("Entro a text");
        textarea.style.height = "46px";
        let scHeight = e.target.scrollHeight;
        textarea.style.height = `${scHeight}px`;

    });
}

const resizeText= ()=>{
    resizE();
};