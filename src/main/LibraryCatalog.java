package main;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import data_structures.ArrayList;
import data_structures.DoublyLinkedList;
import data_structures.SinglyLinkedList;
import interfaces.FilterFunction;
import interfaces.List;

public class LibraryCatalog {
	
	private List<Book> catalog;
	private List<User> user;
	
	/**
	 * Default Constructor for the LibraryCatalog class.
	 */
	public LibraryCatalog() throws IOException {
		
		this.catalog = this.getBooksFromFiles();
		this.user = this.getUsersFromFiles();
		
	}
	
	/**
	 * Gets the Books from the catalog of the library in the Data folder and create a list of that Books. 
	 * Create Books given the information from each line and adds it to the ArrayList<Book>. 
	 * @throws IOException If an I/O error occurs while reading from the file
	 */
	private List<Book> getBooksFromFiles() throws IOException {
		ArrayList<Book> booksArrayList = new ArrayList<>();
		
		/* Read the catalog.cvs from the data */
		BufferedReader br = new BufferedReader(new FileReader("./data/catalog.csv"));
		String data = "";
		boolean firstLine = true;
		
		/* Read from the first line of the catalog.cvs to the end */
		while((data = br.readLine()) != null) {
			
			/* Skip the first line because the first line is not a Book */
			if (!firstLine) {
				
				/* Split the line into an Array */
				String[] components = data.split(",");
				
				/* Convert each value of the Array to their respective Data type */
				Integer id = Integer.parseInt(components[0]);
				String title = components[1];
				String author = components[2];
				String genre = components[3];
				LocalDate lastCheckOut = LocalDate.parse(components[4]);
				Boolean checkedOut = Boolean.parseBoolean(components[5]);  
				
				/* Create a new Book and then is added to the booksArrayList */
				Book book = new Book(id, title, author, genre, lastCheckOut, checkedOut);
				booksArrayList.add(book);
				
			}
			firstLine = false;
			
		}
		br.close();
		return booksArrayList;
	}
	
	/**
	 * Gets the Users from the user of the library in the Data folder and create a list of Users. 
	 * Runs through all entire file for each line and create the User to add it to the ArrayList<User>
	 * if the user have a list of books checkedOutList runs through all the catalog to add that Book 
	 * to a list of userBooks and Create that user with it respective components and list
	 * Otherwise create the User with an empty userBooks.  
	 * @throws IOException If an I/O error occurs while reading from the file
	 */
	private List<User> getUsersFromFiles() throws IOException {
		ArrayList<User> userArrayList = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader("./data/user.csv"));
		String data = "";
		boolean firstLine = true;
		
