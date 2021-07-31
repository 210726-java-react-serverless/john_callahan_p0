package com.revature.projectZero.pages;

public class WelcomePage {

    //Singleton Method implementation; Welcome Screen may not be created multiple times.
    private static final WelcomePage welcome = new WelcomePage();

    private WelcomePage() {

    }

    public static WelcomePage getWelcome() { return welcome; }

    public void render() {
        //Prompt the user for a selection of one of these three things.
        System.out.println("Please make a selection: \n" +
                "\n1) Login" +
                "\n2) Register a new Student" +
                "\n3) Exit Application");
    }

}
