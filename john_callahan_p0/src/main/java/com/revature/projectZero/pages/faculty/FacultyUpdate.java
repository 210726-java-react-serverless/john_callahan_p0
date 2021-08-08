package com.revature.projectZero.pages.faculty;

import com.revature.projectZero.pages.Page;
import com.revature.projectZero.pojos.Course;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;

public class FacultyUpdate extends Page {

    private final ValidationService checker;

    public FacultyUpdate(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("FacultyUpdate", "/facUpdate", reader, router);
        this.checker = checker;
    }

    @Override
    public void render() throws Exception {

        System.out.print("\nWould you like to update an existing class?"
                + "\nY/N: ");
        String input = reader.readLine();

        if(input.equals("n") || input.equals("N")) {
            router.navigate("/f_dashboard");
        }

        System.out.print("\nPlease input the call-sign/ID of the class you would like to update (e.g. ENG101)"
                        + "> ");
        String id = reader.readLine();

        Course thisCourse = checker.getCourseByID(id);

        if(thisCourse != null) {
            System.out.println("Course found!"
                    + "\nName: " + thisCourse.getName()
                    + "\nID/Call-Sign: " + thisCourse.getId()
                    + "\nDescription: " + thisCourse.getDesc()
                    + "\nTeacher: " + thisCourse.getTeacher()
                    + "\nOpen: " + thisCourse.isOpen());
        } else {
            System.out.println("Sorry! We could not find a course with that ID!");
            System.out.println("Sending you back to the dashboard...");
            router.navigate("f_dashboard");
            return;
        }

        System.out.println("Please re-enter the details for this course.");
        System.out.print("\nName: ");
        String name = reader.readLine();

        System.out.print("\nID/Call-Sign: ");
        id = reader.readLine();

        System.out.print("\nDesc: ");
        String desc = reader.readLine();

        boolean notReady = true;
        boolean isOpen = true;
        while(notReady) {
            System.out.print("\nOpen? Y/N:");
            input = reader.readLine();

            switch (input) {
                case "open":
                case "Open":
                    isOpen = true;
                    notReady = false;
                    break;
                case "closed":
                case "Closed":
                    isOpen = false;
                    notReady = false;
                    break;
                default:
                    System.out.println("Sorry, we didn't quite understand that.");
            }
        }

        Course renewedCourse = new Course(name, id, desc, thisCourse.getTeacher(), isOpen);
        checker.updateCourse(renewedCourse, id);

        System.out.println("Update service completed!");
        System.out.println("Sending you back to the dashboard...");
        router.navigate("/f_dashboard");
    }
}
