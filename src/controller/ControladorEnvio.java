package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JProgressBar;
import view.Menu;
import view.VentanaEnvio;

public class ControladorEnvio implements Runnable, ActionListener {

    VentanaEnvio ventanaE;
    Menu m;
    String textoConsola;
    //String Host = "localhost";
    int port = 555;

    public ControladorEnvio(VentanaEnvio ventanaE, Menu m) {
        this.ventanaE = ventanaE;
        this.m = m;
        this.ventanaE.setVisible(true);
        this.ventanaE.BotonStart.addActionListener(this);
        this.ventanaE.BotonVolver.addActionListener(this);
        iniciar();
    }

    private void iniciar() {
        Thread hilo = new Thread(this);
        hilo.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.ventanaE.BotonStart) {
            ventanaE.TextoConsola.setText("");
            String texto = this.ventanaE.Texto.getText();
            String codigoBinario = "";//guarda el en binario
            for (int i = 0; i < texto.length(); i++) {//recorre el texto ingresado
                char letra = texto.charAt(i);//separa letra a letra
                /* si el binario de la letra es menor a 8 bit entonces se le agrega
                 un cero a la izquierda para llenar el bit restante*/
                int aux = Binario((int) (letra)).length();//almacena largo del binario d ela letra debuelta
                for (int j = 0; j < 8; j++) {//recorrido de 8 characteres
                    if (aux < 8) {//si el largo del binario es menor a 7 (0-7)entonces
                        codigoBinario = codigoBinario + "0";//agregar 0 a la concadenacion
                        aux++;//aumentar el largo del binario
                    }
                }
                /*se concatena el binario de cada letra separado por un espacio. al metodo binario
                 se le pasa por parametro el decimal correspondiente a cada letra*/
                codigoBinario = codigoBinario + Binario((int) (letra)) + " ";
            }
            //this.ventanaE.TextoBinario.setText(codigoBinario);/*imprime el codigo binario completo*/
            //textoConsola = "*/ Mensaje transformado a Binario " + "\n" + "*/ Enviando ";
            //this.ventanaE.TextoConsola.setText(textoConsola);

            try {

                Socket sock = new Socket("192.168.100.17", this.port);//Se crea el socket con el puerto y la direccion del otro computador

                DataOutputStream salida = new DataOutputStream(sock.getOutputStream());//Se crea un flujo de salida de bytes con la direccion del socket
                salida.writeUTF("hola");//Se envia el texto en binario
                //principal.txtArea.append("\n" + principal.txtChat.getText());
                salida.close();//Se cierra la conexion

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            Thread h = new Thread(new Avanzado(ventanaE.barra));
            h.start();
        }

        if (e.getSource() == this.ventanaE.BotonVolver) {
            this.ventanaE.dispose();
            this.m.setVisible(true);
        }
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
                DataInputStream flujoentrada = new DataInputStream(sock.getInputStream());//Al recibir una entrada se crea un flujo de entrada con la direccion entrante
                String mensaje = flujoentrada.readUTF();//Se lee el mensaje que esta ingresando
                System.out.println(mensaje);
                this.ventanaE.TextoBinario.setText("Entrada\n" + mensaje);//Se muestra en pantalla el mensaje recibido
                textoConsola = "*/ Recibido " + "\n" + "*/ Transformando a texto ";
                this.ventanaE.TextoConsola.setText(textoConsola);
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
        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException a) {

            }

            barra.setValue(i);
        }

    }

}
