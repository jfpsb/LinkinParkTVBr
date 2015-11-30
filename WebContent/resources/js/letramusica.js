function loadDoc() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState == 4 && xhttp.status == 200) {
      document.getElementById("div").innerHTML = xhttp.responseText;
    }
  };
  
  xhttp.open("GET", "papercut.txt", true);
  xhttp.send();
}