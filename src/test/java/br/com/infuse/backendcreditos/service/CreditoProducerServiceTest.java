package br.com.infuse.backendcreditos.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import br.com.infuse.backendcreditos.dto.CreditoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.concurrent.CompletableFuture;
import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class CreditoProducerServiceTest {

    @Mock
    private KafkaTemplate<String, CreditoDTO> kafkaTemplate;

    @Mock
    private SendResult<String, CreditoDTO> sendResult;

    @Mock
    private RecordMetadata recordMetadata;

    private CreditoProducerService creditoProducerService;
    private CreditoDTO creditoDTO;

    @BeforeEach
    void setUp() {
        creditoProducerService = new CreditoProducerService(kafkaTemplate);
        creditoDTO = criarCreditoDTO();
    }

    @Test
    @SuppressWarnings("unchecked")
    void sendMessageDeveEnviarMensagemComSucesso() throws Exception {
        // Arrange
        TopicPartition topicPartition = new TopicPartition("credito-topic", 1);
        when(recordMetadata.partition()).thenReturn(1);
        when(recordMetadata.offset()).thenReturn(123L);
        when(sendResult.getRecordMetadata()).thenReturn(recordMetadata);

        CompletableFuture<SendResult<String, CreditoDTO>> future = new CompletableFuture<>();
        future.complete(sendResult);
        when(kafkaTemplate.send(anyString(), any(CreditoDTO.class))).thenReturn(future);

        // Act
        creditoProducerService.sendMessage(creditoDTO);

        // Assert
        Thread.sleep(100); // Pequena espera para garantir que a thread virtual complete
        verify(kafkaTemplate, times(1)).send("credito-topic", creditoDTO);
    }

    @Test
    @SuppressWarnings("unchecked")
    void sendMessageDeveTratarErroAoEnviarMensagem() throws Exception {
        // Arrange
        CompletableFuture<SendResult<String, CreditoDTO>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Erro ao enviar mensagem"));
        when(kafkaTemplate.send(anyString(), any(CreditoDTO.class))).thenReturn(future);

        // Act
        creditoProducerService.sendMessage(creditoDTO);

        // Assert
        Thread.sleep(100); // Pequena espera para garantir que a thread virtual complete
        verify(kafkaTemplate, times(1)).send("credito-topic", creditoDTO);
    }

    private CreditoDTO criarCreditoDTO() {
        return new CreditoDTO(
                "CRED123",
                "NFE123",
                LocalDate.now(),
                BigDecimal.valueOf(100),
                "TIPO1",
                false,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(1000)
        );
    }
}