from __future__ import print_function # In python 2.7

from flask import Flask, request
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

@app.route('/uploader', methods=[ 'POST'])
def upload():
    
    #/sparkgis/sample_data/
    loc = request.form.get('name')
    f = request.files['file']
    f.save("tmp/file")

    client_hdfs.upload(str(loc), "../web/tmp/file")
    
    return 'File uploaded at ' +  str(loc)


@app.route('/delete', methods=[ 'GET'])
def delete():
    try:
        client_hdfs.delete("/sparkgis/", True)
    except:
        pass
    try:
        client_hdfs.delete("/results/", True)
    except:
        pass

    return "Success"


@app.route('/run', methods=[ 'GET'])
def run():
    subprocess.call(['../sparkGIS/scripts/generate_heatmap.sh'])   
    return "Success"
    
    

if __name__ == "__main__":
    gunicorn_logger = logging.getLogger('gunicorn.error')
    app.logger.handlers = gunicorn_logger.handlers
    app.logger.setLevel(gunicorn_logger.level)
    #Accessible on http://192.168.99.100 for localhost docker toolbox (windows)
    app.run('0.0.0.0', 5000, debug=True)
