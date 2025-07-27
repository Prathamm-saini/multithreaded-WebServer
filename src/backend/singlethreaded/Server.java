package backend.singlethreaded;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void run() throws IOException {
        int port = 8010;
        ServerSocket socket = new ServerSocket(port);
        // in Production, we don't use any timeout's
        socket.setSoTimeout(10000);
        while (true) {
           try{
               System.out.println("Server is listening on port " + port);
               Socket clientSocket = socket.accept();
               System.out.println("Accepted connection from " + clientSocket.getInetAddress());
               PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
               BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
               toClient.println("Server is up Running");
               toClient.close();
               fromClient.close();
               clientSocket.close();
           }catch(IOException e){
               e.printStackTrace();
           }
        }

    }
    public static void main(String[] args) {
        Server server = new Server();
        try{
            server.run();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
