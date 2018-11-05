from flask import Flask

app = Flask(__name__)
app.url_map.strict_slashes = False


@app.route('/', methods=[ 'GET'])
def hello():
    returnStatement = "Flask server running"
    return returnStatement


if __name__ == "__main__":

    #Accessible on http://192.168.99.100 for localhost docker toolbox (windows)
    app.run('0.0.0.0', 8000, debug=True)
