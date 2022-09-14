package com.example.salesianoscheck.telegram.bo;

import com.example.salesianoscheck.telegram.utils.CommandLineUtils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramCommand {
    private boolean command = false;
    private String verb = null;
    private CommandLine params = null;
    private String chatId;
    private Long userId;


    public TelegramCommand(Update update, Options options) throws ParseException {
        String text = getData(update);
        String[] trozos = text.split(" ");

        if (trozos[0].startsWith("/")){
            this.command = true;
            this.verb = trozos[0].replace("/", "");
            if (options != null){
                this.params = CommandLineUtils.getCommandLine(text, options);
            }
        }
    }

    public TelegramCommand(Update update) {

        String text = getData(update);

        if (text!=null && !text.trim().equals("")){
            String[] trozos = text.split(" ");

            if (trozos.length>0 && trozos[0].startsWith("/")){
                this.command = true;
                this.verb = trozos[0].replace("/", "");
            }
        }
    }

    private String getData(Update update) {
        String text;
        if (update.getMessage() == null) {
            text = update.getCallbackQuery().getData();
            this.chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            this.userId = update.getCallbackQuery().getMessage().getFrom().getId();
        } else {
            text = update.getMessage().getText();
            this.chatId = String.valueOf(update.getMessage().getChatId());
            this.userId = update.getMessage().getFrom().getId();
        }
        return text;
    }


    public SendMessage sendMessage(String text, boolean html){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(this.getChatId());
        sendMessage.setText(text);
        sendMessage.enableHtml(html);
        return sendMessage;
    }

    public SendDocument sendDoc(InputFile file){
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(this.getChatId());
        sendDocument.setDocument(file);
        return sendDocument;
    }

    public SendDice sendDice(String emoji){
        SendDice sendDice = new SendDice();
        sendDice.setChatId(this.getChatId());
        sendDice.setEmoji(emoji);
        return sendDice;
    }

    public boolean isCommand() {
        return command;
    }

    public String getVerb() {
        return verb;
    }

    public CommandLine getParams() {
        return params;
    }

    public String getChatId() {
        return chatId;
    }

    public Long getUserId() {
        return userId;
    }
}
