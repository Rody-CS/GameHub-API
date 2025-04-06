# Use uma imagem base do Maven para a fase de build
FROM maven:3.8.4-openjdk-17-slim AS build

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie apenas os arquivos essenciais primeiro (para cache eficiente)
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./

# Dê permissão de execução para o Maven Wrapper (mvnw)
RUN chmod +x mvnw

# Baixe as dependências do Maven antes de copiar o código-fonte (para otimizar cache)
RUN ./mvnw dependency:go-offline

# Agora copie o restante do código do projeto
COPY src ./src

# Compile o projeto ignorando testes para evitar falhas desnecessárias
RUN ./mvnw clean package -DskipTests

# Use uma imagem de runtime para rodar o projeto
FROM openjdk:17-slim

# Defina o diretório de trabalho
WORKDIR /app

# Copie o JAR compilado para a imagem final (nome exato do arquivo)
COPY --from=build /app/target/gamehub-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta que seu aplicativo vai rodar
EXPOSE 8080

# Comando para rodar a aplicação Java
CMD ["java", "-jar", "app.jar"]
