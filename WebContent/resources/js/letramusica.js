function loadDoc(musica) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState == 4 && xhttp.status == 200) {
      document.getElementById("textArea").innerHTML = xhttp.responseText;
    }
  };
  
  xhttp.open("GET", "letra/" + musica + ".txt", true);
  xhttp.send();
}

function onDialogClose() {
	document.getElementById("textArea").innerHTML = "<p>Aguarde...</p>";
}