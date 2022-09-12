package com.example.salesianoscheck.controller;

import com.example.salesianoscheck.models.dto.CronDTO;
import com.example.salesianoscheck.models.service.CronService;
import com.example.salesianoscheck.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private CronService cronService;

    @GetMapping(path = Constants.ACTIVATE_CHECKIN_PATH)
    public void activateCheckIn() {
        try{
            cronService.modifyCronById(new CronDTO(Constants.ID_1,Constants.CHECK_IN,Constants.ON));
            LOG.info("Activado Cron de checkIn");
        } catch(Exception ex){
            LOG.error("Erro cancelando a activacion do Cron de checkIn", ex.getMessage());
        }
    }

    @GetMapping(path = Constants.ACTIVATE_CHECKOUT_PATH)
    public void activateCheckOut() {
        try{
            cronService.modifyCronById(new CronDTO(Constants.ID_2,Constants.CHECK_OUT,Constants.ON));
            LOG.info("Activado Cron de checkOut");
        } catch(Exception ex){
            LOG.error("Erro cancelando a activacion do Cron de checkOut", ex.getMessage());
        }
    }

    @GetMapping(path = Constants.CANCEL_CHECKIN_PATH)
    public void cancelCheckIn() {
        try{
            cronService.modifyCronById(new CronDTO(Constants.ID_1,Constants.CHECK_IN,Constants.OFF));
            LOG.info("Desactivado Cron de checkIn");
        } catch(Exception ex){
            LOG.error("Erro desactivando o Cron de checkIn", ex.getMessage());
        }
    }

    @GetMapping(path = Constants.CANCEL_CHECKOUT_PATH)
    public void cancelCheckOut() {
        try{
            cronService.modifyCronById(new CronDTO(Constants.ID_2,Constants.CHECK_OUT,Constants.OFF));
            LOG.info("Desactivado Cron de checkOut");
        } catch(Exception ex){
            LOG.error("Erro desactivando o Cron de checkOut", ex.getMessage());
        }
    }

}
