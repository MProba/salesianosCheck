package com.example.salesianoscheck.models.dao;

import com.example.salesianoscheck.models.entity.Cron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronDAO extends JpaRepository<Cron,Long> {
}
