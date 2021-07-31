package com.revature.projectZero.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.revature.projectZero.pages.WelcomePage;

public class AppState {
    private boolean appRunning;

    public AppState() {
        appRunning = true;
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public startApp() {

    }
}
