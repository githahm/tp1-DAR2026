package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            // 1. Création de la socket d'écoute
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Serveur en attente d'un client...");

            // 2. Acceptation du client (bloquant)
            Socket socket = serverSocket.accept();
            System.out.println("Un client est connecté.");

            // 3. Création des flux de traitement pour les entiers
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            int x;
            // 4. Boucle : on lit tant que le client n'envoie pas 0
            // dis.readInt() bloque jusqu'à recevoir un entier
            while ((x = dis.readInt()) != 0) {
                System.out.println("Nombre reçu du client : " + x);

                // 5. Traitement : on multiplie par 5
                int resultat = x * 5;

                // 6. Envoi du résultat au client
                dos.writeInt(resultat);
                dos.flush(); // Très important pour vider le tampon et envoyer immédiatement
            }

            System.out.println("Le client a envoyé 0. Fermeture de la connexion.");

            // 7. Fermeture propre
            dos.close();
            dis.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}