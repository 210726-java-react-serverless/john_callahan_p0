package com.revature.projectZero;

import com.revature.projectZero.service.ExceptionServices;
import com.revature.projectZero.util.AppState;

public class App extends ExceptionServices {

    public static void main(String[] args) throws Exception {
        AppState app = new AppState();
        app.startApp();
    }
}