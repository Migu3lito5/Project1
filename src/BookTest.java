/*
    Name: Miguel A. Francisco
    Date: 2/13/2022
    Description: The test for the book class
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {



    Book book = new Book(null,null,null,0,null,null);
    String testIsbn = "1337";
    String testTitle = "Headfirst Java";
    String testSubject = "education";
    int testPageCount = 1337;
    String testAuthor = "Grady Booch";
    LocalDate testLocalDate = LocalDate.now();


    @BeforeEach
    void setUp() {
        System.out.println("Test Before: ");
        book = new Book(testIsbn,testTitle,testSubject,
                testPageCount,testAuthor,testLocalDate);

    }

    @AfterEach
    void tearDown() {
        System.out.println("Test after: ");
        book = null;
    }

    @Test
    void constructorTest() {
        Book testBook = null;
        assertNull(testBook);
        testBook = new Book(testIsbn,testTitle,testSubject,
                testPageCount,testAuthor,testLocalDate);
        assertNotNull(testBook);
    }

    @Test
    void getIsbn() {
        assertEquals(testIsbn, book.getIsbn());

    }

    @Test
    void setIsbn() {
        assertEquals(testIsbn, book.getIsbn());
        book.setIsbn("1111");
        assertNotEquals(testIsbn, book.getIsbn());
    }

    @Test
    void getTitle() {
        assertEquals(testTitle, book.getTitle());
    }

    @Test
    void setTitle() {
        assertEquals(testTitle, book.getTitle());
        book.setTitle("Headfirst C++");
        assertNotEquals(testTitle,book.getTitle());
    }

    @Test
    void getSubject() {
        assertEquals(testSubject, book.getSubject());
    }

    @Test
    void setSubject() {
        assertEquals(testSubject, book.getSubject());
        book.setSubject("Biology");
        assertNotEquals(testSubject,book.getSubject());
    }

    @Test
    void getPageCount() {
        assertEquals(testPageCount, book.getPageCount());
    }

    @Test
    void setPageCount() {
        assertEquals(testPageCount, book.getPageCount());
        book.setPageCount(1);
        assertNotEquals(testPageCount,book.getPageCount());
    }

    @Test
    void getAuthor() {
        assertEquals(testAuthor, book.getAuthor());
    }

    @Test
    void setAuthor() {
        assertEquals(testAuthor, book.getAuthor());
        book.setAuthor("Bobby Smith");
        assertNotEquals(testAuthor,book.getAuthor());
    }

    @Test
    void getDueDate() {
        assertEquals(testLocalDate, book.getDueDate());
    }

    @Test
    void setDueDate() {
        assertEquals(testLocalDate, book.getDueDate());
        book.setDueDate(testLocalDate.plusDays(1));
        assertNotEquals(testLocalDate, book.getDueDate());
    }

    @Test
    void testEquals() {
        Book book2 = new Book("1010","Not a book", "Mathematics",11111,"Micheal Crichton",
                LocalDate.now().plusDays(2));
        assertNotEquals(book,book2);
        Book testBook = new Book("1010","Not a book", "Mathematics",11111,"Micheal Crichton",
                LocalDate.now().plusDays(2));
        assertEquals(book2,testBook);
    }

    @Test
    void testHashCode() {
        Book book2 = new Book("1010","Not a book", "Mathematics",11111,"Micheal Crichton",
                LocalDate.now().plusDays(2));
        assertNotEquals(book,book2);
        Book testBook = new Book("1010","Not a book", "Mathematics",11111,"Micheal Crichton",
                LocalDate.now().plusDays(2));
        assertEquals(book2,testBook);

    }

}