import javax.annotation.processing.FilerException;
import javax.print.DocFlavor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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

    public Code init(String filename)  {
        File file;
        Scanner scanner;
        String line;

        try {
            file = new File(filename);
            scanner = new Scanner(file);

        } catch (Exception e){
            return Code.FILE_NOT_FOUND_ERROR;
        }

        while(scanner.hasNextLine()){
            line = scanner.nextLine();

            if(line.split()){

            }



        }




    }

    private initBooks()

    private initShelves()

    private initReader()

    public Code addBook(Book newBook){

        // Checks if book is in HashMap
        if(books.containsKey(newBook)){
            // if it does it increments the count
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
                System.out.println("Could not return " + book);
                return temp;
            }

        }

    }

    public Code returnBook(Book book){

    }












}
