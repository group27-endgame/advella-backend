FROM maven:3.8.5-eclipse-temurin-17 AS builder
WORKDIR /server
COPY pom.xml /server/pom.xml
RUN mvn dependency:go-offline

ENV DB_HOST=db \
    DB_NAME=name \
    DB_USER=user \
    DB_PASS=pass

COPY src /server/src
RUN mvn -Dmaven.test.skip=true clean package

# install Docker tools (cli, buildx, compose)
COPY --from=gloursdocker/docker / /
CMD ["mvn", "spring-boot:run"]

FROM builder as prepare-production
RUN mkdir -p target/dependency
WORKDIR /server/target/dependency
RUN jar -xf ../*.jar

RUN |
    rm -rf /app/application.properties
    rm -rf /app/com
    rm -rf /app/lib
    rm -rf /app/META-INF

FROM eclipse-temurin:17-jre-focal

EXPOSE 8080
ARG DEPENDENCY=/server/target/dependency
VOLUME /app
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=prepare-production ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.advella.advellabackend.AdvellaBackendApplication"]
