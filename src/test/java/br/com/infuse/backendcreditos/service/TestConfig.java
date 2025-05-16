package br.com.infuse.backendcreditos.service;

import br.com.infuse.backendcreditos.dto.CreditoDTO;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@TestConfiguration
public class TestConfig {

    @Bean
    public Consumer<CreditoDTO> creditoConsumer() {
        return new Consumer<CreditoDTO>() {
            private final List<CreditoDTO> messages = new ArrayList<>();

            @KafkaListener(topics = "credito-topic", groupId = "test-group")
            public void accept(CreditoDTO creditoDTO) {
                messages.add(creditoDTO);
            }

            public List<CreditoDTO> getMessages() {
                return messages;
            }
        };
    }
}