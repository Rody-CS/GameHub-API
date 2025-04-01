# Use uma imagem base do Maven
FROM maven:3.8.4-openjdk-17-slim AS build

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie o arquivo pom.xml e o Maven Wrapper para o container
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./

# Dê permissão de execução para o Maven Wrapper (mvnw)
RUN chmod +x mvnw

# Instale as dependências do Maven e compile o projeto
RUN ./mvnw clean install

# Use uma imagem de runtime para rodar o projeto
FROM openjdk:17-slim

# Defina o diretório de trabalho
WORKDIR /app

# Copie o JAR compilado para a imagem final
COPY --from=build /app/target/*.jar app.jar

# Exponha a porta que seu aplicativo vai rodar
EXPOSE 8080

# Comando para rodar a aplicação Java
CMD ["java", "-jar", "app.jar"]
