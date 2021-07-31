package com.revature.projectZero;

import com.revature.projectZero.exceptions.ExceptionServices;
import com.revature.projectZero.util.AppState;

public class App extends ExceptionServices {

    public static void main(String[] args) throws Exception {
        AppState app = new AppState();
        app.startApp();
    }
}