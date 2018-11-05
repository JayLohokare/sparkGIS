FROM python:3.4-alpine
ADD /pythonCode /pythonCode
WORKDIR /pythonCode

RUN pip install -r requirements.txt


CMD ["gunicorn", "--workers=2", "--bind=0.0.0.0:8000", "server:app"]