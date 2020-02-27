package TGBot.Command;

import TGBot.Service.AnonymousService;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class DeleteFromQueueCommand extends AnonymizerCommand {

    private final AnonymousService mAnonymouses;

    public DeleteFromQueueCommand(AnonymousService anonymouses){
        super("/delete_user","Delete an user from queue. Format: /get_position [name of queue] [given number phone]\n");
        mAnonymouses = anonymouses;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings){
        StringBuilder sb = new StringBuilder();
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (!mAnonymouses.hasAnonymous(user)){
            sb.append("You are not in bot users' list! Use /start command!");
        }else if (mAnonymouses.getDisplayedName(user) == null) {
            sb.append("Currently you don't have a name.\nSet it using command: /set_name ");
        } else if (strings.length != 2) sb.append("Please fill up information as format!");
        else {
            String nameQueue = strings[0];
            String numberPhone = checkNumPhone(strings[1]);

            if (numberPhone==null) sb.append("Please correct your number phone!");
            else {
                String url = "/api/deleteFromQueue?queueName=" + nameQueue + "&phoneNumber=" + numberPhone;
                sb.append(getResponeToBot(url));
            }
        }
        message.setText(sb.toString());
        execute(absSender, message, user);
    }

    private String checkNumPhone(String number) {
        for(int i=0;i<number.length();i++) if (number.charAt(i) < '0' || number.charAt(i)>'9') return null;
        return number;
    }

}