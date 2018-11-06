#Creating the SparkGIS+Flask image from project
docker build . -t jaylohokare/sparkgis:0.1
docker push jaylohokare/sparkgis:0.1

#Run SparkGIS + Flask + Flask (Using previously created image)
cd Run
docker-compose up --build
