#
# Build stage
#
FROM maven:3.9.12-eclipse-temurin-11 AS build
COPY src /build/src
COPY pom.xml /build/
RUN mvn -f /build/pom.xml clean package -DskipTests

#
# Package stage
#
FROM eclipse-temurin:11-jdk
COPY --from=build /build/target/trading-1.0-SNAPSHOT.jar /usr/local/app/trading/lib/trading_app.jar
ENTRYPOINT ["java","-jar","/usr/local/app/trading/lib/trading_app.jar", "--server.port=5000"]
