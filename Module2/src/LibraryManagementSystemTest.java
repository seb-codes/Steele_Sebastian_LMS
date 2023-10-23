import java.util.Scanner;

public class LibraryManagementSystemTest {
    public static void main(String[] args) {
        testAddBook();
        testRemoveBookByTitle();
        testRemoveBookByBarcode();
    }

    public static void testAddBook() {
        LibraryManagementSystem librarySystem = new LibraryManagementSystem();
        Scanner scanner = new Scanner("Sample Book,Sample Author,12345,Sample Genre\nexit");

        librarySystem.addBook(scanner);

        // Check if the book has been added to the bookList
        if (librarySystem.bookList.size() == 1) {
            System.out.println("testAddBook PASSED");
        } else {
            System.out.println("testAddBook FAILED");
        }

        librarySystem = null;
    }

   public static void testRemoveBookByTitle() {
       LibraryManagementSystem librarySystem = new LibraryManagementSystem();

        librarySystem.removeBook(new Scanner("Title\nSample Book\n0"));

        // Check if the book has been removed from the bookList
        if (librarySystem.bookList.isEmpty()) {
            System.out.println("testRemoveBookByTitle PASSED");
        } else {
            System.out.println("testRemoveBookByTitle FAILED");
        }

       librarySystem = null;
    }
//
    public static void testRemoveBookByBarcode() {
        LibraryManagementSystem librarySystem = new LibraryManagementSystem();
        librarySystem.bookList.add(new LibraryManagementSystem.Book("Sample Book", "Sample Author", 12345, "Sample Genre"));

        Scanner scanner = new Scanner("barcode\n12345\n1");

        librarySystem.removeBook(scanner);

        // Check if the book has been removed from the bookList
        if (librarySystem.bookList.isEmpty()) {
            System.out.println("testRemoveBookByBarcode PASSED");
        } else {
            System.out.println("testRemoveBookByBarcode FAILED");
        }
    }
}
