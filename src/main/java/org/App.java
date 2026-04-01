package org;

import org.Config.ConnectionSQL;
import org.ui.Menu;

public class App {
    public static void main( String[] args ){
        Menu menu = new Menu(new ConnectionSQL());
        menu.start();
    }
}
