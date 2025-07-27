package backend.singlethreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public void run() {
        int port = 8010;
        try {
            InetAddress address = InetAddress.getLocalHost();
            Socket socket = new Socket(address, port);

            PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            toSocket.println("Hello from Client");
            String line = fromSocket.readLine();
            System.out.println("Response from the Client : " + line);

            toSocket.close();
            fromSocket.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client().run();
    }
}
