package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            InetAddress adresseLocale = InetAddress.getLocalHost();
            int port = 1234;

            InetSocketAddress adresseSocket = new InetSocketAddress(adresseLocale, port);
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(adresseSocket);

            System.out.println("Serveur en attente sur l'adresse " + adresseLocale.getHostAddress() + ":" + port);

            Socket socket = serverSocket.accept();
            System.out.println("Un client est connecté.");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            while (true) {
                int operation = dis.readInt();

                if (operation == 0) {
                    System.out.println("Le client a quitté.");
                    break;
                }

                int x = dis.readInt();
                int y = dis.readInt();

                String resultat;

                switch (operation) {
                    case 1:
                        resultat = String.valueOf(x + y);
                        break;
                    case 2:
                        resultat = String.valueOf(x - y);
                        break;
                    case 3:
                        resultat = String.valueOf(x * y);
                        break;
                    case 4:
                        if (y == 0) {
                            resultat = "ERREUR : Division par zéro impossible !";
                        } else {
                            resultat = String.valueOf((double) x / y);
                        }
                        break;
                    default:
                        resultat = "ERREUR : Opération inconnue";
                }

                dos.writeUTF(resultat);
                dos.flush();
                System.out.println("Calcul effectué : " + operation + " sur " + x + " et " + y + " -> " + resultat);
            }

            dos.close();
            dis.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}