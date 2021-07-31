package com.revature.projectZero.pages;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WelcomePage extends Page {



    //Singleton Method implementation; Welcome Screen may not be created multiple times.
    private static final WelcomePage welcome = new WelcomePage();

    private WelcomePage(BufferedReader consoleReader) {
        super(consoleReader);
    }

    public static WelcomePage getWelcome() { return welcome; }

    @Override
    public void render() {
        //Prompt the user for a selection of one of these three things.
        System.out.println("Please make a selection: \n" +
                "\n1) Login" +
                "\n2) Register a new Student" +
                "\n3) Exit Application" +
                "\n>");

        //TODO implement the BufferedReader

        //TODO repair the Switching Statement
        switch("1"){
            case "1":
            case "Login":
                System.out.println("Redirecting you to Login services...");
                //TODO connect the Login page to the Welcome page
                break;
            case "2":
            case "Register":
                System.out.println("Redirecting you to Registration services...");
                //TODO connect the Register page to the Welcome page
                break;
            case "3":
            case "Exit":
                //TODO find a way to close this method in a nonvolatile manner.
                System.exit(0);
            default:
                getWelcome().render();
        }
    }
}
