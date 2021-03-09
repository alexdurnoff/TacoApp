package ru.durnov.taco.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class FileWriterDSLConfig {

    @Bean
    public IntegrationFlow fileWeriterFlow(){
        return IntegrationFlows
                .from(MessageChannels.direct("textInChannel"))
                .<String,String> transform(String::toUpperCase)
                .channel(MessageChannels.direct("fileWriterChannel"))
                .handle(Files.outboundAdapter(new File("/tmp/sia5/files"))
                    .fileExistsMode(FileExistsMode.APPEND)
                    .appendNewLine(true))
                .get();

    }

    @Bean
    @ServiceActivator(inputChannel = "orderChannel")
    MessageChannel orderChannel(){
        return new PublishSubscribeChannel();
    }

    @Bean
    public IntegrationFlow orderFlow(){
        return IntegrationFlows
                .from("textInChannel")
                .transform(String::trim)
                .<String> filter(s -> s.length()<5)
                .channel(MessageChannels.direct("orderChannel"))
                        .handle(Files.outboundAdapter(new File("/tmp/sia5/orders"))
                                .fileExistsMode(FileExistsMode.APPEND)
                                .appendNewLine(true))
                .get();
    }

}
