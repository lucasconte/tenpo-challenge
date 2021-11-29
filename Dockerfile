FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} tenpo-challenge-1.0.jar
ENTRYPOINT ["java", "-jar", "/tenpo-challenge-1.0.jar"]