package com.revature.projectZero.pages;

import com.revature.projectZero.pojos.Student;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;

public class StudentDashboard extends Page{
    ValidationService checker;

    public StudentDashboard(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("StudentDashboard", "/s_dashboard", reader, router);
        this.checker = checker;
    }

    @Override
    public void render() throws Exception {

        Student currentStudent = checker.getStudent();
        if (currentStudent == null) {
            System.out.println("We're sorry, but this session has been invalidated!"
                            + "\nSending you back to the welcome page...");
            router.navigate("/welcome");
            return;
        }

        System.out.println("Welcome to the Student Dashboard!");
        System.out.print("\nWhat would you like to do?"
                + "\n1) View 'My Classes'"
                + "\n2) View all courses"
                + "\n3) Logout"
                + "\n> ");

        String input = reader.readLine();

        switch(input) {
            case "My Classes":
            case "1":
            case "my classes":
                router.navigate("/myclasses"); // TODO: Link this to the '/myclasses' screen!
                return;
            case "All Courses":
            case "all courses":
            case "2":
                router.navigate("/allcourses"); // TODO: Link this to the '/allcourses' screen!
                return;
            case "Logout":
            case "3":
            case "exit":
            case "logout":
                router.navigate("/welcome");
                checker.logout();
                return;
            default:
                System.out.println("Sorry! We didn't quite catch that..");
        }
    }
}
