from __future__ import print_function # In python 2.7

from flask import Flask
import subprocess
import logging
import os


app = Flask(__name__)
app.config['DEBUG'] = True
app.url_map.strict_slashes = False

def debug(string):
    app.logger.debug (string)    

@app.route('/', methods=[ 'GET'])
def hello():
    # for root, dirs, files in os.walk("."):  
    #     for filename in files:
    #         debug(filename)

    cmd = ["ls","-l"]
    p = subprocess.Popen(cmd, stdout = subprocess.PIPE,
                            stderr=subprocess.PIPE,
                            stdin=subprocess.PIPE)
    out,err = p.communicate()
    
    out = str(out)
    out = out.split('\\n')
    for i in out:
        debug(i + '\n')
    s = ""
    return s
    

if __name__ == "__main__":
    gunicorn_logger = logging.getLogger('gunicorn.error')
    app.logger.handlers = gunicorn_logger.handlers
    app.logger.setLevel(gunicorn_logger.level)
    #Accessible on http://192.168.99.100 for localhost docker toolbox (windows)
    app.run('0.0.0.0', 8000, debug=True)
