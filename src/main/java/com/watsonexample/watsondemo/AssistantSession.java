package com.watsonexample.watsondemo;

public class AssistantSession {

    private String sessionId;
    private String textMessage;

    public AssistantSession() {
    }

    public AssistantSession(String sessionId, String textMessage) {
        this.sessionId = sessionId;
        this.textMessage = textMessage;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
    
}
