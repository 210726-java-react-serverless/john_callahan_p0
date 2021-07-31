package com.revature.projectZero.pages;

import java.io.BufferedReader;

public abstract class Page {

    protected BufferedReader reader;

    public Page(BufferedReader reader) {
        this.reader = reader;
    }

    public abstract void render() throws Exception;
}
