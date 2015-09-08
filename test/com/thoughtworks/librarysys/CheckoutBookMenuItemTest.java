package com.thoughtworks.librarysys;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class CheckoutBookMenuItemTest {

    @Test
    public void shouldCheckoutABookFromTheListOfBooksInTheLibraryByInputtingTheNameOfTheBook() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("Gone Girl\n".getBytes());
        System.setIn(inputStream);
        ArrayList<Book> listOfBooks = new ArrayList<Book>();
        Book bookOne = new Book("Gone Girl", "Gillian Flynn", 2014);
        Book bookTwo = new Book("Kite Runner", "Khaled Hossieni", 2003);
        Book bookThree = new Book("Inferno", "Dan Brown", 2012);
        listOfBooks.add(bookOne);
        listOfBooks.add(bookTwo);
        listOfBooks.add(bookThree);
        Library library = new Library(listOfBooks);
        CheckoutBookMenuItem checkoutBookMenuItem = new CheckoutBookMenuItem(library);

        assertEquals("Done", checkoutBookMenuItem.performOperation());
        System.setIn(System.in);
    }

    @Test
    public void shouldNotCheckOutABookFromTheLibraryIfItIsNotABookFromTheLibrary() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("Head First Java\n".getBytes());
        System.setIn(inputStream);
        ArrayList<Book> listOfBooks = new ArrayList<>();
        Book bookOne = new Book("Gone Girl", "Gillian Flynn", 2014);
        Book bookTwo = new Book("Kite Runner", "Khaled Hossieni", 2003);
        Book bookThree = new Book("Inferno", "Dan Brown", 2012);
        listOfBooks.add(bookOne);
        listOfBooks.add(bookTwo);
        listOfBooks.add(bookThree);
        Library library = new Library(listOfBooks);
        CheckoutBookMenuItem checkoutBookMenuItem = new CheckoutBookMenuItem(library);

        assertEquals("Can't perform OP", checkoutBookMenuItem.performOperation());
        System.setIn(System.in);
    }
}