package com.example.salesianoscheck.services.impl;

import com.example.salesianoscheck.models.dto.CronDTO;
import com.example.salesianoscheck.models.service.CronService;
import com.example.salesianoscheck.services.CronManagementService;
import com.example.salesianoscheck.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CronManagementServiceImpl implements CronManagementService {

    private static final Logger LOG = LoggerFactory.getLogger(CronManagementServiceImpl.class);

    @Autowired
    private CronService cronService;

    @Override
    public void activateCheckIn() {
        try{
            cronService.modifyCronById(new CronDTO(Constants.ID_1,Constants.CHECK_IN,Constants.ON));
            LOG.info("Activado Cron de checkIn");
        } catch(Exception ex){
            LOG.error("Erro cancelando a activacion do Cron de checkIn", ex.getMessage());
        }
    }

    @Override
    public void activateCheckOut() {
        try{
            cronService.modifyCronById(new CronDTO(Constants.ID_2,Constants.CHECK_OUT,Constants.ON));
            LOG.info("Activado Cron de checkOut");
        } catch(Exception ex){
            LOG.error("Erro cancelando a activacion do Cron de checkOut", ex.getMessage());
        }
    }

    @Override
    public void cancelCheckIn() {
        try{
            cronService.modifyCronById(new CronDTO(Constants.ID_1,Constants.CHECK_IN,Constants.OFF));
            LOG.info("Desactivado Cron de checkIn");
        } catch(Exception ex){
            LOG.error("Erro desactivando o Cron de checkIn", ex.getMessage());
        }
    }

    @Override
    public void cancelCheckOut() {
        try{
            cronService.modifyCronById(new CronDTO(Constants.ID_2,Constants.CHECK_OUT,Constants.OFF));
            LOG.info("Desactivado Cron de checkOut");
        } catch(Exception ex){
            LOG.error("Erro desactivando o Cron de checkOut", ex.getMessage());
        }
    }
}
