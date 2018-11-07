FROM ubuntu:16.04

USER root

ENV DEBIAN_FRONTEND noninteractive
ENV JAVA_HOME       /usr/lib/jvm/java-8-oracle
ENV LANG            en_US.UTF-8
ENV LC_ALL          en_US.UTF-8
ENV SPARK_VERSION=2.3.2
ENV HADOOP_VERSION=2.7

ADD https://raw.githubusercontent.com/guilhem/apt-get-install/master/apt-get-install /usr/bin/

RUN apt-get update && \
  apt-get install -y --no-install-recommends locales && \
  locale-gen en_US.UTF-8 && \
  apt-get dist-upgrade -y && \
  apt-get --purge remove openjdk* 
RUN apt-get install gcc -y && \
  apt-get install g++ -y && \
  apt-get install openssh-server -y && \
  apt-get install openjdk-8-jdk -y && \
  apt-get install libgeos++-dev -y && \
  apt-get install libspatialindex-dev -y &&\
  apt-get install software-properties-common -y &&\
  add-apt-repository ppa:george-edison55/cmake-3.x -y &&\
  apt-get install cmake -y

WORKDIR /pythonCode
RUN apt-get install -y curl \
      && wget http://apache.mirror.iphh.net/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz \
      && tar -xvzf spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz \
      && mv spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION} spark \
      && rm spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz \
      && cd /


ENV  SPARK_HOME=/pythonCode/spark
ENV  SPGIS_INC_PATH=/usr/include
ENV  SPGIS_LIB_PATH=/usr/lib
ENV  LD_CONFIG_PATH=${LD_LIBRARY_PATH}:${SPGIS_LIB_PATH}
ENV  LD_LIBRARY_PATH=${LD_CONFIG_PATH}

WORKDIR /pythonCode      
RUN apt-get install -y python3 python3-setuptools python3-pip


WORKDIR /spark
ADD slaves conf/slaves
ADD spark-defaults.conf conf/spark-defaults.conf


ADD /sparkGIS /pythonCode/sparkGIS
RUN sh sparkGIS/deploy/setup_spatial_libs_from_source.sh -y

WORKDIR /pythonCode/sparkGIS
RUN apt-get install maven -y
ENV  JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/
ENV  JAVA_INCLUDE_DIR=/usr/lib/jvm/java-8-openjdk-amd64/include
RUN sh compile.sh

RUN apt-get install ssh
RUN ls

ADD /pythonCode /pythonCode/web/
WORKDIR /pythonCode/web
RUN pip3 install -r requirements.txt
RUN  echo "${PATH}"
CMD [ "gunicorn", "--workers=2", "--bind=0.0.0.0:5000", "server:app"]




#Run docker-compose up --build
