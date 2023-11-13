import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class LibraryManagementSystemTest {
    public static void main(String[] args) throws IOException {
        test();
//        testRemoveBookByTitle();
//        testRemoveBookByBarcode();
    }

    public static void test() throws IOException {
        LibraryManagementSystem librarySystem = new LibraryManagementSystem();
        // Prepare input data to simulate user input
        String userInput = "1,Sample Book,Sample Author\nexit\n";
        InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        librarySystem.addBook();

        // Check if the book has been added to the bookList
        if (librarySystem.bookList.size() == 1) {
            System.out.println("\n---AddBook PASSED---");
        } else {
            System.out.println("\n---AddBook FAILED---");
        }

        userInput = "Title\nSample Book\n1";
        inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        librarySystem.removeBook();

        // Check if the book has been removed from the bookList
        if (librarySystem.bookList.isEmpty()) {
            System.out.println("\n---RemoveBookByTitle PASSED---");
        } else {
            System.out.println("\n---RemoveBookByTitle FAILED---");
        }
    }
}
