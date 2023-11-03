import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class LibraryManagementSystem {
    /* Sebastian Steele CEN 3024 Sept. 3 2023
       Class: LibraryManagementSystem
       This class is the main class of the application.
       This program manages the books in a library through multiple abilities.
       add books from a list in a text file, remove books by the id, and list all current books in the library
     */

    public static void main(String[] args) throws IOException {
        LibraryManagementSystem myInstance = new LibraryManagementSystem();
        myInstance.lms();

    }

    public List<Book> bookList = new ArrayList<>();

    public void lms() throws IOException {
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
                case 1 -> addBook();
                case 2 -> removeBook();
                case 3 -> listBooks();
                case 4 -> addlibraryFile();
                case 5 -> printLibrary();
                case 6 -> checkoutBook();
                case 7 -> checkinBook();
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid choice. Please try again.");
            }


        }
    }


    public void addBook() throws IOException {
        /*
        addBook, this method allows you to add a book into your library
        args: Scanner scanner, allows you to scan in values from user
        ret: Void
         */
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter book data (format: id,title,author) or type 'exit' to quit:");
        LibraryManagementSystem.Book newBook = null;
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
                    //System.out.println(parts[0] + parts[1] + parts[2]);

                    boolean dupBook = false;

                    for (Book book : bookList) {
                        // Access each book instance using the 'book' variable.
                        if (title.equals(book.getTitle())) {
                            dupBook = true;
                            break;
                        }
                        // Add more code to process each book as needed.
                    }

                    if (dupBook) {
                        System.out.println("Books already exist in Library can not add again");
                        break;

                    }

                    newBook = new Book(id, title, author);
                    bookList.add(newBook);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input format. Please use format: id,title,author");
                }
            } else {
                System.out.println("Invalid input format. Please use format: id,title,author");
            }
        }

    }

    public void printLibrary() {

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

    public void addlibrary(String filePath) {


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                boolean dupBook = false;
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String author = parts[2];

                    for (Book book : bookList) {
                        // Access each book instance using the 'book' variable.

                        if (title.equals(book.getTitle())) {
                            dupBook = true;
                            System.out.println("Title: " + book.getTitle() + " Found Duplicate");
                            break;
                        }
                        // Add more code to process each book as needed.
                    }

                    if (dupBook) {
                        System.out.println("Books already exist in Library can not add again");
                        continue;
                    }


                    //System.out.println(parts[0] + parts[1] + parts[2]);
                    bookList.add(new Book(id, title, author));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addlibraryFile() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Each row in file must be in the following format \n title,author,barcode,genre \n status defaults to checked in and due date defaults to null");
        System.out.print("Enter the file path: ");
        String filePath = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                boolean dupBook = false;
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String author = parts[2];

                    for (Book book : bookList) {
                        // Access each book instance using the 'book' variable.

                        if (title.equals(book.getTitle())) {
                            dupBook = true;
                            System.out.println("Title: " + book.getTitle() + " Found Duplicate");
                            break;
                        }
                        // Add more code to process each book as needed.
                    }

                    if (dupBook) {
                        System.out.println("Books already exist in Library can not add again");
                        continue;
                    }


                    //System.out.println(parts[0] + parts[1] + parts[2]);
                    bookList.add(new Book(id, title, author));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Define a method to get library book titles as an array of strings
    public String[] getLibraryBookTitles() {
        String[] titles = new String[bookList.size()];
        if (bookList.isEmpty()) {
            System.out.println("The Library is empty.");
            return titles;
        }


        for (int i = 0; i < bookList.size(); i++) {
            titles[i] = bookList.get(i).title;
        }
        return titles;
    }

    public void removeBookByBarcode(int barcode){
        bookList.removeIf(book -> book.getBarcode()==barcode);
    }

    public void removeBookByTitle(String titleToRemove){
        bookList.removeIf(book -> book.getTitle().equals(titleToRemove));

    }

    public List<Integer> getLibraryBookBarcodes(){

        List<Integer> barcodes = new ArrayList<>();

        if (bookList.isEmpty()) {
            System.out.println("The Library is empty.");
            return barcodes;
        }

        for (Book book : bookList) {
            barcodes.add(book.getBarcode());
        }

        return barcodes;
    }



    public  void removeBook(){
       /*
        removeBook, this method allows you to remove a book from your library
        args: Scanner scanner, allows you to scan in values from user
        ret: Void
         */

        Scanner scanner = new Scanner(System.in);

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
                if (book.getTitle().equals(titleToRemove)) {
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

    public void listBooks(){
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

    public void checkoutBook(){
        Scanner scanner = new Scanner(System.in);


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

    public void checkoutBook(String titleToCheckout){

        if(bookList.isEmpty()){
            System.out.println("Book Title is not in the library or Already checked out");
            return;
        }


        for (Book book : bookList) {
            if (book.getTitle().equals(titleToCheckout) && book.getStatus().equals("checked in")) {
                book.status = "checked out";
                book.duedate = LocalDate.now().plusWeeks(4);
                System.out.println("Book checked out.");
                return;
            }
        }

    }

    public  void checkinBook(){
        Scanner scanner = new Scanner(System.in);

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

    public  void checkinBook(String titleToCheckin){

        if(bookList.isEmpty()){
            System.out.println("Book Title is not in the library or Already checked in");
        }



        for (Book book : bookList) {
            if (book.getTitle().equals(titleToCheckin) && book.getStatus().equals("checked out")) {
                book.status = "checked in";
                book.duedate = null;
            }
        }

    }

    public String getLibraryBooksAsString(){
        String ret_string = "";
        if (bookList.isEmpty()) {
            return("The Library is empty.");
        } else {

            for (Book book : bookList) {
                ret_string+=(book.toTxt()+'\n'); // Add a newline separator between entries

            }
            return ret_string;
        }

    }

    public List<String> getAvailableBookTitles(){
        List<String> titles = new ArrayList<String>();
        if (bookList.isEmpty()) {
            System.out.println("The Library is empty.");
            return titles;
        }


        for (Book book: bookList) {
            if(book.getStatus().equals("checked in")){
                titles.add(book.title);
            }
        }
        return titles;
    }

    public List<String> getNotAvailableBookTitles(){
        List<String> titles = new ArrayList<String>();
        if (bookList.isEmpty()) {
            System.out.println("The Library is empty.");
            return titles;
        }


        for (Book book: bookList) {
            if(book.getStatus().equals("checked out")){
                titles.add(book.title);
            }
        }
        return titles;
    }


     public static class Book {
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


        public Book(int id,String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.barcode = generateRandomBarcode();
            this.genre = generateRandomGenre();
            this.status = "checked in";
            this.duedate = null;
        }
         // Generate a random barcode between 4 and 6 digits
         private int generateRandomBarcode() {
             Random random = new Random();
             int minDigits = 1000; // Smallest 4-digit number
             int maxDigits = 999999; // Largest 6-digit number
             return random.nextInt(maxDigits - minDigits + 1) + minDigits;
         }

         // Generate a random book genre from a predefined list
         private String generateRandomGenre() {
             String[] genres = {"Mystery", "Science Fiction", "Fantasy", "Romance", "Thriller", "Non-Fiction", "Historical Fiction", "Adventure"};
             Random random = new Random();
             int index = random.nextInt(genres.length);
             return genres[index];
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
