package com.demoapp.memoryleakdemo;

public class MyThread extends Thread {

    @Override
    public void run() {
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
