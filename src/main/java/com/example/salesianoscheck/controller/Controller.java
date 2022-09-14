package com.example.salesianoscheck.controller;

import com.example.salesianoscheck.models.dto.CronDTO;
import com.example.salesianoscheck.models.service.CronService;
import com.example.salesianoscheck.services.CronManagementService;
import com.example.salesianoscheck.services.ScraperService;
import com.example.salesianoscheck.services.TelegramChannelService;
import com.example.salesianoscheck.telegram.TelegramService;
import com.example.salesianoscheck.utils.Constants;
import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class Controller {

    private final Logger LOG = LoggerFactory.getLogger(Controller.class);

    @Value("${website.url}")
    String url;

    @Value("${website.user}")
    String user;

    @Value("${website.pass}")
    String pass;

    @Autowired
    private CronManagementService cronManagementService;

    @Autowired
    private TelegramChannelService telegramChannelService;

    @GetMapping(path = Constants.ACTIVATE_CHECKIN_PATH)
    public void activateCheckIn() {
        cronManagementService.activateCheckIn();
    }

    @GetMapping(path = Constants.ACTIVATE_CHECKOUT_PATH)
    public void activateCheckOut() {
        cronManagementService.activateCheckOut();
    }

    @GetMapping(path = Constants.CANCEL_CHECKIN_PATH)
    public void cancelCheckIn() {
        cronManagementService.cancelCheckIn();
    }

    @GetMapping(path = Constants.CANCEL_CHECKOUT_PATH)
    public void cancelCheckOut() {
        cronManagementService.cancelCheckOut();
    }

    @GetMapping(path = "/pruebas")
    public String pruebas() {
        telegramChannelService.sendMessageToChannel("Proba");
        /*
        CronExpression expression = CronExpression.parse("* 25 14 * * 2-3");
        LocalDateTime result = expression.next(LocalDateTime.now());
        System.out.println(result);
        return result.toString();
         */
        return null;
    }
}
