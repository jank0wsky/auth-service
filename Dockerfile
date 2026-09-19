#----- SERVICE NAME -----#
ARG SERVICE_NAME=auth-service

#----- BUILDER STAGE -----#
FROM maven:3.9.16-eclipse-temurin-25-alpine AS builder

# Stage prep
ARG SERVICE_NAME
WORKDIR /builder

# Prime dependency cache first for faster rebuilds
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 \
    mvn -q -DskipTests dependency:go-offline

# Build the fat jar
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -DfinalName=${SERVICE_NAME} -DskipTests clean package

# Separates .jar file and libraries
RUN java -Djarmode=tools \
    -jar target/${SERVICE_NAME}.jar \
    extract --destination layers

#----- RUN STAGE -----#
FROM eclipse-temurin:25-jre-alpine

# Stage prep
ARG SERVICE_NAME
ENV SERVICE_NAME=${SERVICE_NAME}
WORKDIR /service

# Create a secure, non-root system user
RUN addgroup -S ${SERVICE_NAME} && adduser -S ${SERVICE_NAME} -G ${SERVICE_NAME}
USER ${SERVICE_NAME}:${SERVICE_NAME}

# Copy .jar file from builder stage
COPY --from=builder --chown=${SERVICE_NAME}:${SERVICE_NAME} \
    /builder/layers/${SERVICE_NAME}.jar ./

# Copy libraries directory from builder stage
COPY --from=builder --chown=${SERVICE_NAME}:${SERVICE_NAME} \
    /builder/layers/lib ./lib

# Run the service
EXPOSE 9999
ENTRYPOINT ["sh", "-c", "exec java -jar ${SERVICE_NAME}.jar"]
