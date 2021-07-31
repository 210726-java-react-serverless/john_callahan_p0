package com.revature.projectZero.pages;

import java.io.BufferedReader;

public abstract class Page {

    protected String name;
    protected String route;
    protected BufferedReader reader;

    public Page(String name, String route, BufferedReader reader) {
        this.name = name;
        this.route = route;
        this.reader = reader;
    }

    public abstract void render() throws Exception;
}
