package search;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import entidade.Video;
import uploads.GetPlayListItems;
import youtube.Auth;

public class GetSearchItems {
	/**
	 * Arquivo que contém a chave de API necessária na aplicação para fazer
	 * chamadas à API.
	 */
	private static final String PROPERTIES_FILENAME = "youtube.properties";

	private static final long NUMBER_OF_VIDEOS_RETURNED = 15;

	/**
	 * Objeto do tipo Youtube para fazer as chamadas para a API do Youtube.
	 */
	private static YouTube youtube;

	public List<Video> getVideoSearch(String palavraChave) {
		// Lê a chave de API de arquivo
		Properties properties = new Properties();
		List<Video> listaVideos = new ArrayList<Video>();
		
		palavraChave = "Linkin Park " + palavraChave + " Live";
		
		System.out.println(palavraChave);

		try {
			InputStream in = GetPlayListItems.class.getResourceAsStream("../resources/" + PROPERTIES_FILENAME);
			properties.load(in);

		} catch (IOException e) {
			System.err.println(
					"Houve um erro ao ler " + PROPERTIES_FILENAME + ": " + e.getCause() + " : " + e.getMessage());
			System.exit(1);
		}

		try {
			// This object is used to make YouTube Data API requests. The last
			// argument is required, but since we don't need anything
			// initialized when the HttpRequest is initialized, we override
			// the interface and provide a no-op function.
			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
				public void initialize(HttpRequest request) throws IOException {
				}
			}).setApplicationName("LinkinParkTVBr").build();

			// Prompt the user to enter a query term.
			String queryTerm = palavraChave;

			// Define the API request for retrieving search results.
			YouTube.Search.List search = youtube.search().list("id, snippet");

			// Set your developer key from the {{ Google Cloud Console }} for
			// non-authenticated requests. See:
			// {{ https://cloud.google.com/console }}
			String apiKey = properties.getProperty("youtube.apikey");
			search.setKey(apiKey);
			search.setQ(queryTerm);

			// Restrict the search results to only include videos. See:
			// https://developers.google.com/youtube/v3/docs/search/list#type
			search.setType("video");

			// To increase efficiency, only retrieve the fields that the
			// application uses.
			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
			search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

			SearchListResponse searchResponse = search.execute();
			List<SearchResult> searchResultList = searchResponse.getItems();

			if (searchResultList != null) {
				// Define a list to store items in the list of uploaded videos.
				List<SearchResult> playlistItemList = new ArrayList<SearchResult>();

				playlistItemList.addAll(searchResultList);

				for (SearchResult item : playlistItemList) {
					Video v = new Video();

					if (item.getId().getKind().equals("youtube#video")) {
						v.setNome(item.getSnippet().getTitle());
						v.setVideoId(item.getId().getVideoId());
						try {
							v.setThumbUrl(item.getSnippet().getThumbnails().getMedium().getUrl());
						}catch(NullPointerException npe) {
							v.setThumbUrl(item.getSnippet().getThumbnails().getDefault().getUrl());
						}

						listaVideos.add(v);
					}
				}
			}
		} catch (GoogleJsonResponseException e) {
			e.printStackTrace();
			System.err.println(
					"There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());

		}  catch (Throwable t) {
			t.printStackTrace();
		}
		return listaVideos;
	}

}
