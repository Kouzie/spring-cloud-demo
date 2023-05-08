package com.example.app.account.message;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMessageSender {
//    private final Processor processor;
//
//    public boolean send(String payload) {
//        Message<String> message = MessageBuilder.withPayload(payload).build();
//        return processor.output().send(message);
//    }

    private final StreamBridge streamBridge;

    public boolean send(String payload) {
        boolean result = streamBridge.send("to-account-out-0", payload);
        return result;
    }
}
