package com.revature.projectZero.pages;

import com.revature.projectZero.util.PageRouter;
import java.io.BufferedReader;

public class LoginPage extends Page {

    public LoginPage(BufferedReader reader, PageRouter router){ super("LoginPage", "/login", reader, router); }

    //TODO: Clean up the Login page

    @Override
    public void render() throws Exception {
        String userInput;
        boolean isUserFaculty = false;
        String username;
        String password;

        System.out.println("Welcome to the Login portal!\n"
                    + "Are you a Student, or a Faculty member?"
                    + "1) Student"
                    + "2) Faculty"
                    + "3) Register a new Student"
                    + "4) Go back to the Welcome page");

        userInput = reader.readLine();

        // This is a long switching statement that checks user input against a variety of potential inputs
        // for the best experience
        switch(userInput) {
            case "1":
            case "Student":
            case "student":
                isUserFaculty = false;
                break;
            case "2":
            case "Faculty":
            case "faculty":
                isUserFaculty = true;
                break;
            case "3":
            case "Register":
            case "New Student":
            case "new":
                System.out.println("Great! Redirecting you to the Registry");
                router.navigate("/register");
                break;
            default:
                System.out.println("Sorry, we didn't understand that!");
                router.navigate("/login");
        }

        // Checks whether or not the user is Faculty or a Student.
        if(isUserFaculty) {
            System.out.println("Welcome to the Faculty log-in portal!");

            // This is a for loop that will track how many times a user attempts to log into a Faculty account.
            for(int i=0; i<3; i++){
                System.out.print("\n\nPlease enter your Username: ");
                username = reader.readLine();
                System.out.print("\nPlease enter your Password: ");
                password = reader.readLine();


                //TODO: Instate Faculty login logic here!


            }
            // This is the "game over" message that displays if you fail to validate as
            // a Faculty member three times.
            System.out.println("You have input an invalid username and password 3 times!\n"
                            + "If you are a Student, please press '2' when prompted!");
            router.navigate("/login");

        } else {
            // This is the student log-in portion.
            System.out.println("Welcome to the Student log-in portal!");
            boolean wantsToBeHere = true;
            int tracker = 0;
            String input;

            // This is a loop that will keep a student inside of this panel for as long as they feel necessary.
            while(wantsToBeHere) {
                for(int i = 0; i<5; i++){
                    System.out.print("\n\nPlease enter your Username: ");
                    username = reader.readLine();
                    System.out.print("\nPlease enter your Password: ");
                    password = reader.readLine();


                    // TODO: Instate Student Login logic here!


                    // This is a simple counter, added as a sort of sobriety test for
                    // the Student's wishes. It is also a way to get out of logging in, rather
                    // than being stuck in a recursive loop.
                    tracker++;
                }
                System.out.println("You have failed " + tracker
                        + " times. Are you sure you want to try again?\n"
                        + "Y/N: ");
                input = reader.readLine();
                // checks user input and learns whether or not the user wishes to be on this login screen.
                switch(input){
                    case "y":
                    case "Y":
                        break;
                    case "n":
                    case "N":
                        wantsToBeHere = false;
                        break;
                }
            }
        }
    }
}
