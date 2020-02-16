import com.sun.net.httpserver.HttpServer;
import exampler.console.ControllerIO;
import exampler.console.MultiQueueController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.Scanner;


class Application {
    private static final String PROXY_HOST = "80.211.29.222";
    private static final int PROXY_PORT = 8975;

    public static void main(String[] args) throws IOException {


        MultiQueueController multiQueueController = new MultiQueueController(new ControllerIO(new Scanner(System.in), new PrintWriter(System.out)));


        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        new Handler(server, multiQueueController);


//        try {
//
//            ApiContextInitializer.init();
//
//            TelegramBotsApi botsApi = new TelegramBotsApi();
//
//
//            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
//
//            botOptions.setProxyHost(PROXY_HOST);
//            botOptions.setProxyPort(PROXY_PORT);
//            botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS4);
//
//            System.out.println("Registering Anonymizer...");
//            botsApi.registerBot(new AnonymizerBot(botOptions, multiQueueController));
//
//            System.out.println("Anonymizer bot is ready for work!");
//
//        } catch (TelegramApiRequestException e) {
//            System.out.println("Error while initializing bot!");
//        }
    }
}