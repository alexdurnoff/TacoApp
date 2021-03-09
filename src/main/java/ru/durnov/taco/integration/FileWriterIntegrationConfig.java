package ru.durnov.taco.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.router.MessageRouter;
import org.springframework.integration.router.PayloadTypeRouter;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
public class FileWriterIntegrationConfig {

    @Bean
    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransformer(){
        return String::toUpperCase;
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter(){
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("/tmp/sia5/files"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }


    @Bean
    public MessageChannel textInChannel(){
        return new DirectChannel();
    }

    @Bean
    public MessageChannel fileWriterChannel(){
        return new DirectChannel();
    }

    @Bean
    @Filter(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
    public boolean lengthOfStringMustLess5(String string){
        return string.length() < 5;
    }

    @Bean
    @Router(inputChannel = "numberChannel")
    public AbstractMessageRouter evenOddRouter(){
        return new AbstractMessageRouter() {
            @Override
            protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
                Integer number = (Integer) message.getPayload();
                if (number %2 == 0) {
                    return Collections.singleton(oddChannel());
                }
                return Collections.singleton(evenChannel());
            }

            private MessageChannel oddChannel() {
                return new DirectChannel();
            }

            private MessageChannel evenChannel() {
                return new DirectChannel();
            }
        };
    }

    @Bean
    @Splitter(inputChannel = "poChannel", outputChannel = "splitOrderChannel")
    public OrderSplitter orderSplitter(){
        return new OrderSplitter();
    }

    @Bean
    @Router(inputChannel = "splitOrderChannel")
    public MessageRouter splitOrderRouter(){
        PayloadTypeRouter router = new PayloadTypeRouter();
        router.setChannelMapping(BillingInfo.class.getName(), "billingInfoChannel");
        router.setChannelMapping(List.class.getName(), "lineItemsChannel");
        return router;
    }

    @Bean
    @Splitter(inputChannel = "lineItemsChannel", outputChannel = "lineChannel")
    public List<LineItem> lineItemSplitter(List<LineItem> lineItems){
        return lineItems;
    }

}
