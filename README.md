*Spark GIS docker image*

```
docker build . -t jaylohokare/sparkgis:0.1
docker push jaylohokare/sparkgis:0.1
```

Docker Hub - https://hub.docker.com/r/jaylohokare/sparkgis/


Using the image for hosted service (Flask server):
```
cd run
docker-compose up --build
```

The service uses sparkgis docker image, along with hadoop (For HDFS), Spark (Master) and Spark (Worker) images
