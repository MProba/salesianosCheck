package com.example.salesianoscheck.telegram;

import com.example.salesianoscheck.telegram.bo.CommandData;
import com.example.salesianoscheck.telegram.bo.TelegramCommand;
import com.example.salesianoscheck.telegram.utils.CommandLineUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Set;

public abstract class AbstractTelegramLongPollingBot extends TelegramLongPollingBot {

    protected String botUsername;
    protected String apiKey;
    protected String botFunction;
    protected HashMap<String, CommandData> commands;
    protected Set<String> restrictedUsers;

    public static final String COMMAND_HELP = "help";


    @PostConstruct
    private void init() throws TelegramApiException {
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.apiKey;
    }


    public void sendMessage(SendMessage sendMessage){
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public Message sendPoll(SendPoll sendPoll){
        Message message = null;
        try {
            message = execute(sendPoll);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void sendDocument(SendDocument sendDocument){
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void sendAudio(SendAudio sendAudio){
        try {
            execute(sendAudio);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void sendDice(SendDice sendDice){
        try {
            execute(sendDice);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void printMessage(TelegramCommand tc, String msg) {
        try {
            execute(tc.sendMessage(msg, false));
        } catch (TelegramApiException telegramApiException) {
            telegramApiException.printStackTrace();
        }
    }

    protected void printHelp(TelegramCommand tc) {
        String line = this.botFunction.concat("\n");
        for(CommandData cd:this.commands.values()){
            line = line.concat(CommandLineUtils.getHelpCommandLine(cd.getVerb(), cd.getOptions(), cd.getPresentation()));
        }
        printMessage(tc, line);
    }

    protected void noImplement(TelegramCommand tc) {
        printMessage(tc, "option no implemented");
    }

    protected boolean restrictedUser(Update update) {
        if (this.restrictedUsers.isEmpty()){
            return true;
        }
        return this.restrictedUsers.contains(String.valueOf(update.getMessage().getFrom().getUserName()));
    }


}
