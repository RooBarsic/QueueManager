import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


class Application {

    public static void main(String[] args) throws IOException {
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        new Handler(server);
    }
}