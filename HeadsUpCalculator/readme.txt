# Build the container using the Dockerfile in the current directory
docker build -t ustadji/headsupcalculator .

# Launch tomcat mapping the docker internal port 8080 to the external port 8888 with the docker image name
docker run -it --rm -p 8888:8080 ustadji/headsupcalculator

# Url:
http://localhost:8888/HeadsUpCalculator/
