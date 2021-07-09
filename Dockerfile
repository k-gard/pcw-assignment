FROM openjdk:16-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE}  app.jar
EXPOSE 8088
ENTRYPOINT ["java","-jar","/app.jar"]
