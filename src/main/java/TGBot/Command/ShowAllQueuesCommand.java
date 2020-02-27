package TGBot.Command;

import TGBot.Service.AnonymousService;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class ShowAllQueuesCommand extends AnonymizerCommand {

    private final AnonymousService mAnonymouses;
    public ShowAllQueuesCommand(AnonymousService anonymouses){
        super("show_all_queues","Show all available queues\n");
        mAnonymouses = anonymouses;

    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        StringBuilder sb = new StringBuilder();
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (!mAnonymouses.hasAnonymous(user)){
            sb.append("You are not in bot users' list! Send /start command!");
        }else if (mAnonymouses.getDisplayedName(user) == null) {
            sb.append("Currently you don't have a name.\nSet it using command: /set_name ");
        }else {
            String url = "/api/getAllQueues";
            sb.append(getResponeToBot(url));
        }

        message.setText(sb.toString());
        execute(absSender, message, user);
    }


}