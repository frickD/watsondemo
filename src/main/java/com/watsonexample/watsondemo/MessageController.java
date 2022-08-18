package com.watsonexample.watsondemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.SessionResponse;

@RestController
public class MessageController {
    

    private final String apiKey = System.getenv("ASSISTANT_APIKEY");
    private final String serviceUrl = "https://api.eu-de.assistant.watson.cloud.ibm.com/instances/75decd21-931b-46c5-af8c-f35edea83cb5";
    private final String assistantId = "af430f3b-0975-4772-aeca-29d26616c5dd";
    private Assistant assistant;

    public MessageController() {
        Authenticator authenticator = new IamAuthenticator.Builder()
        .apikey(apiKey)
        .build();
        Assistant assistant = new Assistant("2021-06-14", authenticator);
        assistant.setServiceUrl(serviceUrl);
        this.assistant = assistant;
    }

    @PostMapping("/messageAssistant")
    public String postBody(@RequestBody AssistantSession session) {
        
        MessageInput input = new MessageInput.Builder()
        .messageType("text")
        .text(session.getTextMessage())
        .build();

        MessageOptions options = new MessageOptions.Builder(assistantId, session.getSessionId())
        .input(input)
        .build();

        MessageResponse response = assistant.message(options).execute().getResult();
        
        return response.toString();
    }

    @GetMapping("/createNewSession")
    public String createNewSession() {
        String assistantId = "af430f3b-0975-4772-aeca-29d26616c5dd";
        CreateSessionOptions sessionOptions;
        SessionResponse sessionResponse;
        try {
            sessionOptions = new CreateSessionOptions.Builder(assistantId).build();
            sessionResponse = assistant.createSession(sessionOptions).execute().getResult(); 
            return sessionResponse.getSessionId();
        } catch (Exception e) {
            return "";
        }
        
    }

}