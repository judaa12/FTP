
package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import view.Menu;
import view.VentanaEnvio;
import view.VentanaEnvio;

public class ControladorMenu implements ActionListener{
    Menu m;

    public ControladorMenu(Menu m) {
        this.m = m;
        this.m.BotonEnviar.addActionListener(this);
        this.m.BotonSalir.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.m.BotonSalir) System.exit(0);
        if(e.getSource()==this.m.BotonEnviar){    
            VentanaEnvio vEnvio = new VentanaEnvio();
            ControladorEnvio cEnvio = new ControladorEnvio(vEnvio,m);
            this.m.setVisible(false);
            
        }
    }
    
    
}
