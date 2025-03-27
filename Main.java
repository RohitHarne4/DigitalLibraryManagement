import java.util.*;

class Book {
    int id;
    String title;
    String author;
    String genre;
    boolean isAvailable;

    public Book(int id, String title, String author, String genre, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Book ID: " + id + ", Title: " + title + ", Author: " + author + ", Genre: " + genre + ", Available: " + (isAvailable ? "Available" : "Checked Out");
    }
}

interface BookServiceInterface {
    void addBook();
    void showAllBooks();
    void showAllAvailableBooks();
    void searchBookByIdOrTitle();
    void updateBookDetails();
    void deleteBookRecord();
    void borrowBook();
    void returnBook();
}

class BookServiceImpl implements BookServiceInterface {
    private final List<Book> books = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);

    @Override
    public void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline
        
        if (books.stream().anyMatch(book -> book.id == id)) {
            System.out.println("Error: Book ID must be unique.");
            return;
        }
        
        System.out.print("Enter Title: ");
        String title = sc.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Error: Title cannot be empty.");
            return;
        }
        
        System.out.print("Enter Author: ");
        String author = sc.nextLine().trim();
        if (author.isEmpty()) {
            System.out.println("Error: Author cannot be empty.");
            return;
        }
        
        System.out.print("Enter Genre: ");
        String genre = sc.nextLine();
        
        System.out.print("Enter Availability (Available/Checked Out): ");
        String availability = sc.nextLine().trim();
        boolean isAvailable;
        
        if (availability.equalsIgnoreCase("Available")) {
            isAvailable = true;
        } else if (availability.equalsIgnoreCase("Checked Out")) {
            isAvailable = false;
        } else {
            System.out.println("Error: Availability must be either 'Available' or 'Checked Out'.");
            return;
        }
        
        books.add(new Book(id, title, author, genre, isAvailable));
        System.out.println("Book added successfully!");
    }

    @Override
    public void showAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(System.out::println);
        }
    }

    @Override
    public void showAllAvailableBooks() {
        books.stream().filter(book -> book.isAvailable).forEach(System.out::println);
    }

    @Override
    public void searchBookByIdOrTitle() {
        System.out.print("Enter Book ID or Title: ");
        sc.nextLine();
        String input = sc.nextLine();
        books.stream().filter(book -> String.valueOf(book.id).equals(input) || book.title.equalsIgnoreCase(input))
                .forEach(System.out::println);
    }

    @Override
    public void updateBookDetails() {
        System.out.print("Enter Book ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        for (Book book : books) {
            if (book.id == id) {
                System.out.print("Enter new Title: ");
                String title = sc.nextLine().trim();
                if (!title.isEmpty()) book.title = title;
                
                System.out.print("Enter new Author: ");
                String author = sc.nextLine().trim();
                if (!author.isEmpty()) book.author = author;
                
                System.out.print("Enter new Genre: ");
                book.genre = sc.nextLine();
                
                System.out.print("Enter Availability (Available/Checked Out): ");
                String availability = sc.nextLine().trim();
                if (availability.equalsIgnoreCase("Available")) {
                    book.isAvailable = true;
                } else if (availability.equalsIgnoreCase("Checked Out")) {
                    book.isAvailable = false;
                } else {
                    System.out.println("Error: Availability must be either 'Available' or 'Checked Out'.");
                    return;
                }
                
                System.out.println("Book updated successfully!");
                return;
            }
        }
        System.out.println("Book not found!");
    }

    @Override
    public void deleteBookRecord() {
        System.out.print("Enter Book ID to delete: ");
        int id = sc.nextInt();
        if (books.removeIf(book -> book.id == id)) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book not found!");
        }
    }

    @Override
    public void borrowBook() {
        System.out.print("Enter Book ID to borrow: ");
        int id = sc.nextInt();
        for (Book book : books) {
            if (book.id == id && book.isAvailable) {
                book.isAvailable = false;
                System.out.println("Book borrowed successfully!");
                return;
            }
        }
        System.out.println("Book not available!");
    }

    @Override
    public void returnBook() {
        System.out.print("Enter Book ID to return: ");
        int id = sc.nextInt();
        for (Book book : books) {
            if (book.id == id && !book.isAvailable) {
                book.isAvailable = true;
                System.out.println("Book returned successfully!");
                return;
            }
        }
        System.out.println("Invalid Book ID!");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookServiceInterface service = new BookServiceImpl();
        
        while (true) {
            System.out.println("\nWelcome to Book Management Application");
            System.out.println("1. Add Book\n2. Show All Books\n3. Show Available Books\n4. Search Book by ID or Title\n5. Update Book Details\n6. Delete Book Record\n7. Borrow Book\n8. Return Book\n9. Exit");
            System.out.print("Enter Your Choice: ");
            int ch = sc.nextInt();
            
            switch (ch) {
                case 1: service.addBook(); break;
                case 2: service.showAllBooks(); break;
                case 3: service.showAllAvailableBooks(); break;
                case 4: service.searchBookByIdOrTitle(); break;
                case 5: service.updateBookDetails(); break;
                case 6: service.deleteBookRecord(); break;
                case 7: service.borrowBook(); break;
                case 8: service.returnBook(); break;
                case 9: System.out.println("Thank you for using the application!"); System.exit(0);
                default: System.out.println("Please enter a valid choice!");
            }
        }
    }
}
