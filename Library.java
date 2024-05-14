import java.util.*;

// Enumeration for Book Categories
enum BookCategory {
    FICTION,
    NON_FICTION,
    SCIENCE_FICTION,
    MYSTERY,
    THRILLER,
    HORROR,
    ROMANCE,
    HISTORY,
    BIOGRAPHY,
    SELF_HELP,
    EDUCATION,
    TECHNOLOGY,
    ART,
    PHILOSOPHY,
    POETRY,
    DRAMA
}

// User class for authentication
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Method to authenticate user
    public boolean authenticate(String enteredPassword) {
        return password.equals(enteredPassword);
    }
}

// Book class
class Book {
    private String title;
    private String author;
    private BookCategory category;
    private boolean available;

    public Book(String title, String author, BookCategory category) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BookCategory getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

// Library class
public class Library {
    private List<Book> books;
    private Map<String, User> users;

    public Library() {
        books = new ArrayList<>();
        users = new HashMap<>();
    }

    // Method to add a book to the library
    public void addBook(String title, String author, BookCategory category) {
        Book book = new Book(title, author, category);
        books.add(book);
    }

    // Method to register a user
    public void registerUser(String username, String password) {
        User user = new User(username, password);
        users.put(username, user);
    }

    // Method to borrow a book
    public void borrowBook(String username, String password, String title) {
        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            for (Book book : books) {
                if (book.getTitle().equals(title) && book.isAvailable()) {
                    book.setAvailable(false);
                    System.out.println(username + " has borrowed " + title);
                    return;
                }
            }
            System.out.println("Book not available or doesn't exist");
        } else {
            System.out.println("Invalid credentials");
        }
    }

    // Method to return a book
    public void returnBook(String username, String password, String title) {
        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            for (Book book : books) {
                if (book.getTitle().equals(title) && !book.isAvailable()) {
                    book.setAvailable(true);
                    System.out.println(username + " has returned " + title);
                    return;
                }
            }
            System.out.println("You didn't borrow this book or it doesn't exist");
        } else {
            System.out.println("Invalid credentials");
        }
    }

    // Method to categorize books
    public void categorizeBooks() {
        Map<BookCategory, List<Book>> categorizedBooks = new HashMap<>();
        for (Book book : books) {
            List<Book> categoryBooks = categorizedBooks.getOrDefault(book.getCategory(), new ArrayList<>());
            categoryBooks.add(book);
            categorizedBooks.put(book.getCategory(), categoryBooks);
        }
        for (Map.Entry<BookCategory, List<Book>> entry : categorizedBooks.entrySet()) {
            System.out.println("Category: " + entry.getKey());
            for (Book book : entry.getValue()) {
                System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor());
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Library library = new Library();

        // Adding some books to the library
        library.addBook("To Kill a Mockingbird", "Harper Lee", BookCategory.FICTION);
        library.addBook("1984", "George Orwell", BookCategory.SCIENCE_FICTION);
        library.addBook("The Great Gatsby", "F. Scott Fitzgerald", BookCategory.ROMANCE);
        library.addBook("The Da Vinci Code", "Dan Brown", BookCategory.MYSTERY);

        // Registering some users
        library.registerUser("user1", "password1");
        library.registerUser("user2", "password2");

        // Borrowing and returning books
        library.borrowBook("user1", "password1", "To Kill a Mockingbird");
        library.borrowBook("user2", "password2", "The Great Gatsby");
        library.returnBook("user1", "password1", "To Kill a Mockingbird");

        // Categorizing books
        library.categorizeBooks();
    }
}
