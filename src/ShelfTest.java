/*
    Name: Miguel A. Francisco
    Date: 3/6/2022
    Description: the tests for the class Shelf.java
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {

    Shelf testShelf;
    int testShelfNum;
    String testSubject;
    HashMap<Book, Integer> testHashMap = null;
    Book testBook = new Book("1234","Hello","Sci-fi",0,"Tim", LocalDate.now());


    @BeforeEach
    void setUp() {
        testShelfNum = 1;
        testSubject = "Sci-fi";
        testHashMap = new HashMap<>();
        testShelf = new Shelf(testShelfNum, testSubject);
    }

    @AfterEach
    void tearDown() {
        testHashMap = null;
    }

    @Test
    void constructorTest(){
        Shelf aShelf = null;
        assertNull(aShelf);
        aShelf = new Shelf(testShelfNum,testSubject);
        assertNotNull(aShelf);
    }

    @Test
    void getBookCount() {
        // generates a random number that is stored in count
        Random rnd = new Random();
        int count = rnd.nextInt(5) + 1;

        // a for loop is run with the value of count as its limit, which adds a book count of times
        for(int i = 0 ; i < count; i++){
            testShelf.addBook(testBook);
        }
        // checks that the amount of books added is == to count
        assertEquals(testShelf.getBookCount(testBook), count);
        testShelf.removeBook(testBook);
        // checks to see whether the amount of testBook is == to count - 1
        assertEquals(testShelf.getBookCount(testBook), count - 1);

        for(int i = 0 ; i < count - 1; i++){
            testShelf.removeBook(testBook);
        }
        assertEquals(testShelf.getBookCount(testBook), 0);
    }

    @Test
    void addBook() {
        Book book = new Book("1233","Bye","Science",0,"Tim", LocalDate.now());
        assertEquals(testShelf.addBook(testBook), Code.SUCCESS);
        assertEquals(testShelf.getBookCount(testBook), 1);
        testShelf.addBook(testBook);
        assertEquals(testShelf.getBookCount(testBook), 2);
        assertEquals(testShelf.addBook(book), Code.SHELF_SUBJECT_MISMATCH_ERROR);
    }

    @Test
    void removeBook() {
        assertEquals(testShelf.removeBook(testBook),Code.BOOK_NOT_IN_INVENTORY_ERROR);
        testShelf.addBook(testBook);
        assertEquals(testShelf.getBookCount(testBook), 1);
        assertEquals(testShelf.removeBook(testBook),Code.SUCCESS);
        assertEquals(testShelf.getBookCount(testBook), 0);
        assertEquals(testShelf.removeBook(testBook),Code.BOOK_NOT_IN_INVENTORY_ERROR);
    }

    @Test
    void listBook() {
        String result = "1 book(s) on shelf: 1 : Sci-fi\n[Hello] by [Tim] ISBN: 1234\n";
        testShelf.addBook(testBook);
        assertEquals(testShelf.listBook(), result);
    }

    @Test
    void getShelfNumber() {
        assertEquals(testShelfNum, 1);
    }

    @Test
    void getSubject() {
        assertEquals(testSubject,testBook.getSubject());
    }

    @Test
    void getBooks() {
      assertEquals(testHashMap, testShelf.getBooks());
    }

    @Test
    void setShelfNumber() {
        assertEquals(testShelfNum, 1);
        testShelf.setShelfNumber(2);
        assertNotEquals(testShelf.getShelfNumber(), testShelfNum);
    }

    @Test
    void setSubject() {
        testShelf.setSubject("Sci-fi");
        assertEquals(testShelf.getSubject(), testSubject);
        testShelf.setSubject("History");
        assertNotEquals(testShelf.getSubject(), testSubject);
    }

    @Test
    void setBooks() {
        HashMap<Book, Integer> aHashMap = new HashMap<>();
        assertEquals(testShelf.getBooks(),testHashMap);
        aHashMap.put(testBook,4);
        testShelf.setBooks(aHashMap);
        assertNotEquals(testShelf.getBooks(),testHashMap);
    }

    @Test
    void testEquals() {
        Shelf shelf = testShelf;
        assertEquals(shelf,testShelf);
        shelf.setShelfNumber(2);
        testShelf.setShelfNumber(2);
        assertEquals(testShelf,shelf);
    }
}