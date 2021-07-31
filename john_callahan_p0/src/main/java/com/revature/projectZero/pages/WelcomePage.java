package com.revature.projectZero.pages;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WelcomePage extends Page {

    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    //Singleton Method implementation; Welcome Screen may not be created multiple times.
    private static final WelcomePage welcome = new WelcomePage(getWelcome().consoleReader);

    private WelcomePage(BufferedReader consoleReader) {
        super(consoleReader);
    }

    public static WelcomePage getWelcome() { return welcome; }

    @Override
    public void render() throws Exception {
        //Prompt the user for a selection of one of these three things.
        System.out.println("Please make a selection: \n" +
                "\n1) Login" +
                "\n2) Register a new Student" +
                "\n3) Exit Application" +
                "\n>");

        consoleReader.readLine();
    }
}
