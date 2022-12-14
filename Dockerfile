FROM maven:3.8.5-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml /app/pom.xml
RUN mvn dependency:go-offline

ENV DB_HOST=db \
    DB_NAME=name \
    DB_USER=user \
    DB_PASS=pass

COPY src /app/src
RUN mvn -Dmaven.test.skip=true clean package

# install Docker tools (cli, buildx, compose)
COPY --from=gloursdocker/docker / /
CMD ["mvn", "spring-boot:run"]

FROM builder as prepare-production
RUN mkdir -p target/dependency
WORKDIR /app/target/dependency
RUN jar -xf ../*.jar

FROM eclipse-temurin:17-jre-focal

EXPOSE 8080
ARG DEPENDENCY=/app/target/dependency
VOLUME /app/static

COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=prepare-production ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.advella.advellabackend.AdvellaBackendApplication"]
