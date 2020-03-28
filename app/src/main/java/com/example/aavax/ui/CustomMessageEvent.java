package com.example.aavax.ui;

public class CustomMessageEvent {
    private String customMessage;

    public CustomMessageEvent(String customMessage) {
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    private void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }
}
