package Command;

import Service.AnonymousService;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CheckQueueCommand extends AnonymizerCommand {

    public CheckQueueCommand(AnonymousService anonymouses){
        super("list_queue","List all available queues\n");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        //TODO
    }
}