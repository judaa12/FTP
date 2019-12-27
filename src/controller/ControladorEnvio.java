package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import view.Menu;
import view.VentanaEnvio;

public class ControladorEnvio implements Runnable, ActionListener {
    ArrayList<String> tramas=new ArrayList();
    VentanaEnvio ventanaE;
    Menu m;
    String textoConsola;
    int port = 555;

    public ControladorEnvio(VentanaEnvio ventanaE, Menu m) {
        this.ventanaE = ventanaE;
        this.m = m;
        this.ventanaE.setVisible(true);
        this.ventanaE.botonEnviar.addActionListener(this);
        this.ventanaE.botonError.addActionListener(this);
        this.ventanaE.botonSalir.addActionListener(this);
        iniciar();
    }

    private void iniciar() {

        Thread hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.ventanaE.botonEnviar) {

            String texto = this.ventanaE.textoMensaje.getText();
            ventanaE.textoTramas.setText("");
            String codigoBinario = "";

            for (int i = 0; i < texto.length(); i++) {
                char letra = texto.charAt(i);
                int aux = Binario((int) (letra)).length();
                for (int j = 0; j < 8; j++) {
                    if (aux < 8) {
                        codigoBinario = codigoBinario + "0";
                        aux++;
                    }
                }
                codigoBinario = codigoBinario + Binario((int) (letra)) + "";
            }
             for(int i =0;i<codigoBinario.length();i=i+16){
                String sub;
                if(i==codigoBinario.length()-8){
                    sub=codigoBinario.substring(i, i+8);
                }else{
                    sub=codigoBinario.substring(i, i+16);
                }
                
                String trama=Entramar(sub);
                tramas.add(trama);
                ventanaE.textoTramas.append(trama+"\n");
            }
            ventanaE.barra.enviar(tramas);
            ventanaE.barra.iniciar();
            //ventanaE.barra1.iniciar();
        }

        if (e.getSource() == this.ventanaE.botonSalir) {
            this.ventanaE.dispose();
            this.m.setVisible(true);
        }

    }
private String Entramar(String binario) {
        String delimitador = "01111110";
        String trama = delimitador;
        int contador = 0;
        for (int i = 0; i < binario.length(); i++) {
            if (binario.charAt(i) == '1') {
                contador++;
            } else {
                contador = 0;
            }
            trama = trama + binario.charAt(i);
            if (contador == 5) {
                trama = trama + '0';
            }
        }
        trama = trama + delimitador;
        return trama;
    }

    private String Desentramar(String trama) {
        String trama1 = "";
        int contador = 0;
        for (int i = 8; i < trama.length() - 8; i++) {
            if (contador == 5) {
                contador = 0;
                continue;
            }
            if (trama.charAt(i) == '1') {
                contador++;
            } else {
                contador = 0;
            }
            trama1 = trama1 + trama.charAt(i);
        }
        return trama1;
    }
    private String Binario(int Decimal) {
        int R, x = 0;//variables que se implementaran
        String Binario = ""; //guarda el contenido en codigo binario
        R = Decimal % 2;//resto del parametro 
        if (R == 1) {//si el resto es 1
            while (Decimal > 1) {//si el parametro es mas grande q el resto
                Decimal /= 2;//entonces decimal se divide en 2 y se guarda en decimal
                x = Decimal % 2;//x contendra el resto del decimal
                Binario = String.valueOf(x + Binario);//binario se ira formando de forma correcta y no alreves
            }
        } else {//si resto no es 1
            while (Decimal > 0) {//y decimal es mayor a 0
                Decimal /= 2;//decimal se divide en 2
                x = Decimal % 2;//x contendra el resto del decimal resultante
                Binario = String.valueOf(x + Binario);//se forma el numero en binario de forma correcta
            }
        }
        return String.valueOf(Binario + x);//devuelve el binario resultante mas el ultimo bit
    }

    @Override
    public void run() {
        
        try {
            ServerSocket socket = new ServerSocket(555);//Se crea un socket servidor 
            while (true) {
                Socket sock = socket.accept();//Se espera a que exista una conexion
                ventanaE.barra1.iniciar();
                DataInputStream flujoentrada = new DataInputStream(sock.getInputStream());//Al recibir una entrada se crea un flujo de entrada con la direccion entrante
                String mensaje = flujoentrada.readUTF();//Se lee el mensaje que esta ingresando
                System.out.println(mensaje);
                ventanaE.textoConsola.append("Trama Recibida\n");
                ventanaE.textoTramas.append(mensaje+"\n");
                //this.ventanaE.TextoBinario.setText("Entrada\n" + mensaje);//Se muestra en pantalla el mensaje recibido
                //textoConsola = "*/ Recibido " + "\n" + "*/ Transformando a texto ";
                //this.ventanaE.TextoConsola.setText(textoConsola);
                sock.close();//Se cierra la conexion con el cliente
            }

            //System.out.println("hola");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

class Avanzado implements Runnable {

    JProgressBar barra;

    public Avanzado(JProgressBar barra) {
        this.barra = barra;
    }

    @Override
    public void run() {

        /*
        try {
            Socket sock = new Socket("192.168.100.17", 555);//Se crea el socket con el puerto y la direccion del otro computador
            
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(15);
                } catch (InterruptedException a) {
                }

                barra.setValue(i);
            }

            DataOutputStream salida = new DataOutputStream(sock.getOutputStream());//Se crea un flujo de salida de bytes con la direccion del socket

            salida.writeUTF("hola");//Se envia el texto en binario
            //principal.txtArea.append("\n" + principal.txtChat.getText());
            salida.close();//Se cierra la conexion

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
         */
    }

}

class Avanzado2 implements Runnable {

    JProgressBar barra;

    public Avanzado2(JProgressBar barra) {
        this.barra = barra;
    }

    @Override
    public void run() {

        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException a) {
            }

            barra.setValue(i);
        }

    }

}
