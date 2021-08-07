package com.revature.projectZero.pages.faculty;

import com.revature.projectZero.pages.Page;
import com.revature.projectZero.service.ValidationService;
import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;

public class FacultyRead extends Page {

    private final ValidationService checker;

    public FacultyRead(BufferedReader reader, PageRouter router, ValidationService checker) {
        super("FacultyDashboard", "/f_dashboard", reader, router);
        this.checker = checker;
    }

    @Override
    public void render() throws Exception {


    }
}
