FROM debian:bullseye-slim

WORKDIR /app

RUN apt-get update && apt-get install -y openjdk-17-jdk maven

COPY . .

RUN mvn package -DskipTests

EXPOSE 8081

CMD ["java", "-jar", "target/consolidadodiario-0.0.1-SNAPSHOT.jar"]