package com.revature.projectZero.util;

import com.revature.projectZero.pages.Page;

import java.util.HashSet;
import java.util.Set;

public class PageRouter {

    // Create a tracker for the current page, and a set of pages that exist in memory.
    private Page currentPage;
    private Set<Page> pageset = new HashSet<>();

    //Method that adds pages to the current pageset.
    public PageRouter addPage(Page page) {
        pageset.add(page);
        return this;
    }


    // Navigation method. Retrieves a string from the function and runs a for loop,
    // checking the route against every page in the pageset. If the page is correct,
    // Switches currentPage to be the Page called.
    public void navigate(String route){
        for(Page page: pageset) {
            if(page.getRoute().equals(route)) {
                currentPage = page;
                break;
            }
        }
    }

    //A call to get the current page, used in the Routing method.
    public Page getCurrentPage() { return currentPage; }

}
