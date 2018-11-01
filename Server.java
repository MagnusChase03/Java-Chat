import java.net.*;
import java.io.*;

public class Server {

    public PrintWriter[] outs = new PrintWriter[100];
    public BufferedReader[] ins = new BufferedReader[100];

    public Server(int port) {

        int users = 0;

        try {
            
            ServerSocket serverSocket = new ServerSocket(port);
            while (users != 100) { 
            
                System.out.println("Waiting...");
                Socket client = serverSocket.accept();     
                System.out.println("Connection from " + client);

                PrintWriter out = new PrintWriter(client.getOutputStream(), true);                   
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                outs[users] = out;
                ins[users] = in;

                new Thread(new ServerThread(this, users)).start();                

                users++;

            }

        } catch (Exception e) {}

    }

    public static void main(String[] args) {
        
        //int port = Integer.parseInt(args[0]);
        
        new Server(9090);
    }
}

class ServerThread extends Thread{

    public Server server;
    public int user;
    public boolean running = true;

    public ServerThread(Server server, int user) {

        this.server = server;
        this.user = user;

    }

    public void run() {

        while (running) {

            try {

                String inputLine;
                while ((inputLine = server.ins[user].readLine()) != null) {
                    
                    for (int i = 0; i < server.outs.length; i++) {                  

                        server.outs[i].println(inputLine);
                
                    }

                }
            
            } catch (Exception e) {}

        }

    }

}
