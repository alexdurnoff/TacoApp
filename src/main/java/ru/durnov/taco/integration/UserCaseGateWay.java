package ru.durnov.taco.integration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

@Component
@MessagingGateway(defaultRequestChannel = "inChannel",
        defaultReplyChannel = "outChannel")
public interface UserCaseGateWay {
    String upperCase(String in);
}
