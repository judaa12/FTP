/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.DataOutputStream;
import java.net.Socket;


/**
 *
 * @author User
 */
public class EnvioTrama implements Runnable {

    Thread hilo = null;
    String valor;
    Socket sock;
    public EnvioTrama(Thread t, String valor,Socket sock) {
        this.valor = valor;
        hilo = t;
        this.sock=sock;
    }

    @Override
    public void run() {
        try {
            hilo.join();
            DataOutputStream salida = new DataOutputStream(sock.getOutputStream());//Se crea un flujo de salida de bytes con la direccion del socket
            salida.writeUTF(valor);//Se envia el texto en binario
            salida.close();//Se cierra la conexion
        } catch (Exception e) {
        }

    }

}