		/* Read the user.cvs from the data */
		while ( (data = br.readLine()) != null) {
			if (!firstLine) {
				
				/* Split the line into an Array */
				String[] components = data.split(",");
				
				/* Convert each value of the Array to their respective Data type */
				Integer id = Integer.parseInt(components[0]);
				String name = components[1];
				
				/* Check if the length of component is equals to 3 means that the User have one or more Books currently checkOut */
				if (components.length == 3) {
					
					/* Remove the curly brackets and splits the list of Books that is separated by space into an Array */
					String listBooksFromUser = components[2];
					listBooksFromUser = listBooksFromUser.replace("{", "").replace("}", "");
					
					List<Book> libraryBooks = catalog;
					String[] booksIdsArray = listBooksFromUser.split("\\s+");
					List<Book> userBooks = new ArrayList<>(booksIdsArray.length);
					
					/* Iterate the entire Array of booksIdsArray */
					for (String bookID : booksIdsArray) {
						
						/* Iterate the entire ArrayList<Book> of libraryBooks */
						for (Book book : libraryBooks) {
							
							/* If the Id of the Book in the libraryBooks ArrayList is greater then the Id of the user Id  
							 * breaks because none Id will be equal to bookID */
							if(book.getId() > Integer.valueOf(bookID) ) {
								break;
							}
							
							/* Founds the Book in the libraryBooks ArrayList and it adds that Book to the userBooks ArrayList<Book> */
							else if(Integer.valueOf(bookID) == (book.getId()) ) {
								userBooks.add(book);
								break;
							}
						}
					}
					/* Create a new User and then is added to the userArrayList */
					User user = new User(id, name, userBooks);
					userArrayList.add(user);
				}
				else {
					/* Create a new User with and empty list of books checkOut and then is added to the userArrayList */
					List<Book> userBooks = new ArrayList<>(0);
					User user = new User(id, name, userBooks);
					userArrayList.add(user);
				}
			}
			firstLine = false;
			
		}
		br.close();
		return userArrayList;
	}
	
	/**
	 * Returns the List of Books that is the complete Catalog of the library.
	 */
	public List<Book> getBookCatalog() {
		return this.catalog;
	}
	
	/**
	 * Returns the List of Users that are register in the library.
	 */
	public List<User> getUsers() {
		return this.user;
	}
	
	/**
	 * Add a new Book with given parameter title, author, genre.
	 * @param title - (String) value to assign to the new Title.
	 * @param author - (String) value to assign to the new Author.
	 * @param genre - (String) value to assign to the new Genre.
	 * For the lastCheckOut, checkedOut are default values and for the id is the current catalog size + 1.
	 */
	public void addBook(String title, String author, String genre) {
		List<Book> newArrayList = catalog;
		LocalDate lastCheckOut = LocalDate.parse("2023-09-15");
		Book book = new Book(newArrayList.size() + 1, title, author, genre, lastCheckOut, false);
		newArrayList.add(book);
	}
	/**
	 * Remove the Book with the corresponding id parameter 
	 * * @param id - (Integer) Id of Book to remove.
	 */
	public void removeBook(int id) {
		List<Book> newArrayList = catalog;
		for (Book book : newArrayList) {
			if (id == book.getId()) {
				newArrayList.remove(book);
			}
		}
		return;
	}	
	
	/**
	 * Return if the id parameter Book isn’t alreadycheckOut the Id of the Book
	 * @param id - (Integer) Id value to check through Catalog of the library.
	 */
	public boolean checkOutBook(int id) {
		List<Book> newArrayList = catalog;
		LocalDate lastCheckOut = LocalDate.parse("2023-09-15");
		for (Book book : newArrayList) {
			if (id > newArrayList.size() || (id == book.getId() && (book.isCheckedOut() == true) )) {
				return false;
			}
			else if ((id == book.getId() && (book.isCheckedOut() == false) )) {
				book.setCheckedOut(true);
				book.setLastCheckOut(lastCheckOut);
				return true;
			}
		}
		return true;
	}
	
	/**
	 * Return if the id parameter Book can be return the Id of the Book.
	 * @param id - (Integer) Id value to check through Catalog of the library.
	 */
	public boolean returnBook(int id) {
		List<Book> newArrayList = catalog;
		for (Book book : newArrayList) {
			if (id == book.getId() && book.isCheckedOut() == true) {
				book.setCheckedOut(false);
				return true;
			}
			else if (id > newArrayList.size() || (id == book.getId() && (book.isCheckedOut() == false) )) {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Return if the id parameter Book is available for chetOut.
	 * @param id - (Integer) Id value to check through Catalog of the library.
	 */
	public boolean getBookAvailability(int id) {
		List<Book> newArrayList = catalog;
		for (Book book : newArrayList) {
			if (id == book.getId() && book.isCheckedOut() == true) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Return the total amount of Books with the title parameter Book.
	 * @param title - (String) Title value to check through Catalog of the library.
	 */
	public int bookCount(String title) {
		List<Book> newArrayList = catalog;
		int count = 0;
		for (Book book : newArrayList) {
			if (book.getTitle().equals(title)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Creates a report  about the library. It includes
	 * information about the books currently in the library
	 * and generate a report.
	 * * @throws IOException If an I/O error occurs while reading from the file
	 */
	public void generateReport() throws IOException {
		
		List<Book> newBookArrayList = catalog;
		int totalAmount = 0;
		
		/* Use Lambda Functions (Bonus) to check if a Book is equals to the specific Genre and adds it to a ArrayList and return a Size */
		FilterFunction<Book> adventureFilter = book -> book.getGenre().equals("Adventure");
		List<Book> adventureBooks = searchForBook(adventureFilter);
		int countAdventureBooks = adventureBooks.size();
		
		FilterFunction<Book> fictonFilter = book -> book.getGenre().equals("Fiction");
		List<Book> fictonBooks = searchForBook(fictonFilter);
		int countFictionBooks = fictonBooks.size();
		
		FilterFunction<Book> classicsFilter = book -> book.getGenre().equals("Classics");
		List<Book> classicsBooks = searchForBook(classicsFilter);
		int countClassicsBooks = classicsBooks.size();
		
		FilterFunction<Book> mysteryFilter = book -> book.getGenre().equals("Mystery");
		List<Book> mysteryBooks = searchForBook(mysteryFilter);
		int countMysteryBooks = mysteryBooks.size();
		
		FilterFunction<Book> scienceFictionFilter = book -> book.getGenre().equals("Science Fiction");
		List<Book> scienceFictionBooks = searchForBook(scienceFictionFilter);
		int countScienceFictionBooks = scienceFictionBooks.size();
		
		/* Total amount of Books in the Library */
		totalAmount = countAdventureBooks + countFictionBooks + countClassicsBooks + countMysteryBooks + countScienceFictionBooks;
				
				
		String output = "\t\t\t\tREPORT\n\n";
		output += "\t\tSUMMARY OF BOOKS\n";
		output += "GENRE\t\t\t\t\t\tAMOUNT\n";
		/*
		 * In this section you will print the amount of books per category.
		 * 
		 * Place in each parenthesis the specified count. 
		 * 
		 * Note this is NOT a fixed number, you have to calculate it because depending on the 
		 * input data we use the numbers will differ.
		 * 
		 * How you do the count is up to you. You can make a method, use the searchForBooks()
		 * function or just do the count right here.
		 */
		output += "Adventure\t\t\t\t\t" + (countAdventureBooks/*Place here the amount of adventure books*/) + "\n";
		output += "Fiction\t\t\t\t\t\t" + (countFictionBooks/*Place here the amount of fiction books*/) + "\n";
		output += "Classics\t\t\t\t\t" + (countClassicsBooks/*Place here the amount of classics books*/) + "\n";
		output += "Mystery\t\t\t\t\t\t" + (countMysteryBooks/*Place here the amount of mystery books*/) + "\n";
		output += "Science Fiction\t\t\t\t\t" + (countScienceFictionBooks/*Place here the amount of science fiction books*/) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (totalAmount/*Place here the total number of books*/) + "\n\n";
		
		/*
		 * This part prints the books that are currently checked out
		 */
		output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		/*
		 * Here you will print each individual book that is checked out.
		 * 
		 * Remember that the book has a toString() method. 
		 * Notice if it was implemented correctly it should print the books in the 
		 * expected format.
		 * 
		 * PLACE CODE HERE
		 */
		
		int booksCheckedOut = 0;
		for (Book book : newBookArrayList) {
			if (book.isCheckedOut()) {
				output += (book.toString()/*BOOKS CURRENTLY CHECKED OUT AND THERE AUTHOR*/) + "\n";
				booksCheckedOut++;		
			}
		}
		
		
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (booksCheckedOut/*Place here the total number of books that are checked out*/) + "\n\n";
		
		
		/*
		 * Here we will print the users the owe money.
		 */
		output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		/*
		 * Here you will print all the users that owe money.
		 * The amount will be calculating taking into account 
		 * all the books that have late fees.
		 * 
		 * For example if user Jane Doe has 3 books and 2 of them have late fees.
		 * Say book 1 has $10 in fees and book 2 has $78 in fees.
		 * 
		 * You would print: Jane Doe\t\t\t\t\t$88.00
		 * 
		 * Notice that we place 5 tabs between the name and fee and 
		 * the fee should have 2 decimal places.
		 * 
		 * PLACE CODE HERE!
		 */
		
		
		/* Use Lambda Functions (Bonus) to check if a User have checkedOutList and adds the User to a List 
		 * and return the User name and the amount of late fee that owns. */
		FilterFunction<User> usersFilter = user ->  !user.getCheckedOutList().isEmpty();
		List<User> usersList = searchForUsers(usersFilter);
		
		double MoneyOwed = 0.00;
		
		for (User user : usersList) {
			double total = 0;

				for (Book book : user.getCheckedOutList()) {
					total += book.calculateFees();
				}
			String doubleToString = String.format("%.2f", total);
			output += user.getName() + "\t\t\t\t\t$" + doubleToString + "\n";
			MoneyOwed += total;
		}
		
		String doubleToString = String.format("%.2f", MoneyOwed);
		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + (doubleToString/*Place here the total amount of money owed to the library.*/) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.
		
		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		
		/* Writes a new File name "report.txt" that is a report of the library */
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./report/report.txt")));
		bw.write(output);
		bw.close();
		
		
		
	}
	
	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for
	 * other parts of the project.
	 */
	
	/**
	 * Sets the Title of the User 
	 * @param name - (String) value to assign to the Name.
	 */
	@FunctionalInterface
	public interface FilterFunction<E> {
		public boolean filter(E e);
	}
	
	/**
	 * Sets the Title of the User 
	 * @param name - (String) value to assign to the Name.
	 */
	public List<Book> searchForBook(FilterFunction<Book> func) {
		List<Book> catalogArrayList = catalog;
		List<Book> sameBooks = new ArrayList<>();

	    /* Iterate through the book list and apply the provided lambda function */
	    for (Book book : catalogArrayList) {
	        if (func.filter(book)) {
	        	sameBooks.add(book);
	        }
	    }

	    return sameBooks;
	}
	
	/**
	 * Sets the Title of the User 
	 * @param name - (String) value to assign to the Name.
	 */
	public List<User> searchForUsers(FilterFunction<User> func) {
		List<User> userArrayList = user;
		List<User> sameUsers = new ArrayList<>();

	    /* Iterate through the book list and apply the provided lambda function */
	    for (User user : userArrayList) {
	        if (func.filter(user)) {
	        	sameUsers.add(user);
	        }
	    }

	    return sameUsers;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			LibraryCatalog lc = new LibraryCatalog();
			lc.generateReport();
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
