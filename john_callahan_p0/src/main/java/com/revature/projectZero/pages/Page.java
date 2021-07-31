package com.revature.projectZero.pages;

import com.revature.projectZero.util.PageRouter;
import java.io.BufferedReader;


public abstract class Page {

    protected String name;
    protected String route;
    protected BufferedReader reader;
    protected PageRouter router;

    public Page(String name, String route, BufferedReader reader, PageRouter router) {
        this.name = name;
        this.route = route;
        this.reader = reader;
        this.router = router;
    }

    public String getRoute() {
        return route;
    }

    public abstract void render() throws Exception;
}
