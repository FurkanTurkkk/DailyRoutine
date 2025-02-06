FROM openjdk:23
WORKDIR /app
COPY target/Daily-Routine-0.0.1-SNAPSHOT.jar daily-routine.jar
ENTRYPOINT ["java","-jar","daily-routine.jar"]