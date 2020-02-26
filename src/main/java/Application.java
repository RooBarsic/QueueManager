import TGBot.BotInitializer;
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
    public static void main(String[] args) {

        QueuesBox queuesBox = new QueuesBox(new ControllerIO(new Scanner(System.in), new PrintWriter(System.out)));
        queuesBox.addQueue("sberbank");

        new Thread(() -> {
            //RE-write bot for using rest, not queuesBox.
            //TODO
            BotInitializer botInitializer = new BotInitializer();
            botInitializer.run();

        }).start();

        new Thread(()-> {
                try{
                    int serverPort = 8000;
                    server = HttpServer.create(new InetSocketAddress(serverPort), 0);
                    new CustomerHandler(server, queuesBox);
                    new QueueHandler(server, queuesBox);
                    server.setExecutor(null); // creates a default executor
                    server.start();

                }
                catch (IOException e){
                    e.printStackTrace();
                }
        }).start();

    }

    public HttpServer getServer() {
        return server;
    }
}