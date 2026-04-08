package org;

import org.Config.ConnectionSQL;
import org.views.Menu;

public class App {
    public static void main( String[] args ){
        ConnectionSQL connectionSQL = new ConnectionSQL();
        Menu menu = new Menu(connectionSQL.getConnection());
        menu.start();
    }
}
