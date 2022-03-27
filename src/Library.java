import javax.annotation.processing.FilerException;
import javax.print.DocFlavor;
import javax.swing.text.html.HTMLDocument;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Library {

    public final static int LENDING_LIMIT = 5;

    private String name;
    private static int libraryCard;
    private List<Reader> readers;
    private HashMap<String, Shelf> shelves;
    private HashMap<Book, Integer> books;

    public Library(String name) {
        this.name = name;
    }

    public  Code init(String filename)  {
        //TODO:

        // These two variables aid in parsing through the file
        String lineInFile = "";
        int countOfRecords = 0;

        // Making the objects that open and read the file passed through
        File file = new File(filename);
        Scanner scanner = null;

        // if file is not found then it catches it
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            return Code.FILE_NOT_FOUND_ERROR;
        }

        if(scanner.hasNextLine()){
            lineInFile = scanner.nextLine().trim();
            countOfRecords = convertInt(lineInFile, Code.BOOK_COUNT_ERROR);
            initBooks(countOfRecords, scanner);
        }



    }

    private Code initBooks(int bookCount, Scanner scan){
        //TODO:
        String[] bookData;
        Book book;
        LocalDate date;
        int pageCount;

        if(bookCount < 1){
            return Code.LIBRARY_ERROR;
        }

        for(int i = 0; i < bookCount; i++){
            bookData = scan.nextLine().split(",");
            
            pageCount = convertInt(bookData[Book.PAGE_COUNT_], Code.PAGE_COUNT_ERROR);
            date = convertDate(bookData[Book.DUE_DATE_], Code.DATE_CONVERSION_ERROR);
            
            if(pageCount <= 0){
                return Code.PAGE_COUNT_ERROR;
            } else if(date == null){
                return Code.DATE_CONVERSION_ERROR;
            } else {
                book = new Book(bookData[Book.ISBN_], bookData[Book.TITLE_], bookData[Book.SUBJECT_],
                        pageCount, bookData[Book.AUTHOR_], date);
                addBook(book);
            }
        }
         return Code.SUCCESS;
    }


    private Code initShelves(int shelfCount, Scanner scan){
        //TODO:
    }

    private Code initReader(int ReaderCount, Scanner scan){
        //TODO:
    }

    public Code addBook(Book newBook){

        // Checks if book is in HashMap
        if(books.containsKey(newBook)){
            // if it does it increment the count
            books.put(newBook, books.get(newBook) + 1);
            System.out.println(books.get(newBook) +
                    " copies of " + newBook.getTitle() + " in the stacks");
        } else {
            // if it does not, it sets the count to 1
            books.put(newBook, 1);
            System.out.println(newBook.getTitle() + "added to the stacks");
        }

        // checks to see whether the subject exists in shelf
        if(shelves.containsKey(newBook.getSubject())){
            //adds the book to the respective shelf
            shelves.get(newBook.getSubject()).addBook(newBook);
            return Code.SUCCESS;
        } else {
            // This prints out if the subject does not exist
            System.out.println("No shelf for " + newBook.getSubject() + " books");
            return Code.SHELF_EXISTS_ERROR;
        }

    }

    public Code returnBook(Reader reader, Book book){

        Code temp;
        // checks if the reader has books
        if(!reader.hasBook(book)){
            // if they don't this runs
            System.out.println(reader.getName() + " doesn't have " +
                    book.getTitle() + " checked out");
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }

        // checks to see if the book exists in the library
        if(!books.containsKey(book)){
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else {
            // if it does it prints out a phrase and removes the book
            System.out.println(reader.getName() + " is returning " + book);
            temp = reader.removeBook(book);
            // if the removal of the book was succesful
            if(temp.equals(Code.SUCCESS)){
                return returnBook(book);
            } else {
                // the removal of the book not successful
                System.out.println("Could not return " + book.toString());
                return temp;
            }

        }

    }

    public Code returnBook(Book book){

        // Checks if the subject exists in the hashmap of shelves
        if(!shelves.containsKey(book.getSubject())){
           // if not, it returns an error
            System.out.println("No shelf for " + book);
            return Code.SHELF_EXISTS_ERROR;
        }else {
            // if it does, it adds it to the shelf
            shelves.get(book.getSubject()).addBook(book);
            // Message the tutors about this return statement
            return Code.SUCCESS;
        }


    }

    private Code addBookToShelf(Book book, Shelf shelf){
        Code temp;
        temp = returnBook(book);

        if(temp.getCode() == 0){
            return Code.SUCCESS;
        }

        if(!shelf.getSubject().equals(book.getSubject())){
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }

        if(!shelf.addBook(book).equals(Code.SUCCESS)){
            System.out.println("Could not add " + book + " to shelf");
        } else {
            System.out.println(book + " added to shelf");
            return Code.SUCCESS;
        }

        return temp;
    }

    public int listBook(){
        //TODO:


    }

    public Code checkOutBook(Reader reader, Book book) {
        Code temp;

        if(!readers.contains(reader)){
            System.out.println(reader.getName() + " doesn't have an account here");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }

        if(reader.getBookCount() > LENDING_LIMIT){
            System.out.println(reader.getName() + " has reached the lending limit, ("
                    + LENDING_LIMIT + ")" );
            return Code.BOOK_LIMIT_REACHED_ERROR;
        }

        if(!books.containsKey(book)){
            System.out.println("Error: could not find " + book);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        if(!shelves.containsKey(book.getSubject())){
            System.out.println("no shelf for " + book.getSubject() + " books!");
            return Code.SHELF_EXISTS_ERROR;
        }

        if(shelves.get(book.getSubject()).getBookCount(book) < 1){
            System.out.println("Error: no copies of " + book + " remain");
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        temp = reader.addBook(book);
        if(temp.getCode() != 0){
            System.out.println("Couldn't checkout " + book);
            return temp;
        }

        if(shelves.get(book.getSubject()).removeBook(book).getCode() == 0){
            System.out.println(book + "checked out successfully");
        }

        return temp;
    }

    public Book getBookByISBN(String isbn) {


        for (Book book : books.keySet()) {

            if (book.getIsbn().equals(isbn)){
                return book;
            }
        }

        System.out.println("Error: could not find a book with isbn: " + isbn);
        return null;
    }

    public Code listShelves(boolean showBooks){
        if(showBooks){

            for(Shelf shelf : shelves.values()){
                shelf.listBook();
            }
        } else{
            for(Shelf shelf : shelves.values()){
                shelf.toString();
            }
        }
        return Code.SUCCESS;
    }

    public Code addShelf(String shelfSubject){
        Shelf aShelf = new Shelf(shelves.size() + 1,shelfSubject);
        return addShelf(aShelf);
    }

    public Code addShelf(Shelf shelf){
        // if shelf exist print out this and return an error
        if(shelves.containsKey(shelf.getSubject())){
            System.out.println("Error: Shelf already exists" + shelf);
            return Code.SHELF_EXISTS_ERROR;
        } else {
            // adds the shelf to the hashmap and sets the number to plus one the size of hashmap
            shelves.put(shelf.getSubject(), shelf);
            shelves.get(shelf.getSubject()).setShelfNumber(shelves.size() + 1);

            // iterates through every book object in the hashmap
            for (Book book : books.keySet()) {
                // if the book matches its subject with the shelf its added to the shelf
                if (book.getSubject().equals(shelf.getSubject())) {

                    // gets the count of that type of book and adds that many to the shelf
                   for(int i = 0; i < books.get(book); i++){
                       shelves.get(shelf.getSubject()).addBook(book);
                   }
                }
            }
            return Code.SUCCESS;

        }
    }

    public Shelf getShelf(Integer shelfNumber){

        for (Shelf shelf : shelves.values()) {
            if (shelfNumber.equals(shelf.getShelfNumber())) {
                return shelf;
            }
        }

        System.out.println("No shelf number " + shelfNumber + "found");
        return null;

    }

    public Shelf getShelf(String subject){


        if(!shelves.containsKey(subject)){
            System.out.println("No shelf for " + subject + " books");
            return null;
        }

        return shelves.get(subject);
    }

    public int listReaders(){

      Iterator<Reader> reader = readers.iterator();

      while(reader.hasNext()){
          System.out.println(reader.toString());
      }

      return readers.size();
    }

    public int listReaders(boolean showBooks){
        //TODO:


    }

    public Reader getReaderByCard(int cardNumber) {

       for(Reader reader : readers){
           if(reader.getCardNumber() == cardNumber){
               return reader;
           }
       }

        System.out.println("Could not find reader with a card #" + cardNumber);
        return null;
    }

    public Code addReader(Reader reader) {
        if(readers.contains(reader)){
            System.out.println(reader.getName() + " already has an account");
            return Code.READER_ALREADY_EXISTS_ERROR;
        }

      // I probably could use the getReaderByCard method below...
      for(Reader aReaderInList : readers){
          if(aReaderInList.getCardNumber() == reader.getCardNumber()){
              System.out.println(aReaderInList.getName() + " and " +
                      reader.getName() + " have the same card number!");
              return Code.READER_CARD_NUMBER_ERROR;
          }
      }

        readers.add(reader);
        System.out.println(reader.getName() + " added to the library!");
        return Code.SUCCESS;


    }

    public Code removeReader(Reader reader) {

       if(!readers.contains(reader)){
           System.out.println(reader + " is not part of this Library");
           return Code.READER_NOT_IN_LIBRARY_ERROR;
       } else if(readers.contains(reader) && reader.getBookCount() > 0){
           System.out.println(reader.getName() + " must return all books!");
           return Code.READER_STILL_HAS_BOOKS_ERROR;
       } else {
           readers.remove(reader);
           return Code.SUCCESS;
       }

    }

    public static int convertInt(String recordCountString, Code code){
        int intToBeReturned;

        try{
            intToBeReturned = Integer.parseInt(recordCountString);
        } catch (NumberFormatException e){
            System.out.println("Value which caused the error: " + recordCountString);
            System.out.println("Error Message: " + code.getMessage());
            return code.getCode();
        }

        return intToBeReturned;
    }

    public static LocalDate convertDate(String date, Code errorCode){
      // Probably more complicated than it needs too...

        LocalDate dateToBeReturned; // This is used as the thing that will be returned
        String[] arrayOfDate = date.split("-"); //An array filled with date values
        int[] intArray = new int[3]; // This is filled with the values of date after they have been parsed into ints

        if(date.equals("0000")){
           return dateToBeReturned = LocalDate.EPOCH;
        }

        // Checks that a valid input has been passed, if not then dateToBeReturned is set as EPOCH
        if(arrayOfDate.length != 3){
            System.out.println("Error: date conversion error, could not parse " + date);
            System.out.println("Using default date (01-Jan-1970)");
            return dateToBeReturned = LocalDate.EPOCH;
        }

        // Puts all the parsed values into a new int array to make it easier to work with
       for(int i = 0; i < arrayOfDate.length; i++){
           intArray[i]  = convertInt(arrayOfDate[i], Code.DATE_CONVERSION_ERROR);
       }

       // Not sure if this was supposed to be done individually for every index
       if(intArray[0] < 0 || intArray[1] < 0 || intArray[2] < 0 ) {
           System.out.println("Error converting date: Year " + intArray[0] +
                   "\n Error converting date: Month " + intArray[1] +
                   "\n Error converting date: Date " + intArray[2] +
                   "\n Using default date (01-Jan-1970)");
           return dateToBeReturned = LocalDate.EPOCH;
       } else {
          return dateToBeReturned = LocalDate.of(intArray[0], intArray[1], intArray[2]);
       }

    }

    public static int getLibraryCardNumber(){
        return libraryCard + 1;
    }

    private Code errorCode(int codeNumber) {


    }


}
