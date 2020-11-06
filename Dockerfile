FROM openjdk:8-jre-alpine

WORKDIR /wordart-gen-api-server

ENV OPENAPI_BASE_PATH=/v1

COPY ./api-server/target/api-server-0.0.1-SNAPSHOT.jar /wordart-gen-api-server/api-server.jar

EXPOSE 8080

CMD ["java", "-Dopen-api.word-art-generator.base-path=${OPENAPI_BASE_PATH}", "-jar", "/wordart-gen-api-server/api-server.jar"]