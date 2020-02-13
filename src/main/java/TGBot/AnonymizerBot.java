package TGBot;

import TGBot.Command.*;
import exampler.console.MultiQueueController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.stream.Stream;

public final class AnonymizerBot extends TelegramLongPollingCommandBot {

    private static final Logger LOG = LogManager.getLogger(AnonymizerBot.class);
    // имя бота, которое мы указали при создании аккаунта у BotFather
    // и токен, который получили в результате
    private static final String BOT_NAME = "QueueManagerBot";
    private static final String BOT_TOKEN = "1097940489:AAEOA5rJH85nr5iUfEJeQkTbfQ2Be0hnl_g";


    private final AnonymousService mAnonymouses;

    public AnonymizerBot(DefaultBotOptions botOptions, MultiQueueController multiQueueController) {
        super(botOptions, false);

        LOG.info("Initializing Anonymizer Bot...");

        LOG.info("Initializing anonymouses list...");
        mAnonymouses = new AnonymousService();

        // регистрация всех кастомных команд
        register(new PrintQueuesCommand(mAnonymouses, multiQueueController));
        register(new PrintQueueCommand(mAnonymouses, multiQueueController));
        LOG.info("Registering commands...");
        LOG.info("Registering '/start'...");
        register(new StartCommand(mAnonymouses));
        LOG.info("Registering '/set_name'...");
        register(new SetNameCommand(mAnonymouses));
        LOG.info("Registering '/stop'...");
        register(new StopCommand(mAnonymouses));
        LOG.info("Registering '/my_name'...");
        register(new MyNameCommand(mAnonymouses));
        HelpCommand helpCommand = new HelpCommand(this);
        LOG.info("Registering '/help'...");
        register(helpCommand);

        // обработка неизвестной команды
        LOG.info("Registering default action'...");
        registerDefaultAction(((absSender, message) -> {


            SendMessage text = new SendMessage();
            text.setChatId(message.getChatId());
            text.setText(message.getText() + " command not found!");

            try {
                absSender.execute(text);
            } catch (TelegramApiException e) {
                LOG.error("Error while replying unknown command to user {}.", message.getFrom(), e);
            }

            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[]{});
        }));
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    // обработка сообщения не начинающегося с '/'
    @Override
    public void processNonCommandUpdate(Update update) {

        LOG.info("Processing non-command update...");

        if (!update.hasMessage()) {
            LOG.error("Update doesn't have a body!");
            throw new IllegalStateException("Update doesn't have a body!");
        }

        Message msg = update.getMessage();
        User user = msg.getFrom();


        if (!canSendMessage(user, msg)) {
            return;
        }

        String clearMessage = msg.getText();
        String messageForUsers = String.format("%s:\n%s", mAnonymouses.getDisplayedName(user), msg.getText());

        SendMessage answer = new SendMessage();

        // отправка ответа отправителю о том, что его сообщение получено
        answer.setText(clearMessage);
        answer.setChatId(msg.getChatId());
        replyToUser(answer, user, clearMessage);

        // отправка сообщения всем остальным пользователям бота
        answer.setText(messageForUsers);
        Stream<Anonymous> anonymouses = mAnonymouses.anonymouses();
        anonymouses.filter(a -> !a.getUser().equals(user))
                .forEach(a -> {
                    answer.setChatId(a.getChat().getId());
                    sendMessageToUser(answer, a.getUser(), user);
                });
    }

    // несколько проверок, чтобы можно было отправлять сообщения другим пользователям
    private boolean canSendMessage(User user, Message msg) {

        SendMessage answer = new SendMessage();
        answer.setChatId(msg.getChatId());

        if (!msg.hasText() || msg.getText().trim().length() == 0) {
            answer.setText("You shouldn't send empty messages!");
            replyToUser(answer, user, msg.getText());
            return false;
        }

        if (!mAnonymouses.hasAnonymous(user)) {
            answer.setText("Firstly you should start bot! Use /start command!");
            replyToUser(answer, user, msg.getText());
            return false;
        }

        if (mAnonymouses.getDisplayedName(user) == null) {
            answer.setText("You must set a name before sending messages.\nUse '/set_name <displayed_name>' command.");
            replyToUser(answer, user, msg.getText());
            return false;
        }

        return true;
    }

    private void sendMessageToUser(SendMessage message, User receiver, User sender) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
        }
    }

    private void replyToUser(SendMessage message, User user, String messageText) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
        }
    }
}