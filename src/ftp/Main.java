
package ftp;

import controller.ControladorMenu;
import view.Menu;


public class Main {


    public static void main(String[] args) {
        Menu menu = new Menu();
        ControladorMenu controladorM = new ControladorMenu(menu);
        menu.setVisible(true);
    }
    
}
