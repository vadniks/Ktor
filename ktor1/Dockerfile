FROM openjdk:18.0.2-buster
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/libs/*.jar /app/ktor.jar
ENTRYPOINT ["java","-jar","/app/ktor.jar"]