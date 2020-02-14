package Command;

import Service.AnonymousService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class JoinQueueCommand extends AnonymizerCommand {
    public JoinQueueCommand(AnonymousService anonymouses){
        super("join_queue","join the queue\n");

    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder sb = new StringBuilder();
        SendMessage message = new SendMessage();
        //TODO
    }
}