/*
    Name: Miguel A. Francisco
    Date: 3/6/2022
    Description: A shelf object that takes a book object and keeps track of the amount of books it keeps
    and the amount of copies of books.
 */

import java.util.HashMap;

public class Shelf {

    public static final int SHELF_NUMBER_ = 0;
    public static final int SUBJECT_ = 1;

    private int shelfNumber;
    private String subject;
    private HashMap<Book, Integer> books;

    public Shelf(int shelfNumber, String subject) {
        this.shelfNumber = shelfNumber;
        this.subject = subject;
        books = new HashMap<>();
    }


    public int getBookCount(Book book){

        if(books.containsKey(book)){
            return books.get(book);
        }

        return -1;
    }

    public Code addBook(Book book){

        if(books.containsKey(book)){

            books.put(book,books.get(book) + 1);
            return Code.SUCCESS;

        } else {
            if(book.getSubject().equals(subject)){
                books.put(book, 1);
                return Code.SUCCESS;
            } else {
                return Code.SHELF_SUBJECT_MISMATCH_ERROR;
            }
        }
    }

    public Code removeBook(Book book){
        if(!books.containsKey(book)){
            System.out.println(book.getTitle() + " is not on shelf: " + subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        } else {
            if(books.get(book) == 0){
                System.out.println("no copies of " + book.getTitle() +
                        " on shelf: " + subject);
                return Code.BOOK_NOT_IN_INVENTORY_ERROR;
            } else {
                books.put(book, books.get(book) - 1);
                return Code.SUCCESS;
            }
        }
    }

    public String listBook(){
        StringBuilder phrase = new StringBuilder(books.size() + " book(s) on shelf: " + toString() + "\n");

       for(Book book : books.keySet()){
           phrase.append(book.toString());
       }

        return phrase.toString();
    }
    public int getShelfNumber() {
        return shelfNumber;
    }

    public String getSubject() {
        return subject;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shelf shelf = (Shelf) o;

        if (getShelfNumber() != shelf.getShelfNumber()) return false;
        return getSubject() != null ? getSubject().equals(shelf.getSubject()) : shelf.getSubject() == null;
    }

    @Override
    public int hashCode() {
        int result = getShelfNumber();
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return shelfNumber + " : " + subject;
    }

}
