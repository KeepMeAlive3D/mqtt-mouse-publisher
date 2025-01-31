FROM gradle:8-jdk23-alpine AS api
WORKDIR /app
COPY . .
RUN ./gradlew build -x test


FROM openjdk:23-slim-bullseye
WORKDIR /app
COPY --from=api /app/api/build/libs/mqtt-mouse-publisher-all.jar .
EXPOSE 8080
CMD ["java", "-jar", "mqtt-mouse-publisher-all.jar"]