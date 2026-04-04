package org;

import org.Config.ConnectionSQL;
import org.views.Menu;

public class App {
    public static void main( String[] args ){
        Menu menu = new Menu(new ConnectionSQL());
        menu.start();
    }
}
