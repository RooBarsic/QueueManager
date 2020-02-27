package TGBot.Command;

import TGBot.Service.AnonymousService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CheckStatusQueueCommand extends AnonymizerCommand {

    private final AnonymousService mAnonymouses;

    public CheckStatusQueueCommand(AnonymousService anonymouses) {
        super("check_queue", "Show the queue of given name\n");
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
            if (strings[0]==null) sb.append("Please insert name of queue, which you want to check");
            else {
                String nameQueue = strings[0];
               // System.out.println(nameQueue);
                String url = "/api/getQueue?queueName=" + nameQueue;
                //sb.append(getResponeToBot(url));
                String lines = getResponeToBot(url).toString();
                int temp = 0;
                for(int i =0;i<lines.length();i++) if (lines.charAt(i)=='\n') temp++;
                sb.append("There is ")
                  .append(temp)
                  .append(" people in given queue.");

            }
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