package ru.durnov.taco.integration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "tetxInChannel")
public interface FileWriterGateWay {
    void writeToFile(@Header(FileHeaders.FILENAME) String fileName, String data);
}
