package main;

import java.awt.BorderLayout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import data_structures.ArrayList;
import interfaces.List;

public class LibraryGUI implements ActionListener {
	/**
	 * Class that give the User the ability to add, remove and show the catalog from the 
	 * library in the system by clicking and scrollable table to view all the Books.
	 * @author Carlos Hernandez
	 * @Class CIIC4020 FALL 2023
	 * @Date 2023-10-11
	 */

	private static JTextField userText;
	private static JButton addButton;
	private static JButton removeButton;
	private static JButton libraryButton;
	private static JLabel instruction;
	private static JLabel example;
	private static JLabel comment;
	private static JLabel note1;
	private static JLabel note2;
	private static LibraryCatalog lc;
	private static List<Book> books;

	public static void main(String[] args) {

		try {
			lc = new LibraryCatalog();
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		books = lc.getBookCatalog();
		
		
		/* Get the screenSize depending of your computer screen size */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		/* Create a panel and a frame */
		JPanel panel = new JPanel();
		JFrame frame = new JFrame();
		
		/* Set the size of the frame */
		frame.setSize(700, 300);
		
		/* When closing the frame the program end */
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* Location where the frame would be display in your screen */
		frame.setLocation((int) (screenSize.getWidth() / 4 - frame.getWidth() / 2),
				(int) screenSize.getHeight() / 3 - frame.getHeight() / 2);
		frame.add(panel);

		/* Create a String Book to display in the screen */
		JLabel label = new JLabel("Book");
		label.setBounds(50, 20, 80, 35);
		panel.add(label);

		/* Create a TextField to write */
		userText = new JTextField(20);
		userText.setBounds(125, 20, 500, 35);
		panel.add(userText);

		
		/* Create respective buttons FOR add, remove and library */
		addButton = new JButton("Add Book");
		addButton.setBounds(50, 80, 150, 40);
		addButton.addActionListener(new LibraryGUI());
		panel.add(addButton);

		removeButton = new JButton("Remove Book");
		removeButton.setBounds(frame.getWidth() / 4 * 2 - addButton.getWidth() / 2, 80, addButton.getWidth(),
				addButton.getHeight());
		removeButton.addActionListener(new LibraryGUI());
		panel.add(removeButton);

		libraryButton = new JButton("Books Available");
		libraryButton.setBounds(frame.getWidth() / 4 * 3, 80, addButton.getWidth(), addButton.getHeight());
		libraryButton.addActionListener(new LibraryGUI());
		panel.add(libraryButton);

		
		/* Create respective INSTRUCTION to the user to use the GUI*/
		instruction = new JLabel(
				"If you want to ADD a Book to the Library IT MOST BE THE SAME FORMAT AS BELLOW: ");
		instruction.setBounds(70, 120, 700, 35);
		panel.add(instruction);

		example = new JLabel("Example: Harry Potter and the Prisoner of Azkaban, J. K. Rowling,Science Fiction");
		example.setBounds(70, 145, 700, 35);
		panel.add(example);


		comment = new JLabel("To remove a Book ONLY ENTER THE ID OF THE BOOK");
		comment.setBounds(70, 170, 700, 35);
		panel.add(comment);

		note1 = new JLabel("NOTE: If you have the List Of Books open make sure to close the tab tapping the button of");
		note1.setBounds(70, 200, 700, 35);
		panel.add(note1);

		note2 = new JLabel("'Books Available' again so you don't have more than one tab open at a time");
		note2.setBounds(70, 220, 700, 35);
		panel.add(note2);

		frame.setTitle("Borders Group");
		frame.setVisible(true);
	}

	/**
	 * ActionEven from the User while using the GUI.
	 * Actions can be clicking the add button for adding a Book to the catalog,
	 * the button for remove a Book from the catalog and last the button of library
	 * that if a button to refresh every time the Books that library has in a catalog. 
	 * @param ActionEvent - (e) action of the User to update the catalog.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

			String inputBook;
			// TODO Auto-generated method stub

			/* Check if the action of the User was clicking the addButton */
			if (e.getSource() == addButton) {
				inputBook = userText.getText();
				/* Check that if User click the button the text is not empty */
				if (!inputBook.isEmpty()) {
					String[] components = inputBook.split(",");
					for (int i = 0; i < components.length; i++) {
						components[i] = components[i].trim();
					}
					
					/**
					 *  Add a new Book with the implemented addBook method from the LibraryCatalog class 
					 * with the User text title, author and genre.
					 */
					lc.addBook(components[0], components[1], components[2]);
					
				}
			}

			/* Check if the action of the User was clicking the removeButton */
			else if (e.getSource() == removeButton) {
				inputBook = userText.getText(); 
				
				/* Check if the action of the User was clicking the removeButton */
				try {
			        int idToRemove = Integer.parseInt(inputBook);
			        lc.removeBook(idToRemove);
			    } catch (NumberFormatException nfe) {
			    	
			    }
			}
			if (e.getSource() == libraryButton) {
				
				
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				JFrame libraryCatframe = new JFrame("Library Catalog");

				/* Create a DefaultTableModel with specific column names */
		        DefaultTableModel model = new DefaultTableModel();
		        model.setColumnIdentifiers(new Object[]{"Book ID", "Title", "Author", "Genre", "Last Checkout Date", "Checked Out"});


		        /* Add the Catalog Books in rows to the model */
		        for (Book b : books) {
		        	model.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getGenre(), b.getLastCheckOut(), b.isCheckedOut()});
		        }
		        // Add more rows as needed

		        /* Create a JTable with the model */
		        JTable table = new JTable(model);

		        /* Create a JScrollPane to make the table vertically scrollable */
		        JScrollPane libraryCat = new JScrollPane(table);

		        /* Add the scroll pane to the frame */
		        libraryCatframe.add(libraryCat);

		        /* Set frame size and make it visible */
		        libraryCatframe.setSize(800, 400);
		        libraryCatframe.setLocationRelativeTo(null); // Center the frame
		        libraryCatframe.setLocation((int) ( 3 * screenSize.getWidth() / 4  - libraryCatframe.getWidth() / 2),
						(int) screenSize.getHeight() / 3 - libraryCatframe.getHeight() / 2);
		        libraryCatframe.setVisible(true);
			}

	}

}
