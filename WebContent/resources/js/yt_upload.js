var channelName = "lptvbrasil";
var chave = "AIzaSyBur2TK-5uqq1egEcuhVVsLYIrJEQy1O4A";

$(document).ready(function() {
	$.get(
		"https://www.googleapis.com/youtube/v3/channels",{
			part: 'contentDetails',
			forUsername: channelName,
			key: chave
		}, 
		function(data) {
			$.each(data.items, function(i, item){
				console.log(item);
				pid = item.contentDetails.relatedPlaylists.uploads;
				getVideos(pid);
			})
		}
	);
	
	function getVideos(pid) {
		$.get(
				"https://www.googleapis.com/youtube/v3/playlistItems",{
					part: "snippet",
					maxResults: 16,
					playlistId: pid,
					key: chave
				}, 
				function(data) {
					var output;
					var button;
					$.each(data.items, function(i, item){
						console.log(item);
						videoTitle = item.snippet.title;
						videoThumb = item.snippet.thumbnails.maxres.url;
						videoUrl = "http://www.youtube.com/watch/" + item.snippet.resourceId.videoId;
						
						output = "<li>";
						button = "<a href=" + videoUrl + " target=\"_blank\" style=\"color: white; font-family: 'Courier New', Courier, monospace; " +
								"width: 100%; height: 70%; background-image: url(" + videoThumb 
						+ "); background-size:cover; display: block; margin-left: auto; margin-right: auto; border-radius: 5px; \"/>";
						output = output + button;
						output = output + "<p style='font-size: 70%;'>" + videoTitle + "</p>" +"</li>";
						
						//Append to results list
						$("#lista").append(output);
					})
				}
			);
	}
});