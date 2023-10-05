# First stage: Java builder
FROM eclipse-temurin:17 AS builder

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build application
RUN ./mvnw clean install -DskipTests

# Second stage: same Java image with Maven installed
FROM eclipse-temurin:17

WORKDIR /app

# Copy Maven wrapper and pom.xml from builder stage
COPY --from=builder /app/mvnw .
COPY --from=builder /app/.mvn .mvn
COPY --from=builder /app/pom.xml .

# Copy Maven local repository from builder stage
COPY --from=builder /root/.m2 /root/.m2

# Copy script and give it appropriate permissions
COPY runTest.sh .
RUN chmod +x runTest.sh

# Copy the built application from the builder stage
RUN echo "Copying target directory from builder stage..."

COPY --from=builder /app/target /app/target