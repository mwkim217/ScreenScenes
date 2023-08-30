package object;

import java.sql.Blob;

public class Movie {
	private int movie_no;
	private String title;
	private Blob poster;

	public Movie(int movie_no, String title, Blob poster) {
		super();
		this.movie_no = movie_no;
		this.title = title;
		this.poster = poster;
	}

	public int getMovie_no() {
		return movie_no;
	}

	public void setMovie_no(int movie_no) {
		this.movie_no = movie_no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Blob getPoster() {
		return poster;
	}

	public void setPoster(Blob poster) {
		this.poster = poster;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + movie_no;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (movie_no != other.movie_no)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movie [movie_no=" + movie_no + ", title=" + title + ", poster=" + poster + "]";
	}

}
