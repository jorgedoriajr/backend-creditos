package br.com.infuse.backendcreditos.configs;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;

@Configuration
public class KafkaAdminConfig {

    /* Porque o SonarQube está reclamando?
       Podemos criar um construtor para essa variável    //ou//
       anotar a classe com @RequiredArgsConstructor e tornar final a variavel properties
     */
    @Autowired
    public KafkaProperties properties;

    /**
     * Definir um método para ao executar se conectar com o Kafka
     * @return
     */
    @Bean
    public KafkaAdmin KafkaAdmin() {
        var configs = new HashMap<String, Object>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

    /**
     * Definir um método para criar um tópico de Créditos em tempos de execução
     */
    @Bean
    public KafkaAdmin.NewTopics newTopics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("credito-topic").partitions(2).replicas(1).build()
        );
    }

}