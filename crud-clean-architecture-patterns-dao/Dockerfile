# Use a imagem base do OpenJDK
FROM openjdk:17-jdk-alpine

# Copie o JAR construído para o contêiner
COPY target/crud-patterns-dao-0.0.1-SNAPSHOT.jar /app.jar

WORKDIR /app

# Defina o comando de inicialização
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Exponha a porta da aplicação
EXPOSE 8080


