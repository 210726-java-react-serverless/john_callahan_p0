package com.revature.projectZero;

import com.revature.projectZero.exceptions.ExceptionServices;
import com.revature.projectZero.pages.WelcomePage;

public class App extends ExceptionServices {

    public static void main(String[] args) throws Exception {
        WelcomePage welcome = WelcomePage.getWelcome();

            welcome.render();
    }
}
