package main;

import interfaces.List;

public class User {
	/**
	 * Class that holds User that contains id, name, author, checkedOutList and methods to get the data.
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
	private String name;
	private List<Book> checkedOutList;
	
	
	/**
	 * Constructor for the Book class.
	 * @param id - (Integer) Represents the Id of the User being initialized
	 * @param name - (String) Represents the Name of the User being initialized
	 * * @param checkedOutList - (List<Book>) Represents the List of Books that the User have being initialized
	 */
	public User(Integer id, String name, List<Book> checkedOutList) {
		this.id = id;
		this.name = name;
		this.checkedOutList = checkedOutList;
		
	}
	
	/**
	 * Returns the Id of the User
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the Id of the User
	 * @param id - (Integer) value to assign to the Id.
	 */
	public void setId(int id) {
		this.id =id;
	}

	/**
	 * Returns the Name of the User
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Title of the User 
	 * @param name - (String) value to assign to the Name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the List of Books that the User have.
	 */
	public List<Book> getCheckedOutList() {
		return checkedOutList;
	}

	/**
	 * Sets the Title of the User 
	 * @param checkedOutList - (List<Book>) list to assign to the checkedOutList.
	 */
	public void setCheckedOutList(List<Book> checkedOutList) {
		this.checkedOutList = checkedOutList;
	}
	
}
