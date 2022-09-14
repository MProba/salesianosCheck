package com.example.salesianoscheck.telegram;

import com.example.salesianoscheck.telegram.bo.CommandData;
import com.example.salesianoscheck.telegram.bo.TelegramCommand;
import com.example.salesianoscheck.telegram.utils.CommandLineUtils;
import com.example.salesianoscheck.telegram.utils.TelegramConstants;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Component
public class TelegramService extends AbstractTelegramLongPollingBot {

    private final Logger log = LoggerFactory.getLogger(TelegramService.class);

    private TelegramProcessor telegramProcessor;

    public TelegramService(
            @Value("${BotTelegramSpring.name}") String botUsername,
            @Value("${BotTelegramSpring.apiKey}") String apiKey,
            @Value("${BotTelegramSpring.function}") String botFunction,
            @Value("${BotTelegramSpring.restrictedUsers}") String restrictedUsers,
            TelegramProcessor telegramProcessor) {

        this.botUsername = botUsername;
        this.apiKey = apiKey;
        this.botFunction = botFunction;
        this.restrictedUsers = StringUtils.commaDelimitedListToSet(restrictedUsers);
        this.telegramProcessor = telegramProcessor;
        initCommands();
    }

    private void initCommands() {
        commands = new HashMap<>();

        CommandData commandDataStart = CommandData.CommandDataBuilder.commandData()
                .withVerb("/" + TelegramConstants.COMMAND_START)
                .withOptions(null)
                .withPresentation("Comando de inicio")
                .build();

        CommandData commandDataStopCheckinCron = CommandData.CommandDataBuilder.commandData()
                .withVerb("/" + TelegramConstants.COMMAND_DESACTIVAR_CRON_ENTRADA)
                .withOptions(null)
                .withPresentation("Desactiva a execución do Cron de entrada")
                .build();

        CommandData commandDataStopCheckoutCron = CommandData.CommandDataBuilder.commandData()
                .withVerb("/" + TelegramConstants.COMMAND_DESACTIVAR_CRON_SALIDA)
                .withOptions(null)
                .withPresentation("Desactiva a execución do Cron de salida")
                .build();

        CommandData commandDataStartCheckinCron = CommandData.CommandDataBuilder.commandData()
                .withVerb("/" + TelegramConstants.COMMAND_ACTIVAR_CRON_ENTRADA)
                .withOptions(null)
                .withPresentation("Activa a execución do Cron de entrada")
                .build();

        CommandData commandDataStartCheckoutCron = CommandData.CommandDataBuilder.commandData()
                .withVerb("/" + TelegramConstants.COMMAND_ACTIVAR_CRON_SALIDA)
                .withOptions(null)
                .withPresentation("Activa a execución do Cron de salida")
                .build();

        commands.put(TelegramConstants.COMMAND_START, commandDataStart);
        commands.put(TelegramConstants.COMMAND_DESACTIVAR_CRON_ENTRADA, commandDataStopCheckinCron);
        commands.put(TelegramConstants.COMMAND_DESACTIVAR_CRON_SALIDA, commandDataStopCheckoutCron);
        commands.put(TelegramConstants.COMMAND_ACTIVAR_CRON_ENTRADA, commandDataStartCheckinCron);
        commands.put(TelegramConstants.COMMAND_ACTIVAR_CRON_SALIDA, commandDataStartCheckoutCron);

    }

    @Override
    public void onUpdateReceived(Update update) {
        TelegramCommand tc = new TelegramCommand(update);

        if (!this.restrictedUser(update)) {
            this.sendMessage(tc.sendMessage("Usuario non válido", false));
        } else {
            if (tc.getVerb() == null) {
                return;
            }

            switch (tc.getVerb()) {
                case TelegramConstants.COMMAND_START:
                    log.info("Iniciado bot desde: " + getInfoUser(update));
                    processMsg(update, TelegramConstants.COMMAND_START);
                    break;
                case TelegramConstants.COMMAND_DESACTIVAR_CRON_ENTRADA:
                    log.info("Solicitado " + TelegramConstants.COMMAND_DESACTIVAR_CRON_ENTRADA + " desde: " + getInfoUser(update));
                    processMsg(update, TelegramConstants.COMMAND_DESACTIVAR_CRON_ENTRADA);
                    break;
                case TelegramConstants.COMMAND_DESACTIVAR_CRON_SALIDA:
                    log.info("Solicitado " + TelegramConstants.COMMAND_DESACTIVAR_CRON_SALIDA + " desde: " + getInfoUser(update));
                    processMsg(update, TelegramConstants.COMMAND_DESACTIVAR_CRON_SALIDA);
                    break;
                case TelegramConstants.COMMAND_ACTIVAR_CRON_ENTRADA:
                    log.info("Solicitado " + TelegramConstants.COMMAND_ACTIVAR_CRON_ENTRADA + " desde: " + getInfoUser(update));
                    processMsg(update, TelegramConstants.COMMAND_ACTIVAR_CRON_ENTRADA);
                    break;
                case TelegramConstants.COMMAND_ACTIVAR_CRON_SALIDA:
                    log.info("Solicitado " + TelegramConstants.COMMAND_ACTIVAR_CRON_SALIDA + " desde: " + getInfoUser(update));
                    processMsg(update, TelegramConstants.COMMAND_ACTIVAR_CRON_SALIDA);
                    break;
                case COMMAND_HELP:
                    printHelp(tc);
                    break;
                default:
                    noImplement(tc);

            }
        }
    }

