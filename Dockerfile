FROM ghcr.io/home-assistant/devcontainer:addons

# Install OpenJDK 17 (oder eine andere Version deiner Wahl)
RUN apt-get update && apt-get install -y openjdk-17-jdk

# Set JAVA_HOME Environment Variable
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH
