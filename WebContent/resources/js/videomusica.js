var videoPath = "../../videos/";

function loadVideo(album, musica) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState == 4 && xhttp.status == 200) {
      document.getElementById("videoDiv").innerHTML = xhttp.responseText;
    }
  };
  
  xhttp.open("GET", videoPath + album + "/" + musica + ".txt", true);
  xhttp.send();
}

function onVideoDialogClose() {
	document.getElementById("videoDiv").innerHTML = "Não há vídeo disponível.";
}