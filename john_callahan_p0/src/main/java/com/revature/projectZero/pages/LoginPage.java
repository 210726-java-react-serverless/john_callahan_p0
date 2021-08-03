package com.revature.projectZero.pages;

import com.revature.projectZero.util.PageRouter;
import java.io.BufferedReader;

public class LoginPage extends Page {

    public LoginPage(BufferedReader reader, PageRouter router){ super("LoginPage", "/login", reader, router); }

    @Override
    public void render() {
        System.out.println("LoginPage works!");
        System.out.println("Page under construction! Sending you back to the welcome screen...");
        
        router.navigate("/welcome");
    }
}