    private void processMsg(Update update, String command) {
        switch (command) {
            case TelegramConstants.COMMAND_START:
                CommandData commandDataStart = commands.get(TelegramConstants.COMMAND_START);
                try {
                    this.sendMessage(this.telegramProcessor.sendMessage(new TelegramCommand(update, commandDataStart.getOptions()), TelegramConstants.START_MESSAGE));
                } catch (ParseException e) {
                    printMessage(new TelegramCommand(update), CommandLineUtils
                            .getHelpCommandLine(commandDataStart.getVerb(), commandDataStart.getOptions(), commandDataStart.getPresentation()));
                }
                break;
            case TelegramConstants.COMMAND_DESACTIVAR_CRON_ENTRADA:
                CommandData commandDataStopCheckinCron = commands.get(TelegramConstants.COMMAND_DESACTIVAR_CRON_ENTRADA);
                try {
                    this.sendMessage((this.telegramProcessor.sendMessage(new TelegramCommand(update, commandDataStopCheckinCron.getOptions()), TelegramConstants.COMMAND_DESACTIVAR_CRON_ENTRADA)));
                } catch (ParseException e) {
                    printMessage(new TelegramCommand(update), CommandLineUtils
                            .getHelpCommandLine(commandDataStopCheckinCron.getVerb(), commandDataStopCheckinCron.getOptions(), commandDataStopCheckinCron.getPresentation()));
                }
                break;
            case TelegramConstants.COMMAND_DESACTIVAR_CRON_SALIDA:
                CommandData commandDataStopCheckoutCron = commands.get(TelegramConstants.COMMAND_DESACTIVAR_CRON_SALIDA);
                try {
                    this.sendMessage((this.telegramProcessor.sendMessage(new TelegramCommand(update, commandDataStopCheckoutCron.getOptions()), TelegramConstants.COMMAND_DESACTIVAR_CRON_SALIDA)));
                } catch (ParseException e) {
                    printMessage(new TelegramCommand(update), CommandLineUtils
                            .getHelpCommandLine(commandDataStopCheckoutCron.getVerb(), commandDataStopCheckoutCron.getOptions(), commandDataStopCheckoutCron.getPresentation()));
                }
                break;
            case TelegramConstants.COMMAND_ACTIVAR_CRON_ENTRADA:
                CommandData commandDataStartCheckinCron = commands.get(TelegramConstants.COMMAND_ACTIVAR_CRON_ENTRADA);
                try {
                    this.sendMessage((this.telegramProcessor.sendMessage(new TelegramCommand(update, commandDataStartCheckinCron.getOptions()), TelegramConstants.COMMAND_ACTIVAR_CRON_ENTRADA)));
                } catch (ParseException e) {
                    printMessage(new TelegramCommand(update), CommandLineUtils
                            .getHelpCommandLine(commandDataStartCheckinCron.getVerb(), commandDataStartCheckinCron.getOptions(), commandDataStartCheckinCron.getPresentation()));
                }
                break;
            case TelegramConstants.COMMAND_ACTIVAR_CRON_SALIDA:
                CommandData commandDataStartCheckoutCron = commands.get(TelegramConstants.COMMAND_ACTIVAR_CRON_SALIDA);
                try {
                    this.sendMessage((this.telegramProcessor.sendMessage(new TelegramCommand(update, commandDataStartCheckoutCron.getOptions()), TelegramConstants.COMMAND_ACTIVAR_CRON_SALIDA)));
                } catch (ParseException e) {
                    printMessage(new TelegramCommand(update), CommandLineUtils
                            .getHelpCommandLine(commandDataStartCheckoutCron.getVerb(), commandDataStartCheckoutCron.getOptions(), commandDataStartCheckoutCron.getPresentation()));
                }
                break;
        }
    }

    private String getInfoUser(Update update) {
        return update.getMessage().getChat().getId() + "-" + update.getMessage().getChat().getUserName();
    }

    @Override
    protected void noImplement(TelegramCommand tc) {
        this.printMessage(tc, "Opción non implementada");
    }

}
