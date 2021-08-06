package com.revature.projectZero.pages;

import com.revature.projectZero.pojos.Faculty;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;

public class FacultyDashboard extends Page{

    private final ValidationService checker;

    public FacultyDashboard(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("FacultyDashboard", "/f_dashboard", reader, router);
        this.checker = checker;
    }

    @Override
    public void render() throws Exception {

        Faculty authFac = checker.getAuthFac();
        if(authFac == null) {
            System.out.println("We're sorry, but this session has been invalidated!"
                            + "\nSending you back to the welcome page...");
            router.navigate("/welcome");
            return;
        }

        System.out.println("Welcome to your dashboard!");
        System.out.print("\nWhat would you like to do?"
                        + "\n1) Add a new class to the catalogue"
                        + "\n2) Change the registration details for a class"
                        + "\n3) Remove a class from the catalog"
                        + "\n4) Logout"
                        + "\n> ");
        String input = reader.readLine();

        switch(input) {
            case "1":
            case "add":
            case "new class":
            case "New Class":
                System.out.println("Directing you to the Class Creation services...");
                router.navigate("/facCreation"); // TODO: Make the facCreation portal
                return;
            case "2":
            case "change":
            case "update":
            case "Change":
                System.out.println("Directing you to the Class Update services...");
                router.navigate("/facUpdate"); // TODO: Make the facUpdate portal
                return;
            case "3":
            case "remove":
            case "delete":
            case "Remove":
                System.out.println("Directing you to the Class Deletion portal...");
                router.navigate("/facDelete"); // TODO: Make the facDelete portal
                return;
            case "4":
            case "Logout":
            case "exit":
            case "logout":
                System.out.println("Logging you out...");
                checker.logout();
                router.navigate("/welcome");
                return;
            default:
                System.out.println("We didn't quite understand that..");
        }
    }
}
