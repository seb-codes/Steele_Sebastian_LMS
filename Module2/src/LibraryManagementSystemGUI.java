import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.io.File;


public class LibraryManagementSystemGUI {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(null);

        JLabel label = new JLabel("Select an option:");
        label.setBounds(50, 10, 200, 25);
        panel.add(label);

        JComboBox<String> optionComboBox = new JComboBox<>();
        optionComboBox.addItem("Add a book");
        optionComboBox.addItem("Remove a book");
        optionComboBox.addItem("Add books from file");
        optionComboBox.addItem("Print books");
        optionComboBox.addItem("Check out a book");
        optionComboBox.addItem("Check in a book");
        optionComboBox.addItem("Quit");
        optionComboBox.setBounds(50, 40, 200, 25);
        panel.add(optionComboBox);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(50, 80, 80, 25);
        panel.add(submitButton);

        LibraryManagementSystem librarySystem = new LibraryManagementSystem();


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = optionComboBox.getSelectedItem().toString();
                int choice = optionComboBox.getSelectedIndex();

                switch (choice) {
                    case 0 : showAddBookDialog(librarySystem);
                        break;
                    case 1 :
                        String[] options = {"By Title", "By Barcode"};
                        int response = JOptionPane.showOptionDialog(frame, "Remove a book by:", "Remove a Book", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                        if (response == 0) {
                            // Remove by Title
                            String[] titles = librarySystem.getLibraryBookTitles();
                            JComboBox<String> titleComboBox = new JComboBox<>(titles);
                            titleComboBox.setSelectedIndex(0);

                            JPanel titlePanel = new JPanel();
                            titlePanel.add(new JLabel("Select a title to remove:"));
                            titlePanel.add(titleComboBox);

                            int result = JOptionPane.showConfirmDialog(frame, titlePanel, "Remove a Book by Title", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                            if (result == JOptionPane.OK_OPTION) {
                                String titleToRemove = (String) titleComboBox.getSelectedItem();
                                librarySystem.removeBookByTitle(titleToRemove);
                            }
                            String output = librarySystem.getLibraryBooksAsString();
                            JOptionPane.showMessageDialog(frame, output, "Library Books", JOptionPane.PLAIN_MESSAGE);
                        } else if (response == 1) {
                            // Remove by Barcode
                            List<Integer> barcodes = librarySystem.getLibraryBookBarcodes();
                            JList<Integer> barcodeList = new JList<>(barcodes.toArray(new Integer[0]));

                            JScrollPane barcodeScrollPane = new JScrollPane(barcodeList);
                            int result = JOptionPane.showOptionDialog(frame, barcodeScrollPane, "Remove a Book by Barcode", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                            if (result == JOptionPane.OK_OPTION) {
                                Integer selectedBarcode = barcodeList.getSelectedValue();
                                librarySystem.removeBookByBarcode(selectedBarcode);
                            }
                            String output = librarySystem.getLibraryBooksAsString();
                            JOptionPane.showMessageDialog(frame, output, "Library Books", JOptionPane.PLAIN_MESSAGE);
                        }
                            break;
                    case 2 :                             JFileChooser fileChooser = new JFileChooser();
                        int fileChooserResult = fileChooser.showOpenDialog(frame);

                        if (fileChooserResult == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            // Process the selected file.
                            // You can use selectedFile.getAbsolutePath() to get the file path.
                            String filePath = selectedFile.getAbsolutePath();

                            librarySystem.addlibrary(filePath);
                            String output = librarySystem.getLibraryBooksAsString();
                            JOptionPane.showMessageDialog(frame, output, "Library Books", JOptionPane.PLAIN_MESSAGE);
                        }
                        break;
                    case 3: String output = librarySystem.getLibraryBooksAsString();
                        JOptionPane.showMessageDialog(frame, output, "Library Books", JOptionPane.PLAIN_MESSAGE);
                        break;
                    case 4 : List<String> availableTitles = librarySystem.getAvailableBookTitles();

                        if (availableTitles.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "No available books to check out.", "Check Out a Book", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JComboBox<String> titleComboBox = new JComboBox<>(availableTitles.toArray(new String[0]));
                            JPanel titlePanel = new JPanel();
                            titlePanel.add(new JLabel("Select a book to check out:"));
                            titlePanel.add(titleComboBox);

                            int result = JOptionPane.showConfirmDialog(frame, titlePanel, "Check Out a Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                            if (result == JOptionPane.OK_OPTION) {
                                String titleToCheckout = (String) titleComboBox.getSelectedItem();
                                librarySystem.checkoutBook(titleToCheckout);
                            }
                        }
                        break;
                    case 5 : List<String> Titles = librarySystem.getNotAvailableBookTitles();

                        if (Titles.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "No available books to check out.", "Check Out a Book", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JComboBox<String> titleComboBox = new JComboBox<>(Titles.toArray(new String[0]));
                            JPanel titlePanel = new JPanel();
                            titlePanel.add(new JLabel("Select a book to check in:"));
                            titlePanel.add(titleComboBox);

                            int result = JOptionPane.showConfirmDialog(frame, titlePanel, "Check Out a Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                            if (result == JOptionPane.OK_OPTION) {
                                String titleToCheckout = (String) titleComboBox.getSelectedItem();
                                librarySystem.checkinBook(titleToCheckout);
                            }
                        }
                        break;
                    case 6 : System.exit(0);
                }
            }
        });

        frame.setVisible(true);
    }
    private static void showAddBookDialog(LibraryManagementSystem librarySystem) {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id of the book:"));
        String title = JOptionPane.showInputDialog("Enter the title of the book:");
        String author = JOptionPane.showInputDialog("Enter the author of the book:");

        if (title != null && author != null) {
            // Process the entered book information.

            try {
                String userInput = String.valueOf(id)+","+title+","+author;
                InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
                System.setIn(inputStream);
                librarySystem.addBook();

                String message = "Added Book "+ userInput;
                JOptionPane.showMessageDialog(null, message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
