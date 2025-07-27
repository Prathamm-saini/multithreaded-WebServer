package backend.multithreaded;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.io.OutputStream;

public class Server {

    //we will handle each client in a separate thread
    public Consumer<Socket> getConsumer() {
        //Consumer is a functional interface that takes a single argument and returns nothing
        //clientSocket is the socket of the client
        //PrintWriter is used to send data to the client
        //true is used to flush the output stream
        //clientSocket.getOutputStream() returns the output stream of the client
        return (clientSocket) -> {
            //lambda expression is used to create a new thread for each client
            try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
                //clientSocket.getInetAddress() returns the IP address of the client
                toSocket.println("Hello from server " + clientSocket.getInetAddress());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }public void startHttpEndpoint(int httpPort, String serverName) {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(httpPort), 0);

            // Handle browser requests
            httpServer.createContext("/test", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    // Enable CORS for browser
                    exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                    exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST");
                    exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

                    if ("OPTIONS".equals(exchange.getRequestMethod())) {
                        exchange.sendResponseHeaders(200, 0);
                        exchange.close();
                        return;
                    }

                    // Simple response with server info
                    String response = "OK|" + serverName + "|" + System.currentTimeMillis();
                    exchange.sendResponseHeaders(200, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();

                    System.out.println("HTTP request handled by " + serverName);
                }
            });

            httpServer.start();
            System.out.println(serverName + " HTTP endpoint started on port " + httpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();
        server.startHttpEndpoint(9010, "Multi-threaded Server");

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("Server is listening on port " + port);
            //infinite loop to accept connections
            while (true) {
                //accepting a connection from the client
                Socket clientSocket = serverSocket.accept();
                //serverSocket.accept() is a blocking call.
                //the programs execution pauses here until a client connects.
                //when a connection is made, it returns a socket objeect.
                //this socket object is then passed to the getConsumer() method.


                //creating a new thread for each client
                //getConsumer() is a method that returns a Consumer<Socket>
                //accept(clientSocket) is a method that accepts a Socket
                //server.getConsumer().accept(clientSocket) is a method that accepts a Socket
                //server.getConsumer() is a method that returns a Consumer<Socket>
                //server.getConsumer().accept(clientSocket) is a method that accepts a Socket
                Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket));
                thread.start();

                //every time a client connects, this upper code will start a new thread.
                //the actual contet of the code that is to be run is ddefined in the getConsumer() method.
                //where it handles the client handling logic.
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}