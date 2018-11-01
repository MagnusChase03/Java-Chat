import java.io.*;
import java.net.*;

public class Client {

    public PrintWriter out;
    public BufferedReader in;

    public Client(String host, int port) {

        try {

            Socket socket = new Socket(host, port);
            System.out.println("Connected to " + socket);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            new Thread(new ClientThread(this)).start();

            while (true) {

                String userInput;
                while ((userInput = stdIn.readLine()) != null) {

                    out.println(userInput);

                }

            }

        } catch (Exception e) {}

    }

    public static void main(String[] args) {

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        new Client(host, port);    

    }
}

class ClientThread extends Thread {

    Client client;

    public ClientThread(Client client) {

        this.client = client;

    }

    public void run() {

        while (true) {

            try {

                String input;
                while ((input = client.in.readLine()) != null) {

                    System.out.println(input);

                }
            
            } catch (Exception e) {}

        }

    }

}

