# Etapa 1: Build com Maven
FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

# Copiar arquivos essenciais primeiro para otimizar o cache
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copiar o restante do código e empacotar
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagem final para rodar o app
FROM openjdk:17-slim

WORKDIR /app

# Copia o JAR compilado
COPY --from=build /app/target/gamehub-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta usada pela aplicação
EXPOSE 8080

# Iniciar o app com definição de memória e leitura da variável de porta do Railway
CMD sh -c "java -Xms256m -Xmx512m -Dserver.port=\$PORT -jar app.jar"
