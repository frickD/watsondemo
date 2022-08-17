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

    @PostMapping("/messageAssistant")
    public String postBody(@RequestBody String userText) {

        String apiKey = System.getenv("ASSISTANT_APIKEY");
        String serviceUrl = System.getenv("ASSISTANT_URL");
        String assistantId = System.getenv("ASSISTANT_ID");

        Authenticator authenticator = new IamAuthenticator.Builder()
        .apikey(apiKey)
        .build();
        Assistant assistant = new Assistant("2021-06-14", authenticator);
        assistant.setServiceUrl(serviceUrl);

        CreateSessionOptions sessionOptions = new CreateSessionOptions.Builder(assistantId).build();
        SessionResponse sessionResponse = assistant.createSession(sessionOptions).execute().getResult(); 
        
        MessageInput input = new MessageInput.Builder()
        .messageType("text")
        .text(userText)
        .build();

        MessageOptions options = new MessageOptions.Builder(assistantId, sessionResponse.getSessionId())
        .input(input)
        .build();

        MessageResponse response = assistant.message(options).execute().getResult();
        
        return response.toString();
    }

    @GetMapping("/getSystemVariables")
    public String home() {
        String apiKey = System.getenv("ASSISTANT_APIKEY");
        String serviceUrl = System.getenv("ASSISTANT_URL");
        String assistantId = System.getenv("ASSISTANT_ID");
        return "Apikey: " + apiKey + "/n Service: " + serviceUrl + "/n assistantId: " + assistantId;
    }
}