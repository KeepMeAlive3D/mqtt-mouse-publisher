FROM gradle:8-jdk23-alpine AS api
WORKDIR /app
COPY . .
RUN ./gradlew build -x test


FROM openjdk:23-slim-bullseye
WORKDIR /app
COPY --from=api /app/api/build/libs/*all.jar ./app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]