/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package uploads;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;

import entidade.Video;
import youtube.Auth;

public class GetPlayListItems {

	/**
	 * Arquivo que contém a chave de API necessária na aplicação para fazer
	 * chamadas à API.
	 */
	private static final String PROPERTIES_FILENAME = "youtube.properties";

	/**
	 * Objeto do tipo Youtube para fazer as chamadas para a API do Youtube.
	 */
	private static YouTube youtube;

	public List<Video> getPlayListId() {
		// Lê a chave de API de arquivo
		Properties properties = new Properties();
		List<Video> listaVideos = new ArrayList<Video>();

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

			// Call the API's channels.list method to retrieve the
			// resource that represents the authenticated user's channel.
			// In the API response, only include channel information needed for
			// this use case. The channel's contentDetails part contains
			// playlist IDs relevant to the channel, including the ID for the
			// list that contains videos uploaded to the channel.
			YouTube.Channels.List channelRequest = youtube.channels().list("contentDetails");

			String apiKey = properties.getProperty("youtube.apikey");
			channelRequest.setKey(apiKey);
			channelRequest.setPart("contentDetails");
			channelRequest.setForUsername("lptvbrasil");

			ChannelListResponse channelResult = channelRequest.execute();

			List<Channel> channelsList = channelResult.getItems();

			if (channelsList != null) {
				String uploadPlaylistId = channelsList.get(0).getContentDetails().getRelatedPlaylists().getUploads();

				// Define a list to store items in the list of uploaded videos.
				List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();

				// Retrieve the playlist of the channel's uploaded videos.
				YouTube.PlaylistItems.List playlistItemRequest = youtube.playlistItems()
						.list("id,contentDetails,snippet");

				playlistItemRequest.setPlaylistId(uploadPlaylistId);
				playlistItemRequest.setKey(apiKey);
				playlistItemRequest.setMaxResults((long) 16);

				// Only retrieve data used in this application, thereby making
				// the application more efficient. See:
				// https://developers.google.com/youtube/v3/getting-started#partial
				playlistItemRequest.setFields(
						"items(contentDetails/videoId,snippet/title,snippet/publishedAt," + "snippet/thumbnails)");

				PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

				playlistItemList.addAll(playlistItemResult.getItems());

				for (PlaylistItem item : playlistItemList) {
					Video v = new Video();

					v.setNome(item.getSnippet().getTitle());
					v.setVideoId(item.getContentDetails().getVideoId());
					try {
						v.setThumbUrl(item.getSnippet().getThumbnails().getMaxres().getUrl());
					} catch (NullPointerException ec) {
						v.setThumbUrl(item.getSnippet().getThumbnails().getHigh().getUrl());
					}
					
					listaVideos.add(v);
				}
			}

		} catch (GoogleJsonResponseException e) {
			e.printStackTrace();
			System.err.println(
					"There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());

		} catch (Throwable t) {
			t.printStackTrace();
		}

		return listaVideos;
	}
}