# Development Dockerfile for Java (Maven project)
# - Matches docker/docker-compose.yml which sets build.context to project root and mounts code at /app
# - Provides a consistent JDK environment for running Maven wrapper and the app during development

# Base JDK image (LTS)
FROM eclipse-temurin:17-jdk-jammy

# Install useful CLI tools (e.g., curl)
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
 && rm -rf /var/lib/apt/lists/*

# Working directory inside the container
WORKDIR /app

# Default command keeps the container alive for interactive dev (overridable via docker-compose)
CMD ["sleep", "infinity"]
