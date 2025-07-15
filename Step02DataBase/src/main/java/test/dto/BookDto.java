package test.dto;

public class BookDto {
	private int num;
	private String name;
	private String author;
	private String publisher;
	
	// 생성자
	public BookDto() {} 
	
	public BookDto(int num, String name, String author, String publisher) {
		super();
		this.num = num;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	
}
