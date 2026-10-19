package clientpackage;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            System.out.println("Je suis un client pas encore connecté...");

            // Se connecter au serveur : adresse + port
            Socket socket = new Socket("localhost", 1234);

            System.out.println("je suis un client connecté");

            // Fermer la socket
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}