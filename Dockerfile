FROM ubuntu:16.04
MAINTAINER Chorwon Kim <cwkim@nm.gist.ac.kr>

RUN apt-get update
RUN apt-get install -y python-software-properties software-properties-common
RUN add-apt-repository ppa:openjdk-r/ppa -y
RUN apt-get update
RUN apt-get -y install openjdk-7-jdk

COPY testClient.java /
COPY getRandomString.java /
COPY Intro_javaClient.sh /

RUN chmod 777 Intro_javaClient.sh

EXPOSE 12345

ENTRYPOINT ["/Intro_javaClient.sh"]
