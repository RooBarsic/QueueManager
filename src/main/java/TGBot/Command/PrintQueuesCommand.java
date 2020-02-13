package TGBot.Command;

import TGBot.AnonymousService;
import exampler.console.MultiQueueController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public final class PrintQueuesCommand extends AnonymizerCommand {
    private final AnonymousService mAnonymouses;
    private MultiQueueController multiQueueController;

    public PrintQueuesCommand(AnonymousService anonymouses, MultiQueueController multiQueueController) {
        super("all_queues", "shows you all queues\n");
        mAnonymouses = anonymouses;
        this.multiQueueController = multiQueueController;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {


        StringBuilder sb = new StringBuilder();

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (!mAnonymouses.hasAnonymous(user)) {

            sb.append("You are not in bot users' list! Send /start command!");

        } else if (mAnonymouses.getDisplayedName(user) == null) {

            sb.append("Currently you don't have a name.\nSet it using command:\n'/set_name &lt;displayed_name&gt;'");

        } else {


            sb.append("All queues:\n");

            for (String nm : multiQueueController.getQueuesNames()) {
                sb.append(nm.concat("\n"));
            }

        }

        message.setText(sb.toString());
        execute(absSender, message, user);
    }
}