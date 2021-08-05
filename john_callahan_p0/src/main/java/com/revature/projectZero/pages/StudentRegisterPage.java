package com.revature.projectZero.pages;

import com.revature.projectZero.pojos.Student;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

public class StudentRegisterPage extends Page {

    public StudentRegisterPage(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("RegisterPage", "/register", reader, router);
        this.checker = checker;
    }

    public final ValidationService checker;

    @Override
    public void render() throws Exception{
        Logger logger = LogManager.getLogger(StudentRegisterPage.class);

        // This is a simple check to see if the user actually intends to make a new student account.
        // Prior to this, one would have to make a null user, just to get an exception.
        System.out.println("Would you like to register for a new Student Account?"
                    + "Y/N: ");
        String input = reader.readLine();

        if(input.equals("N") || input.equals("n")) {
            System.out.println("Directing you back to the Welcome page...");
            router.navigate("/welcome");
            return;
        }

        System.out.print("First name: ");
        String firstname = reader.readLine();

        System.out.print("\nLast name: ");
        String lastname = reader.readLine();

        System.out.print("\nEmail: ");
        String email = reader.readLine();

        // TODO: Implement "Is username taken?" While loop.
        System.out.print("\nUsername: ");
        String username = reader.readLine();
        // checker.validateStudent(username);

        System.out.print("\nPassword: ");
        String password = reader.readLine();

        Student newStudent = new Student(firstname, lastname, email, username, password);

        try {
            checker.register(newStudent);
            router.navigate("s_dashboard");
        } catch(Exception e) {
            logger.error(e.getMessage());
            logger.debug("User not registered!");
            router.navigate("/welcome");
        }
    }
}
