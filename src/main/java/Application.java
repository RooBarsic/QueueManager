import com.sun.net.httpserver.HttpServer;
import handlers.CustomerHandler;
import handlers.QueueHandler;
import helpers.ControllerIO;
import logic.queue.QueuesBox;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.Scanner;


class Application {
    private static HttpServer server;
    public static void main(String[] args) throws IOException {


        QueuesBox queuesBox = new QueuesBox(new ControllerIO(new Scanner(System.in), new PrintWriter(System.out)));

        int serverPort = 8000;
        server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        new CustomerHandler(server, queuesBox);
        new QueueHandler(server, queuesBox);
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public HttpServer getServer() {
        return server;
    }
}