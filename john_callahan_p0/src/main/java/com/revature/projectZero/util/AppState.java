package com.revature.projectZero.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.revature.projectZero.pages.WelcomePage;

public class AppState {
    // Set up the appRunning boolean for closing the app, and the router, linked to the PageRouter class.
    private boolean appRunning;
    private final PageRouter router;

    // This should be the only instantiation needed of a Buffered Reader, which will be injected to each Page.
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // This is AppState, the constructor for this class.
    public AppState() {
        appRunning = true;
        router = new PageRouter();


    }

    // The beating heart of the application. So long as appRunning is true,
    // the application will continue trying to render.
    public void startApp() {
        router.navigate("/welcome");

        while(appRunning) {
            try {
                router.getCurrentPage().render();
            } catch (Exception e) {
                //TODO replace this with a logging request to a text file.
                e.printStackTrace();
            }
        }
    }

    // the application shutdown method, safely (and effectively) closes the app.
    public void closeApp() {
        appRunning = false;
    }
}
