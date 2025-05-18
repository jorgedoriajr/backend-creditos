package br.com.infuse.backendcreditos.integration;

import br.com.infuse.backendcreditos.dto.CreditoDTO;
import br.com.infuse.backendcreditos.model.Credito;
import br.com.infuse.backendcreditos.repository.CreditoRepository;
import br.com.infuse.backendcreditos.service.CreditoConsumerTest;
import br.com.infuse.backendcreditos.service.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Import({ObjectMapper.class, TestConfig.class})
public class CreditoIntegrationTest {

    @Autowired
    private CreditoConsumerTest creditoConsumerTest;

    /*private static final Network network = Network.builder()
            .createNetworkCmdModifier(cmd -> cmd.withName("my_network"))
            .build();*/

    //private static final Network network = DockerNetworkUtil.useExistingNetworkByName("my_network");

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            //.withNetwork(network)
            .withNetworkAliases("postgres")
            .withEnv("POSTGRES_DB", "infuse")
            .withEnv("POSTGRES_USER", "postgres")
            .withEnv("POSTGRES_PASSWORD", "postgres")
            .withExposedPorts(5432);

    @Container
    static KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.4.0")
                    .asCompatibleSubstituteFor("confluentinc/cp-kafka") // Não apache/kafka!
    );
            //.withNetwork(network);

    @Test
    void testKafkaIsRunning() {
        assertTrue(kafka.isRunning());
        System.out.println("Kafka bootstrap servers: " + kafka.getBootstrapServers());
        System.out.println("Kafka port: " + kafka.getMappedPort(9093));
        System.out.println("Kafka url: http://localhost:" + kafka.getMappedPort(9093));
        System.out.println("Kafka host: " + kafka.getHost());
    }

    @Test
    void testPostgresIsRunning() {
        assertTrue(postgres.isRunning());
        System.out.println("Postgres jdbc url: " + postgres.getJdbcUrl());
        System.out.println("Postgres username: " + postgres.getUsername());
        System.out.println("Postgres password: " + postgres.getPassword());
        System.out.println("Postgres port: " + postgres.getFirstMappedPort());
        System.out.println("Postgres url: http://localhost:" + postgres.getFirstMappedPort());
        System.out.println("Postgres host: " + postgres.getHost());
        System.out.println("Postgres database: " + postgres.getDatabaseName());
        System.out.println("Postgres schema: " + postgres.getDatabaseName());
        System.out.println("Postgres url: jdbc:postgresql://" + postgres.getHost() + ":" + postgres.getFirstMappedPort() + "/" + postgres.getDatabaseName());
        System.out.println("Postgres url: jdbc:postgresql://" + postgres.getHost() + ":" + postgres.getFirstMappedPort() + "/" + postgres.getDatabaseName() + "?user=" + postgres.getUsername() + "&password=" + postgres.getPassword());
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreditoRepository creditoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @BeforeEach
    void setUp() {
        creditoRepository.deleteAll();
    }

    @Test
    void deveRetornarCreditoPorNumeroCredito() throws Exception {
        // Arrange
        Credito credito = criarCredito("CRED123", "NFE123");
        creditoRepository.save(credito);

        // Act & Assert
        mockMvc.perform(get("/api/creditos/credito/{numeroCredito}", "CRED123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCredito").value("CRED123"))
                .andExpect(jsonPath("$[0].numeroNfse").value("NFE123"))
                .andExpect(jsonPath("$[0].valorFaturado").value(credito.getValorFaturado()))
                .andExpect(jsonPath("$[0].dataConstituicao").value(credito.getDataConstituicao().toString()));
    }

    @Test
    void deveRetornarCreditoPorNumeroNfse() throws Exception {
        // Arrange
        Credito credito = criarCredito("CRED123", "NFE123");
        creditoRepository.save(credito);

        // Act & Assert
        mockMvc.perform(get("/api/creditos/{numeroNfse}", "NFE123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCredito").value("CRED123"))
                .andExpect(jsonPath("$[0].numeroNfse").value("NFE123"));
    }

    @Test
    void deveRetornarNotFoundQuandoCreditoNaoExiste() throws Exception {
        mockMvc.perform(get("/api/creditos/credito/{numeroCredito}", "INEXISTENTE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornarNotFoundQuandoNfseNaoExiste() throws Exception {
        mockMvc.perform(get("/api/creditos/{numeroNfse}", "INEXISTENTE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveEnviarMensagemParaKafkaComSucesso() throws Exception {
        // Arrange
        CreditoDTO creditoDTO = criarCreditoDTO();

        // Act & Assert
        mockMvc.perform(post("/producer/credito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditoDTO)))
                .andExpect(status().isCreated()); // alterado para 200 igual no Controller


        // Aguarda até 5 segundos para receber a mensagem
        await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    List<CreditoDTO> mensagensRecebidas = creditoConsumerTest.getMessages();
                    assertFalse(mensagensRecebidas.isEmpty(), "Nenhuma mensagem foi recebida");
                    CreditoDTO mensagemRecebida = mensagensRecebidas.get(0);
                    assertEquals(creditoDTO.getNumeroCredito(), mensagemRecebida.getNumeroCredito());
                    assertEquals(creditoDTO.getNumeroNfse(), mensagemRecebida.getNumeroNfse());
                });
    }

    private Credito criarCredito(String numeroCredito, String numeroNfse) {
        return new Credito(
                null,
                numeroCredito,
                numeroNfse,
                LocalDate.now(),
                BigDecimal.valueOf(100),
                "TIPO1",
                false,
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(1000.0),
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(1000.0)
        );
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
                BigDecimal.valueOf(1000.0),
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(1000.0)
        );
    }
}