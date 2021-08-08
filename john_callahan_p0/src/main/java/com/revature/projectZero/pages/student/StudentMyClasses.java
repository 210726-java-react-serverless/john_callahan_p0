package com.revature.projectZero.pages.student;

import com.revature.projectZero.pages.Page;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;

public class StudentMyClasses  extends Page {

    ValidationService checker;

    public StudentMyClasses(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("StudentDashboard", "/s_dashboard", reader, router);
        this.checker = checker;
    }

    @Override
    public void render() throws Exception {
        System.out.println("Student 'My Classes' works!");
        reader.readLine();
        router.navigate("/s_dashboard");
    }
}
