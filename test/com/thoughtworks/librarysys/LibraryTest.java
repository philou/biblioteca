package com.thoughtworks.librarysys;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LibraryTest {

    @Test
    public void shouldCheckOutABookPassedToIt() {
        Books books = mock(Books.class);
        Movies movies = mock(Movies.class);
        Library library = new Library(books, movies);

        Book bookToCheckOut = new Book("Book", "Author", 0);
        library.checkOut(bookToCheckOut);

        verify(books).checkOut(bookToCheckOut);
    }

    @Test
    public void shouldCheckOutAMoviePassedToIt() {
        Books books = mock(Books.class);
        Movies movies = mock(Movies.class);
        Library library = new Library(books, movies);

        Movie movieToCheckOut = new Movie("Movie", 1000, "Director", 0);
        library.checkOut(movieToCheckOut);
    }
}