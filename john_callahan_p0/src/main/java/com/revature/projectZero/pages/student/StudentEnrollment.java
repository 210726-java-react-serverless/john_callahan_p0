package com.revature.projectZero.pages.student;

import com.revature.projectZero.pages.Page;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;

public class StudentEnrollment extends Page {

    ValidationService checker;

    public StudentEnrollment(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("StudentDashboard", "/s_dashboard", reader, router);
        this.checker = checker;
    }

    // TODO: Complete this!
    @Override
    public void render() throws Exception {
        System.out.println("Student Enrollment works!");
        reader.readLine();
        router.navigate("/s_dashboard");
    }
}
