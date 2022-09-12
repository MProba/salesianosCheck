package com.example.salesianoscheck.models.impl;

import com.example.salesianoscheck.models.dao.CronDAO;
import com.example.salesianoscheck.models.dto.CronDTO;
import com.example.salesianoscheck.models.entity.Cron;
import com.example.salesianoscheck.models.service.CronService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CronServiceImpl implements CronService {

    @Autowired
    private CronDAO cronDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CronDTO getCronById(Long id) {
        return modelMapper.map(cronDAO.findById(id).get(),CronDTO.class);
    }

    @Override
    public void modifyCronById(CronDTO cronDTO) {
        cronDAO.save(modelMapper.map(cronDTO, Cron.class));
    }
}
