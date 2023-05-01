FROM openjdk:11-jdk-alpine

RUN mkdir -p /app

WORKDIR /app

COPY ./target/wsconsultarlicitacoessipac-0.0.1-SNAPSHOT.jar /app/wsconsultarlicitacoessipac-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/wsconsultarlicitacoessipac-0.0.1-SNAPSHOT.jar"]
