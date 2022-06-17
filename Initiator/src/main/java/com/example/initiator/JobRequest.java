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

//    Generate a random number between 100 & 1000 which will be used for the Job time
    public int generateTime(){
        return (int) Math.floor(Math.random()*(1000-100+1)+100);
    }
}
