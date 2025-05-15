# Usei a base do eclipse-temurin, uma versão mais recente, que é baseada no OpenJDK e geralmente tem repositórios mais estáveis
FROM eclipse-temurin:latest

# Adiciona o volume padrão
VOLUME /tmp

# Define o argumento para o arquivo JAR
ARG JAR_FILE=target/backend-creditos-0.0.1-SNAPSHOT.jar

# Copia o JAR para a imagem
COPY ${JAR_FILE} backend-creditos.jar

# Define o entrypoint para executar a aplicação com o modo "headless" habilitado ao executar a aplicação
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar","/backend-creditos.jar"]