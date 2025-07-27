package backend.threadpool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//the thread pool method is a significant improvement over the
//client-server model, yes even the multithreaded model.
//creating a new thread for each client is expensive in computational terms.

//even tho, thread pool is single threaded it provides better efficiency.
// it maintains a fixed number of worker threads. These threads are reused to handle incoming client requests.
//this reduces the overhead of creating and destroying threads.
//this also reduces the overhead of context switching.
//this also reduces the overhead of synchronization.
//this also reduces the overhead of memory allocation.
//this also reduces the overhead of memory deallocation.
//this also reduces the overhead of memory allocation.

public class Server {
    private final ExecutorService threadPool;
    //The ExecutorService is a high-level API from Java's concurrency framework for managing threads.
//it provides a pool of threads that can be reused to handle incoming client requests.
    public Server(int poolSize) {
        this.threadPool = Executors.newFixedThreadPool(poolSize);

        // initializes the pool. Executors.newFixedThreadPool(10) creates a pool with exactly 10 worker threads.
        // These 10 threads will be created once and then reused for the lifetime of the server.
    }

    public void handleClient(Socket clientSocket) {


        //This method contains the logic for how to interact with a connected client. In this case, it's very simple
        // it opens a PrintWriter, sends a "Hello" message, and the try-with-resources block ensures the writer (and underlying socket stream) is closed automatically.
        //Crucially, this method does not create a thread. It's just a plain method that will be executed by a thread from the pool.
        try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
            toSocket.println("Hello from server " + clientSocket.getInetAddress());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 8010;
        int poolSize = 10; // Adjust the pool size as needed
        Server server = new Server(poolSize);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("Server is listening on port " + port);


//             This is the most important part. When a client connects (serverSocket.accept()), the server executes this line:
// server.threadPool.execute(() -> server.handleClient(clientSocket));
// execute() is a method of the ExecutorService. It takes a Runnable task and assigns it to one of the available threads in the pool.
// If all 10 threads are busy handling other clients, the new task is placed in a queue. As soon as a thread finishes its current task, it will pick up the next task from the queue.
// The main server loop does not wait. It immediately loops back to accept() to listen for the next client. This ensures the server remains highly responsive.

            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Use the thread pool to handle the client
                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Shutdown the thread pool when the server exits
            server.threadPool.shutdown();
        }
    }
}