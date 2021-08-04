package com.revature.projectZero.pages;

import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;

public class StudentDashboard extends Page{

    public StudentDashboard(BufferedReader reader, PageRouter router) { super("StudentDashboard", "/s_dashboard", reader, router); }

    @Override
    public void render() throws Exception {
        System.out.println("The Student Dashboard works!");
    }
}
