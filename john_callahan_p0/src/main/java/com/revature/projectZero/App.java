package com.revature.projectZero;

import com.revature.projectZero.pages.WelcomePage;

public class App {

    public static void main(String[] args) {
        WelcomePage welcome = WelcomePage.getWelcome();
        welcome.render();
    }
}
