FROM debian:stable-slim
ENV DEBIAN_FRONTEND noninteractive

# Ensure UTF-8 locale
RUN echo "LANG=\"en_GB.UTF-8\"" > /etc/default/locale
ENV JAVA_HOME "/usr/lib/jvm/java-8-openjdk-amd64/bin/java"

RUN mkdir -p /usr/share/man/man1

RUN apt-get update \
	&& apt-get install -y \
    wget \
    imagemagick \
	fonts-freefont-ttf \
	openjdk-8-jre 
	
RUN apt-get clean autoclean \
	&& apt-get autoremove --yes \
	&& rm -rf /var/lib/{apt,dpkg,cache,log}/
	
WORKDIR /wordart-gen-queue-processor

COPY ./target/queue-processor-0.0.1-SNAPSHOT.jar /wordart-gen-queue-processor/queue-processor.jar