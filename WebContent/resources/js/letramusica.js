function loadTraducao(musica) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState == 4 && xhttp.status == 200) {
      document.getElementById("tradTextArea").innerHTML = xhttp.responseText;
    }
  };
  
  xhttp.open("GET", "letra/traducao/" + musica + ".txt", true);
  xhttp.send();
}

function loadOriginal(musica) {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (xhttp.readyState == 4 && xhttp.status == 200) {
	      document.getElementById("origTextArea").innerHTML = xhttp.responseText;
	    }
	  };
	  
	  xhttp.open("GET", "letra/original/" + musica + ".txt", true);
	  xhttp.send();
	}

function onDialogClose() {
	document.getElementById("tradTextArea").innerHTML = "<p>Aguarde...</p>";
	document.getElementById("origTextArea").innerHTML = "<p>Aguarde...</p>";
}