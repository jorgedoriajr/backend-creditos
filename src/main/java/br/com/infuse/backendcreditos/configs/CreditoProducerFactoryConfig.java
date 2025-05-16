package br.com.infuse.backendcreditos.configs;

import br.com.infuse.backendcreditos.dto.CreditoDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

@Configuration
public class CreditoProducerFactoryConfig {

    private final KafkaProperties properties;

    public CreditoProducerFactoryConfig(KafkaProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ProducerFactory<String, CreditoDTO> producerFactory() {
        var configs = new HashMap<String, Object>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Qual o objeto que fará a serialização?
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Alterado para Json
        return new DefaultKafkaProducerFactory<>(configs);
    }

    /**
     * Criar o KafkaTemplate para utilizar o producerFactory para poder enviar as mensagem para o tópico
     */
    @Bean
    public KafkaTemplate<String, CreditoDTO> kafkaTemplate(ProducerFactory<String, CreditoDTO> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}