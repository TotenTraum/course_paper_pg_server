FROM openjdk:17
EXPOSE 8081:8081
RUN mkdir /app
COPY ./build/libs/*-all.jar /app/ktor-server.jar
ENTRYPOINT ["java","-jar","/app/ktor-server.jar"]