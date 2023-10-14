package main;

import java.time.LocalDate;

public class Book {
	/**
	 * Class that holds Book that contains id, title, author, genre, lastCheckOut, checkOut and methods to get the data.
	 * @author Carlos Hernandez
	 * @Class CIIC4020 FALL 2023
	 * @Date 2023-10-11
	 */
	
	/*TODO ADD THE FOLLOWING:
	 * PRIVATE FIELDS, 
	 * CONSTRUCTOR, 
	 * GETTERS, 
	 * SETTERS, 
	 * MEMBER METHODS
	 */
	private Integer id;
	private String title;
	private String author;
	private String genre;
	private LocalDate lastCheckOut;
	private Boolean checkedOut;
	
	
	/**
	 * Constructor for the Book class.
	 * @param id - (Integer) Represents the Id of the Book being initialized
	 * @param tittle - (String) Represents the Title of the Book being initialized
	 * * @param author - (String) Represents the Genre of the Book being initialized
	 * @param genre - (String) Represents the Last Check Out Date of the Book being initialized
	 * * @param lastCheckOut - (LocalDate) Represents the Last Check Out Date of the Book being initialized
	 * @param checkedOut - (Boolean) Represents the Check Out of the Book being initialized
	 */
	public Book(Integer id, String title, String author, String genre, LocalDate lastCheckOut, Boolean checkedOut) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.lastCheckOut = lastCheckOut;
		this.checkedOut = checkedOut;
	}
	
	/**
	 * Returns the Id of the Book
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the Id of the Book
	 * @param id - (Integer) value to assign to the Id.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Returns the Title of the Book
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the Title of the Book 
	 * @param title - (String) value to assign to the Title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Returns the Author of the Book 
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Sets the Author of the Book 
	 * @param author - (String) value to assign to the Author.
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * Returns the Genre of the Book
	 */
	public String getGenre() {
		return genre;
	}
	
	/**
	 * Sets the Genre  of the Book 
	 * @param genre - (String) value to assign to the Genre.
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	/**
	 * Returns the Last Check Out of the Book 
	 */
	public LocalDate getLastCheckOut() {
		return lastCheckOut;
	}
	
	/**
	 * Sets the Last Last Check Out of the Book 
	 * @param lastCheckOut - (LocalDate) value to assign to the Last Last Check Out.
	 */
	public void setLastCheckOut(LocalDate lastCheckOut) {
		this.lastCheckOut = lastCheckOut;
	}
	
	/**
	 * Returns the Check Out of the Book 
	 */
	public boolean isCheckedOut() {
		return checkedOut;
	}
	
	/**
	 * Sets the Check Out of the Book
	 * @param checkedOut - (Boolean) value to assign to the X component.
	 */
	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}
	
	
	/**
	 * Returns the title by the author of the Book 
	 */
	@Override
	public String toString() {
		/*
		 * This is supposed to follow the format
		 * 
		 * {TITLE} By {AUTHOR}
		 * 
		 * Both the title and author are in uppercase.
		 */
		
		String title = this.getTitle().toUpperCase();
		String author = this.getAuthor().toUpperCase();
		return title + " BY " + author ;
//		return "{" + title + "}" + " BY " + "{" + author + "}";
	}
	
	/**
	 * Calculate the late fee of the book regard the current date that is 2023-09-15
	 * this is calculated if the difference between the LastCheckOut and the current date is grater or equals than 31 
	 */
	public float calculateFees() {
		/*
		 * fee (if applicable) = base fee + 1.5 per additional day
		 */
		float fee = 0;
		
		if (this.isCheckedOut()) {
			
			/*Sets the current date to be 2023-09-15  */
			LocalDate todaysDate = LocalDate.of(2023, 9, 15);
			
			/* Convert the values to toEpochDay to see the days between them */
			long daysBetween =  todaysDate.toEpochDay() - this.getLastCheckOut().toEpochDay() ;
			if (daysBetween >= 31) {
				fee = (float) (10 + (daysBetween - 31) * 1.5);
			}
		}
		return fee;
	}
	

	
}
