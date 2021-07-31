package com.revature.projectZero.pages;

import com.revature.projectZero.util.AppState;
import com.revature.projectZero.util.PageRouter;
import java.io.BufferedReader;


public class WelcomePage extends Page {

    // How does dependency injection work?
    /*
        Dependency injection works by calling the super method (which contains references to both the
        router and the consoleReader) and looping them through the WelcomePage() constructor.
     */
    public WelcomePage(BufferedReader reader, PageRouter router) {
        super("WelcomePage", "/welcome", reader, router);
    }

    @Override
    public void render() throws Exception {
        //Prompt the user for a selection of one of these three things.
         System.out.print("Please make a selection: \n" +
                "\n1) Login" +
                "\n2) Register a new Student" +
                "\n3) Exit Application" +
                "\n> ");

        String userSelection = reader.readLine();

        switch(userSelection){
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
                System.out.println("Thank you for using our App!");
                AppState as = new AppState();
                as.closeApp();
            default:
                System.out.println("Sorry, but your input was invalid.");
                router.navigate("/welcome");
        }
    }
}
