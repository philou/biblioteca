package com.thoughtworks.librarysys;

public class InputParser {
    MainMenuItem mainMenuItem;
    Library library;

    public InputParser(MainMenuItem mainMenuItem, Library library) {
        this.mainMenuItem = mainMenuItem;
        this.library = library;
    }

    public MainMenuItem parse(String userChoice) {
        if (userChoice.equals("1"))
            return new ListBooksMenuItem(library);
        else
            return new QuitMenuItem();
    }
}
