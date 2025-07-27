package backend.multithreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    //the client is designed to test the concurrent capabilities of the serer by
    //simulating manny client connections at once.
    //it creates a new thread for each client and sends a message to the server.
    //it then reads the response from the server and prints it to the console.
    //it does this for 100 clients.
    //the server is designed to handle multiple clients concurrently.

    public Runnable getRunnable() throws UnknownHostException, IOException {
        //runnable is a functional interface that takes no arguments and returns nothing
        return new Runnable() {
            @Override
            //override is used to override the run method of the runnable interface
            public void run() {
                int port = 8010;
                //port is the port number of the server
                try {
                    //InetAddress is used to get the IP address of the server locally
                    InetAddress address = InetAddress.getByName("localhost");
                    //Socket is used to connect to the server
                    Socket socket = new Socket(address, port);
                    //PrintWriter is used to send data to the server
                    //true is used to flush the output stream
                    //socket.getOutputStream() returns the output stream of the server
                    try (
                            //print writer in this try block is used to send data to the server
                            PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                            //buffered reader here is used to read data from the server
                            //new InputStreamReader(socket.getInputStream()) returns the input stream of the server
                            BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                    ) {

                        toSocket.println("Hello from Client " + socket.getLocalSocketAddress());
                        String line = fromSocket.readLine();
                        //readLine is used to read data from the server
                        //fromSocket.readLine() returns the data from the server
                        System.out.println("Response from Server " + line);
                        //println is used to print the response from the server
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // The socket will be closed automatically when leaving the try-with-resources block
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    public static void main(String[] args){
        Client client = new Client();
        //client is the object of the client class
        //for loop is used to create 100 threads
        for(int i=0; i<100; i++){
            try{
                //thread is the object of the thread class
                //new Thread(client.getRunnable()) is used to create a new thread
                //client.getRunnable() is used to get the runnable object of the client class

                Thread thread = new Thread(client.getRunnable());
                thread.start();
            }catch(Exception ex){
                return;
            }
        }
        return;
    }
}