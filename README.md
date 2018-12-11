**Creating Spark GIS docker image and updating Docker hub**

```
docker build . -t jaylohokare/sparkgis:0.1
docker push jaylohokare/sparkgis:0.1
```

Docker Hub - https://hub.docker.com/r/jaylohokare/sparkgis/

Using the image for hosted service (Flask server):
```
cd run
docker-compose up --build
#Try sudo docker-compose up --build if not root user on Linux docker
```

The service uses sparkgis docker image, along with hadoop (For HDFS), Spark (Master) and Spark (Worker) images

This repo contains:
1. Spark GIS (Stony Brook BMIDB project) - SparkGIS is a distributed, in-memory spatial data processing framework to query, retrieve, and compare large volumes of analytical image result data for algorithm evaluation. The framework combines the in-memory distributed processing capabilities of Apache Spark and the efficient spatial query processing of Hadoop-GIS. 
2. Spark GIS dockerfile - Dockerfile to automate installation of dependencies, building the project
3. Flask REST service that provides web-interface for SparkGIS
4. Docker compose file for starting SparkGIS, spark cluster and HDFS cluster


Open webApp/index.html to access the web interface