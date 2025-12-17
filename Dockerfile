FROM ghcr.io/home-assistant/devcontainer:2-addons

RUN apt-get update \
	&& apt-get install -y ca-certificates curl gnupg \
	&& install -d /etc/apt/keyrings \
	&& curl -fsSL https://packages.adoptium.net/artifactory/api/gpg/key/public \
		| gpg --dearmor -o /etc/apt/keyrings/adoptium.gpg \
	&& echo "deb [signed-by=/etc/apt/keyrings/adoptium.gpg] https://packages.adoptium.net/artifactory/deb $(. /etc/os-release && echo \"$VERSION_CODENAME\") main" \
		> /etc/apt/sources.list.d/adoptium.list \
	&& apt-get update \
	&& apt-get install -y temurin-25-jdk \
	&& apt-get install -y maven \
	&& apt-get purge -y --auto-remove gnupg \
	&& rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/temurin-25-jdk-arm64
ENV PATH="$JAVA_HOME/bin:$PATH"
