package br.com.infuse.backendcreditos.service;

import br.com.infuse.backendcreditos.dto.CreditoDTO;
import br.com.infuse.backendcreditos.model.Credito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CreditoProducerService {

    private static final Logger log = LoggerFactory.getLogger(CreditoProducerService.class);
    public final KafkaTemplate<String, CreditoDTO> kafkaTemplate;

    public CreditoProducerService(KafkaTemplate<String, CreditoDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(CreditoDTO credito) {
        Thread.startVirtualThread(() -> {
            kafkaTemplate.send("credito-topic", credito)
                    .thenAccept(result -> {
                        int partition = result.getRecordMetadata().partition();
                        long offset = result.getRecordMetadata().offset();
                        log.info("Credito sent to partition {} at offset {}: {}", partition, offset, credito);
                    })
                    .exceptionally(error -> {
                        log.error("Failed to send message", error);
                        return null;
                    });
        });
    }

}