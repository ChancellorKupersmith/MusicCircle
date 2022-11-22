package com.example.musiccircle.Services;

import ua.naiksoftware.stomp.StompClient;

public class StompUtils {
    @SuppressWarnings({"ResultOfMethodCallIgnored", "CheckResult"})
    public static void lifecycle(StompClient stompClient) {
        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    System.out.println("Stomp connection opened");
                    break;

                case ERROR:
                    System.out.println(lifecycleEvent.getException().toString());
                    break;

                case CLOSED:
                    System.out.println("Stomp connection closed");
                    break;
            }
        });
    }
}

