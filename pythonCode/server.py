from __future__ import print_function # In python 2.7

from flask import Flask
import subprocess
import logging
import os
from hdfs import InsecureClient
import shutil


app = Flask(__name__)
app.config['DEBUG'] = True
app.url_map.strict_slashes = False

hdfsLocation = "hdfs:50070"
client_hdfs = InsecureClient('http://'+ hdfsLocation)

def debug(string):
    app.logger.debug (string)    

@app.route('/', methods=[ 'GET'])
def hello():
    client_hdfs.delete("/sparkgis/", True)
    client_hdfs.delete("/results/", True)
    client_hdfs.upload("/sparkgis/sample_data/algo-v1/TCGA-02-0007-01Z-00-DX1", "../sparkGIS/deploy/sample_pia_data/Algo1-TCGA-02-0007-01Z-00-DX1")
    client_hdfs.upload("/sparkgis/sample_data/algo-v2/TCGA-02-0007-01Z-00-DX1", "../sparkGIS/deploy/sample_pia_data/Algo2-TCGA-02-0007-01Z-00-DX1") 

    subprocess.call(['../sparkGIS/scripts/generate_heatmap.sh'])
    
    client_hdfs.download('/results/', '/tmp', overwrite=True, n_threads=1)
    debug("*********************************************************************************************************")
    shutil.make_archive('results.zip', 'zip', '/tmp')
    
    try:
        return send_from_directory(directory='/', filename='results.zip')
    except Exception as e:
        self.log.exception(e)
        self.Error(400)
    

if __name__ == "__main__":
    gunicorn_logger = logging.getLogger('gunicorn.error')
    app.logger.handlers = gunicorn_logger.handlers
    app.logger.setLevel(gunicorn_logger.level)
    #Accessible on http://192.168.99.100 for localhost docker toolbox (windows)
    app.run('0.0.0.0', 5000, debug=True)
