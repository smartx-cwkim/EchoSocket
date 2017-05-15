FROM ubuntu:16.04

RUN apt-get -y update
RUN apt-get -y install openjdk-8-jdk

COPY testClient.java /
COPY getRandomString.java /
COPY Intro_javaClient.sh /

RUN chmod 777 Intro_javaClient.sh

EXPOSE 12345

ENTRYPOINT ["/Intro_javaClient.sh"]
