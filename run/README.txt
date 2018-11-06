SparkGIS service run instructions

1. Install and start Docker 

2. Run command 'docker-compose up --build'
    This might take some time (Based on internet connection) - Wait for atleast 3 minutes AFTER DOWNLOADS ARE COMPLETED for all services to start

3. Open upload.html in browser (To upload sample data) -> This is interface to upload data to HDFS
   Upload files in sample_pia_data with following format:
    Select file Algo1-TCGA-02-0007-01Z-00-DX1. HDFS location "/sparkgis/sample_data/algo-v1/TCGA-02-0007-01Z-00-DX1"
    Select file Algo2-TCGA-02-0007-01Z-00-DX1. HDFS location "/sparkgis/sample_data/algo-v2/TCGA-02-0007-01Z-00-DX1"

4. http://192.168.99.100:5000/run
    (This takes some time - Wait till you see 'Success' on browser)

5. http://192.168.99.100:50070/explorer.html#/
    The results should be in results folder!

    