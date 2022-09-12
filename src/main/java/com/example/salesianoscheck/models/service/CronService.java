package com.example.salesianoscheck.models.service;

import com.example.salesianoscheck.models.dto.CronDTO;

public interface CronService {
    CronDTO getCronById(Long id);
    void modifyCronById(CronDTO cronDTO);
}
