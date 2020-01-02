/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import view.VentanaEnvio;

/**
 *
 * @author User
 */
public class HiloServidor implements Runnable {

    VentanaEnvio ventanaE;

    public HiloServidor(VentanaEnvio ventanaE) {
        this.ventanaE = ventanaE;
    }

    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(555);//Se crea un socket servidor 
            while (true) {
                Socket sock = socket.accept();//Se espera a que exista una conexion
                Thread hilo=new Thread(ventanaE.barra);
                hilo.start();
                DataInputStream flujoentrada = new DataInputStream(sock.getInputStream());//Al recibir una entrada se crea un flujo de entrada con la direccion entrante
                String mensaje = flujoentrada.readUTF();//Se lee el mensaje que esta ingresando
                System.out.println(mensaje);
                ventanaE.textoConsola.append("Trama Recibida\n");
                ventanaE.textoTramas.append(mensaje + "\n");
                ventanaE.textoMensaje.append(ControladorEnvio.Texto(ControladorEnvio.Desentramar(mensaje)));
                sock.close();//Se cierra la conexion con el cliente
            }
            //System.out.println("hola");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
