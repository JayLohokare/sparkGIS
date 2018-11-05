FROM java:8

ADD /pythonCode /pythonCode
WORKDIR /pythonCode

ENV SPARK_VERSION=2.3.2
ENV HADOOP_VERSION=2.7

ADD https://raw.githubusercontent.com/guilhem/apt-get-install/master/apt-get-install /usr/bin/
RUN chmod +x /usr/bin/apt-get-install

RUN apt-get-install -y curl \
      && wget http://apache.mirror.iphh.net/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz \
      && tar -xvzf spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz \
      && mv spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION} spark \
      && rm spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz \
      && cd /

RUN apt-get-install -y python3 python3-setuptools python3-pip


RUN pip3 install -r requirements.txt


CMD ["gunicorn", "--workers=2", "--bind=0.0.0.0:8000", "server:app"]

#Run docker-compose up --build