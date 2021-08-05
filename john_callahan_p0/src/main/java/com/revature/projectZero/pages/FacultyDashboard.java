package com.revature.projectZero.pages;

import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;

public class FacultyDashboard extends Page{

    public FacultyDashboard(BufferedReader reader, PageRouter router) { super("FacultyDashboard", "/f_dashboard", reader, router); }

    @Override
    public void render() throws Exception {
        System.out.println("The Faculty Dashboard works!");
        //TODO: Finish faculty dashboard
    }
}
