package TGBot.Command;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

abstract class AnonymizerCommand extends BotCommand {

    final Logger log = LogManager.getLogger(getClass());
    final String url = "http://localhost:8000";

    AnonymizerCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    void execute(AbsSender sender, SendMessage message, User user) {
        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error. Please try it again!");
        }
    }

    /**
     * get data from url for displaying in BOT
     * @param webURL given url
     * @return List of String
     */
     protected List<String> readFromWeb(String webURL) throws IOException {
        List<String> result = new ArrayList<>();
        URL url = new URL(this.url + webURL);
        InputStream is =  url.openStream();
        try( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                result.add(line);
            }
            return result;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new MalformedURLException("URL is malformed!!");
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
     }

     StringBuilder getResponeToBot(String url){
         StringBuilder sb = new StringBuilder();
         try{
             List<String> queues = readFromWeb(url);
             for(int i=0;i<queues.size();i++) {
                 sb.append(queues.get(i));
                 sb.append("\n");
             }
             return sb;
         } catch (IOException e){
             sb.append("Something was wrong. Please try it again");
             e.printStackTrace();
             return sb;

         }

     }
}