package org;

import org.Config.ConnectionSQL;
import org.views.Menu;

public class App {
    public static void main( String[] args ){
        ConnectionSQL connSQL = new ConnectionSQL();
        Menu menu = new Menu(connSQL.getConnection());
        menu.start();
    }
}
