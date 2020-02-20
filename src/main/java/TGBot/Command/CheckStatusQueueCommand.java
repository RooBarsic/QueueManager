package TGBot.Command;

import TGBot.Service.AnonymousService;
import logic.queue.QueuesBox;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

public class CheckStatusQueueCommand extends AnonymizerCommand {

    private final AnonymousService mAnonymouses;
    private QueuesBox QueuesBox;

    public CheckStatusQueueCommand(AnonymousService anonymouses, QueuesBox QueuesBox) {
        super("check_queue", "Show the queue of given name\n");
        mAnonymouses = anonymouses;
        this.QueuesBox = QueuesBox;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) throws NullPointerException{
        StringBuilder sb = new StringBuilder();
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (!mAnonymouses.hasAnonymous(user)){
            sb.append("You are not in bot users' list! Send /start command!");
        }else if (mAnonymouses.getDisplayedName(user) == null) {
            sb.append("Currently you don't have a name.\nSet it using command: /set_name ");
        }else {
            String nameQueue = getNameQueue(strings);
            if (nameQueue==null) sb.append("Please insert name of queue, which you want to check");
            else if (!QueuesBox.queueExist(nameQueue)) sb.append("The given name does not match any queue");
            else sb.append(QueuesBox.getQueue(nameQueue).values().toString());
        }
        message.setText(sb.toString());
        execute(absSender, message, user);

    }

    private String getNameQueue(String[] strings) {

        if (strings == null || strings.length == 0) {
            return null;
        }

        String name = String.join(" ", strings);
        return name.replaceAll(" ", "").isEmpty() ? null : name;
    }
}