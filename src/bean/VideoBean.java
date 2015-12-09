package bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import entidade.Video;
import uploads.GetPlayListItems;

@ManagedBean(name="videoBean")
@RequestScoped
public class VideoBean {
	
	GetPlayListItems getPlayListItems = new GetPlayListItems();
	
	public List<Video> getListaVideos() {
		return getPlayListItems.getPlayListId();
	}
}