import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

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
            System.out.println("2. Remove a book");
            System.out.println("3. Output books to file");
            System.out.println("4. Upload file to the Library");
            System.out.println("5. Print Books in Library");
            System.out.println("6. Check Book Out");
            System.out.println("7. Check Book In");
            System.out.println("0. Quit");
            System.out.print("Enter your choice: ");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            scanner.nextLine();

            switch (choice) {
                case 1 -> addBook(scanner);
                case 2 -> removeBook(scanner);
                case 3 -> listBooks();
                case 4 -> addlibraryFile(scanner);
                case 5-> printLibrary();
                case 6 -> checkoutBook(scanner);
                case 7 -> checkinBook(scanner);
                case 0 -> System.exit(0);
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
            if (parts.length == 4) {
                try{
                    String title = parts[0];
                    String author = parts[1];
                    int barcode = Integer.parseInt(parts[2]);
                    String genre = parts[3];
                    //System.out.println(parts[0] + parts[1] + parts[2]);
                    bookList.add(new Book(title, author, barcode, genre));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input format. Please use format: id,title,author");
                }
            } else {
                System.out.println("Invalid input format. Please use format: id,title,author");
            }
        }

    }

    private static void printLibrary(){

        if (bookList.isEmpty()) {
            System.out.println("The Library is empty.");
        } else {
            System.out.println("\n\nPrinting out Books in the Library");
                for (Book book : bookList) {
                    System.out.println(book.toTxt()); // Add a newline separator between entries
            }
            System.out.println("\n\n");
        }

    }

    private static void addlibraryFile(Scanner scanner){
            System.out.println("Each row in file must be in the following format \n title,author,barcode,genre \n status defaults to checked in and due date defaults to null");
            System.out.print("Enter the file path: ");
            String filePath = scanner.nextLine();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        String title = parts[0];
                        String author = parts[1];
                        int barcode = Integer.parseInt(parts[2]);
                        String genre = parts[3];
                        //System.out.println(parts[0] + parts[1] + parts[2]);
                        bookList.add(new Book(title, author, barcode, genre));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    };


    private static void removeBook(Scanner scanner){
       /*
        removeBook, this method allows you to remove a book from your library
        args: Scanner scanner, allows you to scan in values from user
        ret: Void
         */

        List<Book> bookdupeList = new ArrayList<>();


        System.out.print("Do you want to remove by title or barcode? ");
        String decision = scanner.nextLine().toLowerCase();

        if (decision.equals("barcode")){
            System.out.print("Enter the barcode of the book to remove: ");
            int barcodeToRemove = scanner.nextInt();

            boolean removed = false;
            for (Book book : bookList) {
                if (book.getBarcode() == barcodeToRemove) {
                    bookdupeList.add(book);
                    removed = true;
                }
            }
            if (!removed) {
                System.out.println("Book with ID " + barcodeToRemove + " not found in the collection.");
                return;
            }

            System.out.print("Confirm which book ID you want to remove\n");
            for (Book book: bookdupeList){
                System.out.println(book.toTxt());
            }

            System.out.print("Which book ID do you wish to remove? ");
            int idToRemove = scanner.nextInt();

            for (Book book : bookList) {
                if (book.getId() == idToRemove) {
                    bookList.remove(book);
                    System.out.println("Book removed from the collection.");
                    break;
                }
            }


            System.out.println("book removed");

            printLibrary();

        }

        if (decision.equals("title")){
            System.out.print("Enter the title of the book to remove: ");
            String titleToRemove = scanner.nextLine();

            boolean removed = false;
            for (Book book : bookList) {
                if (book.getTitle().toLowerCase() == titleToRemove.toLowerCase()) {
                    bookdupeList.add(book);
                    removed = true;
                }
            }
            if (!removed) {
                System.out.println("Book with title " + titleToRemove + " not found in the collection.");
                return;
            }

            System.out.print("Confirm which book Title you want to remove\n");
            for (Book book: bookdupeList){
                System.out.println(book.toTxt());
            }

            System.out.print("Which book ID do you wish to remove? ");
            int idToRemove = scanner.nextInt();

            for (Book book : bookList) {
                if (book.getId() == idToRemove) {
                    bookList.remove(book);
                    System.out.println("Book removed from the collection.");
                    break;
                }
            }

            System.out.println("book removed");
            printLibrary();

        }

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

    private static void checkoutBook(Scanner scanner){
        System.out.print("Enter the title of the book to checkout: ");
        String titleToCheckout = scanner.nextLine().toLowerCase();

        List<Book> bookdupeList = new ArrayList<>();

        for (Book book : bookList) {
            if (book.getTitle().equals(titleToCheckout) && book.getStatus().equals("checked in")) {
                bookdupeList.add(book);
            }
        }

        if(bookdupeList.isEmpty()){
            System.out.println("Book Title is not in the library or Already checked out");
        }

        System.out.print("Confirm which book ID you want to checkout\n");
        for (Book book: bookdupeList){
            System.out.println(book.toTxt());
        }

        System.out.print("Which book ID do you wish to check out? ");
        int idToCheckout = scanner.nextInt();

        for (Book book : bookList) {
            if (book.getId() == idToCheckout) {
                book.status = "checked out";
                book.duedate = LocalDate.now().plusWeeks(4);
                System.out.println("Book checked out.");
                break;
            }
        }

        printLibrary();

    }

    private static void checkinBook(Scanner scanner){
        System.out.print("Enter the title of the book to checkin: ");
        String titleToCheckin = scanner.nextLine().toLowerCase();

        List<Book> bookdupeList = new ArrayList<>();

        for (Book book : bookList) {
            if (book.getTitle().equals(titleToCheckin) && book.getStatus().equals("checked out")) {
                bookdupeList.add(book);
            }
        }

        if(bookdupeList.isEmpty()){
            System.out.println("Book Title is not in the library or Already checked in");
        }

        System.out.print("Confirm which book ID you want to checkin\n");
        for (Book book: bookdupeList){
            System.out.println(book.toTxt());
        }

        System.out.print("Which book ID do you wish to check in? ");
        int idToCheckout = scanner.nextInt();

        for (Book book : bookList) {
            if (book.getId() == idToCheckout) {
                book.status = "checked in";
                book.duedate = null;
                System.out.println("Book checked in.");
                break;
            }
        }

        printLibrary();

    }


     static class Book {
        /* Sebastian Steele CEN 3024 Sept. 3 2023
           Class: Book
           This class contains information regarding books in a library.
           You will be able to store the id, title and author
         */
        // keeping a unique id counter to help with duplicates
        private  int id;
        private static int idCounter = 0;

        private String title;
        private String author;
        private int barcode;
        private String genre;
        private String status;
        private LocalDate duedate;


        public Book(String title, String author, int barcode, String genre) {
            this.id = idCounter++;
            this.title = title;
            this.author = author;
            this.barcode = barcode;
            this.genre = genre;
            this.status = "checked in";
            this.duedate = null;
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
             return "id:" + id + ", title:" + title + ", author:" + author + ", barcode: " + barcode + ", genre:" + genre +", status:" + status + ", due date:" + duedate ;
         }

         public static int getIdCounter() {
             return idCounter;
         }

         public static void setIdCounter(int idCounter) {
             Book.idCounter = idCounter;
         }

         public int getBarcode() {
             return barcode;
         }

         public void setBarcode(int barcode) {
             this.barcode = barcode;
         }

         public String getGenre() {
             return genre;
         }

         public void setGenre(String genre) {
             this.genre = genre;
         }

         public String getStatus() {
             return status;
         }

         public void setStatus(String status) {
             this.status = status;
         }

         public LocalDate getDuedate() {
             return duedate;
         }

         public void setDuedate(LocalDate duedate) {
             this.duedate = duedate;
         }

    }
}
