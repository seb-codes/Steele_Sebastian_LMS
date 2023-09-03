import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class LibraryManagementSystem {
    /* Sebastian Steele CEN 3024 Sept. 3 2023
       Class: LibraryManagementSystem
       This class is the main class of the application.
       This program manages the books in a library through multiple abilities.
       add books from a list in a text file, remove books by the id, and list all current books in the library
     */
    private static final List<Book> bookList = new ArrayList<>();
    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the Library Management System.\n Select from the options below:");
            System.out.println("1. Add a book");
            System.out.println("2. Remove a book by ID");
            System.out.println("3. List all books");
            System.out.println("4. Quit");
            System.out.print("Enter your choice: ");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            scanner.nextLine();

            switch (choice) {
                case 1 -> addBook(scanner);
                case 2 -> removeBook(scanner);
                case 3 -> listBooks();
                case 4 -> System.exit(0);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addBook(Scanner scanner) {
        /*
        addBook, this method allows you to add a book into your library
        args: Scanner scanner, allows you to scan in values from user
        ret: Void
         */
        System.out.println("Enter book data (format: id,title,author) or type 'exit' to quit:");
        while (true) {
            System.out.print("> ");
            String inputLine = scanner.nextLine().trim();

            if (inputLine.equalsIgnoreCase("exit")) {
                break; // Exit the loop if the user types 'exit'.
            }

            String[] parts = inputLine.split(",");
            if (parts.length == 3) {
                try {
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    bookList.add(new Book(id, title, author));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input format. Please use format: id,title,author");
                }
            } else {
                System.out.println("Invalid input format. Please use format: id,title,author");
            }
        }

    }

    private static void removeBook(Scanner scanner){
       /*
        removeBook, this method allows you to remove a book from your library
        args: Scanner scanner, allows you to scan in values from user
        ret: Void
         */
        System.out.print("Enter the ID of the book to remove: ");
        int idToRemove = scanner.nextInt();

        boolean removed = false;
        for (Book book : bookList) {
            if (book.getId() == idToRemove) {
                bookList.remove(book);
                removed = true;
                System.out.println("Book removed from the collection.");
                break;
            }
        }

        if (!removed) {
            System.out.println("Book with ID " + idToRemove + " not found in the collection.");
        }
        System.out.println("book removed");
    }

    private static void listBooks(){
        /*
        listBooks, this method creates a text file and writes each book in csv format to it
        args: None
        ret: Void
         */

        if (bookList.isEmpty()) {
            System.out.println("The Library is empty.");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("LibraryBooks.txt"))) {
                for (Book book : bookList) {
                    writer.write(book.toTxt());
                    writer.newLine(); // Add a newline separator between entries
                }
                System.out.println("Books saved to LibraryBooks.txt");
            } catch (IOException e) {
                System.err.println("Error saving books to file: " + e.getMessage());
            }
        }
    }


     static class Book {
        /* Sebastian Steele CEN 3024 Sept. 3 2023
           Class: Book
           This class contains information regarding books in a library.
           You will be able to store the id, title and author
         */
        private int id;
        private String title;
        private String author;

        public Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

         public String toTxt() {
             return id + "," + title + "," + author;
         }

    }
}
