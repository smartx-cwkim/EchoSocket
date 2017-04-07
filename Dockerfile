FROM ubuntu:16.04
MAINTAINER Chorwon Kim <cwkim@nm.gist.ac.kr>

RUN apt-get update
RUN apt-get install -y python-software-properties software-properties-common
RUN add-apt-repository ppa:webupd8team/java -y
RUN apt-get update
RUN echo "oracle-java7-installer shared/accepted-oracle-license-v1-1 boolean true" | debconf-set-selections
RUN apt-get -y install oracle-java7-installer

COPY testClient.java /
COPY getRandomString.java /
COPY Intro_javaClient.sh /

RUN chmod 777 Intro_javaClient.sh

EXPOSE 12345

ENTRYPOINT ["/Intro_javaClient.sh"]
