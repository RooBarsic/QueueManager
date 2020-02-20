package TGBot.Command;

import TGBot.Service.AnonymousService;
import logic.customer.Customer;
import logic.queue.QueuesBox;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

public class JoinQueueCommand extends AnonymizerCommand {

    private final AnonymousService mAnonymouses;
    private QueuesBox QueuesBox;

    public JoinQueueCommand(AnonymousService anonymouses,QueuesBox QueuesBox){
        super("join_queue","Register your information into the queue\n Format : /join_queue [name of queue] [your name] [your numberphone] ");
        mAnonymouses = anonymouses;
        this.QueuesBox = QueuesBox;
    }

    // format of strings : [name of queue] [user's name] [phone]
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder sb = new StringBuilder();
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        Customer customer;

        if (!mAnonymouses.hasAnonymous(user)){
            sb.append("You are not in bot users' list! Send /start command!");
        }else if (mAnonymouses.getDisplayedName(user) == null) {
            sb.append("Currently you don't have a name.\nSet it using command: /set_name ");
        } else if (strings.length != 3) sb.append("Please fill up information as format!");
        else {
            String nameQueue = checkNameQueue(strings[0]);
            String nameUser = checkName(strings[1]);
            String numberPhone = checkNumPhone(strings[2]);
            //
            if (nameQueue==null) sb.append("Please correct name of queue!");
            else if (nameUser==null) sb.append("Please correct your name!");
            else if (numberPhone==null) sb.append("Please correct your number phone!");
            else {
                customer = new Customer(nameUser, numberPhone);
                QueuesBox.getQueue(nameQueue).add(customer);
                sb.append(nameUser + " was added to the queue. Your position is ")
                        .append(QueuesBox.getQueue(nameQueue).findIndex(customer))
                        .append(" ! ");
            }
        }


        message.setText(sb.toString());
        execute(absSender, message, user);
    }

    /**
     * The method take information of client, and check, then put it into a list
     * The first index - name of queue, the second - name of client, and last - number phone
     *
     * @param strings given information of user
     * @return List of string
     */
    private List<String> checkInformation(String[] strings){
        return null;
    }


    /**
     * The method checks given number is number phone or not
     * @param number given number phone
     * @return number phone if it were right, otherwise null
     */
    private String checkNumPhone(String number) {
        for(int i=0;i<number.length();i++) if (number.charAt(i) < '0' || number.charAt(i)>'9') return null;
        return number;
    }

    /**
     * The method checks given name is right name or not. The right name is a string, in which does not contain number or special character
     * @param name given name of user
     * @return name if normal name, otherwise null.
     */
    private String checkName(String name){
        String nameCase = name.toLowerCase();
        for(int i=0;i<name.length();i++) if (nameCase.charAt(i) < 'a' || nameCase.charAt(i) > 'z') return null;
        return name;
    }


    /**
     * The method check if name of Queue exists or not
     * @param nameQueue given name of queue, to which client will register
     * @return name of queue if it exists, otherwise null
     */
    private String checkNameQueue(String nameQueue){
        if (!QueuesBox.queueExist(nameQueue)) return null;
        return nameQueue;
    }
}