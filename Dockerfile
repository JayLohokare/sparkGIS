FROM ubuntu:16.04

ENV DEBIAN_FRONTEND noninteractive
ENV JAVA_HOME       /usr/lib/jvm/java-8-oracle
ENV LANG            en_US.UTF-8
ENV LC_ALL          en_US.UTF-8
ENV SPARK_VERSION=2.3.2
ENV HADOOP_VERSION=2.7

ADD https://raw.githubusercontent.com/guilhem/apt-get-install/master/apt-get-install /usr/bin/


# RUN  add-apt-repository main \
#    add-apt-repository universe \
#    add-apt-repository restricted \
#    add-apt-repository multiverse
# RUN apt-get -y install sudo
# RUN adduser --disabled-password --gecos '' docker
# RUN adduser docker sudo
# RUN echo '%sudo ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers

RUN apt-get update && \
  apt-get install -y --no-install-recommends locales && \
  locale-gen en_US.UTF-8 && \
  apt-get dist-upgrade -y && \
  apt-get --purge remove openjdk* && \
  echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections && \
  echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu xenial main" > /etc/apt/sources.list.d/webupd8team-java-trusty.list && \
  apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886 && \
  apt-get update && \
  apt-get install -y --no-install-recommends oracle-java8-installer oracle-java8-set-default && \
  apt-get clean all
RUN apt-get install -y curl \
      && wget http://apache.mirror.iphh.net/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz \
      && tar -xvzf spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz \
      && mv spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION} spark \
      && rm spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz \
      && cd /

WORKDIR /pythonCode      
RUN apt-get install -y python3 python3-setuptools python3-pip

ARG SPARK_HOME=spark/
RUN echo "${SPARK_HOME}"

ADD /sparkGIS /pythonCode/sparkGIS

# USER docker
RUN sh sparkGIS/deploy/ubuntu_setup_dependencies.sh -y

ADD /pythonCode /pythonCode
RUN pip3 install -r requirements.txt
# RUN gunicorn --workers=2 --bind=0.0.0.0:8000 server:app
RUN  echo "${PATH}"
CMD [ "gunicorn", "--workers=2", "--bind=0.0.0.0:8000", "server:app"]

#Run docker-compose up --build