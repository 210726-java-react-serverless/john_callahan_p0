package com.revature.projectZero.pages;

import com.revature.projectZero.util.PageRouter;

import java.io.BufferedReader;

public class StudentRegisterPage extends Page {

    public StudentRegisterPage(BufferedReader reader, PageRouter router) { super("RegisterPage", "/register", reader, router); }

    @Override
    public void render() {
        System.out.println("RegisterPage works!");
        System.out.println("Page under construction. Sending you back to the Welcome screen...");

        router.navigate("/welcome");
    }
}
