package clientPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // 1. Connexion au serveur
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Connecté au serveur !");

            // 2. Création des flux de traitement
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            // Pour lire ce que l'utilisateur tape au clavier
            Scanner scanner = new Scanner(System.in);

            int x;
            // 3. Boucle : on demande un nombre tant qu'il n'est pas égal à 0
            do {
                System.out.print("Entrez un entier (0 pour quitter) : ");
                x = scanner.nextInt();

                // Envoi du nombre au serveur
                dos.writeInt(x);
                dos.flush();

                // Si on n'a pas envoyé 0, on attend la réponse
                if (x != 0) {
                    int resultat = dis.readInt();
                    System.out.println(" -> Résultat renvoyé par le serveur : " + resultat + " (car " + x + " * 5)");
                }

            } while (x != 0);

            System.out.println("Déconnexion...");

            // 4. Fermeture propre
            scanner.close();
            dos.close();
            dis.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}