from flask import Flask
import subprocess

app = Flask(__name__)
app.url_map.strict_slashes = False


@app.route('/', methods=[ 'GET'])
def hello():
    cmd = ["ls","-l"]
    p = subprocess.Popen(cmd, stdout = subprocess.PIPE,
                            stderr=subprocess.PIPE,
                            stdin=subprocess.PIPE)
    out,err = p.communicate()
    return out


if __name__ == "__main__":

    #Accessible on http://192.168.99.100 for localhost docker toolbox (windows)
    app.run('0.0.0.0', 8000, debug=True)
