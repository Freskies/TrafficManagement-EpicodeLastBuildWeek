package Utils;

public class Book {

	private String title;
	private String author;
	private String genre;
	private int year;

	public Book(String title, String author, String genre, int year) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}