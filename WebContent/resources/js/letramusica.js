function loadDoc() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState == 4 && xhttp.status == 200) {
      document.getElementById("textArea").innerHTML = xhttp.responseText;
    }
  };
  
  xhttp.open("GET", "letra/papercut.txt", true);
  xhttp.send();
}