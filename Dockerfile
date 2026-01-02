# Usa uma imagem do Java com Maven para build
FROM maven:3.9.1-eclipse-temurin-17 AS build

WORKDIR /app

# Copia arquivos de configuração para download de dependências
COPY pom.xml .

# Baixa dependências (cache nisso pra builds mais rápidos)
RUN mvn dependency:go-offline -B

# Copia todo o código e faz o build
COPY src ./src
RUN mvn clean package -DskipTests

# Segunda etapa: imagem mais leve para rodar
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia o .jar gerado
COPY --from=build /app/target/*.jar app.jar

# Indica a porta que seu app usa (acho que 8080 por padrão)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
