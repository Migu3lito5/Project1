/*
    Name: Miguel A. Francisco
    Date: 2/13/2022
    Description: The class of Reader
 */

import java.util.ArrayList;
import java.util.List;


import java.util.Objects;



public class Reader {

    public static final int CARD_NUMBER_ = 0;
    public static final int NAME_ = 1;
    public static final int PHONE_ = 2;
    public static final int BOOK_COUNT_ = 3;
    public static final int BOOK_START_ = 4;

    private int cardNumber;
    private String name;
    private String phone;
    private List<Book> books;


    public Reader(int cardNumber, String name, String phone) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.phone = phone;
        books = new ArrayList<>();
    }


    public Code addBook(Book book){

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).equals(book)) {
                return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;
            }
        }
        books.add(book);
        return Code.SUCCESS;

    }


    public Code removeBook(Book book){
        if(books.size() < 1){
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }

        for(int i = 0; i < books.size(); i++){
            if(books.get(i).equals(book)){
                books.remove(i);            //maybe put that error here
                return Code.SUCCESS;
            }
        }

        return Code.READER_DOESNT_HAVE_BOOK_ERROR;
    }

    public boolean hasBook(Book book){
        if(books.size() < 1){
            return false;
        }
        for(int i = 0; i < books.size(); i++){
            if(books.get(i).equals(book)){
                return true;
            }
        }
        return false;
    }

    public int getBookCount(){
        return books.size();
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return cardNumber == reader.cardNumber && name.equals(reader.name) && phone.equals(reader.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, name, phone);
    }


    @Override
    public String toString() {
        String a =  name + "(#" + cardNumber + ") ";
        String b = "{";

        for(int i = 0; i < books.size(); i++){
            b = b + books.get(i).toString() + ",";
        }
        return a + b + "}";

    }

}
