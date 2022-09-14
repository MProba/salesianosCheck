package com.example.salesianoscheck.services;

public interface CronManagementService {
     void activateCheckIn();
     void activateCheckOut();
     void cancelCheckIn();
     void cancelCheckOut();
}
