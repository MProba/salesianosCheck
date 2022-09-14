package com.example.salesianoscheck.telegram;

import com.example.salesianoscheck.services.CronManagementService;
import com.example.salesianoscheck.telegram.bo.TelegramCommand;
import com.example.salesianoscheck.telegram.utils.TelegramConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.text.SimpleDateFormat;

@Component
public class TelegramProcessor {

    @Autowired
    private CronManagementService cronManagementService;

    public SendMessage sendMessage(TelegramCommand telegramCommand, String message) {
        return telegramCommand.sendMessage(buildMessage(message),false);
    }

    public String buildMessage(String message) {
        StringBuilder sb = new StringBuilder();
        switch (message){
            case TelegramConstants.START_MESSAGE:
                sb.append(TelegramConstants.START_MESSAGE);
                break;
            case TelegramConstants.COMMAND_DESACTIVAR_CRON_ENTRADA:
                cronManagementService.cancelCheckIn();
                sb.append("Desactivado Cron de entrada");
                break;
            case TelegramConstants.COMMAND_DESACTIVAR_CRON_SALIDA:
                cronManagementService.cancelCheckOut();
                sb.append("Desactivado Cron de saída");
                break;
            case TelegramConstants.COMMAND_ACTIVAR_CRON_ENTRADA:
                cronManagementService.activateCheckIn();
                sb.append("Activado Cron de entrada");
                break;
            case TelegramConstants.COMMAND_ACTIVAR_CRON_SALIDA:
                cronManagementService.activateCheckOut();
                sb.append("Activado Cron de saída");
                break;

        }
        return sb.toString();
    }

}
