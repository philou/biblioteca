package com.thoughtworks.librarysys;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BooksTest {

    @Test
    public void shouldReturnTheNamesOfAllTheBooksInTheList() {
        ArrayList<Book> listOfBooks = new ArrayList<>();
        Book bookOne = new Book("Gone Girl", "Gillian Flynn", 2014);
        Book bookTwo = new Book("Kite Runner", "Khaled Hossieni", 2003);
        Book bookThree = new Book("Inferno", "Dan Brown", 2012);
        listOfBooks.add(bookOne);
        listOfBooks.add(bookTwo);
        listOfBooks.add(bookThree);
        HashMap<Book, User> listOfBooksCheckedOutByUsers = new HashMap<>();
        ConsoleView consoleView = mock(ConsoleView.class);
        LibraryObserver libraryObserver = new LibraryObserver(consoleView);
        Books books = new Books(listOfBooks, listOfBooksCheckedOutByUsers, libraryObserver);

        String testString = String.format("%085d\n", 0).replace("0","-") +
                String.format("%-30s%-30s%-20s\n", "Name Of The Book", "Author", "Year Of Publication") +
                String.format("%085d\n", 0).replace("0", "-") +
                String.format("%-30s%-30s%-20s\n", "Gone Girl", "Gillian Flynn", 2014) +
                String.format("%-30s%-30s%-20s\n", "Kite Runner", "Khaled Hossieni", 2003) +
                String.format("%-30s%-30s%-20s\n", "Inferno", "Dan Brown", 2012) + "\n";

        assertEquals(testString, books.displayListOfBookDetails());
    }

    @Test
    public void shouldNotIncludeABookInItsListOnceItIsCheckedOut() {
        ArrayList<Book> listOfBooks = new ArrayList<>();
        Book bookOne = new Book("Gone Girl", "Gillian Flynn", 2014);
        Book bookTwo = new Book("Kite Runner", "Khaled Hossieni", 2003);
        Book bookThree = new Book("Inferno", "Dan Brown", 2012);
        listOfBooks.add(bookOne);
        listOfBooks.add(bookTwo);
        listOfBooks.add(bookThree);
        HashMap<Book, User> listOfBooksCheckedOutByUsers = new HashMap<>();
        LibraryObserver libraryObserver = mock(LibraryObserver.class);
        Books books = new Books(listOfBooks, listOfBooksCheckedOutByUsers, libraryObserver);
        User user = mock(User.class);

        books.checkOut(bookTwo, user);

        verify(libraryObserver).notifySuccessfulBookCheckout();
    }

    @Test
    public void cannotCheckoutABookThatIsNotPresentInTheLibrary() {
        ArrayList<Book> listOfBooks = new ArrayList<>();
        Book bookOne = new Book("Gone Girl", "Gillian Flynn", 2014);
        Book bookTwo = new Book("Kite Runner", "Khaled Hossieni", 2003);
        Book bookThree = new Book("Inferno", "Dan Brown", 2012);
        listOfBooks.add(bookOne);
        listOfBooks.add(bookTwo);
        listOfBooks.add(bookThree);
        HashMap<Book, User> listOfBooksCheckedOutByUsers = new HashMap<>();
        LibraryObserver libraryObserver = mock(LibraryObserver.class);
        Books books = new Books(listOfBooks, listOfBooksCheckedOutByUsers, libraryObserver);
        User user = mock(User.class);

        Book notALibraryBook = new Book("Head First java", "Bert Bates", 2003);
        books.checkOut(notALibraryBook, user);

        verify(libraryObserver).notifyUnsuccessfulBookCheckout();
    }

    @Test
    public void shouldReturnACheckedOutBookByTheSameUserWhoBorrowedItToTheLibrary() {
        ArrayList<Book> listOfBooks = new ArrayList<>();
        Book bookOne = new Book("Gone Girl", "Gillian Flynn", 2014);
        Book bookTwo = new Book("Kite Runner", "Khaled Hossieni", 2003);
        listOfBooks.add(bookOne);
        listOfBooks.add(bookTwo);
        HashMap<Book, User> listOfBooksCheckedOutByUsers = new HashMap<>();
        Book borrowedBook = new Book("Inferno", "Dan Brown", 2012);
        User user = new User("John", "abc@mail.com", "12345678", "222-2222", "password2", "Registered");
        listOfBooksCheckedOutByUsers.put(borrowedBook, user);
        LibraryObserver libraryObserver = mock(LibraryObserver.class);
        Books books = new Books(listOfBooks, listOfBooksCheckedOutByUsers, libraryObserver);

        Book libraryBook = new Book("Inferno", "Author", 0);
        books.toReturn(libraryBook, user);

        verify(libraryObserver).notifySuccessfulBookReturn();
    }

    @Test
    public void shouldNotReturnABookThatDoesNotBelongToTheLibrary() {
        ArrayList<Book> listOfBooks = new ArrayList<>();
        Book bookOne = new Book("Gone Girl", "Gillian Flynn", 2014);
        Book bookTwo = new Book("Kite Runner", "Khaled Hossieni", 2003);
        listOfBooks.add(bookOne);
        listOfBooks.add(bookTwo);
        HashMap<Book, User> listOfBooksCheckedOutByUsers = new HashMap<>();
        Book borrowedBook = new Book("Inferno", "Dan Brown", 2012);
        User user = new User("Mary", "xyz@mail.com", "22345678", "222-2222", "password2", "Registered");
        listOfBooksCheckedOutByUsers.put(borrowedBook, user);
        LibraryObserver libraryObserver = mock(LibraryObserver.class);
        Books books = new Books(listOfBooks, listOfBooksCheckedOutByUsers, libraryObserver);
        User returningUser = mock(User.class);

        Book libraryBook = new Book("Head First Java", "Author", 0);
        books.toReturn(libraryBook, returningUser);

        verify(libraryObserver).notifyUnsuccessfulBookReturn();
    }

    @Test
    public void shouldNotReturnABookToTheLibraryFromADifferentUserEvenIfItBelongsToTheLibrary() {
        ArrayList<Book> listOfBooks = new ArrayList<>();
        Book bookOne = new Book("Gone Girl", "Gillian Flynn", 2014);
        Book bookTwo = new Book("Kite Runner", "Khaled Hossieni", 2003);
        listOfBooks.add(bookOne);
        listOfBooks.add(bookTwo);
        HashMap<Book, User> listOfBooksCheckedOutByUsers = new HashMap<>();
        Book borrowedBook = new Book("Inferno", "Dan Brown", 2012);
        User user = new User("Mary", "xyz@mail.com", "22345678", "222-2222", "password2", "Registered");
        listOfBooksCheckedOutByUsers.put(borrowedBook, user);
        LibraryObserver libraryObserver = mock(LibraryObserver.class);
        Books books = new Books(listOfBooks, listOfBooksCheckedOutByUsers, libraryObserver);
        User returningUser = new User("John", "abc@mail.com", "12345678", "111-1111", "password1", "Registered");

        Book libraryBook = new Book("Inferno", "Author", 0);
        books.toReturn(libraryBook, returningUser);

        verify(libraryObserver).notifyInvalidUser();
    }

    @Test
    public void shouldContainTheBookWhichWasReturnedInTheListOfAvailableBooks() {
        ArrayList<Book> listOfBooks = new ArrayList<>();
        Book bookOne = new Book("Gone Girl", "Gillian Flynn", 2014);
        Book bookTwo = new Book("Kite Runner", "Khaled Hossieni", 2003);
        listOfBooks.add(bookOne);
        listOfBooks.add(bookTwo);
        HashMap<Book, User> listOfBooksCheckedOutByUsers = new HashMap<>();
        Book borrowedBook = new Book("Inferno", "Dan Brown", 2012);
        User user = new User("Mary", "xyz@mail.com", "22345678", "222-2222", "password2", "Registered");
        listOfBooksCheckedOutByUsers.put(borrowedBook, user);
        LibraryObserver libraryObserver = mock(LibraryObserver.class);
        Books books = new Books(listOfBooks, listOfBooksCheckedOutByUsers, libraryObserver);
        Book libraryBook = new Book("Inferno", "Author", 0);

        books.toReturn(libraryBook, user);
        String testString = String.format("%085d\n", 0).replace("0","-") +
                String.format("%-30s%-30s%-20s\n", "Name Of The Book", "Author", "Year Of Publication") +
                String.format("%085d\n", 0).replace("0", "-") +
                String.format("%-30s%-30s%-20s\n", "Gone Girl", "Gillian Flynn", 2014) +
                String.format("%-30s%-30s%-20s\n", "Kite Runner", "Khaled Hossieni", 2003) +
                String.format("%-30s%-30s%-20s\n", "Inferno", "Dan Brown", 2012) + "\n";

        assertEquals(testString, books.displayListOfBookDetails());
    }

    @Test
    @Ignore
    public void shouldListCheckOutDetailsOfTheBorrowedBooksInTheLibrary() {
        ArrayList<Book> listOfBooks = new ArrayList<>();
        Book bookOne = new Book("Gone Girl", "Gillian Flynn", 2014);
        listOfBooks.add(bookOne);
        HashMap<Book, User> listOfBooksCheckedOutByUsers = new HashMap<>();
        Book borrowedBookOne = new Book("Inferno", "Dan Brown", 2012);
        Book borrowedBookTwo = new Book("Kite Runner", "Khaled Hossieni", 2003);
        User userOne = new User("Mary", "xyz@mail.com", "22345678", "222-2222", "password2", "Registered");
        User userTwo = new User("David", "pqr@mail.com", "32345678", "333-3333", "password3", "Registered");
        listOfBooksCheckedOutByUsers.put(borrowedBookOne, userOne);
        listOfBooksCheckedOutByUsers.put(borrowedBookTwo, userTwo);
        LibraryObserver libraryObserver = mock(LibraryObserver.class);
        Books books = new Books(listOfBooks, listOfBooksCheckedOutByUsers, libraryObserver);


        String testString = String.format("%085d\n", 0).replace("0","-") +
                String.format("%-25s%-25s%-25s%-20s\n", "Name of the book", "Author", "Year Of Publication", "Library ID") +
                String.format("%085d\n", 0).replace("0", "-") +
                String.format("%-25s%-25s%-25s%-20s\n", "Inferno", "Dan Brown", "2012", "222-2222") +
                String.format("%-25s%-25s%-25s%-20s\n\n", "Kite Runner", "Khaled Hossieni", "2003", "333-3333") ;

        assertEquals(testString, books.listCheckoutDetails());
    }
}