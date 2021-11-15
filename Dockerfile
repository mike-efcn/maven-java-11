FROM eclipse-temurin:11.0.13_8-jdk-alpine as build
WORKDIR /service
COPY ./service/.mvn/ ./.mvn/
COPY ./service/mvnw* ./service/pom.xml ./
RUN ./mvnw verify clean --fail-never
COPY ./service/src/ ./src/
RUN ./mvnw package -Dmaven.test.skip

FROM eclipse-temurin:11.0.13_8-jre-alpine
WORKDIR /app
COPY --from=build /service/target/*.jar ./app.jar
CMD ["java", "-jar", "app.jar"]
