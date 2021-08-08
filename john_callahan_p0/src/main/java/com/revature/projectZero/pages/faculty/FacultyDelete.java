package com.revature.projectZero.pages.faculty;

import com.revature.projectZero.pages.Page;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;

public class FacultyDelete extends Page {

    private final ValidationService checker;

    public FacultyDelete(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("FacultyDelete", "/facDelete", reader, router);
        this.checker = checker;

    }

    @Override
    public void render() throws Exception {

        // TODO: Find a way to make this not repeat its query.
        System.out.print("\nWould you like to delete a class?"
                + "\nY/N: ");

        String input = reader.readLine();
        if(input.equals("n") || input.equals("N")) {
            router.navigate("/f_dashboard");
        }

        System.out.print("\nPlease enter the ID/call-sign of the class you would like to delete (A good example of this is ENG101)"
                    + "\n> ");
        String id = reader.readLine();

        checker.deleteCourse(id);
        System.out.println("The Delete function was a success!");

        // Query the user for their intention.
        System.out.print("\nWould you like to delete another course?"
                        + "Y/N: ");
        input = reader.readLine();

        if (input.equals("n")||input.equals("N")) {
            System.out.println("Sending you back to the Dashboard...");
            router.navigate("/f_dashboard");
        }
    }
}
