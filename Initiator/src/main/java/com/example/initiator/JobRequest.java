package com.example.initiator;
import java.util.Random;
import java.util.UUID;

public class JobRequest {
    UUID id;
    int time;
    JobRequest(){
     id = java.util.UUID.randomUUID();
     time = generateTime();
    }

    public int generateTime(){
        return (int) Math.floor(Math.random()*(1000-100+1)+100);
    }
}
