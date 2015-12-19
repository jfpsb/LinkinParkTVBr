package bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import entidade.Video;
import search.GetSearchItems;

@ManagedBean(name="searchBean")
@RequestScoped
public class SearchVideoBean {
	
	GetSearchItems getSearchItems = new GetSearchItems();
	
	public List<Video> getListaVideos(String palavraChave) {
		return getSearchItems.getVideoSearch(palavraChave);
	}
}
