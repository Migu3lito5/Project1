
/*
    Name: Miguel A. Francisco
    Date: 2/13/2022
    Description:
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


class ReaderTest {

    Reader testReader;

    int testCardNumber = 1234;
    String testName = "Bobby";
    String testPhone = "123-567-7891";
    Book testBook = new Book("1234","Hello","Science",0,"Tim",LocalDate.now());
    List<Book> testList;
    Reader reader = new Reader(1234,"Bobby","123-567-7891");


    @BeforeEach
    void setUp() {
        System.out.println("Test Before: ");
        testReader = new Reader(testCardNumber,testName,testPhone);
        testList = new ArrayList<>();
        //testReader.setBooks(testList);

    }

    @AfterEach
    void tearDown() {
        System.out.println("Test after: ");
        testReader = null;
        testList = null;
    }

    @Test
    void addBook() {
        assertEquals(testReader.addBook(testBook),Code.SUCCESS);
        testReader.addBook(testBook);
        assertNotEquals(testReader.addBook(testBook),Code.SUCCESS);
        assertEquals(testReader.addBook(testBook),Code.BOOK_ALREADY_CHECKED_OUT_ERROR);

    }

    @Test
    void removeBook() {
        assertEquals(testReader.removeBook(testBook),Code.READER_DOESNT_HAVE_BOOK_ERROR);
        testReader.addBook(testBook);
        assertEquals(testReader.removeBook(testBook),Code.SUCCESS);
    }

    @Test
    void hasBook() {
        Book testBook = new Book("1234","Hello","Science",0,"Tim",LocalDate.now());
        assertFalse(testReader.hasBook(testBook));
        testReader.addBook(testBook);
        assertTrue(testReader.hasBook(testBook));
    }

    @Test
    void getBookCount() {
        Reader reader = new Reader(9876,"Mike","2340985467");
        assertEquals(reader.getBookCount(),0);
        reader.addBook(testBook);
        assertEquals(reader.getBookCount(),1);
        reader.removeBook(testBook);
        assertEquals(reader.getBookCount(),0);

    }

    @Test
    void getCardNumber() {
        assertEquals(testCardNumber,testReader.getCardNumber());
    }

    @Test
    void setCardNumber() {
        assertEquals(testCardNumber,testReader.getCardNumber());
        testReader.setCardNumber(2222);
        assertNotEquals(testCardNumber,testReader.getCardNumber());
    }

    @Test
    void getName() {
        assertEquals(testName,testReader.getName());
    }

    @Test
    void setName() {
        assertEquals(testName,testReader.getName());
        testReader.setName("Levi");
        assertNotEquals(testName,testReader.getName());
    }

    @Test
    void getPhone() {
        assertEquals(testPhone,testReader.getPhone());
    }

    @Test
    void setPhone() {
        assertEquals(testPhone,testReader.getPhone());
        testReader.setPhone("11111111");
        assertNotEquals(testPhone,testReader.getPhone());

    }

    @Test
    void getBooks() {

        assertEquals(testList,testReader.getBooks());
    }

    @Test
    void setBooks() {
        assertEquals(testList,testReader.getBooks());
        List<Book> a = new ArrayList<>();
        a.add(testBook);
        testReader.setBooks(a);
        assertNotEquals(testList,testReader.getBooks());
    }

    @Test
    void testEquals() {

        assertEquals(reader,testReader);
        reader.addBook(testBook);
        testReader.addBook(testBook);
        assertEquals(reader,testReader);
    }

    @Test
    void testHashCode() {
        assertEquals(reader,testReader);
        reader.addBook(testBook);
        testReader.addBook(testBook);
        assertEquals(reader,testReader);
    }
}