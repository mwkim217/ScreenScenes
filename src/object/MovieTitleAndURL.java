package object;

import java.util.Objects;



/**
 * URL은
 * https://moviemaps.org/movies/50
 * 와 같은 장소 이미지를 가지는 페이지의 url 입니다.
 * @author mwk217
 * s
 * 
 *
 */
public class MovieTitleAndURL {
	private String title;
	private String URL;
	
	
	public MovieTitleAndURL(String title, String uRL) {
		this.title = title;
		URL = uRL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	@Override
	public int hashCode() {
		return Objects.hash(URL, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovieTitleAndURL other = (MovieTitleAndURL) obj;
		return Objects.equals(URL, other.URL) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "MovieTitleAndURL [title=" + title + ", URL=" + URL + "]";
	}
	
	
	
	
}
