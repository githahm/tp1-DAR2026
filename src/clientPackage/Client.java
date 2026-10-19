package clientPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            String ipServeur = "192.168.56.1";

            int port = 1234;

            InetAddress adresse = InetAddress.getByName(ipServeur);
            InetSocketAddress adresseSocket = new InetSocketAddress(adresse, port);

            Socket socket = new Socket();
            socket.connect(adresseSocket);

            System.out.println("Connecté au serveur : " + ipServeur + ":" + port);

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);

            int choix = -1;

            while (choix != 0) {
                System.out.println("\n--- MENU CALCULATRICE ---");
                System.out.println("1. Addition (+)");
                System.out.println("2. Soustraction (-)");
                System.out.println("3. Multiplication (*)");
                System.out.println("4. Division (/)");
                System.out.println("0. Quitter");
                System.out.print("Votre choix : ");

                choix = scanner.nextInt();

                dos.writeInt(choix);
                dos.flush();

                if (choix != 0) {
                    if (choix >= 1 && choix <= 4) {
                        System.out.print("Entrez le premier nombre (x) : ");
                        int x = scanner.nextInt();
                        System.out.print("Entrez le deuxième nombre (y) : ");
                        int y = scanner.nextInt();

                        dos.writeInt(x);
                        dos.writeInt(y);
                        dos.flush();

                        String resultat = dis.readUTF();
                        System.out.println("=> Résultat : " + resultat);
                    } else {
                        System.out.println("Choix invalide.");
                        dos.writeInt(0);
                        dos.writeInt(0);
                        dos.flush();
                        String msgErreur = dis.readUTF();
                        System.out.println(msgErreur);
                    }
                }
            }

            System.out.println("Déconnexion...");
            scanner.close();
            dos.close();
            dis.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }
}