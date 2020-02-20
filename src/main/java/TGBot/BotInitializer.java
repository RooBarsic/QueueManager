package TGBot;
import TGBot.Bot.AnonymizerBot;
import com.google.common.base.Strings;
import helpers.ControllerIO;
import logic.queue.QueuesBox;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.PrintWriter;
import java.util.Scanner;

public class BotInitializer {

    private static final Logger LOG = LogManager.getLogger(BotInitializer.class);

    private static final String PROXY_HOST = "80.211.29.222";
    private static final int PROXY_PORT = 8975;
    private QueuesBox queuesBox;
    public BotInitializer(QueuesBox queuesBox){
        this.queuesBox = queuesBox;
    }

    public void run() {
  /*      System.getProperties().put("proxySet","true");
        System.getProperties().put("socksProxyHost","127.0.0.1");
        System.getProperties().put("socksProxyPort","9150");

        QueuesBox QueuesBox = new QueuesBox(new ControllerIO(new Scanner(System.in), new PrintWriter(System.out)));*/
        try {

            LOG.info("Initializing API context...");
            ApiContextInitializer.init();

            TelegramBotsApi botsApi = new TelegramBotsApi();

            LOG.info("Configuring bot options...");
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            botOptions.setProxyHost(PROXY_HOST);
            botOptions.setProxyPort(PROXY_PORT);
            botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS4);

            LOG.info("Registering Anonymizer...");
            botsApi.registerBot(new AnonymizerBot(/*botOptions,*/queuesBox));


            LOG.info("Bot is ready for work!");

        } catch (TelegramApiRequestException e) {
            LOG.error("Error while initializing bot!", e);
        }
    }
}