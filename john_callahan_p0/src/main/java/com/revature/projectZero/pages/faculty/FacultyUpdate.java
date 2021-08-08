package com.revature.projectZero.pages.faculty;

import com.revature.projectZero.pages.Page;
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
    }
}
