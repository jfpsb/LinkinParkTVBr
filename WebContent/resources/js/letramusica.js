var letraPathTrad = "../../letras/traducao/";
var letraPathOrig = "../../letras/original/";

function loadLetra(album, musica) {	
	loadTraducao(album, musica);
	loadOriginal(album, musica);	
} 

function loadTraducao(album, musica) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState == 4 && xhttp.status == 200) {
      document.getElementById("tradTextArea").innerHTML = xhttp.responseText;
    }
  };
  
  xhttp.open("GET", letraPathTrad + album + "/" + musica + ".txt", true);
  xhttp.send();
}

function loadOriginal(album, musica) {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (xhttp.readyState == 4 && xhttp.status == 200) {
	      document.getElementById("origTextArea").innerHTML = xhttp.responseText;
	    }
	  };
	  
	  xhttp.open("GET", letraPathOrig + album + "/" + musica + ".txt", true);
	  xhttp.send();
	}

function onDialogClose() {
	document.getElementById("tradTextArea").innerHTML = "<p>Aguarde...</p>";
	document.getElementById("origTextArea").innerHTML = "<p>Aguarde...</p>";
}