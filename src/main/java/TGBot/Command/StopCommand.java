package TGBot.Command;

import TGBot.Service.AnonymousService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StopCommand extends AnonymizerCommand {
    private final AnonymousService mAnonymouses;

    public StopCommand(AnonymousService anonymouses){
        super("stop","stop using bot\n");
        mAnonymouses = anonymouses;
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        StringBuilder sb = new StringBuilder();

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (mAnonymouses.removeAnonymous(user)) {
            sb.append("You've been removed from bot's users list! Bye!");
        } else {
            sb.append("You were not in bot users' list. Bye!");
        }

        message.setText(sb.toString());
        execute(absSender, message, user);
    }
}
