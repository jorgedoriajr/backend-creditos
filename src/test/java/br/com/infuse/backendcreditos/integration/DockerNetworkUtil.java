package br.com.infuse.backendcreditos.integration;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.testcontainers.containers.Network;

public class DockerNetworkUtil {

    public static Network useExistingNetworkByName(String networkName) {
        return new Network() {
            @Override
            public Statement apply(Statement statement, Description description) {
                return null;
            }

            @Override
            public String getId() {
                return networkName;
            }

            @Override
            public void close() {
                // Não faz nada — não queremos que o Testcontainers feche essa rede
            }
        };
    }
}