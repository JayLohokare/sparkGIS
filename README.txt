#Creating the SparkGIS+Flask image from project
docker build . -t jaylohokare/sparkgis:0.1
docker push jaylohokare/sparkgis:0.1

#Run SparkGIS + Flask + Flask (Using previously created image)
cd run
docker-compose up --build


Following was changed:
1. conf/sparkgis.properies
2. deploy/setup_spatial_libs_from_source.sh (Added -y)