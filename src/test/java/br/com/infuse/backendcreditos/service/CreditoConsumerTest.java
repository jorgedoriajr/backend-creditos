package br.com.infuse.backendcreditos.service;

import br.com.infuse.backendcreditos.dto.CreditoDTO;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CreditoConsumerTest implements Consumer<CreditoDTO> {

    private final List<CreditoDTO> messages = new ArrayList<>();

    @Override
    @KafkaListener(topics = "credito-topic", groupId = "test-group")
    public void accept(CreditoDTO creditoDTO) {
        messages.add(creditoDTO);
    }

    public List<CreditoDTO> getMessages() {
        return messages;
    }

}